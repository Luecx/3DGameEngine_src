
package engine.debugging.entityeditor.gui.tree;


import engine.core.components.Group;

import javax.swing.*;

public class EntityPanel extends javax.swing.JPanel {

    private Group coreGroup = new Group();


    public EntityPanel() {
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        add_button = new javax.swing.JButton();
        group_button = new javax.swing.JButton();
        remove_button = new javax.swing.JButton();
        collapse_button = new javax.swing.JButton();
        expand_button = new javax.swing.JButton();
        treeDragAndDrop1 = new TreeDragAndDrop();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(400, 30));
        jPanel1.setLayout(new java.awt.GridLayout(1, 4));

        add_button.setBackground(new java.awt.Color(102, 102, 102));
        add_button.setText("Add");
        add_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_buttonActionPerformed(evt);
            }
        });
        jPanel1.add(add_button);

        group_button.setBackground(new java.awt.Color(102, 102, 102));
        group_button.setText("Group");
        group_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                group_buttonActionPerformed(evt);
            }
        });
        jPanel1.add(group_button);

        remove_button.setBackground(new java.awt.Color(102, 102, 102));
        remove_button.setText("Remove");
        remove_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remove_buttonActionPerformed(evt);
            }
        });
        jPanel1.add(remove_button);

        collapse_button.setBackground(new java.awt.Color(102, 102, 102));
        collapse_button.setText("Collapse");
        collapse_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collapse_buttonActionPerformed(evt);
            }
        });
        jPanel1.add(collapse_button);

        expand_button.setBackground(new java.awt.Color(102, 102, 102));
        expand_button.setText("Expand");
        expand_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expand_buttonActionPerformed(evt);
            }
        });
        jPanel1.add(expand_button);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        treeDragAndDrop1.setBackground(new java.awt.Color(102, 102, 102));
        treeDragAndDrop1.setForeground(new java.awt.Color(255, 255, 255));
        add(treeDragAndDrop1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>                        

    private void expand_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void collapse_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void remove_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void group_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void add_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          


    private javax.swing.JButton add_button;
    private javax.swing.JButton collapse_button;
    private javax.swing.JButton expand_button;
    private javax.swing.JButton group_button;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton remove_button;
    private TreeDragAndDrop treeDragAndDrop1;
}
