package com.jms.service.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

 

@ServletComponentScan
@EnableDiscoveryClient  
@SpringBootApplication
public class AccountApp 
{
    public static void main( String[] args )
    {
    	SpringApplication sa= new SpringApplication(AccountApp.class);
    	
    	sa.run(args);        
   
    }
}
