package config;

import lang.Language;
import lang.implement.Vietnamese;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"bean"})
public class AppConfiguration {
    @Bean(name = "language")
    public Language getLanguague() {
        return new Vietnamese();
    }
}
