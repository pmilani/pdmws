package com.strazzabosco.pdmws.doc;

import org.springframework.stereotype.Component;

import com.strazzabosco.pdmws.bo.BusinessDataMapping;
import com.strazzabosco.pdmws.bo.BusinessDataMapping.MappingName;
import com.strazzabosco.schemas.pdm_docs.ControlloService;
import com.strazzabosco.schemas.pdm_ws.DatiBO;

@Component("controlloServiceGenerator")
public class ControlloServiceGenerator extends DocumentGenerator<ControlloService> {

    @Override
    public ControlloService transform(DatiBO meta) {
        String company = BusinessDataMapping.getMappedValue(MappingName.COMPANY_MAPPING, meta.getLibreria());
        String boCode = BusinessDataMapping.toBoCode(meta.getNumeroBO());

        ControlloService c = new ControlloService();
        c.setPath("documento.rtf"); //FIXME
        c.setCompany(company);
        c.setPathVault("\\ordini");
        c.setBO(boCode);
        
        c.setCodice(newPVString("Codice", "abc"));  // FIXME
        c.setTipoOrdine(newPVString("Tipo ordine", "A")); // FIXME
        c.setNumeroOrdine(newPVString("Numero ordine", "123")); // FIXME
        c.setOrdineIntercompany(newPVString("Ordine intercompany", meta.getOrdineIntercompany()));
        c.setDataInserimento(newPVString("Data inserimento", meta.getDataIngressoProposta()));
        c.setClienteCodice(newPVString("Codice cliente", meta.getCodiceCliente()));
        c.setClienteRagioneS(newPVString("Ragione sociale", meta.getRagioneSociale()));
        c.setDestinatarioMerce(newPVString("Destinatario merce", meta.getRagioneSociale()));
        
        return c;
    }

}
