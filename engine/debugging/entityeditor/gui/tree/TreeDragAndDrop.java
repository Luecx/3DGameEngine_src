
package engine.debugging.entityeditor.gui.tree;

import engine.core.components.Group;
import engine.debugging.entityeditor.gui.tree.model.EntityTreeModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Enumeration;
import javax.swing.DropMode;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class TreeDragAndDrop extends JPanel{
    
    private JScrollPane scrollPane;
    private JTree tree;

    public JTree getTree() {
        return tree;
    }

    public void setTree(JTree tree) {
        this.tree = tree;
    }

    public TreeDragAndDrop() {
        
        tree = new JTree();

        EntityTreeModel model = new EntityTreeModel();
        model.getRoot().addChild(new Group(0,0,0));
        model.getRoot().addChild(new Group(0,0,0));

        tree.setModel(new EntityTreeModel());
        tree.setDragEnabled(true);
        tree.setDropMode(DropMode.ON_OR_INSERT);
        tree.setTransferHandler(new TreeTransferHandler());
        tree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
        expandTree();
        
        this.setLayout(new BorderLayout());
        this.scrollPane = new JScrollPane(tree);
        this.scrollPane.setBackground(Color.gray);
        this.scrollPane.setOpaque(false);
        this.add(scrollPane, BorderLayout.CENTER);
        
    }
    
    private void expandTree() {
        DefaultMutableTreeNode root =
            (DefaultMutableTreeNode)tree.getModel().getRoot();
        Enumeration e = root.breadthFirstEnumeration();
        while(e.hasMoreElements()) {
            DefaultMutableTreeNode node =
                (DefaultMutableTreeNode)e.nextElement();
            if(node.isLeaf()) continue;
            int row = tree.getRowForPath(new TreePath(node.getPath()));
            tree.expandRow(row);
        }
    }

    private void collapseTree() {
        DefaultMutableTreeNode root =
                (DefaultMutableTreeNode)tree.getModel().getRoot();
        Enumeration e = root.breadthFirstEnumeration();
        while(e.hasMoreElements()) {
            DefaultMutableTreeNode node =
                    (DefaultMutableTreeNode)e.nextElement();
            if(node.isLeaf()) continue;
            int row = tree.getRowForPath(new TreePath(node.getPath()));
            tree.collapseRow(row);
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new TreeDragAndDrop());
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setVisible(true);
    }
}

