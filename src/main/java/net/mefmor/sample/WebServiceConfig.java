package net.mefmor.sample;

import org.apache.camel.component.spring.ws.bean.CamelEndpointMapping;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointAdapter;
import org.springframework.ws.server.endpoint.adapter.MessageEndpointAdapter;
import org.springframework.ws.soap.axiom.AxiomSoapMessageFactory;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "countries")
    public SimpleWsdl11Definition simpleWsdl11Definition() {
        return new SimpleWsdl11Definition(new ClassPathResource("countries.wsdl"));
    }

// TODO: Engage AXIOM instead SAAJ
//    @Bean
//    public AxiomSoapMessageFactory messageFactory() {
//        return new AxiomSoapMessageFactory();
//    }

    /**
     * See <a href="https://bit.ly/3eCckTF">Исключение "нет адаптера для конечной точки" - apache-camel с spring-boot & spring-ws</a>
     * @return EndpointAdapter
     */
    @Bean
    public EndpointAdapter endpoint() {
        return new MessageEndpointAdapter();
    }

    @Bean
    public CamelEndpointMapping endpointMapping() {
        return new CamelEndpointMapping();
    }


}
