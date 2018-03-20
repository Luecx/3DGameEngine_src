package engine.debugging.entityeditor.gui.tree.model;

import engine.core.components.Group;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * Created by finne on 15.03.2018.
 */
public class EntityTreeModel implements TreeModel {

    EntityTreeNode root = new EntityTreeNode();

    @Override
    public EntityTreeNode getRoot() {
        return root;
    }

    @Override
    public EntityTreeNode getChild(Object parent, int index) {
        return ((EntityTreeNode)parent).getChildAt(index);
    }

    @Override
    public int getChildCount(Object parent) {
        return ((EntityTreeNode)parent).getChildCount();
    }

    @Override
    public boolean isLeaf(Object node) {
        return ((EntityTreeNode)node).isLeaf();
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return ((EntityTreeNode)parent).getIndex((EntityTreeNode)child);
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {

    }
}
