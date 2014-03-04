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
