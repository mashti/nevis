package org.mashti.nevis.element;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class Node {

    private Node parent;
    private final List<Node> children;

    public Node(Node parent) {

        this.parent = parent;
        children = new ArrayList<Node>();
    }

    public Node getParent() {

        return parent;
    }

    public List<Node> getChildren() {

        return new CopyOnWriteArrayList<Node>(children);
    }

    public boolean addChild(Node child) {

        return children.add(child);
    }

    public void accept(Visitor visitor) {

        visitor.visit(this);
    }

    public void setParent(final Node parent) {

        this.parent = parent;
    }
}
