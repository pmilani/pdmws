package com.strazzabosco.pdmws.bo.generators;

import com.strazzabosco.schemas.pdm_ws.BusinessOpportunity;
import com.strazzabosco.schemas.pdm_ws.DatiBO;

public interface BusinessOpportunityGenerator {

    public abstract BusinessOpportunity transform(DatiBO meta);

}