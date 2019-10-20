package com.lms.LMSOrchestrator;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class LmsOrchestratorApplication {

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LmsOrchestratorApplication.class, args);
	}
	
	@Bean
	public ServletWebServerFactory servletContainer() {
	    //Enabling SSL Traffic on tomcat
	    TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory() {
	        @Override
	        protected void postProcessContext(Context context) {
	            SecurityConstraint securityConstraint = new SecurityConstraint();
	            securityConstraint.setUserConstraint("CONFIDENTIAL");
	            SecurityCollection securityCollection = new SecurityCollection();
	            securityCollection.addPattern("/*");
	            securityConstraint.addCollection(securityCollection);
	            context.addConstraint(securityConstraint);
	        }
	    };
	    
	    //this line will add http to https automatic redirect
	    tomcatServletWebServerFactory.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
	    
	    return tomcatServletWebServerFactory;
	}
	/*
	* following method is in charge of creating a Connector which 
	* automates the redirection from http to https
	* any request to 8080 will get redirected to 443
	* */
	private Connector httpToHttpsRedirectConnector(){
	    Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
	    connector.setScheme("http");
	    connector.setPort(8080);
	    connector.setSecure(false);
	    connector.setRedirectPort(8443);
	    return connector;
	}

}
