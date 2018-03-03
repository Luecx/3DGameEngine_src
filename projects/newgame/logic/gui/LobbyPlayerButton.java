
package projects.newgame.logic.gui;

import javax.swing.*;
import java.awt.*;

public class LobbyPlayerButton extends JButton {

    public LobbyPlayerButton() {
        initComponents();
    }

    private void initComponents() {

        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();

        setLayout(new GridLayout(1, 3, 0, 10));

        jLabel1.setFont(new Font("Arial", Font.BOLD, 28));
        jLabel1.setFocusable(true);
        jLabel1.setOpaque(false);
        jLabel1.setHorizontalAlignment(JTextField.CENTER);
        add(jLabel1);

        jLabel2.setFont(new Font("Arial", Font.BOLD, 28));
        jLabel2.setFocusable(true);
        jLabel2.setOpaque(false);
        jLabel2.setHorizontalAlignment(JTextField.CENTER);
        add(jLabel2);

        jLabel3.setFont(new Font("Arial", Font.BOLD, 28));
        jLabel3.setFocusable(true);
        jLabel3.setOpaque(false);
        jLabel3.setHorizontalAlignment(JTextField.CENTER);
        add(jLabel3);
    }

    public JLabel getjLabel1() {
        return jLabel1;
    }

    public JLabel getjLabel2() {
        return jLabel2;
    }

    public JLabel getjLabel3() {
        return jLabel3;
    }




    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
}
