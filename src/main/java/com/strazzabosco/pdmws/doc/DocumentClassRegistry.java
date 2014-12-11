package com.strazzabosco.pdmws.doc;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.strazzabosco.pdmws.PdmException;

@Component
public class DocumentClassRegistry {

    private static List<String> documentClasses;

    @Autowired
    public DocumentClassRegistry(@Value("${documentClasses}") String acceptedClasses) {
        documentClasses = Arrays.asList(StringUtils.split(acceptedClasses, ","));
    }

    public void validate(String documentClass) {
        if (!documentClasses.contains(documentClass)) {
            throw new PdmException(String.format("%s: document class not accepted", documentClass));
        }
    }
    
}
