package com.strazzabosco.pdmws;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

@Component
public class MetadataRepository {
    public static final Logger LOG = Logger.getLogger(MetadataRepository.class);

    private static final Map<String, Document> metadata = new HashMap<String, Document>();
    
    public void addMetadata(String id, Document data) {
        metadata.put(id, data);
        LOG.info("Added metadata to repository, id="+ id);
    }
    
    public Document getMetadata(String id) {
        if (!metadata.containsKey(id)) {
            throw new IllegalArgumentException("metadataId not present");
        }
        return metadata.get(id);
    }
}
