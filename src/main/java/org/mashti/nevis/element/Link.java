package org.mashti.nevis.element;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class Link extends Node {

    private final String destination;
    private String title;
    private String id;

    public Link(Node parent, String destination) {

        super(parent);
        this.destination = destination;
    }

    public String getDestination() {

        return destination;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(final String title) {

        this.title = title;
    }

    public String getId() {

        return id;
    }

    public void setId(final String id) {

        this.id = id;
    }

    public Link copy() {

        final Link copy = new Link(getParent(), getDestination());
        copy.setTitle(getTitle());
        copy.setId(getId());
        return copy;
    }
}
