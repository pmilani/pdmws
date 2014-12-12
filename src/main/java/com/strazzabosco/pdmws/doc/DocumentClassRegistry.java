package com.strazzabosco.pdmws.doc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.strazzabosco.pdmws.PdmException;

@Component
public class DocumentClassRegistry {
    
    private static final Logger LOG = Logger.getLogger(DocumentClassRegistry.class);

    private static List<String> documentClasses;
    
    private Map<String, DocumentGenerator<?>> generators;

    @Autowired
    public DocumentClassRegistry(@Value("${documentClasses}") String acceptedClasses, Map<String, DocumentGenerator<?>> generators) {
        documentClasses = Arrays.asList(StringUtils.commaDelimitedListToStringArray(acceptedClasses));
        LOG.info("Accepted document classes: "+ documentClasses);
        this.generators = generators;
    }

    public void validate(String documentClass) {
        if (!documentClasses.contains(documentClass)) {
            throw new PdmException(String.format("%s: document class not accepted", documentClass));
        }
    }

    public DocumentGenerator<?> getGenerator(String documentClass) {
        return generators.get(documentClass + "Generator");
    }
}
