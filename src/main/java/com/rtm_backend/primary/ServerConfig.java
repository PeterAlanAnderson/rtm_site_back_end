package com.rtm_backend.primary;

import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class ServerConfig {
    private final String password;

    public ServerConfig() {
        // Load the password from environment variables using Dotenv
        this.password = Dotenv.configure().load().get("EXPORT_PASSWORD");
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> {
            factory.setPort(8443);
            Ssl ssl = new Ssl();
            ssl.setEnabled(true);
            ssl.setKeyStoreType("PKCS12");
            ssl.setKeyStore("/home/ec2-user/springboot-ssl/selfsigned.p12");
            ssl.setKeyStorePassword(this.password);
            ssl.setKeyAlias("springboot");
            factory.setSsl(ssl);
        };
    }
}