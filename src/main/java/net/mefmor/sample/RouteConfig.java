package net.mefmor.sample;

import net.mefmor.sample.model.AddResponse;
import net.mefmor.sample.model.ObjectFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;

@Component
public class RouteConfig extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        JaxbDataFormat jaxb = new JaxbDataFormat(AddResponse.class.getPackage().getName());

        from("spring-ws:rootqname:{http://Example.org}Add?endpointMapping=#endpointMapping")
                .unmarshal(jaxb)
                .process(new IncrementProcessor())
                .marshal(jaxb);
    }

    private static final class IncrementProcessor implements Processor {
        @Override
        public void process(Exchange exchange) {
            exchange.getOut().setBody(new ObjectFactory().createAddResponse());
        }
    }
}
