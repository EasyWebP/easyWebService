package easyweb.easywebservice.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "<EasyWeb>",
                description = "<EasyWeb> API 명세서",
                version = "1.0"
        )

)
@RequiredArgsConstructor
@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi uligaApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .displayName("API Doc Ver 1.0")
                .group("EasyWeb_v1")
                .pathsToMatch(paths)
                .build();
    }

}
