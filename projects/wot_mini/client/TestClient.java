package projects.wot_mini.client;

import projects.wot_mini.ServerInput;
import projects.wot_mini.ServerOutput;
import templates.gameserver_v1.UDPGameClient;

public class TestClient extends UDPGameClient<ServerInput, ServerOutput> {

    ClientCore clientCore;


    public TestClient() throws Exception {
        super("localhost", 12345);
        this.clientCore = new ClientCore(this);
    }

    @Override
    protected void package_received(ServerOutput serverOutput) {

        for(int i = 0; i < serverOutput.getIds().length; i++){
            if(serverOutput.getIds()[i] == this.getId()){
                this.clientCore.entity_group.setPosition(serverOutput.getPositions()[i]);
                this.clientCore.entity_group.setRotation(serverOutput.getRotation()[i]);
                this.clientCore.camera_group.setPosition(serverOutput.getPositions()[i]);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new TestClient();
    }



}
