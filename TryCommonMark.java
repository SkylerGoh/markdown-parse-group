import java.util.ArrayList;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;


class TryCommonMark {

    public static void main(String[] args) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse("This is *Sparta*");
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        System.out.println(renderer.render(document));  // "<p>This is <em>Sparta</em></p>\n"

        Node node = parser.parse("Example\n=======\n\nSome more text");
        WordCountVisitor visitor = new WordCountVisitor();
        node.accept(visitor);
        System.out.println("Visitor: " + visitor.wordCount); 

        node = parser.parse("[a link!](https://something.com) [another link!](some-page.html)");
        LinkCountVisitor linkVisitor = new LinkCountVisitor();
        node.accept(linkVisitor);
        System.out.println("Links: " + linkVisitor.linkCount); 
    }
}

class WordCountVisitor extends AbstractVisitor {
    int wordCount = 0;

    @Override
    public void visit(Text text) {
        // This is called for all Text nodes. Override other visit methods for other node types.

        // Count words (this is just an example, don't actually do it this way for various reasons).
        wordCount += text.getLiteral().split("\\W+").length;

        // Descend into children (could be omitted in this case because Text nodes don't have children).
        visitChildren(text);
    }
}
class LinkCountVisitor extends AbstractVisitor {
    int linkCount = 0;
    ArrayList<String> linkList;
    public LinkCountVisitor() {
        linkList = new ArrayList<>();
    }
    @Override
    public void visit(Link links) {
        // This is called for all Text nodes. Override other visit methods for other node types.

        // Count words (this is just an example, don't actually do it this way for various reasons).
        linkCount += 1;
        // Descend into children (could be omitted in this case because Text nodes don't have children).
        visitChildren(links);
    }
    public ArrayList<String> getLinks(Link links) {
        // This is called for all Text nodes. Override other visit methods for other node types.
        if (links == null) {
            return linkList;
        }
        // Count words (this is just an example, don't actually do it this way for various reasons).
        linkCount += 1;
        linkList.add(links.getDestination());
        // Descend into children (could be omitted in this case because Text nodes don't have children).
        visitChildren(links);
    }
}