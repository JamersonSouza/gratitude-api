package tech.jamersondev.gratitude.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {OpenAPI.class})
class DocConfigurationTest {

    @Test
    void testOpenAPIHasBearerSecurityScheme() {
        DocConfiguration docConfiguration = new DocConfiguration(); // classe onde está seu @Bean
        OpenAPI openAPI = docConfiguration.openAPI();

        Components components = openAPI.getComponents();
        assertNotNull(components);

        SecurityScheme scheme = components.getSecuritySchemes().get("bearer-key");
        assertNotNull(scheme);
        assertEquals(SecurityScheme.Type.HTTP, scheme.getType());
        assertEquals("bearer", scheme.getScheme());
        assertEquals("JWT", scheme.getBearerFormat());
    }
}
