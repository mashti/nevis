/**
 * Copyright © 2014, Masih H. Derkani
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.derkani.nevis.visitor;

import org.derkani.nevis.element.*;
import org.derkani.nevis.processor.Utils;

import java.util.HashMap;

/**
 * @author Masih Hajiarab Derkani
 */
public class HtmlVisitor implements Visitor {

    private final StringBuilder html = new StringBuilder();

    private final HashMap<String, LinkDefinition> definitions = new HashMap<>();

    @Override
    public void visit(final Node root) {

        getDefinitions(root);
        for (Node node : root.getChildren()) {

            if (node instanceof Heading) {
                visit((Heading) node);
            } else if (node instanceof Text) {
                visit((Text) node);
            } else if (node instanceof Paragraph) {
                visit((Paragraph) node);
            } else if (node instanceof Bold) {
                visit((Bold) node);
            } else if (node instanceof Emphasized) {
                visit((Emphasized) node);
            } else if (node instanceof Code) {
                visit((Code) node);
            } else if (node instanceof CodeBlock) {
                visit((CodeBlock) node);
            } else if (node instanceof BreakLine) {
                visit((BreakLine) node);
            } else if (node instanceof Html) {
                visit((Html) node);
            } else if (node instanceof HorizontalRule) {
                visit((HorizontalRule) node);
            } else if (node instanceof Escaped) {
                visit((Escaped) node);
            } else if (node instanceof BlockQuote) {
                visit((BlockQuote) node);
            } else if (node instanceof LinkDefinition) {
                // do nothing
            } else if (node instanceof Link) {
                visit((Link) node);
            } else if (node instanceof List) {
                visit((List) node);
            } else if (node instanceof ListItem) {
                visit((ListItem) node);
            } else if (node instanceof Image) {
                visit((Image) node);
            }
        }
    }

    private void getDefinitions(Node root) {
        for (Node node : root.getChildren()) {
            if (node instanceof LinkDefinition) {
                LinkDefinition definition = (LinkDefinition) node;
                definitions.put(definition.getId(), definition);
            }
            getDefinitions(node);
        }
    }

    public void visit(Heading heading) {

        html.append("<h").append(heading.getLevel()).append('>');
        visit((Node) heading);
        html.append("</h").append(heading.getLevel()).append(">\n\n");
    }

    public void visit(Image image) {

        String source = image.getSource();
        if (source == null) {
            Link linkReference = definitions.get(image.getId());
            if (linkReference != null) {
                source = linkReference.getDestination();
                image.setAlt(image.getId());
                image.setTitle(linkReference.getTitle());
            } else {
                html.append(image.getMatch());
                return;
            }
        }

        html.append("<img src=\"");
        html.append(Utils.escapeHtmlAttribute(source));
        html.append("\" alt=\"");
        html.append(Utils.escapeHtmlAttribute(image.getAlt()));
        html.append('"');

        if (image.getTitle() != null) {
            html.append(" title=\"");
            html.append(Utils.escapeHtmlAttribute(image.getTitle()));
            html.append('"');
        }
        if (image.getId() != null) {

            html.append(" id=\"");
            html.append(image.getId());
            html.append('"');
        }
        html.append("/>");
    }

    public void visit(Text text) {

        final String value = text.getValue();
        html.append(Utils.escapeHtml(value));
        visit((Node) text);
    }

    public void visit(Paragraph paragraph) {

        html.append("<p>");
        visit((Node) paragraph);
        html.append("</p>\n\n");
    }

    public void visit(Bold paragraph) {

        html.append("<strong>");
        visit((Node) paragraph);
        html.append("</strong>");
    }

    public void visit(BreakLine paragraph) {

        html.append("</br>");
        visit((Node) paragraph);
    }

    public void visit(CodeBlock codeBlock) {

        html.append("<pre><code>");
        visit((Node) codeBlock);
        html.append("\n</code></pre>\n\n");

    }

    public void visit(Html codeBlock) {

        html.append(codeBlock.getContent());

        if (!codeBlock.isInline()) {
            html.append("\n\n");

        }
    }

    public void visit(HorizontalRule hr) {

        html.append("<hr />\n\n");
        visit((Node) hr);
    }

    public void visit(Code code) {

        html.append("<code>");
        visit((Node) code);
        html.append("</code>");
    }

    public void visit(BlockQuote code) {

        html.append("\n<blockquote>\n");
        visit((Node) code);
        html.append("</blockquote>\n\n");
    }

    public void visit(Emphasized emphasized) {

        html.append("<em>");
        visit((Node) emphasized);
        html.append("</em>");
    }

    public void visit(Link link) {

        String destination = link.getDestination();
        final String id = link.getId();
        if (link.getDestination() == null) {
            Link linkReference = definitions.get(id);
            if (linkReference != null) {
                destination = linkReference.getDestination();
                link.setTitle(linkReference.getTitle());
            } else {
                html.append(link.getMatch());
                return;
            }
        }

        html.append("<a href=\"");
        html.append(Utils.escapeHtmlAttribute(destination));
        html.append('"');
        if (link.getTitle() != null) {
            html.append(" title=\"");
            html.append(Utils.escapeHtmlAttribute(link.getTitle()));
            html.append('"');
        }
        html.append('>');
        visit((Node) link);
        html.append("</a>");
    }

    public void visit(Escaped emphasized) {

        visit((Node) emphasized);
    }

    public void visit(ListItem emphasized) {

        html.append("<li>");
        visit((Node) emphasized);
        html.append("</li>\n");
    }

    public void visit(List emphasized) {

        final String list_type = (emphasized.isOrdered() ? "o" : "u") + 'l';
        html.append('<').append(list_type).append(">\n");
        visit((Node) emphasized);
        html.append("</").append(list_type).append(">\n");
    }

    @Override
    public String toString() {

        return html.toString();
    }
}
