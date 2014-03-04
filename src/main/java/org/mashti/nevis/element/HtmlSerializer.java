package org.mashti.nevis.element;

import org.mashti.nevis.Parser;
import org.mashti.nevis.processor.Utils;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class HtmlSerializer implements Visitor {

    private final StringBuilder html = new StringBuilder();
    private final Parser parser;

    public HtmlSerializer(Parser parser) {

        this.parser = parser;
    }

    @Override
    public void visit(final Node node) {

        for (Node node1 : node.getChildren()) {
            if (node1 instanceof Heading) {
                Heading heading = (Heading) node1;
                visit(heading);
            }
            else if (node1 instanceof Text) {
                Text text = (Text) node1;
                visit(text);
            }
            else if (node1 instanceof Paragraph) {
                Paragraph text = (Paragraph) node1;
                visit(text);
            }
            else if (node1 instanceof Bold) {
                Bold text = (Bold) node1;
                visit(text);
            }
            else if (node1 instanceof Emphasized) {
                Emphasized emphasized = (Emphasized) node1;
                visit(emphasized);
            }
            else if (node1 instanceof Code) {
                Code emphasized = (Code) node1;
                visit(emphasized);
            }
            else if (node1 instanceof CodeBlock) {
                CodeBlock emphasized = (CodeBlock) node1;
                visit(emphasized);
            }
            else if (node1 instanceof BreakLine) {
                BreakLine emphasized = (BreakLine) node1;
                visit(emphasized);
            }
            else if (node1 instanceof Html) {
                Html emphasized = (Html) node1;
                visit(emphasized);
            }
            else if (node1 instanceof HorizontalRule) {
                HorizontalRule emphasized = (HorizontalRule) node1;
                visit(emphasized);
            }

            else if (node1 instanceof Escaped) {
                Escaped emphasized = (Escaped) node1;
                visit(emphasized);
            }
            else if (node1 instanceof BlockQuote) {
                BlockQuote emphasized = (BlockQuote) node1;
                visit(emphasized);
            }

            else if (node1 instanceof Link) {
                Link link = (Link) node1;
                visit(link);
            }
            else if (node1 instanceof List) {
                List link = (List) node1;
                visit(link);
            }
            else if (node1 instanceof ListItem) {
                ListItem link = (ListItem) node1;
                visit(link);
            }
        }
    }

    public void visit(Heading heading) {

        html.append("\n<h" + heading.getLevel() + ">");
        visit((Node) heading);
        html.append("</h" + heading.getLevel() + ">\n");
    }

    public void visit(Text text) {

        final String value = text.getValue();
        html.append(Utils.escapeHtml(value));
        visit((Node) text);
    }

    public void visit(Paragraph paragraph) {

        if (paragraph.getParent().getParent() == null) { html.append("\n"); }
        html.append("<p>");
        visit((Node) paragraph);
        html.append("</p>");
        if (paragraph.getParent().getParent() == null) { html.append("\n"); }
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

        html.append("\n<pre><code>");
        visit((Node) codeBlock);
        html.append("\n</code></pre>\n");

        if (codeBlock.getParent() == null) {
            html.append("\n");
        }
    }

    public void visit(Html codeBlock) {

        if (!(codeBlock.getParent() instanceof Paragraph)) { html.append("\n"); }
        html.append(codeBlock.getContent());
        if (!(codeBlock.getParent() instanceof Paragraph)) { html.append("\n"); }
    }

    public void visit(HorizontalRule codeBlock) {

        html.append("\n<hr />\n");
        visit((Node) codeBlock);
    }

    public void visit(Code code) {

        html.append("<code>");
        visit((Node) code);
        html.append("</code>");
    }

    public void visit(BlockQuote code) {

        html.append("\n<blockquote>\n");
        visit((Node) code);
        html.append("</blockquote>\n");
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
            Link linkReference = parser.getLinkReference(link.getId());
            if (linkReference != null) {
                destination = linkReference.getDestination();
                link.setTitle(linkReference.getTitle());
            }
            else {
                html.append("[").append(id).append("] []");
                return;
            }
        }

        html.append("<a href=\"");
        html.append(Utils.escapeHtml(destination));
        html.append("\"");
        //        if (id != null && !id.trim().isEmpty()) {
        //            html.append(" id=\"");
        //            html.append(id);
        //            html.append("\"");
        //        }
        if (link.getTitle() != null) {
            html.append(" title=\"");
            html.append(Utils.escapeHtml(link.getTitle()));
            html.append("\"");
        }
        html.append(">");
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

        final String list_type = emphasized.isOrdered() ? "ol" : "ul";
        html.append("\n<").append(list_type).append(">\n");
        visit((Node) emphasized);
        html.append("</").append(list_type).append(">\n");
    }

    public String getHtml() {

        return html.toString().trim() + "\n";
    }
}
