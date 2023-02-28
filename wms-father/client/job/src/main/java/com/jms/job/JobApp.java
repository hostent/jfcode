package com.jms.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.jfcore.job.IJobEvent;
 

 

@ServletComponentScan
@EnableDiscoveryClient  
@SpringBootApplication
public class JobApp 
{
    public static void main( String[] args )
    {
    	SpringApplication sa= new SpringApplication(JobApp.class);    	
    	sa.run(args);
    	
    	
    	JobMain.init();
    }
    
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    
    
    
    @Bean
    @LoadBalanced
    public IJobEvent jobEvent(){
        return new JobEvent();
    }
    
    
    
}
