package projects.game.solarsystem;

import java.util.ArrayList;

/**
 * Created by Luecx on 20.01.2017.
 */
public class SolarSystem {

    private Celestial center;

    public ArrayList<Celestial> suns = new ArrayList<>();

    public SolarSystem(double massSun1, double massSun2,double distSun1){
        double centerMass = massSun1 + massSun2;
        double distSun2 = Calculus.calculateRadius(massSun1, massSun2, distSun1);
        Celestial sun1 = new Celestial(massSun1, centerMass, distSun1, 0);
        Celestial sun2 = new Celestial(massSun2, centerMass, distSun2, 0);
        double circulationTime = Calculus.calculateCirculationTime(massSun1,centerMass,distSun1 + distSun2);
        sun1.setCirculationTime(circulationTime);
        sun2.setCirculationTime(circulationTime);

        center = new Celestial(centerMass, centerMass, 0, 0);
        sun1.setParent(center);
        sun2.setParent(center);
        sun1.getConnection().increaseRotation(0,180,0);
        suns.add(sun1);
        suns.add(sun2);
    }

    public Celestial addPlanet(double mass, double dist, double rotationTime){
       return center.addSubObject(mass, dist, rotationTime);
    }

    public Celestial getCenter() {
        return center;
    }

    public ArrayList<Celestial> getPlanets() {
        return this.center.subObjects;
    }

    public void update(double days){
        for(Celestial sun:suns){
            sun.update(days);
        }
        center.updateAllChilds(days);
    }

}
