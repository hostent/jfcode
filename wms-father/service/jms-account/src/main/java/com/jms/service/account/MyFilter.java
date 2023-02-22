package com.jms.service.account;

import javax.servlet.annotation.WebFilter;

import com.jfcore.web.ServiceFilter;

@WebFilter(filterName = "GatewayFilter", urlPatterns = "*")
public class MyFilter extends ServiceFilter {

}
