package project.foodordersystem.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Food Order System REST API"

        ),

        externalDocs = @ExternalDocumentation(
                description = "Access the full API documentation",
                url = "http://localhost:8080/swagger-ui/index.html"
        )


)
public class SwaggerConfig {

}
