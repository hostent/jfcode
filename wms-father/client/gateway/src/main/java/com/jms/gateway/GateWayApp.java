package com.jms.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
 



@ServletComponentScan
@EnableDiscoveryClient  
@SpringBootApplication
public class GateWayApp 
{
    public static void main( String[] args )
    {
    	SpringApplication sa= new SpringApplication(GateWayApp.class);    	
    	sa.run(args);
    	
    }
    
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    

    

    
}
