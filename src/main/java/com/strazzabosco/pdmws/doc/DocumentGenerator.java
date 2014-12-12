package com.strazzabosco.pdmws.doc;

import com.strazzabosco.schemas.pdm_docs.PVString;
import com.strazzabosco.schemas.pdm_ws.DatiBO;

public abstract class DocumentGenerator<T> {

    public abstract T transform(DatiBO meta);
    
    static PVString newPVString(String name, String value) {
        PVString pvs = new PVString();
        pvs.setPropertyVault(name);
        pvs.setValue(value);
        return pvs;
    }

}
