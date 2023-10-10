import com.itextpdf.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

class PrintPdf {
    public static void main(String[] args){

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("title", "测试标题");
        map.put("name", "smile");

        String htmlFile = "htmlTemplate.html";
        String outputPdfFile = "output.pdf";
        String htmlString = "";
        Writer out = new StringWriter();

        try{
            Configuration freemarkerCfg =new Configuration();
            freemarkerCfg.setDirectoryForTemplateLoading(new File(""));
            // 获取模板,并设置编码方式
            Template template = freemarkerCfg.getTemplate(htmlFile);
            template.setEncoding("UTF-8");
            // 合并数据模型与模板
            template.process(map, out); //将合并后的数据和模板写入到流中，这里使用的字符流
            out.flush();
            htmlString = out.toString();

            OutputStream os = new FileOutputStream(outputPdfFile);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlString);

            //中文支持
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont("simhei.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            renderer.layout();
            renderer.createPDF(os);
            os.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
