package org.mashti.nevis;

import org.mashti.nevis.element.Link;
import org.mashti.nevis.element.Node;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public interface Parser {

    Node parse();

    void parseBlock(Node parent, String value);

    void parseInline(Node parent, String value);

    Link referenceLink(String id, Link link);

    Link getLinkReference(String id);
    
}
