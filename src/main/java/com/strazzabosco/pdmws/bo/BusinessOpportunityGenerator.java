package com.strazzabosco.pdmws.bo;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.strazzabosco.schemas.pdm_ws.BusinessOpportunity;
import com.strazzabosco.schemas.pdm_ws.DatiBO;

@Component
public class BusinessOpportunityGenerator {
    
    public static final Map<String, Map<String, String>> fieldMapping = new HashMap<String, Map<String,String>>();
    
    static {
        Map<String, String> valueMappingLibreria = new HashMap<String, String>();
        fieldMapping.put("Libreria", valueMappingLibreria);
        valueMappingLibreria.put("PRO80DAT", "ALPAC");
    }

    private String getMappedValue(String fieldName, String originalValue) {
        Map<String, String> valueMapping = fieldMapping.get(fieldName);
        if (valueMapping.containsKey(originalValue)) {
            return valueMapping.get(originalValue);
        } else {
            return originalValue;
        }
    }
    
    public BusinessOpportunity transform(DatiBO meta) {
        String company = getMappedValue("Libreria", meta.getLibreria());
        String boCode = formatBoCode(meta.getNumeroBO());
        
        BusinessOpportunity bo = new BusinessOpportunity();
        bo.setCompany(company);
        bo.setPathVault(formatPathVault(company, boCode));
        
        bo.setNumeroBo(boCode);
        bo.setRiferimentoBo(meta.getRiferimento());
        bo.setCodiceCliente(meta.getCodiceCliente());
        bo.setRagioneSociale(meta.getRagioneSociale());
        LocalDate docDate = LocalDate.parse(meta.getDataDocumento(), DateTimeFormat.forPattern("YYYYMMdd"));
        bo.setAnno(Integer.toString(docDate.getYear()));
        return bo;
    }

    private String formatBoCode(String numeroBO) {
        int n = Integer.valueOf(numeroBO);
        return String.format("BO-%07d", n);
    }

    private String formatPathVault(String company, String boCode) {
        return String.format("$\\%s\\COMMESSE\\%s", company, boCode);
    }
    
}