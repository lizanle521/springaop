package com.lzl.design_pattern.O_Facade.pagemaker;

import java.io.Writer;

/**
 * @author lizanle
 * @data 2019/4/6 2:17 PM
 */
public class HtmlWriter {
    private Writer writer;

    public HtmlWriter(Writer writer) {
        this.writer = writer;
    }

    public void title(String title) throws Exception {
        writer.write("<html>");
        writer.write("<head>");
        writer.write("<title>" + title + "</title>");
        writer.write("</head>");
        writer.write("<body>\n");
        writer.write("<h1>"+title+"</h1>\n");

    }

    public void paragraph(String msg) throws Exception {
        writer.write("<p>"+msg+"</p>\n");
    }

    public void link(String href,String caption) throws Exception {
        paragraph("<a href=\"" + href + "\">" + caption + "</a>" );
    }

    public void mailto(String mailaddr,String username) throws Exception {
        link("mail:to"+mailaddr,username);
    }

    public void close() throws Exception {
        writer.write("</body>");
        writer.write("</html>\n");
        writer.close();
    }
}
