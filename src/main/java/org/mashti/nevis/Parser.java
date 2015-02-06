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
package org.mashti.nevis;

import org.mashti.nevis.element.LinkDefinition;
import org.mashti.nevis.element.Node;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public interface Parser {

    Node parse();

    void parseBlock(Node parent, String value);

    void parseInline(Node parent, String value);
}
