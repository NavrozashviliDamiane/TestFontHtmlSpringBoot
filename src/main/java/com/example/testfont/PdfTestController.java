package com.example.testfont;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class PdfTestController {

    private final HtmlToPdfConverter htmlToPdfConverter;

    public PdfTestController(HtmlToPdfConverter htmlToPdfConverter) {
        this.htmlToPdfConverter = htmlToPdfConverter;
    }

    @GetMapping(path = "/generate-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateSimplePdf() {
        Map<String, String> placeholders = Map.of(
                "sizeLeftField", "Test Value 1",
                "sizeRightField", "Test Value 2"
        );

        byte[] pdfBytes = htmlToPdfConverter.generatePdfFromHtml("html/tuningdoc.html", placeholders);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}