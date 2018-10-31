package alexkirnsu.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

public class TestAppPropertiesInitializer  implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ResourcePropertySource source;
        try {
            source = new ResourcePropertySource(new ClassPathResource("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        applicationContext.getEnvironment().getPropertySources().addFirst(source);
    }
}
