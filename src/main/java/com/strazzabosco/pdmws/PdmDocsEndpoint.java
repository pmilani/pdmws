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
import com.strazzabosco.schemas.pdm_ws.StoreMetadataResponse;

@Endpoint
public class PdmDocsEndpoint {
    public static final String NAMESPACE_URI="http://strazzabosco.com/schemas/pdm-ws";

    private PdmWorkflow pdmWorkflow;

    @Autowired
    public PdmDocsEndpoint(PdmWorkflow pdmWorkflow) {
        this.pdmWorkflow = pdmWorkflow;
    }
    
    @PayloadRoot(localPart="storeMetadataRequest", namespace=NAMESPACE_URI)
    @ResponsePayload
    public StoreMetadataResponse storeMetadata(@RequestPayload StoreMetadataRequest request) {
        if (StringUtils.isEmpty(request.getMetadataId())) {
            throw new IllegalArgumentException("metadataId is required");
        }
        pdmWorkflow.addMetadata(request.getMetadataId(), request.getMetadataContent());

        StoreMetadataResponse response = new StoreMetadataResponse();
        response.setStored(true);
        response.setNote("");
        return response;
    }
    
    @PayloadRoot(localPart="storeBusinessOpportunityRequest", namespace=NAMESPACE_URI)
    @ResponsePayload
    public StoreBusinessOpportunityResponse storeBO(@RequestPayload StoreBusinessOpportunityRequest request) {
        PdmExecutionResult execution = pdmWorkflow.acquireBusinessOpportunity(request.getMetadataId());
        
        StoreBusinessOpportunityResponse response = new StoreBusinessOpportunityResponse();
        response.setStored(execution.acquired);
        response.setNote(execution.message);
        return response;
    }

    @PayloadRoot(localPart="storeDocumentRequest", namespace=NAMESPACE_URI)
    @ResponsePayload
    public StoreDocumentResponse storeDocument(@RequestPayload StoreDocumentRequest request) {
        PdmExecutionResult execution = pdmWorkflow.acquireDocument(
                request.getMetadataId(), 
                request.getDocumentClass(), 
                request.getDocumentContent());
        
        StoreDocumentResponse response = new StoreDocumentResponse();
        response.setStored(execution.acquired);
        response.setNote(execution.message);
        return response;
    }
}
