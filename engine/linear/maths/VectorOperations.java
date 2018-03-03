package engine.linear.maths;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static java.lang.Math.sin;

/**
 * Created by Luecx on 21.02.2017.
 */
public abstract class VectorOperations {

    /**Returns a vector where the dot product of the input an doutput vector is
     * less than rad (in degrees). The length is equal.
     *
     * @param vec
     * @param rad
     */
    public static Vector3f randomKegelVector(Vector3f vec, float rad){
        float l = vec.length();
        float g = (float)Math.tan(Math.toRadians(rad)) * l;
        Vector3f a;
        if(vec.x == 0 && vec.y == 0){
            a = new Vector3f(g,0,0);
        }else{
            a = (Vector3f) new Vector3f(vec.y, -vec.x,0).normalise(null).scale(g);
        }
        Vector3f b = (Vector3f) Vector3f.cross(a, vec, null).normalise(null).scale(g);
        Vector2f point = randomPointOnCircle(1);
        return (Vector3f)(new Vector3f(
                vec.x + a.x * point.x + b.x * point.y,
                vec.y + a.y * point.x + b.y * point.y,
                vec.z + a.z * point.x + b.z * point.y).normalise(null).scale(l));
    }

    public static Vector2f randomPointOnCircle(float radius) {
        double x = random(-1,1);
        return new Vector2f((float)x * radius, (float)random(-Math.sqrt(1 - x * x), Math.sqrt(1 - x * x)) * radius);
    }

    public static Vector3f randomPointOnSphereSurface(float radius){
        double alpha = random(0, Math.PI);
        double beta = random(0, Math.PI * 2);
        return new Vector3f((float)(radius * sin(alpha) * Math.cos(beta)),(float)(radius * sin(alpha) * sin(beta)), (float)Math.cos(alpha) * radius);
    }

    public static double random(double lower, double upper){
        return Math.random() * (upper - lower) - upper;
    }
}
