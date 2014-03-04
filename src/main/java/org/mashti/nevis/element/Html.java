package org.mashti.nevis.element;

/**
* @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
*/
public class Html extends Node {

    private final String content;

    public Html(Node parent, String content) {

        super(parent);
        this.content = content;
    }

    public String getContent() {

        return content;
    }
}
