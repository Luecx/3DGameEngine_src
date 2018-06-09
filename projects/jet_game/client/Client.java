package projects.jet_game.client;

import engine.core.master.DisplayManager;
import engine.core.master.RenderCore;
import engine.core.system.Sys;
import projects.jet_game.server.ServerIn;
import projects.jet_game.server.ServerOut;
import udp.client_side.UDPClient;

public class Client {

    private UDPClient<ServerOut, ServerIn> udpClient;
    private RenderCore renderCore;

    public Client(String ip, int port) {
        try {
            this.udpClient = new UDPClient<ServerOut, ServerIn>(ip, port) {
                @Override
                protected void package_received(ServerOut serverOut) {
                    Client.this.package_received(serverOut);
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.renderCore = new RenderCore() {
            @Override
            protected void onEnable() {
                World.generate_world(Client.this.udpClient == null ? 1337: Client.this.udpClient.getId());
            }

            @Override
            protected void onDisable() {

            }

            @Override
            protected void render() {
                Sys.SKYDOME_SYSTEM.render(World.player.getActiveCamera());
                Sys.ADVANCED_TERRAIN_SYSTEM.render(World.lights, World.player.activeCamera);
                Sys.NORMAL_ENTITY_SYSTEM.render(World.lights, World.player.activeCamera);
            }
        };
    }

    public Client() {
        this.renderCore = new RenderCore() {
            @Override
            protected void onEnable() {
                World.generate_world(Client.this.udpClient == null ? 1337: Client.this.udpClient.getId());
            }

            @Override
            protected void onDisable() {

            }

            @Override
            protected void render() {
                World.player.move(DisplayManager.processedFrameTime());
                Sys.SKYDOME_SYSTEM.render(World.player.getActiveCamera());
                Sys.ADVANCED_TERRAIN_SYSTEM.render(World.lights, World.player.activeCamera);
                Sys.NORMAL_ENTITY_SYSTEM.render(World.lights, World.player.activeCamera);
            }
        };
    }

    protected void package_received(ServerOut serverOut) {

    }

    public static void main(String[] args){
        Client c = new Client();
    }
}
