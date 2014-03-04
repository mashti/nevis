package org.mashti.nevis.element;

/**
* @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
*/
public class List extends Node {

    private final boolean ordered;

    public List(Node parent, boolean ordered) {

        super(parent);
        this.ordered = ordered;
    }

    public boolean isOrdered() {

        return ordered;
    }
}
