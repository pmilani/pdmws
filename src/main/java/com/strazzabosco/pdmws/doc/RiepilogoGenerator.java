package com.strazzabosco.pdmws.doc;

import org.springframework.stereotype.Component;

import com.strazzabosco.pdmws.bo.BusinessDataMapping;
import com.strazzabosco.pdmws.bo.BusinessDataMapping.MappingName;
import com.strazzabosco.schemas.pdm_docs.Riepilogo;
import com.strazzabosco.schemas.pdm_ws.DatiBO;

@Component("riepilogoGenerator")
public class RiepilogoGenerator extends DocumentGenerator<Riepilogo> {

    @Override
    public Riepilogo transform(DatiBO meta) {
        String company = BusinessDataMapping.getMappedValue(MappingName.COMPANY_MAPPING, meta.getLibreria());
        String boCode = BusinessDataMapping.toBoCode(meta.getNumeroBO());

        Riepilogo r = new Riepilogo();
        r.setPath("documento.rtf"); //FIXME
        r.setCompany(company);
        r.setPathVault("\\ordini");
        r.setBO(boCode);
        
        r.setCodice(newPVString("Codice", "abc"));  // FIXME
        r.setNumeroOrdine(newPVString("Numero ordine", "123")); // FIXME
        r.setOrdineIntercompany(newPVString("Ordine intercompany", meta.getOrdineIntercompany()));
        r.setDataInserimento(newPVString("Data inserimento", meta.getDataIngressoProposta()));
        r.setClienteCodice(newPVString("Codice cliente", meta.getCodiceCliente()));
        r.setClienteRagioneS(newPVString("Ragione sociale", meta.getRagioneSociale()));
        r.setDataRicCons(newPVString("Data richiesta consegna", meta.getDataRichiestaConsegna()));
        r.setDataConfCons(newPVString("Data conferma consegna", meta.getDataConfermaConsegna()));
        
        return r;
    }

}
