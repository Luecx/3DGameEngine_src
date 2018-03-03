package projects.newgame.logic.game;

import engine.core.components.Light;
import engine.core.master.RenderCore;
import engine.core.master.RenderSettings;
import engine.core.system.Sys;
import engine.linear.loading.Loader;
import engine.linear.material.SkydomeElement;
import engine.linear.material.TerrainMaterial;
import engine.linear.terrain.TerrainMultimapTexturePack;
import engine.linear.advancedterrain.Chunk;
import engine.linear.advancedterrain.HeightMap;
import engine.linear.advancedterrain.Terrain;
import projects.newgame.crafts.missiles.MissileEnum;
import projects.newgame.crafts.missiles.MissileSystem;
import projects.newgame.crafts.planes.Jet;
import projects.newgame.logic.client.ClientLobby;
import projects.newgame.newcrafts.control.ControlStatus;
import projects.newgame.newcrafts.control.ControlStatusPackage;
import projects.newgame.newcrafts.control.Controller;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import server.client.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by finne on 27.09.2017.
 */
public class ClientGame extends RenderCore {

    private Terrain terrain;
    private ArrayList<Light> lights = new ArrayList<>();
    private Jet jets[][];
    private projects.newgame.logic.client.Client personalClient;
    private Client TCP_CLIENT;
    private udpserver.Client UDP_CLIENT;
    private ClientLobby lobby;

