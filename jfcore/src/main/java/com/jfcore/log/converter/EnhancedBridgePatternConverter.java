package com.jfcore.log.converter;

 
import org.apache.log4j.pattern.BridgePatternConverter;
import org.apache.log4j.pattern.FormattingInfo;
import org.apache.log4j.pattern.LoggingEventPatternConverter;
import org.apache.log4j.pattern.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

import com.jfcore.log.parser.EnhancedPatternParserChain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EnhancedBridgePatternConverter extends org.apache.log4j.helpers.PatternConverter {
    /**
     * Pattern converters.
     */
    private LoggingEventPatternConverter[] patternConverters;

    /**
     * Field widths and alignment corresponding to pattern converters.
     */
    private FormattingInfo[] patternFields;

    /**
     * Does pattern process exceptions.
     */
    private boolean handlesExceptions;

    /**
     * Create a new instance.
     * @param pattern pattern, may not be null.
     */
    public EnhancedBridgePatternConverter(
            final String pattern) {
        next = null;
        handlesExceptions = false;

        List converters = new ArrayList();
        List fields = new ArrayList();
        Map converterRegistry = null;

        EnhancedPatternParserChain.parse(
                pattern, converters, fields, converterRegistry,
                EnhancedPatternParserChain.getPatternLayoutRules());

        patternConverters = new LoggingEventPatternConverter[converters.size()];
        patternFields = new FormattingInfo[converters.size()];

        int i = 0;
        Iterator converterIter = converters.iterator();
        Iterator fieldIter = fields.iterator();

        while (converterIter.hasNext()) {
            Object converter = converterIter.next();

            if (converter instanceof LoggingEventPatternConverter) {
                patternConverters[i] = (LoggingEventPatternConverter) converter;
                handlesExceptions |= patternConverters[i].handlesThrowable();
            } else {
                patternConverters[i] =
                        new org.apache.log4j.pattern.LiteralPatternConverter("");
            }

            if (fieldIter.hasNext()) {
                patternFields[i] = (FormattingInfo) fieldIter.next();
            } else {
                patternFields[i] = FormattingInfo.getDefault();
            }

            i++;
        }
    }

    /**
     * {@inheritDoc}
     */
    protected String convert(final LoggingEvent event) {
        //
        //  code should be unreachable.
        //
        StringBuffer sbuf = new StringBuffer();
        format(sbuf, event);
        
        
        

        return sbuf.toString();
    }

    /**
     Format event to string buffer.
     @param sbuf string buffer to receive formatted event, may not be null.
     @param e event to format, may not be null.
     */
    public void format(final StringBuffer sbuf, final LoggingEvent e) {
        for (int i = 0; i < patternConverters.length; i++) {
            int startField = sbuf.length();
            patternConverters[i].format(e, sbuf);
            patternFields[i].format(startField, sbuf);
        }
    }

    /**
     * Will return false if any of the conversion specifiers in the pattern
     * handles {@link Exception Exceptions}.
     * @return true if the pattern formats any information from exceptions.
     */
    public boolean ignoresThrowable() {
        return !handlesExceptions;
    }
}
