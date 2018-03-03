package projects.game.solarsystem;

/**
 * Created by Luecx on 20.01.2017.
 */
public class Calculus {

    public static double GRAVITATION_CONST = 6.673 * Math.pow(10,-11);
    public static double ASTRONOMIC_UNIT = 1.49597870700 * Math.pow(10,11);
    public static double SUN_MASS = 1.98892 * Math.pow(10,30);
    public static double LIGHT_YEAR = 9.4605 * Math.pow(10,15);
    public static double PARSEC = 3.2616 * LIGHT_YEAR;

    public static double calculateRadius(double mass1, double mass2, double rad1){
        return (mass1 * rad1)/mass2;
    }

    public static double calculateCirculationTime(double mass, double centerMass, double distance) {
        //System.out.println(mass + " " + centerMass + " " + distance);
        return (2 * Math.PI / (86400 * Math.sqrt(gravitationalForce(mass, centerMass, distance) / (mass * distance))));
    }

    public static double gravitationalForce(double a, double b, double dist){

        double z0 = a / (dist);
        double z1 = b / (dist);

        return GRAVITATION_CONST * z0 * z1;
    }
}
