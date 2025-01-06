package com.example.testfont;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Service
public class HtmlToPdfConverter {

    public byte[] generatePdfFromHtml(String templatePath, Map<String, String> placeholders) {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(templatePath)) {
            if (inputStream == null) {
                throw new RuntimeException("HTML template not found at path: " + templatePath);
            }

            String htmlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                String value = entry.getValue() != null ? entry.getValue() : "";
                htmlContent = htmlContent.replace("{{" + entry.getKey() + "}}", value);
            }

            Document sanitizedHtml = Jsoup.parse(htmlContent);
            sanitizedHtml.outputSettings().escapeMode(org.jsoup.nodes.Entities.EscapeMode.xhtml);
            sanitizedHtml.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            String cleanHtml = sanitizedHtml.html();

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.useFastMode();
                builder.withHtmlContent(cleanHtml, null);
                builder.toStream(outputStream);
                builder.useFont(() -> getClass().getClassLoader().getResourceAsStream("font/Roboto-Regular.ttf"), "Roboto");

                builder.run();
                return outputStream.toByteArray();
            } catch (Exception e) {
                throw new RuntimeException("Error rendering PDF", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}
