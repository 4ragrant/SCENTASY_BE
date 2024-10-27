package fouragrant.scentasy.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
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
    @Value("${aws.ec2.ip}") String serverIp;
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("http://"+serverIp+":8080").description("Dev Server"))
                .addServersItem(new Server().url("http://localhost:8080").description("Local Server"))
                .components(new Components().addSecuritySchemes("Bearer Token", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization")
                        .scheme("bearer")
                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Token"));
    }
}
