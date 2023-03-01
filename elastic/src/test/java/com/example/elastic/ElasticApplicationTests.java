package com.example.elastic;

import io.github.swagger2markup.*;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;

import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ElasticApplicationTests {

    @Test
    public void generateMarkdownDocsToFile() throws Exception {
        // 输出Markdown到单文件
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8090/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFile(Paths.get("src/docs/asciidoc"));
//                .toFolder(Paths.get("src/docs/asciidoc"));

    }


}
