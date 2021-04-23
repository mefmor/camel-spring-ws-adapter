package net.mefmor.sample;


import net.mefmor.sample.model.GetCountryRequest;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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