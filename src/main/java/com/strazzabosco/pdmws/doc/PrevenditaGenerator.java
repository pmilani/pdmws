package com.strazzabosco.pdmws.doc;

import org.springframework.stereotype.Component;

import com.strazzabosco.pdmws.bo.BusinessDataMapping;
import com.strazzabosco.pdmws.bo.BusinessDataMapping.MappingName;
import com.strazzabosco.schemas.pdm_docs.Prevendita;
import com.strazzabosco.schemas.pdm_ws.DatiBO;

@Component("prevenditaGenerator")
public class PrevenditaGenerator extends DocumentGenerator<Prevendita> {

    @Override
    public Prevendita transform(DatiBO meta) {
        String company = BusinessDataMapping.getMappedValue(MappingName.COMPANY_MAPPING, meta.getLibreria());
        String boCode = BusinessDataMapping.toBoCode(meta.getNumeroBO());

        Prevendita p = new Prevendita();
        p.setPath("documento.rtf"); //FIXME
        p.setCompany(company);
        p.setPathVault(calcPathVault(meta.getDocumento()));
        p.setBO(boCode);
        
        p.setCodice(newPVString("Codice", calcCodice(meta)));
        p.setTCE(true);
        p.setGalileo(true);
        p.setData(meta.getDataDocumento());
        p.setRiferimento(newPVString("Riferimento", meta.getRiferimento()));
        p.setRiferimentoTecnico(newPVString("Riferimento tecnico", meta.getRiferimentoTecnico()));
        p.setClienteCodice(newPVString("Codice cliente", meta.getCodiceCliente()));
        p.setClienteRagioneS(newPVString("Ragione sociale", meta.getRagioneSociale()));
        p.setFirma(false);
        
        return p;
    }

    private String calcPathVault(String documento) {
        if ("OF".equals(documento)) {
            return "\\offerte";
        } else if ("OC".equals(documento)) {
            return "\\ordini";
        } else {
            return "\\non_categorizzato";
        }
    }

    private String calcCodice(DatiBO meta) {
        return String.format("%s01%s%s",
                meta.getDocumento(),
                meta.getTipoDocumento(),
                meta.getNumeroDocumento());
    }

}
