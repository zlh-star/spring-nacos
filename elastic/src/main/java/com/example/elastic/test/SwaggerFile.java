//package com.example.elastic.test;
//
//import io.github.swagger2markup.GroupBy;
//import io.github.swagger2markup.Language;
//import io.github.swagger2markup.Swagger2MarkupConfig;
//import io.github.swagger2markup.Swagger2MarkupConverter;
//import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
//import io.github.swagger2markup.markup.builder.MarkupLanguage;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.nio.file.Paths;
//
//public class SwaggerFile {
//
//    public static void generateMarkdownDocsToFile() throws MalformedURLException {
//        // 输出Markdown到单文件
//        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
//                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
//                .withOutputLanguage(Language.ZH)
//                .withPathsGroupedBy(GroupBy.TAGS)
//                .withGeneratedExamples()
//                .withoutInlineSchema()
//                .build();
//
//        Swagger2MarkupConverter.from(new URL("http://localhost:8090/elastic/v2/api-docs"))
//                .withConfig(config)
//                .build()
////                .toFile(Paths.get("src/docs/asciidoc"));
//                .toFolder(Paths.get("src/docs/asciidoc"));
//    }
//}
