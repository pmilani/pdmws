package com.strazzabosco.pdmws;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.strazzabosco.schemas.pdm_ws.StoreMetadataRequest.MetadataContent;

@Component
public class MetadataRepository {
    public static final Logger LOG = Logger.getLogger(MetadataRepository.class);

    private static final Map<String, MetadataContent> metadata = new HashMap<String, MetadataContent>();
    
    public void addMetadata(String id, MetadataContent metadataContent) {
        metadata.put(id, metadataContent);
        LOG.info("Added metadata to repository, id="+ id);
    }
    
    public MetadataContent getMetadata(String id) {
        if (!metadata.containsKey(id)) {
            throw new IllegalArgumentException("metadataId not found in repository");
        }
        return metadata.get(id);
    }
}
