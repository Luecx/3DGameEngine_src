/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.debugging.guis;


import engine.linear.entities.Entity;

import java.util.HashMap;

public class Panel extends javax.swing.JPanel {

    Frame frame;

    public Panel(Frame f) {
        frame = f;
        initComponents();
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        jSlider1 = new javax.swing.JSlider();
        jSlider2 = new javax.swing.JSlider();
        jSlider10 = new javax.swing.JSlider();
        jSlider11 = new javax.swing.JSlider();
        jSlider12 = new javax.swing.JSlider();
        jSlider13 = new javax.swing.JSlider();
        jSlider14 = new javax.swing.JSlider();
        jSlider15 = new javax.swing.JSlider();
        jSlider16 = new javax.swing.JSlider();

        setLayout(new java.awt.BorderLayout());

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.GridLayout(3, 3));

        jSlider1.setMinorTickSpacing(1);
        jSlider1.setMajorTickSpacing(10);
        jSlider1.setMinimum(-100);
        jSlider1.setPaintLabels(true);
        jSlider1.setPaintTicks(true);
        jSlider1.setSnapToTicks(true);
        jSlider1.setValue(0);
        jSlider1.setBorder(javax.swing.BorderFactory.createTitledBorder("Translation X"));
        jSlider1.setValueIsAdjusting(true);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                updateEntity();
            }
        });
        jPanel2.add(jSlider1);

        jSlider2.setMaximum(360);
        jSlider2.setMinorTickSpacing(5);
        jSlider2.setMajorTickSpacing(30);
        jSlider2.setMinimum(0);
        jSlider2.setPaintLabels(true);
        jSlider2.setPaintTicks(true);
        jSlider2.setSnapToTicks(true);
        jSlider2.setBorder(javax.swing.BorderFactory.createTitledBorder("Rotation X"));
        jSlider2.setValueIsAdjusting(true);
        jSlider2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                updateEntity();
            }
        });
        jPanel2.add(jSlider2);

        jSlider10.setMajorTickSpacing(100);
        jSlider10.setMaximum(500);
        jSlider10.setMinorTickSpacing(25);
        jSlider10.setPaintLabels(true);
        jSlider10.setPaintTicks(true);
        jSlider10.setValue(100);
        jSlider10.setBorder(javax.swing.BorderFactory.createTitledBorder("Scale X"));
        jSlider10.setValueIsAdjusting(true);
        jSlider10.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                updateEntity();
            }
        });
        jPanel2.add(jSlider10);

        jSlider11.setMinorTickSpacing(1);
        jSlider11.setMajorTickSpacing(10);
        jSlider11.setMinimum(-100);
        jSlider11.setPaintLabels(true);
        jSlider11.setPaintTicks(true);
        jSlider11.setSnapToTicks(true);
        jSlider11.setValue(0);
        jSlider11.setBorder(javax.swing.BorderFactory.createTitledBorder("Translation Y"));
        jSlider11.setValueIsAdjusting(true);
        jSlider11.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                updateEntity();
            }
        });
        jPanel2.add(jSlider11);

        jSlider12.setMaximum(360);
        jSlider12.setMinorTickSpacing(5);
        jSlider12.setMajorTickSpacing(30);
        jSlider12.setMinimum(0);
        jSlider12.setPaintLabels(true);
        jSlider12.setPaintTicks(true);
        jSlider12.setSnapToTicks(true);
        jSlider12.setBorder(javax.swing.BorderFactory.createTitledBorder("Rotation Y"));
        jSlider12.setValueIsAdjusting(true);
        jSlider12.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                updateEntity();
            }
        });
        jPanel2.add(jSlider12);

        jSlider13.setMajorTickSpacing(100);
        jSlider13.setMaximum(500);
        jSlider13.setMinorTickSpacing(25);
        jSlider13.setPaintLabels(true);
        jSlider13.setPaintTicks(true);
        jSlider13.setValue(100);
        jSlider13.setBorder(javax.swing.BorderFactory.createTitledBorder("Scale Y"));
        jSlider13.setValueIsAdjusting(true);
        jSlider13.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                updateEntity();
            }
        });
        jPanel2.add(jSlider13);

        jSlider14.setMinorTickSpacing(1);
        jSlider14.setMajorTickSpacing(10);
        jSlider14.setMinimum(-100);
        jSlider14.setPaintLabels(true);
        jSlider14.setPaintTicks(true);
        jSlider14.setSnapToTicks(true);
        jSlider14.setValue(0);
        jSlider14.setBorder(javax.swing.BorderFactory.createTitledBorder("Translation Z"));
        jSlider14.setValueIsAdjusting(true);
        jSlider14.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                updateEntity();
            }
        });
        jPanel2.add(jSlider14);

        jSlider15.setMaximum(360);
        jSlider15.setMinorTickSpacing(5);
        jSlider15.setMajorTickSpacing(30);
        jSlider15.setMinimum(0);
        jSlider15.setPaintLabels(true);
        jSlider15.setPaintTicks(true);
        jSlider15.setSnapToTicks(true);
        jSlider15.setBorder(javax.swing.BorderFactory.createTitledBorder("Rotation Z"));
        jSlider15.setValueIsAdjusting(true);
        jSlider15.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                updateEntity();
            }
        });
        jPanel2.add(jSlider15);

        jSlider16.setMajorTickSpacing(100);
        jSlider16.setMaximum(500);
        jSlider16.setMinorTickSpacing(25);
        jSlider16.setPaintLabels(true);
        jSlider16.setPaintTicks(true);
        jSlider16.setValue(100);
        jSlider16.setBorder(javax.swing.BorderFactory.createTitledBorder("Scale Z"));
        jSlider16.setValueIsAdjusting(true);
        jSlider16.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                updateEntity();
            }
        });
        jPanel2.add(jSlider16);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }

    private void updateEntity() {
        if(frame.getCurrentEntity() != null) {
            frame.getCurrentEntity().setPosition((float)jSlider1.getValue() / 10f,(float)jSlider11.getValue()/ 10f,(float)jSlider14.getValue()/ 10f);
            frame.getCurrentEntity().setRotation(jSlider2.getValue(),jSlider12.getValue(),jSlider15.getValue());
            frame.getCurrentEntity().setScale((float)jSlider10.getValue() / 100f,(float)jSlider13.getValue()/ 100f,(float)jSlider16.getValue()/ 100f);
        }
    }

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {

    }

    javax.swing.JList<String> jList1;
     javax.swing.JPanel jPanel1;
     javax.swing.JPanel jPanel2;
     javax.swing.JScrollPane jScrollPane1;
     javax.swing.JSlider jSlider1;
     javax.swing.JSlider jSlider10;
     javax.swing.JSlider jSlider11;
     javax.swing.JSlider jSlider12;
     javax.swing.JSlider jSlider13;
     javax.swing.JSlider jSlider14;
     javax.swing.JSlider jSlider15;
     javax.swing.JSlider jSlider16;
     javax.swing.JSlider jSlider2;
}
