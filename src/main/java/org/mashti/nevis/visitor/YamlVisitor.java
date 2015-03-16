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
package org.mashti.nevis.visitor;

import org.mashti.nevis.element.*;
import org.yaml.snakeyaml.Yaml;

/**
 * @author masih
 */
public class YamlVisitor implements Visitor {

    private final Yaml yaml;
    private Node node;

    public YamlVisitor() {

        yaml = new Yaml();
    }

    @Override
    public void visit(Node node) {

        this.node = node;
    }

    @Override
    public String toString() {
        return yaml.dump(node);
    }
}
