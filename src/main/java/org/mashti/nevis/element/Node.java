/**
 * This file is part of nevis.
 *
 * nevis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * nevis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with nevis.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.mashti.nevis.element;

import org.mashti.nevis.visitor.Visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class Node {

    private java.util.List<Node> children;
    private Map<String, String> attributes;

    public Node() {

        children = new ArrayList<>();
        attributes = new HashMap<>();
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public java.util.List<Node> getChildren() {

        return new CopyOnWriteArrayList<Node>(children);
    }

    public void setChildren(java.util.List<Node> children) {
        this.children = children;
    }

    public boolean addChild(Node child) {

        return children.add(child);
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
