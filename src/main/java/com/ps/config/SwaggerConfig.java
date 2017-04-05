package com.ps.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;


/**
 * Created by samchu on 2017/2/17.
 */
@Configuration
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {

    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .consumes(Collections.singleton(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .produces(Collections.singleton(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .select()
                .paths(PathSelectors.any())
                .build();
    }


    //@Bean
    public Docket newsApi() {
        ArrayList globalResponseMessage = new ArrayList();
        globalResponseMessage.add(new ResponseMessageBuilder()
                .code(500)
                .message("Failure")
                .responseModel(new ModelRef("ErrorMessage"))
                .build());
        globalResponseMessage.add(new ResponseMessageBuilder()
                .code(400)
                .message("資料或是結構上錯誤")
                //.responseModel(new ModelRef("Error"))
                .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("ps-Account")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                //.paths(paths())
                .build()
                .forCodeGeneration(true)
                //.securitySchemes(apiKey())
                //.securityContexts(securityContext())
                //.directModelSubstitute(LocalDate.class, Date.class)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalResponseMessage)
                .globalResponseMessage(RequestMethod.POST, globalResponseMessage);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ps-account REST API")
                .description(String.format("寶勝帳號管理"))
                .contact(new Contact("SamChu", "http://samchu.logdown.com/", "samzhu18@gmail.com"))
                //.termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
                //.license("Apache License Version 2.0")
                //.licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
                .version("0.1")
                .build();
    }

    private Predicate<String> paths() {
        return Predicates.or(
                regex(DEFAULT_INCLUDE_PATTERN),
                regex("/gift.*"));
    }
}
