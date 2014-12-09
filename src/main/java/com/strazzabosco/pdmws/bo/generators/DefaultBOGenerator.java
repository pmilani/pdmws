package com.strazzabosco.pdmws.bo.generators;

import org.springframework.stereotype.Component;

import com.strazzabosco.schemas.pdm_ws.BusinessOpportunity;
import com.strazzabosco.schemas.pdm_ws.DatiBO;

@Component
public class DefaultBOGenerator implements BusinessOpportunityGenerator {

    @Override
    public BusinessOpportunity transform(DatiBO meta) {
        BusinessOpportunity bo = new BusinessOpportunity();
        bo.setNumeroBo(meta.getNumeroBO());
        bo.setRiferimentoBo(meta.getRiferimento());
        bo.setCodiceCliente(meta.getCodiceCliente());
        bo.setRagioneSociale(meta.getRagioneSociale());
        return bo;
    }
}
