package com.example.wavefrontaccountprovisioner;

import com.example.wavefrontaccountprovisioner.account.AccountInfoStorage;
import com.example.wavefrontaccountprovisioner.account.AccountManagementClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@SpringBootApplication(proxyBeanMethods = false)
public class WavefrontAccountProvisionerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WavefrontAccountProvisionerApplication.class, args);
    }

    @Bean
    AccountManagementClient accountManagementClient(RestTemplateBuilder restTemplateBuilder) {
        return new AccountManagementClient(restTemplateBuilder, null, Duration.ofSeconds(30));
    }

    @Bean
    AccountInfoStorage accountInfoStorage() {
        return new AccountInfoStorage();
    }

}
