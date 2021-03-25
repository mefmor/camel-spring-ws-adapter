package net.mefmor.sample;

import org.apache.camel.component.spring.ws.bean.CamelEndpointMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.server.EndpointAdapter;
import org.springframework.ws.server.endpoint.adapter.MessageEndpointAdapter;

@Configuration
public class WebConfig {
    @Bean
    public CamelEndpointMapping endpointMapping() {
        return new CamelEndpointMapping();
    }

    @Bean
    public EndpointAdapter endpointAdapter() {
        return new MessageEndpointAdapter();
    }
}
