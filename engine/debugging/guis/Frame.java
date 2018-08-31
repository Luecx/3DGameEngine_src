/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.debugging.guis;

import engine.linear.entities.Entity;

import java.awt.BorderLayout;
import java.util.HashMap;
import javax.swing.*;

/**
 *
 * @author finne
 */
public class Frame extends JFrame{

    private Panel transformWindow;

    public Frame() {
        this.setDefaultCloseOperation(3);
        this.setLayout(new BorderLayout());
        
        this.setSize(1380,720);
        transformWindow = new Panel(this);
        this.add(transformWindow, BorderLayout.CENTER);
        
        this.setVisible(true);
    }

    private HashMap<String, Entity> entityHashMap = new HashMap<>();

    public void addEntity(String arg, Entity e) {
        entityHashMap.put(arg, e);
        final String[] s = entityHashMap.keySet().toArray(new String[]{});
        transformWindow.jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = s;
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        try{
            transformWindow.jList1.setSelectedIndex(0);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void removeEntity(String arg) {
        entityHashMap.remove(arg);
        final String[] s = (String[]) entityHashMap.keySet().toArray(new String[]{});
        transformWindow.jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = s;
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        try{
            transformWindow.jList1.setSelectedIndex(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    Entity getCurrentEntity() {
        return entityHashMap.get(transformWindow.jList1.getSelectedValue());
    }

    public JPanel getTransformWindow() {
        return transformWindow;
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame();
            }
        });
    }    
}
