package com.strazzabosco.pdmws;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.Marshaller;
import org.springframework.stereotype.Component;

import com.strazzabosco.pdmws.bo.generators.BusinessOpportunityGenerator;
import com.strazzabosco.schemas.pdm_ws.BusinessOpportunity;
import com.strazzabosco.schemas.pdm_ws.DatiBO;

@Component
public class PdmWorkflow {
    public static final Logger LOG = Logger.getLogger(PdmWorkflow.class);

    private PdmProcessor pdmProcessor;
    private BusinessOpportunityGenerator boGenerator;
    
    private Marshaller marshaller;
    
    @Value("${directory.bo}")
    private String boOutputDir;

    private static int boSerialNumber = 1;

    @Autowired
    public PdmWorkflow(PdmProcessor pdmProcessor, Marshaller marshaller, BusinessOpportunityGenerator generator) {
        this.pdmProcessor = pdmProcessor;
        this.marshaller = marshaller;
        this.boGenerator = generator;
    }

    public File generateBoXml(DatiBO boMetadata) {
        String dateStr = new LocalDateTime().toString(DateTimeFormat.forPattern("YYYYMMddHHmm"));
        String fname = String.format("%s-%06d.xml", dateStr, boSerialNumber);
        File file = new File(FilenameUtils.concat(boOutputDir, fname));
        try {
            BusinessOpportunity bo = boGenerator.transform(boMetadata);
            FileWriter writer = new FileWriter(file);
            StreamResult result = new StreamResult(writer);
            marshaller.marshal(bo, result);
            boSerialNumber++;
            LOG.info("Generated "+ fname);
            
            pdmProcessor.invokePdm(file.getCanonicalPath());
            
            return file;
        } catch (IOException e) {
            throw new PdmException("Error creating BO "+ fname);
        }
    }
    
}
