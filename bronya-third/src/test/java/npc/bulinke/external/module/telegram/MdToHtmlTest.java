package npc.bulinke.external.module.telegram;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.junit.jupiter.api.Test;

public class MdToHtmlTest {

    @Test
    public void toHtml(){
        String markdown = "## Hello, *world*!";
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);

        // 渲染为 HTML
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String html = renderer.render(document);
        System.out.println("html = " + html);
    }
}
