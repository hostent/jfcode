package com.mypro.serviceOne;

import javax.servlet.annotation.WebFilter;

import com.jfcore.web.ServiceFilter;

@WebFilter(filterName = "GatewayFilter", urlPatterns = "*")
public class MyFilter extends ServiceFilter {

}
