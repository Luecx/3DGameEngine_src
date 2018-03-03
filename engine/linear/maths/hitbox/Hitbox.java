package engine.linear.maths.hitbox;

import engine.linear.maths.Ray;
import engine.linear.maths.Triangle;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by finne on 28.09.2017.
 */
public class Hitbox {

    private Vector3f center;
    private float radius;
    private ArrayList<Triangle> triangles = new ArrayList<>();

    public Hitbox(String model, String hitbox, Vector3f center) {

        try {
            BufferedReader modelReader = new BufferedReader(new FileReader(new File(model)));
            BufferedReader hitboxReader = new BufferedReader(new FileReader(new File(model)));

            Vector3f furthest = new Vector3f(1,0,0);
            String line;
            boolean readsVertex = false;
            while((line = modelReader.readLine()) != null) {
                if(line.startsWith("v ")){
                    line.replace("  ", " ");
                    String[] values = line.split(" ");
                    readsVertex = true;
                    Vector3f vec = new Vector3f(Float.parseFloat(values[1]),Float.parseFloat(values[2]),Float.parseFloat(values[3]));
                    if(furthest.length() < vec.length()){
                        furthest = vec;
                    }
                } else{
                    if(readsVertex) {
                        break;
                    }
                }
            }
            ArrayList<Vector3f> vertices = new ArrayList<>();
            while((line = hitboxReader.readLine()) != null) {
                if(line.startsWith("v ")){
                    line.replace("  ", " ");
                    String[] values = line.split(" ");
                    Vector3f vec = new Vector3f(Float.parseFloat(values[1]),Float.parseFloat(values[2]),Float.parseFloat(values[3]));
                    vertices.add(vec);
                } else if(line.startsWith("f ")){
                    line.replace("  ", " ");
                    String[] values = line.split(" ");
                    Triangle t = new Triangle(
                            vertices.get(Integer.parseInt(values[1]) - 1),
                            vertices.get(Integer.parseInt(values[2]) - 1),
                            vertices.get(Integer.parseInt(values[3]) - 1));
                    triangles.add(t);
                }
            }
            modelReader.close();
            hitboxReader.close();

            this.radius = furthest.length();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.center = center;
    }

    public Vector3f intersectionPoint(Ray r, float min, float max) {
        float dist = Vector3f.cross(r.getDirection(), Vector3f.sub(center, r.getOrigin(),null), null).length();

        if(dist < radius) {
            float actualDist = Vector3f.sub(center, r.getOrigin(), null).length();
            if(new Vector3f(r.getDirection()).scale(max).length() < actualDist - radius ||
                    new Vector3f(r.getDirection()).scale(min).length() > actualDist + radius){
                return null;
            }else{
                Vector3f closest = null;
                for(Triangle t:triangles) {
                    Vector3f inter = t.intersectionPoint(r);
                    if(inter != null) {
                        float l = Vector3f.sub(inter, center, null).length();
                        if(l < new Vector3f(r.getDirection()).scale(max).length() &&
                                l > new Vector3f(r.getDirection()).scale(min).length()){
                            if(closest == null) {
                                closest = inter;
                            }else{
                                if(Vector3f.sub(closest, center, null).length() > Vector3f.sub(inter, center, null).length()){
                                    closest = inter;
                                }
                            }
                        }
                    }
                }
                return closest;
            }
        }else{
            return null;
        }
    }
}
