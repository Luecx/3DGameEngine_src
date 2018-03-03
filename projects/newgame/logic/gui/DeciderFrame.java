package projects.newgame.logic.gui;

import projects.newgame.logic.client.ClientLobby;
import projects.newgame.logic.server.ServerMain;
import server.client.Client;
import server.connection.Connection;
import server.entities.IncomingMessageHandler;

import javax.swing.*;

public class DeciderFrame extends JFrame {

    public DeciderFrame() {
        initComponents();
    }

    private void initComponents() {

        connectButton = new JButton();
        hostButton = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(500, 300));
        getContentPane().setLayout(new java.awt.GridLayout(1, 2));

        connectButton.setBackground(new java.awt.Color(255, 255, 255));
        connectButton.setText("Connect to Server");
        connectButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });
        getContentPane().add(connectButton);

        hostButton.setBackground(new java.awt.Color(255, 255, 255));
        hostButton.setText("Host your own Server");
        hostButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(hostButton);

        pack();
    }

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {
        IPWindow window = new IPWindow(this, false);
        window.setVisible(true);
        String ip = window.getReturnIP();
        if(ip != null) {
            try{

                final ClientLobby lobby = new ClientLobby();
                final projects.newgame.logic.client.Client client = new projects.newgame.logic.client.Client();

                Client c = new Client();
                c.addIncomingMessageHandler(new IncomingMessageHandler(){
                    @Override
                    public void incomingMessage(Connection<?> cnctn, Object o) {
                        if(o instanceof ClientLobby) {
                            lobby.load((ClientLobby) o);
                        }
                        if(o instanceof projects.newgame.logic.client.Client){
                            client.load((projects.newgame.logic.client.Client)o);
                        }
                    }
                });
                c.connect(ip, ServerMain.TCP_PORT);
                ConnectingWindow window1 = new ConnectingWindow(this, false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (window1.getReturnStatus() == 0) {
                            if(lobby.getClients() != null && client.getTeamIndex() > -1) {
                                window1.doClose(2);
                                break;
                            }
                            if(c.getConnection().isRunning() == false) {
                                window1.doClose(1);
                            }
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }).start();
                window1.setVisible(true);
                if(window1.getReturnStatus() == 1) {
                    throw new Exception();
                }

                this.setVisible(false);
                this.dispose();

                System.out.println(this.hasFocus());

                client.setName(window.getReturnName());

                LobbyWindow window2 = new LobbyWindow(c, lobby, client, ip);
                window2.setAlwaysOnTop(true);
                window2.setVisible(true);
            }catch(Exception e) {
                e.printStackTrace();
                int n = JOptionPane.showOptionDialog(this, "Oops! Something went wrong!",
                "Title", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
                null, new Object[] {"OK"}, JOptionPane.OK_OPTION);
            }
        }
    }

    public static void main(String args[]) throws Exception {

        ServerMain serverMain = new ServerMain(1,1);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DeciderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new DeciderFrame().setVisible(true);
        });
    }

    private JButton connectButton;
    private JButton hostButton;
}
