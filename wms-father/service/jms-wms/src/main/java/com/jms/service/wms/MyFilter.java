package com.jms.service.wms;

import javax.servlet.annotation.WebFilter;

import com.jfcore.web.ServiceFilter;

@WebFilter(filterName = "GatewayFilter", urlPatterns = "*")
public class MyFilter extends ServiceFilter {

}
