package com.strazzabosco.pdmws;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SuppressWarnings("serial")
@SoapFault(faultCode=FaultCode.SERVER)
public class PdmException extends RuntimeException {

    public PdmException(String msg) {
        super(msg);
    }
}
