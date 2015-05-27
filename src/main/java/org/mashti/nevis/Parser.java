/**
 * This file is part of nevis.
 * <p>
 * nevis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * nevis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with nevis.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.mashti.nevis;

import org.mashti.nevis.element.LinkDefinition;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.processor.Processor;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public abstract class Parser {

    private final Processor[] processors;

    public Parser(Processor[] processors) {

        this.processors = processors;
    }

    public Node parse(String value) {

        final Node root = new Node();
        parse(root, value);
        return root;
    }

    public void parse(Node parent, String value) {

        while (!value.isEmpty() && !Thread.currentThread().isInterrupted()) {

            boolean match = false;
            for (Processor processor : processors) {

                final String new_value = processor.process(parent, value, this);

                if (!value.equals(new_value)) {
                    value = new_value;
                    match = true;
                    break;
                }
            }

            if (!match) {
                throw new RuntimeException("no mach by any processor");
            }
        }
    }
}
