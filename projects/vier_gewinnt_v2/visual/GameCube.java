package projects.vier_gewinnt_v2.visual;

import engine.core.components.Group;
import engine.core.exceptions.CoreException;
import engine.core.master.Time;
import engine.core.system.Sys;
import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.gui.GuiPanel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import projects.vier_gewinnt_v2.communication.client.GameClient;
import projects.vier_gewinnt_v2.logic.GameMap;
import projects.vier_gewinnt_v2.logic.Vector3i;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by finne on 01.04.2018.
 */
public class GameCube extends GameMap {

    public static final int         STRETCH     = 6;

    //Entities + grid + gridtimer-------------------------------
    private final GameCamera        gameCamera  = new GameCamera(0, 1, 0);
    private final Time              gridTimer   = new Time();
    private boolean                 showGrid    = true;
    private Group                   group       = new Group();
    private ArrayList<Entity>       grid        = new ArrayList<>();
    private ArrayList<Entity>       entities    = new ArrayList<>();
    //Entities + grid + gridtimer-------------------------------

    //GUI------------------------------------------
    private GuiPanel                waiting;
    private GuiPanel                activeturn;
    private GuiPanel                gameOver;
    //GUI------------------------------------------

    //Preview / Move ----------------------------------------
    private GameClient              gameClient   = null;
    private Entity                  preview      = null;
    private boolean                 activeTurn   = false;
    private int                     playerID     = 0;
    private boolean                 singlePlayer = false;
    private boolean                 showPreview  = false;
    private Time                    previewTimer = new Time();
    //Preview / Move ----------------------------------------


    public GameCube(GameClient gameClient, int size, int id) {
        super(size, 1);
        this.gameClient = gameClient;
        if(this.gameClient == null) {
            this.singlePlayer = true;
        }
        this.playerID = id;
        generateGrid();
        this.setActiveTurn(false);
    }

