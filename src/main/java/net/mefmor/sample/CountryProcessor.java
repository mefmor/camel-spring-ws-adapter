package net.mefmor.sample;

import net.mefmor.sample.model.GetCountryRequest;
import net.mefmor.sample.model.GetCountryResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountryProcessor implements Processor {

    @Autowired
    CountryRepository countryRepository;

    public void process(Exchange exchange) {
        GetCountryRequest request = exchange.getIn().getBody(GetCountryRequest.class);
        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(countryRepository.findCountry(request.getName()));
        exchange.getOut().setBody(response);
    }
}
