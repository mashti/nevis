package org.mashti.nevis.element;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class Text extends Node {

    private final String value;

    public Text(Node parent, String value) {

        super(parent);
        this.value = value;
    }

    public String getValue() {

        return value;
    }
}