    public  void update         (){
        gameCamera.move();
        gridUpdate();
        placementUpdate();
    }
    private void gridUpdate     (){
        if(Keyboard.isKeyDown(Keyboard.KEY_X)){
            if(gridTimer.timerIsUp()){
                gridTimer.setTimer(0.2);
                showGrid = !showGrid;
                if(showGrid){
                    for(Entity e:grid){
                        try {
                            Sys.NORMAL_ENTITY_SYSTEM.addElement(e);
                        } catch (CoreException e1) {
                            e1.printStackTrace();
                        }
                    }
                }else{
                    for(Entity e:grid){
                        Sys.NORMAL_ENTITY_SYSTEM.removeElement(e);
                    }
                }
            }
        }
    }
    private void placementUpdate(){
        if(this.activeTurn){
            Vector3f placePosition = this.closestPosition(this.gameCamera.lookAt(this.STRETCH));
            if(Mouse.isButtonDown(0) && placePosition != null && this.preview != null){
                if(this.gameClient != null && singlePlayer == false){
                    this.gameClient.sendPlacement(
                            (int)placePosition.x,
                            (int)placePosition.y,
                            (int)placePosition.z);
                }else{
                    this.place((int)placePosition.x, (int)placePosition.y, (int)placePosition.z, (int)this.playerID);
                }
                if(preview != null){
                    Sys.NORMAL_ENTITY_SYSTEM.removeElement(preview);
                    preview = null;
                    showPreview = false;
                    this.setActiveTurn(false);
                }
            }
            if(Mouse.isButtonDown(1) && previewTimer.timerIsUp()){
                previewTimer.setTimer(0.2f);
                showPreview = !showPreview;
                if(showPreview){
                    if(this.preview == null){
                        preview = Stones.getStone(this.playerID).generateEntity(
                                this.STRETCH * (placePosition.x + 0.5f),
                                this.STRETCH * (placePosition.y + 0.5f),
                                this.STRETCH * (placePosition.z + 0.5f), 1);
                        try {
                            Sys.NORMAL_ENTITY_SYSTEM.addElement(this.preview);
                        } catch (CoreException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    if(this.preview != null){
                        Sys.NORMAL_ENTITY_SYSTEM.removeElement(this.preview);
                        this.preview = null;
                    }
                }
            }

            if(placePosition != null && preview != null){
                preview.setPosition(new Vector3f(
                        this.STRETCH * (placePosition.x + 0.5f),
                        this.STRETCH * (placePosition.y + 0.5f),
                        this.STRETCH * (placePosition.z + 0.5f))
                );
            }


        }
    }

    public void setActiveTurn(boolean activeTurn) {
        if(this.singlePlayer) activeTurn = true;
        this.activeTurn = activeTurn;
        if(this.activeTurn){
            this.activeturn.setVisible(true);
            this.waiting.setVisible(false);
        }else{
            this.activeturn.setVisible(false);
            this.waiting.setVisible(true);
        }
    }

    @Override
    public void place(int x, int y, int z, int id) {
        super.place(x, y, z, id);

        System.out.println(Arrays.toString(this.getEvaluation()));

        try {
            Entity e = Stones.getStone(id).generateEntity
                    ((x + 0.5f) * STRETCH,
                            (y + 0.5f) * STRETCH,
                            (z + 0.5f) * STRETCH,
                            1);
            if(e != null) {
                Sys.NORMAL_ENTITY_SYSTEM.addElement(e);
                Sys.SHADOW_SYSTEM.addElement(e);
                this.entities.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateGrid() {
        EntityMaterial material = new EntityMaterial(Loader.loadTexture("textures/colormaps/grid1"));
        TexturedModel texturedModel = new TexturedModel(
                OBJLoader.loadOBJ("models/gridPlane", true), material);
        texturedModel.setTextureStretch(this.getSize());
        material.setTransparency(true);

        this.waiting = new GuiPanel("textures/colormaps/waiting");
        this.activeturn = new GuiPanel("textures/colormaps/move");
        this.gameOver = new GuiPanel("textures/colormaps/move");
        waiting.setLocation(new Vector2f(0.7f, 0.7f));
        waiting.setScale(new Vector2f(.25f, .25f));
        gameOver.setLocation(new Vector2f(0, .25f));
        gameOver.setScale(new Vector2f(.5f, .5f));
        activeturn.setLocation(new Vector2f(0.7f, 0.7f));
        activeturn.setScale(new Vector2f(.25f, .25f));

        gameOver.setVisible(false);
        waiting.setVisible(false);
        activeturn.setVisible(false);

        group.setScale(STRETCH, STRETCH, STRETCH);

        for (int i = 0; i <= getSize(); i++) {
            Entity a = new Entity(texturedModel);
            a.setScale(getSize(), getSize(), getSize());
            a.setPosition(0, i, 0);

            Entity b = new Entity(texturedModel);
            b.setRotation(0, 0, 90);
            b.setScale(getSize(), getSize(), getSize());
            b.setPosition(i, 0, 0);

            Entity c = new Entity(texturedModel);
            c.setRotation(-90, 0, 0);
            c.setScale(getSize(), getSize(), getSize());
            c.setPosition(0, 0, i);

            group.addChild(a);
            group.addChild(b);
            group.addChild(c);

            grid.add(a);
            grid.add(b);
            grid.add(c);


            try {
                Sys.NORMAL_ENTITY_SYSTEM.addElement(a);
                Sys.NORMAL_ENTITY_SYSTEM.addElement(b);
                Sys.NORMAL_ENTITY_SYSTEM.addElement(c);
                Sys.OVERLAY_SYSTEM.addElement(waiting);
                Sys.OVERLAY_SYSTEM.addElement(activeturn);
                Sys.OVERLAY_SYSTEM.addElement(gameOver);
            } catch (CoreException e) {
                e.printStackTrace();
            }

        }
    }

    public void showWinner() {
        ArrayList<Vector3i> vector3fs = this.getWinningPosition();
        for (Entity e : entities) {
            Vector3f r = (Vector3f) new Vector3f(e.getPosition()).scale(1f / this.STRETCH);
            r.x -= 0.5f;
            r.y -= 0.5f;
            r.z -= 0.5f;

            boolean keep = false;
            for (Vector3i v : vector3fs) {
                if (v.x == r.x && v.y == r.y && v.z == r.z) {
                    keep = true;
                }
            }
            if (keep == false) {
                Sys.NORMAL_ENTITY_SYSTEM.removeElement(e);
                Sys.SHADOW_SYSTEM.removeElement(e);
            }
        }
    }

    public Vector3f closestPosition(Vector3f current) {
        double distSquare = 1000000;
        Vector3f fav = null;

        for (int x = 0; x < this.values.length; x++) {
            for (int y = 0; y < this.values.length; y++) {
                for (int z = 0; z < this.values.length; z++) {
                    if (values[x][y][z] == -1 &&
                            (x - current.x + 0.5) * (x - current.x + 0.5) +
                                    (y - current.y + 0.5) * (y - current.y + 0.5) +
                                    (z - current.z + 0.5) * (z - current.z + 0.5) < distSquare) {
                        distSquare = (x - current.x + 0.5) * (x - current.x + 0.5) +
                                (y - current.y + 0.5) * (y - current.y + 0.5) +
                                (z - current.z + 0.5) * (z - current.z + 0.5);
                        if (fav != null) {
                            fav.x = x;
                            fav.y = y;
                            fav.z = z;
                        } else {
                            fav = new Vector3f(x, y, z);
                        }
                    }
                }
            }
        }
        return fav;
    }

    public GameCamera getGameCamera() {
        return gameCamera;
    }

    public void showGameOver() {
        gameOver.setVisible(true);
    }
}
