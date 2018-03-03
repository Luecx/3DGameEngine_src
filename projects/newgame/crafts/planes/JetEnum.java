package projects.newgame.crafts.planes;

import engine.linear.entities.Entity;
import engine.linear.entities.TexturedModel;
import engine.linear.loading.Loader;
import engine.linear.loading.OBJLoader;
import engine.linear.material.EntityMaterial;
import org.lwjgl.util.vector.Vector3f;
import parser.Node;
import parser.Parser;

/**
 * Created by Luecx on 24.02.2017.
 */
public enum JetEnum {

    F_4E_Phantom_II(
            "F_4E_Phantom_II",
            "objectWithTextures/F-4E_Phantom_II/hitbox",
            "objectWithTextures/F-4E_Phantom_II/F-4E_Phantom_II_2",
            "objectWithTextures/F-4E_Phantom_II/F-4E_Phantom_II_P01",
            "objectWithTextures/F-4E_Phantom_II/F-4E_Phantom_II_N",
            new Vector3f(0,0,0),
            400,
            800,
            40,
            180,
            40,
            70,
            new String[]{},
            new String[]{},
            new Vector3f[]{new Vector3f(0,10,20),new Vector3f(0,11,30), new Vector3f(0,4,0), new Vector3f(0,6,-20)},
            new Vector3f[]{new Vector3f(0,0,0),new Vector3f(0,0,0), new Vector3f(0,180,0), new Vector3f(0,180,0)},
            new Vector3f[]{new Vector3f(-0.7f,-0.6f,3.5f),new Vector3f(0.7f,-0.6f,3.5f)},
            new Vector3f[]{new Vector3f(0,-5,0)},
            new Vector3f[]{new Vector3f(5,0,5),new Vector3f(-5,0,5)},
            new Vector3f[]{
                    new Vector3f(0,-0.75f,-11.1f),
                    new Vector3f(6.28f,-0.38f,3),
                    new Vector3f(-6.28f,-0.38f,3),
                    new Vector3f(0,1.27f,-4.99f),
                    new Vector3f(0,3,8),
                    new Vector3f(0,-1.2f,0)});

    private String identifier;
    private Vector3f nullRotate;
    private String source_material;
    private String source_normal;
    private String source_model;
    private String source_hitbox;

    private TexturedModel texturedModel;

    private float min_speed;
    private float max_speed;
    private float speed_change;

    private float rotation_speed;
    private float gear_speed;
    private float nick_speed;

    private String[] missileTypes;
    private String[] gunTypes;
    private Vector3f[] cameraLocations;
    private Vector3f[] cameraRotations;
    private Vector3f[] turbinePositions;
    private Vector3f[] missilePositions;
    private Vector3f[] gunPositions;
    private Vector3f[] hitPosition;

    JetEnum(
            String identifier,
            String source_hitbox,
            String source_model,
            String source_material,
            String source_normal,
            Vector3f nullRotate,
            float min_speed,
            float max_speed,
            float speed_change,
            float rotation_speed,
            float gear_speed,
            float nick_speed,
            String[] missileTypes,
            String[] gunTypes,
            Vector3f[] cameraLocations,
            Vector3f[] cameraRotations,
            Vector3f[] turbinePositions,
            Vector3f[] missilePositions,
            Vector3f[] gunPositions,
            Vector3f[] hitPosition) {
        this.identifier = identifier;
        this.source_hitbox = source_hitbox;
        this.source_material = source_material;
        this.source_normal = source_normal;
        this.source_model = source_model;
        this.nullRotate = nullRotate;
        this.min_speed = min_speed;
        this.max_speed = max_speed;
        this.speed_change = speed_change;
        this.rotation_speed = rotation_speed;
        this.gear_speed = gear_speed;
        this.nick_speed = nick_speed;
        this.cameraLocations = cameraLocations;
        this.turbinePositions = turbinePositions;
        this.missilePositions = missilePositions;
        this.gunPositions = gunPositions;
        this.hitPosition = hitPosition;
        this.cameraRotations = cameraRotations;
        this.missileTypes = missileTypes;
        this.gunTypes = gunTypes;
    }

