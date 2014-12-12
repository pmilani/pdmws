package com.strazzabosco.pdmws.bo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.strazzabosco.schemas.pdm_ws.LibreriaType;

public class BusinessDataMapping {

    public enum MappingName {
        COMPANY_MAPPING
    }
    
    /**
     * Defines a mapping: mapping name -> map of values
     * The map of values is a map : original value -> new value
     */
    private static Map<MappingName, Map<?, String>> dataMapping = new HashMap<MappingName, Map<?,String>>();

    static {
        HashMap<MappingName, Map<?, String>> all = new HashMap<MappingName, Map<?,String>>();
        
        Map<LibreriaType, String> valueMappingCompany = new HashMap<LibreriaType, String>();
        all.put(MappingName.COMPANY_MAPPING, valueMappingCompany);
        valueMappingCompany.put(LibreriaType.ALP_80_DAT, "ALPAC");
        valueMappingCompany.put(LibreriaType.PRO_80_DAT, "ALPAC");
        valueMappingCompany.put(LibreriaType.CLI_80_DAT, "CLIMAPAC");
        
        dataMapping = Collections.unmodifiableMap(all);
    }

    /**
     * Maps a value or returns the original if no mapping is defined
     */
    public static <T> String getMappedValue(MappingName mappingName, T originalValue) {
        Map<?, String> valueMapping = dataMapping.get(mappingName);
        if (valueMapping.containsKey(originalValue)) {
            return valueMapping.get(originalValue);
        } else {
            return originalValue.toString();
        }
    }

    public static String toBoCode(int numeroBO) {
        return String.format("BO-%07d", numeroBO);
    }

}
