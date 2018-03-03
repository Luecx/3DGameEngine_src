package projects.newgame.logic.client;

import java.io.Serializable;

/**
 * Created by finne on 25.09.2017.
 */
public class Loadout implements Serializable {

    private String jet, gun, missile;

    public Loadout(Loadout loadout){
        this.jet = loadout.jet;
        this.gun = loadout.gun;
        this.missile = loadout.missile;
    }

    public Loadout(String jet, String gun, String missile) {
        this.jet = jet;
        this.gun = gun;
        this.missile = missile;
    }

    public String getJet() {
        return jet;
    }

    public void setJet(String jet) {
        this.jet = jet;
    }

    public String getGun() {
        return gun;
    }

    public void setGun(String gun) {
        this.gun = gun;
    }

    public String getMissile() {
        return missile;
    }

    public void setMissile(String missile) {
        this.missile = missile;
    }

    public String toString() {
        return jet + "  gun: " +gun + "  missile: "+ missile;
    }
}
