package engine.linear.maths;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by finne on 28.09.2017.
 */
public class Triangle {
    Vector3f v1,v2,v3;

    public Triangle(Vector3f v1, Vector3f v2, Vector3f v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public Vector3f intersectionPoint(Ray ray) {
        if(ray.getDirection().length() != 1) {
            ray.getDirection().normalise();
        }

        Vector3f e1 = new Vector3f(
                v2.x - v1.x,
                v2.y - v1.y,
                v2.z - v1.z);
        Vector3f e2 = new Vector3f(
                v3.x - v1.x,
                v3.y - v1.y,
                v3.z - v1.z);

        Vector3f pvec = Vector3f.cross(ray.getDirection(), e2,null);

        float det = Vector3f.dot(e1, pvec);

        if (det < 0.0001f && det > -0.0001f) {
            return null;
        }

        float inv_det = 1 / det;
        Vector3f tvec = new Vector3f(
                ray.getOrigin().x - v1.x,
                ray.getOrigin().y - v1.y,
                ray.getOrigin().z - v1.z);
        float u = Vector3f.dot(tvec, pvec) * inv_det;
        if(u < 0 || u > 1) {
            return null;
        }

        Vector3f qvec = Vector3f.cross(tvec, e1,null);
        float v = Vector3f.dot(ray.getDirection(), qvec) * inv_det;
        if (v < 0 || u + v > 1) {
            return null;
        }
        float dist = Vector3f.dot(e2, qvec) * inv_det;
        if(dist <= 0.001) return null;
        return ray.calculatePosition(dist);
    }
}
