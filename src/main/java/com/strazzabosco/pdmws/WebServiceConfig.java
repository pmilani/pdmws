package com.strazzabosco.pdmws;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.server.endpoint.SoapFaultAnnotationExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean dispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }
    
    @Bean(name="pdm-ws")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema pdmImportServiceSchema) {
        DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
        defaultWsdl11Definition.setPortTypeName("PdmWsPort");
        defaultWsdl11Definition.setTargetNamespace(PdmDocsEndpoint.NAMESPACE_URI);
        defaultWsdl11Definition.setLocationUri("/ws");
        defaultWsdl11Definition.setSchema(pdmImportServiceSchema);
        return defaultWsdl11Definition;
    }
    
    @Bean
    public XsdSchema pdmImportServiceSchema() {
        return new SimpleXsdSchema(new ClassPathResource("pdm-ws.xsd"));
    }
    
    @Bean
    public SoapFaultAnnotationExceptionResolver soapFaultAnnotationExceptionResolver() {
        return new SoapFaultAnnotationExceptionResolver();
    }
}
