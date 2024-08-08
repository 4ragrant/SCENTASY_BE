package fouragrant.scentasy.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Scentasy API docs",
                description = "Description",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {

    private Info apiInfo() {
        return new Info().title("BomMeong API").description("BomMeong API Docs with Swagger UI").version("1.0.0");
    }
}
