package projects.vier_gewinnt.logic.server;

import engine.core.components.Group;
import engine.core.components.PerspectiveCamera;
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
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import projects.mediavle_game.map.entities.abs.InstancedGameEntity;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by finne on 28.03.2018.
 */
public class GameCube {


    private enum Stones{

        STONE_1("models/cube","textures/colormaps/redpng"),
        STONE_2("models/cube","textures/colormaps/redpng"),
        STONE_3("models/cube","textures/colormaps/redpng"),
        STONE_4("models/cube","textures/colormaps/redpng");


        TexturedModel texturedModel;

        Stones(String obj, String texture) {
            this.texturedModel = new TexturedModel(
                    OBJLoader.loadOBJ(obj,true),
                    new EntityMaterial(Loader.loadTexture(texture))
            );
        }

        public static Stones getStone(int index){
            switch (index + 1){
                case 1: return STONE_1;
                case 2: return STONE_2;
                case 3: return STONE_3;
                case 4: return STONE_4;
            }
            return null;
        }

        public Entity generateEntity(float x, float y, float z, float scale){
            Entity e  = new Entity(texturedModel,x,y,z);
            e.setScale(scale,scale,scale);
            return e;
        }
    }

    private final GameClient gameClient;
    public final int SIZE;
    public final int STRETCH = 6;
    public final Group group;

    private int[][][] values;
    private ArrayList<Entity> entities = new ArrayList<>();
    private Entity preview;

    GuiPanel activeturn;
    GuiPanel waiting;

    GameCamera perspectiveCamera = new GameCamera(0,10,0);

    boolean activeTurn = false;
    int activeIndex = 0;
    boolean showPreview = false;
    Time previewShowTimer = new Time();

    public void setActiveTurn(boolean activeTurn, int i) {
        this.activeTurn = activeTurn;
        this.activeIndex = i;
        if(this.activeTurn){
            this.activeturn.setVisible(true);
            this.waiting.setVisible(false);
        }else{
            this.activeturn.setVisible(false);
            this.waiting.setVisible(true);
        }
    }

    public PerspectiveCamera getPerspectiveCamera() {
        return perspectiveCamera;
    }

    public void setPerspectiveCamera(GameCamera perspectiveCamera) {
        this.perspectiveCamera = perspectiveCamera;
    }

    public GameCube(int SIZE, GameClient gameClient) {
        this.SIZE = SIZE;
        this.group = new Group();
        this.gameClient = gameClient;

        values = new int[SIZE][SIZE][SIZE];
        this.reset();

        this.generateGrid();
        this.setActiveTurn(false, 0);
    }

    public void showWinner(){
        ArrayList<Vector3f> vector3fs = this.winnerWinnerChickenDinnerPositions();
        for(Entity e:entities){
            Vector3f r = (Vector3f) new Vector3f(e.getPosition()).scale(1f / this.STRETCH);
            r.x -= 0.5f;
            r.y -= 0.5f;
            r.z -= 0.5f;

            boolean keep = false;
            for(Vector3f v:vector3fs){
                if(v.x == r.x && v.y == r.y && v.z == r.z){
                    keep = true;
                }
            }
            if(keep == false){
                Sys.NORMAL_ENTITY_SYSTEM.removeElement(e);
                Sys.SHADOW_SYSTEM.removeElement(e);
            }
        }
    }

    public void reset(){
        for(Entity e:entities) {
            Sys.NORMAL_ENTITY_SYSTEM.removeElement(e);
            Sys.SHADOW_SYSTEM.removeElement(e);
        }
        entities.clear();
        values = new int[SIZE][SIZE][SIZE];
        for(int x = 0; x < this.values.length; x++){
            for(int y = 0; y < this.values.length; y++){
                for(int z = 0; z < this.values.length; z++){
                    values[x][y][z] = -1;
                }
            }
        }
    }

