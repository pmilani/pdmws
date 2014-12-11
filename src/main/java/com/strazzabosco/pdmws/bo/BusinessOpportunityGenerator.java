package com.strazzabosco.pdmws.bo;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.strazzabosco.schemas.pdm_ws.BusinessOpportunity;
import com.strazzabosco.schemas.pdm_ws.DatiBO;
import com.strazzabosco.schemas.pdm_ws.LibreriaType;

@Component
public class BusinessOpportunityGenerator {
    
    /**
     * Defines a mapping: mapping name -> map of values
     * The map of values is a map : original value -> new value
     */
    public static final Map<String, Map<?, String>> dataMapping = new HashMap<String, Map<?,String>>();

    private static final String LIBRERIA_MAPPING = "Libreria";
    
    static {
        Map<LibreriaType, String> valueMappingLibreria = new HashMap<LibreriaType, String>();
        dataMapping.put(LIBRERIA_MAPPING, valueMappingLibreria);
        valueMappingLibreria.put(LibreriaType.ALP_80_DAT, "ALPAC");
        valueMappingLibreria.put(LibreriaType.PRO_80_DAT, "ALPAC");
        valueMappingLibreria.put(LibreriaType.CLI_80_DAT, "CLIMAPAC");
    }

    /**
     * Maps a value or returns the original if no mapping is defined
     */
    private <T> String getMappedValue(String mappingName, T originalValue) {
        Map<?, String> valueMapping = dataMapping.get(mappingName);
        if (valueMapping.containsKey(originalValue)) {
            return valueMapping.get(originalValue);
        } else {
            return originalValue.toString();
        }
    }
    
    public BusinessOpportunity transform(DatiBO meta) {
        String company = getMappedValue(LIBRERIA_MAPPING, meta.getLibreria());
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

    private String formatBoCode(int numeroBO) {
        return String.format("BO-%07d", numeroBO);
    }

    private String formatPathVault(String company, String boCode) {
        return String.format("$\\%s\\COMMESSE\\%s", company, boCode);
    }
    
}