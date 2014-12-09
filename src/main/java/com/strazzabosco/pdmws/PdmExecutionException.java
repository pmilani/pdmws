package com.strazzabosco.pdmws;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SuppressWarnings("serial")
@SoapFault(faultCode=FaultCode.SERVER, faultStringOrReason="Failed executing PDM process")
public class PdmExecutionException extends RuntimeException {

}