    public static void main(String[] args) {
        createParserFile();
    }

    private static void createParserFile() {

        try {
            Parser parser = new Parser();
            parser.create("res/oldproject.game/jetenum.txt");
            parser.getContent().addChild(createJetParserNode(JetEnum.F_4E_Phantom_II));
            parser.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node createJetParserNode(JetEnum jetEnum) {
        Node n = new Node(jetEnum.getIdentifier());
        Node files = new Node("files");
        files.addAttribute("source_model", jetEnum.getSource_model());
        files.addAttribute("source_texture", jetEnum.getSource_material());
        files.addAttribute("source_normal", jetEnum.getSource_normal());
        n.addChild(files);

        Node speeds = new Node("speeds");
        speeds.addAttribute("min", ""+jetEnum.getMin_speed());
        speeds.addAttribute("max", ""+jetEnum.getMax_speed());
        speeds.addAttribute("change", ""+jetEnum.getSpeed_change());
        speeds.addAttribute("rotation", ""+jetEnum.getRotation_speed());
        speeds.addAttribute("gear", ""+jetEnum.getGear_speed());
        speeds.addAttribute("nick", ""+jetEnum.getNick_speed());
        n.addChild(speeds);

        Node missiles = new Node("missiles");
        Node locations = new Node("locations");
        for(int i = 0; i < jetEnum.missilePositions.length; i++) {
            locations.addAttribute(""+i, jetEnum.missilePositions[i].getX()+" "+jetEnum.missilePositions[i].getY()+" "+jetEnum.missilePositions[i].getZ());
        }
        missiles.addChild(locations);
        Node types = new Node("types");
        for(int i = 0; i < jetEnum.missileTypes.length; i++) {
            types.addAttribute(""+i, jetEnum.missileTypes[i]);
        }
        missiles.addChild(types);
        n.addChild(missiles);

        Node guns = new Node("guns");
        Node locations1 = new Node("locations");
        for(int i = 0; i < jetEnum.gunPositions.length; i++) {
            locations1.addAttribute(""+i, jetEnum.gunPositions[i].getX()+" "+jetEnum.gunPositions[i].getY()+" "+jetEnum.gunPositions[i].getZ());
        }
        guns.addChild(locations1);
        Node types1 = new Node("types");
        for(int i = 0; i < jetEnum.gunTypes.length; i++) {
            types1.addAttribute(""+i, jetEnum.gunTypes[i]);
        }
        guns.addChild(types1);
        n.addChild(guns);

        Node other = new Node("other");
        other.addAttribute("nullRotate", jetEnum.nullRotate.getX()+" "+jetEnum.nullRotate.getY()+" "+jetEnum.nullRotate.getZ());
        Node camera = new Node("cameras");
        Node cameraLocation = new Node("cameraLocations");
        Node cameraRotation = new Node("cameraRotation");
        camera.addChild(cameraLocation);
        camera.addChild(cameraRotation);
        for(int i = 0; i < jetEnum.cameraLocations.length; i++) {
            cameraLocation.addAttribute(""+i,jetEnum.cameraLocations[i].getX()+" "+jetEnum.cameraLocations[i].getY()+" "+jetEnum.cameraLocations[i].getZ());
            cameraRotation.addAttribute(""+i,jetEnum.cameraRotations[i].getX()+" "+jetEnum.cameraRotations[i].getY()+" "+jetEnum.cameraRotations[i].getZ());
        }
        other.addChild(camera);

        Node turbines = new Node("turbineLocations");
        for(int i = 0; i < jetEnum.turbinePositions.length; i++) {
            turbines.addAttribute(""+i,jetEnum.turbinePositions[i].getX()+" "+jetEnum.turbinePositions[i].getY()+" "+jetEnum.turbinePositions[i].getZ());
        }
        other.addChild(turbines);

        Node terrainHitPositions = new Node("terrainHitPositions");
        for(int i = 0; i < jetEnum.hitPosition.length; i++) {
            terrainHitPositions.addAttribute(""+i,jetEnum.hitPosition[i].getX()+" "+jetEnum.hitPosition[i].getY()+" "+jetEnum.hitPosition[i].getZ());
        }
        other.addChild(terrainHitPositions);
        n.addChild(other);
        return n;
    }

    public static void generateJetsFromFile() {
        try {
            Parser parser = new Parser();
            parser.load("res/oldproject.game/jetenum.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateTexturedModel() {
        JetEnum.F_4E_Phantom_II.genTex();
    }

    private void genTex() {
        this.texturedModel = new TexturedModel(
                OBJLoader.loadOBJ(source_model, true),
                new EntityMaterial(Loader.loadTexture(source_material)));
        this.texturedModel.getMaterial().setNormalMap(Loader.loadTexture(source_normal));
        this.texturedModel.getMaterial().setUseNormalMap(true);
    }

    public Jet generatePlane(Vector3f position, Vector3f rotation) {
        if(texturedModel == null) return null;
        Entity e = new Entity(texturedModel);
        Jet j = null;
        j.setPosition(position);
        j.setRotation(rotation);
        return j;
    }

    public void setSource_material(String source_material) {
        this.source_material = source_material;
    }

    public void setSource_normal(String source_normal) {
        this.source_normal = source_normal;
    }

    public void setSource_model(String source_model) {
        this.source_model = source_model;
    }

    public TexturedModel getTexturedModel() {
        return texturedModel;
    }

    public void setTexturedModel(TexturedModel texturedModel) {
        this.texturedModel = texturedModel;
    }

    public void setMin_speed(float min_speed) {
        this.min_speed = min_speed;
    }

    public void setMax_speed(float max_speed) {
        this.max_speed = max_speed;
    }

    public void setSpeed_change(float speed_change) {
        this.speed_change = speed_change;
    }

    public void setRotation_speed(float rotation_speed) {
        this.rotation_speed = rotation_speed;
    }

    public void setGear_speed(float gear_speed) {
        this.gear_speed = gear_speed;
    }

    public void setNick_speed(float nick_speed) {
        this.nick_speed = nick_speed;
    }

    public void setCameraLocations(Vector3f[] cameraLocations) {
        this.cameraLocations = cameraLocations;
    }

    public void setTurbinePositions(Vector3f[] turbinePositions) {
        this.turbinePositions = turbinePositions;
    }

    public void setMissilePositions(Vector3f[] missilePositions) {
        this.missilePositions = missilePositions;
    }

    public void setGunPositions(Vector3f[] gunPositions) {
        this.gunPositions = gunPositions;
    }

    public String getSource_material() {
        return source_material;
    }

    public String getSource_normal() {
        return source_normal;
    }

    public String getSource_model() {
        return source_model;
    }

    public float getMin_speed() {
        return min_speed;
    }

    public float getMax_speed() {
        return max_speed;
    }

    public float getSpeed_change() {
        return speed_change;
    }

    public float getRotation_speed() {
        return rotation_speed;
    }

    public float getGear_speed() {
        return gear_speed;
    }

    public float getNick_speed() {
        return nick_speed;
    }

    public Vector3f[] getCameraLocations() {
        return cameraLocations;
    }

    public Vector3f[] getTurbinePositions() {
        return turbinePositions;
    }

    public Vector3f[] getMissilePositions() {
        return missilePositions;
    }

    public Vector3f[] getGunPositions() {
        return gunPositions;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Vector3f getNullRotate() {
        return nullRotate;
    }

    public void setNullRotate(Vector3f nullRotate) {
        this.nullRotate = nullRotate;
    }

    public Vector3f[] getCameraRotations() {
        return cameraRotations;
    }

    public void setCameraRotations(Vector3f[] cameraRotations) {
        this.cameraRotations = cameraRotations;
    }

    public Vector3f[] getHitPosition() {
        return hitPosition;
    }

    public void setHitPosition(Vector3f[] hitPosition) {
        this.hitPosition = hitPosition;
    }

    public String[] getMissileTypes() {
        return missileTypes;
    }

    public void setMissileTypes(String[] missileTypes) {
        this.missileTypes = missileTypes;
    }

    public String[] getGunTypes() {
        return gunTypes;
    }

    public void setGunTypes(String[] gunTypes) {
        this.gunTypes = gunTypes;
    }
}
