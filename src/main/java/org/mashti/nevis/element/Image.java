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

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class Image extends Node {

    private final String source;
    private String title;
    private String alt;
    private String id;

    public Image(Node parent, String source) {

        super(parent);
        this.source = source;
    }

    public String getSource() {

        return source;
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

    public Image copy() {

        final Image copy = new Image(getParent(), getSource());
        copy.setTitle(getTitle());
        copy.setId(getId());
        return copy;
    }

    public String getAlt() {

        return alt;
    }

    public void setAlt(final String alt) {

        this.alt = alt;
    }
}
