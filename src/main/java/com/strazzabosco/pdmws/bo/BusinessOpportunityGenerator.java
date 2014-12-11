package com.strazzabosco.pdmws.bo;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.strazzabosco.pdmws.bo.BusinessDataMapping.MappingName;
import com.strazzabosco.schemas.pdm_docs.BusinessOpportunity;
import com.strazzabosco.schemas.pdm_docs.PVString;
import com.strazzabosco.schemas.pdm_ws.DatiBO;

@Component
public class BusinessOpportunityGenerator {
    
    private static PVString newPVString(String name, String value) {
        PVString pvs = new PVString();
        pvs.setPropertyVault(name);
        pvs.setValue(value);
        return pvs;
    }
    
    public BusinessOpportunity transform(DatiBO meta) {
        String company = BusinessDataMapping.getMappedValue(MappingName.COMPANY_MAPPING, meta.getLibreria());
        String boCode = BusinessDataMapping.toBoCode(meta.getNumeroBO());
        
        BusinessOpportunity bo = new BusinessOpportunity();
        bo.setCompany(company);
        bo.setPathVault(formatPathVault(company, boCode));
        
        bo.setNumeroBo(newPVString("Numero BO", boCode));
        bo.setRiferimentoBo(newPVString("Riferimento BO", meta.getRiferimento()));
        bo.setCodiceCliente(newPVString("Codice Cliente", meta.getCodiceCliente()));
        bo.setRagioneSociale(newPVString("Ragione Sociale",meta.getRagioneSociale()));
        
        LocalDate docDate = LocalDate.parse(meta.getDataDocumento(), DateTimeFormat.forPattern("YYYYMMdd"));
        bo.setAnno(Integer.toString(docDate.getYear()));
        return bo;
    }

    private String formatPathVault(String company, String boCode) {
        return String.format("$\\%s\\COMMESSE\\%s", company, boCode);
    }
    
}