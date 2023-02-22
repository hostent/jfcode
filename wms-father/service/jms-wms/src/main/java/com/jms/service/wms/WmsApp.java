package com.jms.service.wms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
 


@ServletComponentScan
@EnableDiscoveryClient  
@SpringBootApplication
public class WmsApp 
{
    public static void main( String[] args )
    {
    	SpringApplication sa= new SpringApplication(WmsApp.class);
    	
    	sa.run(args);
    }
}