    public ClientGame(Client TCP_CLIENT, udpserver.Client UDP_CLIENT, projects.newgame.logic.client.Client personalClient, ClientLobby lobby) {
        this.TCP_CLIENT = TCP_CLIENT;
        this.UDP_CLIENT = UDP_CLIENT;
        this.personalClient = personalClient;
        this.lobby = lobby;

        try {
            TCP_CLIENT.getConnection().sendMessage(InetAddress.getLocalHost());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onEnable() {

        Sys.enableAll();
        TerrainMultimapTexturePack advancedTerrainMaterial = new TerrainMultimapTexturePack(
                Loader.loadTexture("textures/heightmaps/canyonOverlay"),
                new TerrainMaterial(Loader.loadTexture("textures/colormaps/sand2"), Loader.loadTexture("textures/normalmaps/sand_NORM2")),
                new TerrainMaterial(Loader.loadTexture("textures/colormaps/sand2"), Loader.loadTexture("textures/normalmaps/sand_NORM2")),
                new TerrainMaterial(Loader.loadTexture("textures/colormaps/Craggy"), Loader.loadTexture("textures/normalmaps/stoneNormal")),
                new TerrainMaterial(Loader.loadTexture("textures/colormaps/cliffRock"), Loader.loadTexture("textures/normalmaps/cliffNormal")
                ));
        advancedTerrainMaterial.getBlackMaterial().setTextureStretch(3000);
        advancedTerrainMaterial.getRedMaterial().setTextureStretch(3000);
        advancedTerrainMaterial.getGreenMaterial().setTextureStretch(3000);
        advancedTerrainMaterial.getBlueMaterial().setTextureStretch(3000);

        SkydomeElement skydomeElement = new SkydomeElement(Loader.loadTexture("textures/colormaps/sky"));

        RenderSettings.skydome_fog = true;
        RenderSettings.skydome_follow_x_axis = true;
        RenderSettings.skydome_follow_z_axis = true;
        RenderSettings.skydome_follow_y_axis = false;
        RenderSettings.skydome_use_skysphere = false;
        RenderSettings.skydome_bounding_y_axis = -1000;
        RenderSettings.skydome_radius = 30000;

        RenderSettings.terrain_fog = true;
        RenderSettings.terrain_fog_density = 0.0002f;
        RenderSettings.terrain_fog_gradient = 3;


        terrain = new engine.linear.advancedterrain.Terrain();
        terrain.setTerrainMaterial(advancedTerrainMaterial);
        Chunk chunk = new Chunk((float)-Math.pow(2,13),(float)-Math.pow(2,13),11,8);
        HeightMap heightMap = new HeightMap(11,1);
        heightMap.setScaleFactor(1000);
        heightMap.convertImageToData("textures/heightmaps/canH");
        chunk.generateHeights(heightMap.getHeights());

        chunk.addNoise(0.7f);
        chunk.generateModelData();

        chunk.generateBlendDataFromNormals( 0,1000,0.15f,0.05f);
        chunk.createRawModelFromCollectedData();
        terrain.getChunks().add(chunk);

        lights = new ArrayList<>();
        lights.add(new Light(10000,100000,0));

        MissileSystem.enableSystem();
        projects.newgame.crafts.planes.JetEnum.generateTexturedModel();
        MissileEnum.generateAllMissiles();



    }

    private void generatePlanes() {
        jets = new Jet[lobby.getTeams()][lobby.getPlayer()];
        for(int i = 0; i < lobby.getTeams(); i++) {
            for(int n = 0; n<  lobby.getPlayer(); n++) {
                jets[i][n] = projects.newgame.crafts.planes.JetEnum.F_4E_Phantom_II.generatePlane(new Vector3f(), new Vector3f());
            }
        }
    }

    @Override
    protected void onDisable() {

    }

    private void sendKeys() {


        ControlStatusPackage controlStatusPackage = new ControlStatusPackage(new ControlStatus[]{

                new ControlStatus(Controller.CONTROL_SPEED_UP, Keyboard.isKeyDown(Keyboard.KEY_W)),
                new ControlStatus(Controller.CONTROL_SPEED_DOWN, Keyboard.isKeyDown(Keyboard.KEY_S)),
                new ControlStatus(Controller.CONTROL_GEAR_LEFT, Keyboard.isKeyDown(Keyboard.KEY_A)),
                new ControlStatus(Controller.CONTROL_GEAR_RIGHT, Keyboard.isKeyDown(Keyboard.KEY_D)),
                new ControlStatus(Controller.CONTROL_ROTATE_RIGHT, Keyboard.isKeyDown(Keyboard.KEY_RIGHT)),
                new ControlStatus(Controller.CONTROL_ROTATE_LEFT, Keyboard.isKeyDown(Keyboard.KEY_LEFT)),
                new ControlStatus(Controller.CONTROL_NICK_UP, Keyboard.isKeyDown(Keyboard.KEY_UP)),
                new ControlStatus(Controller.CONTROL_NICK_DOWN, Keyboard.isKeyDown(Keyboard.KEY_DOWN)),
                new ControlStatus(Controller.CONTROL_MISSILE_FIRE, Keyboard.isKeyDown(Keyboard.KEY_F)),
                new ControlStatus(Controller.CONTROL_GUN_FIRE, Keyboard.isKeyDown(Keyboard.KEY_SPACE))
                });

        try {
            UDP_CLIENT.send(controlStatusPackage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void render() {
        sendKeys();
//        oldproject.game.objects.plane.missile.MissileSystem.update(DisplayManager.processedFrameTime());
//        Sys.INSTANCED_ENTITY_SYSTEM.render(lights, jets[personalClient.getTeamIndex()][personalClient.getPlayerIndex()].getActiveCamera());
//        Sys.ADVANCED_TERRAIN_SYSTEM.render(lights, jets[personalClient.getTeamIndex()][personalClient.getPlayerIndex()].getActiveCamera());
//        Sys.SKYDOME_SYSTEM.render(jets[personalClient.getTeamIndex()][personalClient.getPlayerIndex()].getActiveCamera());
//        Sys.TERRAIN_SYSTEM.render(lights, jets[personalClient.getTeamIndex()][personalClient.getPlayerIndex()].getActiveCamera());
//        Sys.ENTITY_SYSTEM.render(lights, jets[personalClient.getTeamIndex()][personalClient.getPlayerIndex()].getActiveCamera());
//        Sys.NORMAL_ENTITY_SYSTEM.render(lights, jets[personalClient.getTeamIndex()][personalClient.getPlayerIndex()].getActiveCamera());
//        Sys.PARTICLE_SYSTEM.render(jets[personalClient.getTeamIndex()][personalClient.getPlayerIndex()].getActiveCamera());
    }
}
