package com.jfcore.log.converter;

import org.apache.log4j.pattern.LoggingEventPatternConverter;
import org.apache.log4j.spi.LoggingEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 当前主机Host名
 */
public class HostNameConverter  extends LoggingEventPatternConverter {
    private static final HostNameConverter INSTANCE =
            new HostNameConverter();
    private static final String HOSTNAME =
            getHostName();
    public static HostNameConverter newInstance(
            final String[] options){
        return INSTANCE;
    }
    protected HostNameConverter() {
        super("current system host name", "hostUser");
    }

    @Override
    public void format(LoggingEvent event, StringBuffer toAppendTo) {
        toAppendTo.append(HOSTNAME);
        
       
    }

    public static String getHostNameForLiunx() {
        try {
            return (InetAddress.getLocalHost()).getHostName();
        } catch (UnknownHostException uhe) {
            String host = uhe.getMessage(); // host = "hostname: hostname"
            if (host != null) {
                int colon = host.indexOf(':');
                if (colon > 0) {
                    return host.substring(0, colon);
                }
            }
            return "UnknownHost";
        }
    }


    public static String getHostName() {
        if (System.getenv("COMPUTERNAME") != null) {
            return System.getenv("COMPUTERNAME");
        } else {
            return getHostNameForLiunx();
        }
    }
}
