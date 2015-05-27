package org.mashti.nevis;

import org.mashti.nevis.element.Node;
import org.mashti.nevis.processor.Processor;

/**
 * @author masih
 */
public interface Context {
    
    Processor[] getProcessors();
    
    
    Node getParent();

}
