package engine.debugging.entityeditor.gui.tree.model;

import engine.core.components.Group;
import engine.linear.entities.Entity;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by finne on 15.03.2018.
 */
public class EntityTreeNode extends DefaultMutableTreeNode {

    private Group content;
    private EntityTreeNode parent;
    private ArrayList<EntityTreeNode> childs = new ArrayList<>();



    public void addChild(Group g){
        EntityTreeNode node = new EntityTreeNode();
        node.content = g;
        node.parent = this;
        this.childs.add(node);
    }

    @Override
    public EntityTreeNode getChildAt(int childIndex) {
        return childs.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return childs.size();
    }

    @Override
    public EntityTreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return childs.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        if(content instanceof Entity) return false;
        return true;
    }

    @Override
    public boolean isLeaf() {
        return childs.size() == 0;
    }

    @Override
    public Enumeration children() {
        return new Enumeration() {
            int currentIndex = 0;

            @Override
            public boolean hasMoreElements() {
                return currentIndex < childs.size() - 1;
            }

            @Override
            public Object nextElement() {
                currentIndex++;
                return childs.get(currentIndex - 1);
            }
        };
    }
}
