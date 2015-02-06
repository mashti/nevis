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
