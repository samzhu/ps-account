package com.ps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.robwin.markup.builder.MarkupLanguage;
import io.github.robwin.swagger2markup.GroupBy;
import io.github.robwin.swagger2markup.Swagger2MarkupConverter;
import org.apache.commons.lang3.Validate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by samchu on 2017/3/28.
 */

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class Swagger2MarkupTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {

    }

    // 輸出成 adoc 格式
    @Test
    public void convertSwaggerToAsciiDoc() throws Exception {
        this.mockMvc.perform(get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
                // 原本的寫法
                //.andDo(Swagger2MarkupResultHandler.outputDirectory("src/docs/asciidoc/generated").build())
                // 稍微客製化只留下api開頭的path
                .andDo(new PsSwagger2MarkupHandler("src/docs/asciidoc/generated"))
                .andExpect(status().isOk());
    }

    // 輸出成 Markdown 格式
//    @Test
//    public void convertSwaggerToMarkdown() throws Exception {
//        this.mockMvc.perform(get("/v2/api-docs")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(Swagger2MarkupResultHandler.outputDirectory("docs/markdown/generated")
//                        .withMarkupLanguage(MarkupLanguage.MARKDOWN).build())
//                .andExpect(status().isOk());
//    }

    // 一般寫測試兼輸出的方式
//    @Test
//    public void testIndex() throws Exception {
//        this.mockMvc.perform(get("/api/test").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(document("test", preprocessResponse(prettyPrint())));
//    }

    class PsSwagger2MarkupHandler implements ResultHandler {
        private String outputDir;
        private final MarkupLanguage markupLanguage = MarkupLanguage.ASCIIDOC;
        private String examplesFolderPath;
        private final GroupBy pathsGroupedBy = GroupBy.AS_IS;
        private final String encoding = "UTF-8";
        private String passStr = "listRepositories,getCollectionResource,getItemResource,getCollectionResourceCompact,listSearches,errorHtml,listAllFormsOfMetadata,schema";


        public PsSwagger2MarkupHandler(String outputDir) {
            Validate.notEmpty(outputDir, "outputDir must not be empty!");
            this.outputDir = outputDir;
        }

        @Override
        public void handle(MvcResult result) throws Exception {
            MockHttpServletResponse response = result.getResponse();
            response.setCharacterEncoding(encoding);
            String swaggerJson = response.getContentAsString();
            // 去除掉 Spring Data Rest 提供的基礎路徑
            List<String> passSummary = Arrays.asList(passStr.split(","));
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode newPaths = mapper.createObjectNode();
            JsonNode jsonRoot = mapper.readTree(swaggerJson);
            JsonNode jsonPaths = jsonRoot.get("paths");
            Iterator<String> keyit = jsonPaths.fieldNames();
            while (keyit.hasNext()) {
                String key = keyit.next();
                JsonNode jsonPath = jsonPaths.get(key);
                String summary = jsonPath.findPath("summary").asText();
                if (!passSummary.contains(summary)) {
                    newPaths.set(key, jsonPath);
                }
            }
            ((ObjectNode) jsonRoot).set("paths", newPaths);
            swaggerJson = jsonRoot.toString();
            // 這邊輸出成 adoc
            Swagger2MarkupConverter.fromString(swaggerJson).withMarkupLanguage(markupLanguage)
                    .withPathsGroupedBy(this.pathsGroupedBy)
                    .withExamples(examplesFolderPath).build().intoFolder(outputDir);
        }
    }
}

