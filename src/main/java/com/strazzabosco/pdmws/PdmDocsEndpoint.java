package com.strazzabosco.pdmws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.strazzabosco.schemas.pdm_ws.StoreBusinessOpportunityRequest;
import com.strazzabosco.schemas.pdm_ws.StoreBusinessOpportunityResponse;
import com.strazzabosco.schemas.pdm_ws.StoreDocumentRequest;
import com.strazzabosco.schemas.pdm_ws.StoreDocumentResponse;
import com.strazzabosco.schemas.pdm_ws.StoreMetadataRequest;
import com.strazzabosco.schemas.pdm_ws.StoreMetadataRequest.MetadataContent;
import com.strazzabosco.schemas.pdm_ws.StoreMetadataResponse;

@Endpoint
public class PdmDocsEndpoint {
    public static final String NAMESPACE_URI="http://strazzabosco.com/schemas/pdm-ws";

    private MetadataRepository metadataRepository;
    private PdmWorkflow pdmWorkflow;

    @Autowired
    public PdmDocsEndpoint(MetadataRepository metadataRepository, PdmWorkflow pdmWorkflow) {
        this.metadataRepository = metadataRepository;
        this.pdmWorkflow = pdmWorkflow;
    }
    
    @PayloadRoot(localPart="storeMetadataRequest", namespace=NAMESPACE_URI)
    @ResponsePayload
    public StoreMetadataResponse storeMetadata(@RequestPayload StoreMetadataRequest request) {
        StoreMetadataResponse response = new StoreMetadataResponse();
        if (StringUtils.isEmpty(request.getMetadataId())) {
            throw new IllegalArgumentException("metadataId is required");
        }
    
        metadataRepository.addMetadata(request.getMetadataId(), request.getMetadataContent());
        response.setStored(true);
        response.setNote("");
        return response;
    }
    
    @PayloadRoot(localPart="storeBusinessOpportunityRequest", namespace=NAMESPACE_URI)
    @ResponsePayload
    public StoreBusinessOpportunityResponse storeBO(@RequestPayload StoreBusinessOpportunityRequest request) {
        StoreBusinessOpportunityResponse response = new StoreBusinessOpportunityResponse();
        
        MetadataContent metadata = metadataRepository.getMetadata(request.getMetadataId());
        pdmWorkflow.generateBoXml(metadata.getDatiBO());
        response.setStored(true);
        response.setNote("");
        return response;
    }

    @PayloadRoot(localPart="storeDocumentRequest", namespace=NAMESPACE_URI)
    @ResponsePayload
    public StoreDocumentResponse storeDocument(@RequestPayload StoreDocumentRequest request) {
        StoreDocumentResponse response = new StoreDocumentResponse();
        return response;
    }
}
