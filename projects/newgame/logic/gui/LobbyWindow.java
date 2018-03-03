package projects.newgame.logic.gui;


import projects.newgame.logic.client.ClientLobby;
import projects.newgame.logic.client.Loadout;
import projects.newgame.logic.game.ClientGame;
import projects.newgame.logic.server.ServerMain;
import projects.newgame.newcrafts.jets.JetData;
import projects.newgame.newcrafts.jets.JetSystem;
import server.client.Client;
import server.connection.Connection;
import server.entities.IncomingMessageHandler;
import udpserver.Package;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LobbyWindow extends JFrame{


    public static final Color[] colors = new Color[]{Color.PINK, Color.CYAN, Color.red, Color.yellow, Color.gray};


    private projects.newgame.logic.client.Client personalClient;
    private ClientLobby data;
    private Client client;

    public LobbyWindow(Client c, ClientLobby d, projects.newgame.logic.client.Client client, String ip) {
        this.client = c;
        this.data = d;
        this.personalClient = client;
        this.personalClient.setLoadout(new Loadout("","",""));

        this.client.addIncomingMessageHandler(new IncomingMessageHandler() {
            @Override
            public void incomingMessage(Connection<?> connection, Object o) {
                if(o instanceof ClientLobby) {
                    data = (ClientLobby) o;
                    playerUpdater();
                } else if (o instanceof String[]) {
                    chatPanel.addMessage(((String[])o)[0], ((String[])o)[1]);
                } else if (o instanceof String) {
                    if(((String) o).startsWith("-start")) {
                        dispose();
                        setVisible(false);
                        udpserver.Client client1 = new udpserver.Client(ip, ServerMain.UDP_PORT) {
                            @Override
                            public void processMessage(byte[] bytes) {

                            }

                            @Override
                            public void processMessage(Package aPackage) {
                                System.out.println(aPackage);
                            }
                        };
                        ClientGame game = new ClientGame(c, client1, personalClient,  d);
                    }
                }
            }
        });
        initComponents();
        playerUpdater();

        try {
            this.client.getConnection().sendMessage(personalClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {

        this.requestFocus();

        chatPanel = new ChatPanel(this.client);
        jTabbedPane1 = new JTabbedPane();
        jPanel1 = new JPanel();
        jPanel3 = new JPanel();
        jScrollPane1 = new JScrollPane();
        planes = new JList<>();
        jScrollPane2 = new JScrollPane();
        loadout = new JList<>();
        jScrollPane3 = new JScrollPane();
        pieces = new JList<>();
        jScrollPane4 = new JScrollPane();
        overview = new JTextArea();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        playerPanels = new PlayerPanel[data.getTeams()][data.getPlayer()];
        jPanel1.setLayout(new GridLayout(data.getPlayer(), data.getTeams(), 20, 3));
        for(int n = 0; n < data.getPlayer(); n++) {
            for(int i = 0; i < data.getTeams(); i++) {
                PlayerPanel playerPanel = new PlayerPanel();
                playerPanel.getjButton1().setEnabled(false);
                playerPanel.getjButton1().setFocusPainted(false);
                playerPanel.getjButton1().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(personalClient.getLoadout().getJet().length() > 0
                                && personalClient.getLoadout().getMissile().length() > 0
                                && personalClient.getLoadout().getGun().length() > 0){
                            personalClient.setReady(!personalClient.isReady());
                            tellServerUpdatedClient();
                        }
                    }
                });
                playerPanel.setBackground(colors[i]);
                playerPanel.setOpaque(true);
                jPanel1.add(playerPanel);
                playerPanels[i][n] = playerPanel;
            }
        }

        jTabbedPane1.addTab("Players", jPanel1);
        jTabbedPane1.addTab("Chat", chatPanel);

        jPanel3.setLayout(new GridLayout(1, 4));

        ArrayList<String> planesString = new ArrayList<>();
        for(JetData d: JetSystem.jetData){
            planesString.add(d.getIdentifier());
        }
        planes.setBorder(BorderFactory.createTitledBorder("Planes"));
        planes.setModel(new DefaultListModel<String>() {
            String[] strings = planesString.toArray(new String[]{});
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        planes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        planes.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selecterUpdater();
            }
        });
        jScrollPane1.setViewportView(planes);

        jPanel3.add(jScrollPane1);

        loadout.setBorder(BorderFactory.createTitledBorder("Loadout"));
        loadout.setModel(new DefaultListModel() {
            String[] strings = { "Guns", "Missiles"};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        loadout.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        loadout.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(planes.getSelectedIndex() >= 0){
                    selecterUpdater();
                }
            }
        });
        jScrollPane2.setViewportView(loadout);

        jPanel3.add(jScrollPane2);

        pieces.setBorder(BorderFactory.createTitledBorder("Specification"));
        pieces.setModel(new DefaultListModel<String>());
        ((DefaultListModel<String>)pieces.getModel()).ensureCapacity(100);
        pieces.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pieces.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println(pieces.getSelectedValue());

                if(loadout.getSelectedValue().equals("Guns")) {
                    personalClient.getLoadout().setGun(pieces.getSelectedValue());
                }
                if(loadout.getSelectedValue().equals("Missiles")) {
                    personalClient.getLoadout().setMissile(pieces.getSelectedValue());
                }
                overviewUpdater();
            }
        });
        jScrollPane3.setViewportView(pieces);

        jPanel3.add(jScrollPane3);

        overview.setColumns(20);
        overview.setRows(5);
        overview.setBorder(BorderFactory.createTitledBorder("Overview"));
        jScrollPane4.setViewportView(overview);

        jPanel3.add(jScrollPane4);

        jTabbedPane1.addTab("Planes", jPanel3);

        getContentPane().add(jTabbedPane1, BorderLayout.CENTER);

        pack();
    }

    private void tellServerUpdatedClient() {
        try {
            client.getConnection().sendMessage(new projects.newgame.logic.client.Client(personalClient));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void overviewUpdater() {
        JetData data = JetSystem.getJet(planes.getSelectedValue());

        personalClient.getLoadout().setJet(data.getIdentifier());
        String text = data.getIdentifier() + "\n  ";
        if(loadout.getSelectedIndex() >= 0) {
                text += "missile: " + personalClient.getLoadout().getMissile() + "\n  ";
                text += "gun: " + personalClient.getLoadout().getGun() + "\n  ";
        }

        overview.setText(text);
    }

    private void selecterUpdater() {

        JetData data = JetSystem.getJet(planes.getSelectedValue());

        if(data != null) {
            ((DefaultListModel<String>)pieces.getModel()).clear();
            if(loadout.getSelectedIndex() == 0) {
                for(String s:data.getAvailableGuns()){
                    ((DefaultListModel<String>)pieces.getModel()).addElement(s);
                }
            }else if(loadout.getSelectedIndex() == 1) {
                for(String s:data.getAvailableMissiles()){
                    ((DefaultListModel<String>)pieces.getModel()).addElement(s);
                }
            }
        }
        overviewUpdater();
    }

    private void playerUpdater() {
        for(int i = 0;i < data.getTeams(); i++) {
            for(int n = 0; n<  data.getPlayer(); n++) {
                if(data.getClients()[i][n] != null) {
                    playerPanels[i][n].getjLabel2().setText(data.getClients()[i][n].getName());
                    if(data.getClients()[i][n].getLoadout() != null) {
                        playerPanels[i][n].getjLabel1().setText(data.getClients()[i][n].getLoadout().toString());
                    }else{
                        playerPanels[i][n].getjLabel1().setText("");
                    }
                    if(i == personalClient.getTeamIndex() && n == personalClient.getPlayerIndex()) {
                        playerPanels[i][n].getjButton1().setEnabled(true);
                    }
                    if(data.getClients()[i][n].isReady()){
                        playerPanels[i][n].getjButton1().setText("Ready");
                        playerPanels[i][n].getjPanel1().setBackground(Color.green);
                    }else{
                        playerPanels[i][n].getjButton1().setText("Not Ready");
                        playerPanels[i][n].getjPanel1().setBackground(Color.red);
                    }
                    playerPanels[i][n].getjPanel1().setBorder(new LineBorder(Color.black, 4));
                    playerPanels[i][n].setBackground(colors[i]);
                }else{
                    playerPanels[i][n].getjButton1().setText("");
                    playerPanels[i][n].getjLabel2().setText("");
                    playerPanels[i][n].getjLabel1().setText("");
                    playerPanels[i][n].setBackground(Color.lightGray);
                    playerPanels[i][n].setBorder(new LineBorder(Color.black, 0));
                    playerPanels[i][n].getjPanel1().setBackground(Color.gray);
                    playerPanels[i][n].setBackground(Color.lightGray);
                }
            }
        }
        revalidate();
    }

    private PlayerPanel[][] playerPanels;
    private JPanel jPanel1;
    private JPanel jPanel3;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JTabbedPane jTabbedPane1;
    private JList<String> loadout;
    private JTextArea overview;
    private JList<String> pieces;
    private JList<String> planes;
    private ChatPanel chatPanel;
}
