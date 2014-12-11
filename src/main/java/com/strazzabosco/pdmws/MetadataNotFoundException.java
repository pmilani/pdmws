package com.strazzabosco.pdmws;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SuppressWarnings("serial")
@SoapFault(faultCode=FaultCode.SERVER)
public class MetadataNotFoundException extends RuntimeException {

    public MetadataNotFoundException(String id) {
        super(String.format("%s: metadataId not found in repository", id));
    }
}
