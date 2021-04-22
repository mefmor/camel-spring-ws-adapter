# Apache Camel and Spring WS

https://people.apache.org/~dkulp/camel/spring-ws-example.html
https://docs.spring.io/spring-ws/sites/1.5/reference/html/server.html

Пример демонстрирует построение SOAP сервера на основе Spring Boot используя Apache Camel и компонент spring-ws

В качестве источника запросов предполагается использование SoapUI, проект которого генерируется из WSDL

spring-ws:rootqname:{http://Example.org}Add?endpointMapping=#endpointMapping

spring-ws - имя компонента
rootqname - обозначение, что будем обслуживать запросы (работаем как SOAP сервер)
{http://Example.org} - имя основной используемой схемы в SOAP-запросе
Add - имя операции в SOAP-запросе

Также потребуются бины endpointMapping и endpointAdapter, которые можно создать из стандартных классов:
org.apache.camel.component.spring.ws.bean.CamelEndpointMapping;
org.springframework.ws.server.endpoint.adapter.MessageEndpointAdapter

Пример соответствующего URI SOAP-запроса:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:gs="http://spring.io/guides/gs-producing-web-service">
<soapenv:Header/>
    <soapenv:Body>
        <gs:getCountryRequest><gs:name>Poland</gs:name></gs:getCountryRequest>
    </soapenv:Body>
</soapenv:Envelope>

Объекты для маршалинга/анмаршалинга сообщений поможет сделать maven-jaxb2-plugin из WSDL

2021-04-20
Я не знаю, что мне делать с этою бедой...
При попытке обратиться к http://localhost:8080/ws с запросом выше - получаю сообщение
o.s.ws.server.EndpointNotFound: No endpoint mapping found for [AxiomSoapMessage {http://spring.io/guides/gs-producing-web-service}getCountryRequest]
Если не создавать свою messageFactory, а использовать стандартную получим "[SaajSoapMessage {..." Соответственно.
Но! Если обратиться по адресу: http://localhost:8080/ws/countries.wsdl - он с радостью сдаст wsdl'ку

Это значит, что messageFactory он-таки видит, а вот маппинг для эндпоинта почему-то проставлять не желает.
Очевидно, что ругань исходит от спринга. И да, для него действительно эндпоинт не прописан. Но я-то захожу со стороны кэмэла
Как прописать этот мапинг?
Да, проблема была довольно очевидно, но я её не нащупал. Забыл прописать @Configuration для роута и он файтически не работал

Сейчас получаю вообще довольно странную экзотику:
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
<soapenv:Header/>
<soapenv:Body>
<soapenv:Fault>
<faultcode>soapenv:Server</faultcode>
<faultstring xml:lang="en">java.lang.NullPointerException</faultstring>
</soapenv:Fault>
</soapenv:Body>
</soapenv:Envelope>
в логах при этом ничего нет. И даже --debug не прояснил ситуацию
Может быть там в роуте действительно NPE случается. Сейчас проверим
Отключил использование AXIOM. Хоть что-то валиться в логи стало! 
Походу, CountryRepository не работает. Да проблема в нём. Сейчас будем исправлять
Всё взлетело. Но! Если я создам бин AxiomSoapMessageFactory messageFactory() - то буду получать ошибку, как и выше

Поменял версию AXIOM на 1.2.13, теперь получаю ошибку:
java.lang.NoSuchMethodError: org.apache.axiom.soap.SOAPModelBuilder.getSOAPMessage()Lorg/apache/axiom/soap/SOAPMessage;
Вернул версию на 1.2.22, попробовал выставлять factory.setPayloadCaching(false); - не помогло
