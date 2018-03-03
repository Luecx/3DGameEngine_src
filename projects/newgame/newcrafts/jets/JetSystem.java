package projects.newgame.newcrafts.jets;

import parser.Node;
import parser.Parser;

import java.util.ArrayList;

/**
 * Created by finne on 29.09.2017.
 */
public abstract class JetSystem {

    public final static ArrayList<JetData> jetData = new ArrayList<>();

    static{
        try {
            Parser p = new Parser();
            p.load("res/oldproject.game/jetenum.txt");
            for(Node n:p.getContent().getChilds()){
                jetData.add(new JetData(n));
                System.out.println(jetData.get(jetData.size()-1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static ServerJet SERVER_generate_plane(String name, String missile, String gun) {
        for(JetData j:jetData) {
            if(j.identifier.equals(name)){
                for(String s:j.getAvailableMissiles()){
                    if(s.equals(missile)){
                        for(String s2:j.getAvailableGuns()) {
                            if(s2.equals(gun)){
                                ServerJet jet = new ServerJet(j);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static ClientJet CLIENT_generate_plane(String name, String missile, String gun) {
        for(JetData j:jetData) {
            if(j.identifier.equals(name)){
                for(String s:j.getAvailableMissiles()){
                    if(s.equals(missile)){
                        for(String s2:j.getAvailableGuns()) {
                            if(s2.equals(gun)){
                                if(j.getTexturedModel() == null) {
                                    j.generateTexturedModel();
                                }
                                ClientJet jet = new ClientJet(j);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static JetData getJet(String jet) {
        for(JetData j:jetData) {
            if(j.getIdentifier().equals(jet)){
                return j;
            }
        }
        return null;
    }

    public static void main(String[] args) {

    }

}
