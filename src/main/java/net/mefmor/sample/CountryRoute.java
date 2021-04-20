package net.mefmor.sample;


import net.mefmor.sample.model.GetCountryRequest;
import net.mefmor.sample.model.GetCountryResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountryRoute extends RouteBuilder {
    @Autowired
    CountryProcessor countryProcessor;

    @Override
    public void configure() {
        JaxbDataFormat jaxb = new JaxbDataFormat(GetCountryRequest.class.getPackage().getName());
        
        from("spring-ws:rootqname:{http://spring.io/guides/gs-producing-web-service}getCountryRequest?endpointMapping=#endpointMapping")
            .unmarshal(jaxb)
            .process(countryProcessor)
            .marshal(jaxb);
    }
   
}