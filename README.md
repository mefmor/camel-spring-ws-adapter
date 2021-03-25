# Apache Camel and Spring WS

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
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:exam="http://Example.org">
<soapenv:Header/>
<soapenv:Body>
<exam:Add>
...
</exam:Add>
</soapenv:Body>
</soapenv:Envelope>

Объекты для маршалинга/анмаршалинга сообщений поможет сделать maven-jaxb2-plugin из WSDL