    public void place(int x, int y, int z, int value){
        values[x][y][z] = value;
        try {
            Entity e = Stones.getStone(value).generateEntity
                    (       (x + 0.5f) * STRETCH,
                            (y + 0.5f) * STRETCH,
                            (z + 0.5f) * STRETCH,
                            1);
            Sys.NORMAL_ENTITY_SYSTEM.addElement(e);
            Sys.SHADOW_SYSTEM.addElement(e);
            this.entities.add(e);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    private int winnerInLine(ArrayList<Vector4f> row){
        int cur = -1;
        int counter = 1;
        for(Vector4f i:row){
            if((int)i.getW() == cur && cur != -1){
                counter ++;
            }else{
                cur = (int)i.getW();
                counter = 1;
            }
            if(counter == 5){
                return cur;
            }
        }
        return -1;
    }
    private ArrayList<Vector3f> winningPositions(ArrayList<Vector4f> row){
        int cur = -1;
        int counter = 1;
        ArrayList<Vector4f> vector4fs = new ArrayList<>();
        for(Vector4f i:row){
            if((int)i.getW() == cur && cur != -1){
                counter ++;
                vector4fs.add(i);
            }else{
                vector4fs.clear();
                cur = (int)i.getW();
                vector4fs.add(i);
                counter = 1;
            }
            if(counter == 5){
                ArrayList<Vector3f> vector3fs = new ArrayList<>();
                for(Vector4f v:vector4fs){
                    vector3fs.add(new Vector3f(v.x,v.y,v.z));
                }
                return vector3fs;
            }
        }
        return null;
    }
    private void addRow(ArrayList<Vector4f> arrayList, int index){
        for(int i = 0; i < this.SIZE; i++) {
            for(int n = 0; n < this.SIZE; n++) {
                for(int k = 0; k < this.SIZE; k++){
                    if(index == 1){
                        arrayList.add(new Vector4f(i,k,n,values[i][k][n]));
                    }else if(index == 2){
                        arrayList.add(new Vector4f(i,n,k,values[i][n][k]));
                    }else{
                        arrayList.add(new Vector4f(k,i,n,values[k][i][n]));
                    }
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
        }
    }
    private void addDiagonal2DY(ArrayList<Vector4f> arrayList){
        for(int i = 0; i < this.SIZE; i++) {
            for(int j = - this.SIZE + 1; j < this.SIZE; j++){
                int x = j;
                int h = 0;
                while(h < this.SIZE){
                    if(x >= 0 && x < this.SIZE){
                        arrayList.add(new Vector4f(x,i,h,values[x][i][h]));
                    }
                    x ++;
                    h ++;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
            for(int j = 0; j < this.SIZE + this.SIZE - 1; j++){
                int x = 0;
                int h = j;
                while(x < this.SIZE){
                    if(h >= 0 && h < this.SIZE){
                        arrayList.add(new Vector4f(x,i,h,values[x][i][h]));
                    }
                    x ++;
                    h --;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
        }
    }
    private void addDiagonal2DX(ArrayList<Vector4f> arrayList){
        for(int i = 0; i < this.SIZE; i++) {
            for(int j = - this.SIZE + 1; j < this.SIZE; j++){
                int x = j;
                int h = 0;
                while(h < this.SIZE){
                    if(x >= 0 && x < this.SIZE){
                        arrayList.add(new Vector4f(i,x,h,values[i][x][h]));
                    }
                    x ++;
                    h ++;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
            for(int j = 0; j < this.SIZE + this.SIZE - 1; j++){
                int x = 0;
                int h = j;
                while(x < this.SIZE){
                    if(h >= 0 && h < this.SIZE){
                        arrayList.add(new Vector4f(i,x,h,values[i][x][h]));
                    }
                    x ++;
                    h --;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
        }
    }
    private void addDiagonal2DZ(ArrayList<Vector4f> arrayList){
        for(int i = 0; i < this.SIZE; i++) {
            for(int j = - this.SIZE + 1; j < this.SIZE; j++){
                int x = j;
                int h = 0;
                while(h < this.SIZE){
                    if(x >= 0 && x < this.SIZE){
                        arrayList.add(new Vector4f(x,h,i,values[x][h][i]));
                    }
                    x ++;
                    h ++;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
            for(int j = 0; j < this.SIZE + this.SIZE - 1; j++){
                int x = 0;
                int h = j;
                while(x < this.SIZE){
                    if(h >= 0 && h < this.SIZE){
                        arrayList.add(new Vector4f(x,h,i,values[x][h][i]));
                    }
                    x ++;
                    h --;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
        }
    }
    private void addDiagonal3D(ArrayList<Vector4f> arrayList) {
        for(int x = - (SIZE -1); x < (SIZE - 1); x++){
            for(int z = - (SIZE -1); z < (SIZE - 1); z++){
                Vector3f pos = new Vector3f(x,0,z);
                while(pos.y < SIZE){
                    if(pos.x >= 0 && pos.x < SIZE && pos.z >= 0 && pos.z < SIZE){
                        arrayList.add(new Vector4f(
                                pos.x, pos.y, pos.z,values[(int)pos.x][(int)pos.y][(int)pos.z]));
                    }
                    pos.x ++;
                    pos.y ++;
                    pos.z ++;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
        }
        for(int x = 0; x < (SIZE * 2 - 1); x++){
            for(int z = - (SIZE -1); z < (SIZE - 1); z++){
                Vector3f pos = new Vector3f(x,0,z);
                while(pos.y < SIZE){
                    if(pos.x >= 0 && pos.x < SIZE && pos.z >= 0 && pos.z < SIZE){
                        arrayList.add(new Vector4f(
                                pos.x, pos.y, pos.z,values[(int)pos.x][(int)pos.y][(int)pos.z]));
                    }
                    pos.x --;
                    pos.y ++;
                    pos.z ++;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
        }for(int x = - (SIZE -1); x < (SIZE - 1); x++){
            for(int z = 0; z < (SIZE * 2 - 1); z++){
                Vector3f pos = new Vector3f(x,0,z);
                while(pos.y < SIZE){
                    if(pos.x >= 0 && pos.x < SIZE && pos.z >= 0 && pos.z < SIZE){
                        arrayList.add(new Vector4f(
                                pos.x, pos.y, pos.z,values[(int)pos.x][(int)pos.y][(int)pos.z]));
                    }
                    pos.x ++;
                    pos.y ++;
                    pos.z --;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
        }for(int x = 0; x < (SIZE * 2 - 1); x++){
            for(int z = 0; z < (SIZE * 2 - 1); z++){
                Vector3f pos = new Vector3f(x,0,z);
                while(pos.y < SIZE){
                    if(pos.x >= 0 && pos.x < SIZE && pos.z >= 0 && pos.z < SIZE){
                        arrayList.add(new Vector4f(
                                pos.x, pos.y, pos.z,values[(int)pos.x][(int)pos.y][(int)pos.z]));
                    }
                    pos.x --;
                    pos.y ++;
                    pos.z --;
                }
                arrayList.add(new Vector4f(-1,-1,-1,-1));
            }
        }
    }
    public int winnerWinnerChickenDinner(){
        ArrayList<Vector4f> arrayList = new ArrayList<>();
        addRow(arrayList, 0);
        addRow(arrayList, 1);
        addRow(arrayList, 2);
        addDiagonal2DX(arrayList);
        addDiagonal2DY(arrayList);
        addDiagonal2DZ(arrayList);
        addDiagonal3D(arrayList);
        return winnerInLine(arrayList);
    }
    public ArrayList<Vector3f> winnerWinnerChickenDinnerPositions(){
        ArrayList<Vector4f> arrayList = new ArrayList<>();
        addRow(arrayList, 0);
        addRow(arrayList, 1);
        addRow(arrayList, 2);
        addDiagonal2DX(arrayList);
        addDiagonal2DY(arrayList);
        addDiagonal2DZ(arrayList);
        addDiagonal3D(arrayList);
        return winningPositions(arrayList);
    }

    public Vector3f closestPosition(Vector3f current) {
        double distSquare = 1000000;
        Vector3f fav = null;

        for(int x = 0; x < this.values.length; x++){
            for(int y = 0; y < this.values.length; y++){
                for(int z = 0; z < this.values.length; z++){
                    if(values[x][y][z] == -1 &&
                                    (x - current.x + 0.5) * (x - current.x + 0.5) +
                                    (y - current.y + 0.5) * (y - current.y + 0.5) +
                                    (z - current.z + 0.5) * (z - current.z + 0.5) < distSquare){
                        distSquare = (x - current.x + 0.5) * (x - current.x + 0.5) +
                                (y - current.y + 0.5) * (y - current.y + 0.5) +
                                (z - current.z + 0.5) * (z - current.z + 0.5);
                        if(fav != null){
                            fav.x = x;
                            fav.y = y;
                            fav.z = z;
                        }else{
                            fav = new Vector3f(x,y,z);
                        }
                    }
                }
            }
        }
        return fav;
    }

    public void update() {
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
        this.perspectiveCamera.move();
        if(this.activeTurn){
            Vector3f placePosition = this.closestPosition(this.perspectiveCamera.lookAt(this.STRETCH));
            if(Mouse.isButtonDown(0) && placePosition != null){
                this.gameClient.sendPlacement((int) placePosition.x, (int)placePosition.y,(int)placePosition.z);
                if(preview != null){
                    Sys.NORMAL_ENTITY_SYSTEM.removeElement(preview);
                    preview = null;
                    showPreview = false;
                    this.setActiveTurn(false,-1);
                }
            }
            if(Mouse.isButtonDown(1) && previewShowTimer.timerIsUp()){
                previewShowTimer.setTimer(0.2f);
                showPreview = !showPreview;
                if(showPreview){
                    if(this.preview == null){
                        preview = Stones.getStone(this.activeIndex).generateEntity(
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

    ArrayList<Entity> grid = new ArrayList<>();
    boolean showGrid = true;
    Time gridTimer = new Time();

    private void generateGrid() {
        EntityMaterial material = new EntityMaterial(Loader.loadTexture("textures/colormaps/grid1"));
        TexturedModel texturedModel = new TexturedModel(
                OBJLoader.loadOBJ("models/gridPlane",true), material);
        texturedModel.setTextureStretch(SIZE);
        material.setTransparency(true);

        group.setScale(STRETCH,STRETCH,STRETCH);

        this.waiting = new GuiPanel("textures/colormaps/waiting");
        this.activeturn = new GuiPanel("textures/colormaps/move");
        waiting.setLocation(new Vector2f(0.7f,0.7f));
        waiting.setScale(new Vector2f(.25f,.25f));
        activeturn.setLocation(new Vector2f(0.7f,0.7f));
        activeturn.setScale(new Vector2f(.25f,.25f));

        for(int i = 0; i <= SIZE; i++){
            Entity a = new Entity(texturedModel);
            a.setScale(SIZE,SIZE,SIZE);
            a.setPosition(0,i,0);

            Entity b = new Entity(texturedModel);
            b.setRotation(0,0,90);
            b.setScale(SIZE,SIZE,SIZE);
            b.setPosition(i,0,0);

            Entity c = new Entity(texturedModel);
            c.setRotation(-90,0,0);
            c.setScale(SIZE,SIZE,SIZE);
            c.setPosition(0,0,i);

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
            } catch (CoreException e) {
                e.printStackTrace();
            }

        }
    }
}
