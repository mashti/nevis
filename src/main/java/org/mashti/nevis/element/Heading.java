package org.mashti.nevis.element;

/**
* @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
*/
public class Heading extends Node {

    private final int level;

    public Heading(Node parent, int level) {

        super(parent);
        this.level = level;
    }

    public int getLevel() {

        return level;
    }
}
