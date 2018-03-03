/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projects.newgame.logic.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class IPWindow extends JDialog {

    private String returnIP;
    private String returnName;
    
    public IPWindow(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(null,null);
            }
        });
        
        this.cancelButton.requestFocusInWindow();
    }

    private void initComponents() {

        okButton = new JButton();
        cancelButton = new JButton();
        hintTextField1 = new HintTextField("IP");
        hintTextField2 = new HintTextField("User name");

        setAlwaysOnTop(true);
        setAutoRequestFocus(false);
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setText("OK");
        okButton.requestFocus();
        okButton.grabFocus();
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        hintTextField1.setFont(new Font("Arial", Font.BOLD, 32));
        hintTextField1.setFocusable(true);
        hintTextField1.setForeground(Color.gray);
        hintTextField1.setHorizontalAlignment(JTextField.CENTER);

        hintTextField2.setFont(new Font("Arial", Font.BOLD, 32));
        hintTextField2.setFocusable(true);
        hintTextField2.setForeground(Color.gray);
        hintTextField2.setHorizontalAlignment(JTextField.CENTER);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(hintTextField1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(okButton, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                    .addComponent(hintTextField2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hintTextField2, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hintTextField1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        getRootPane().setDefaultButton(okButton);

        pack();
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        doClose(this.hintTextField2.getText(), this.hintTextField1.getText());
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        doClose(null, null);
    }

    private void closeDialog(java.awt.event.WindowEvent evt) {
        doClose(null,null);
    }

    private void doClose(String name, String ip) {
        returnIP = ip;
        returnName = name;
        setVisible(false);
        dispose();
    }

    public String getReturnName() {
        return returnName;
    }

    public String getReturnIP() {
        return returnIP;
    }



    private JButton cancelButton;
    private HintTextField hintTextField1;
    private HintTextField hintTextField2;
    private JButton okButton;

}
