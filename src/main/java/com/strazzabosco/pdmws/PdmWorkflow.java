package com.strazzabosco.pdmws;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
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
import com.strazzabosco.schemas.pdm_ws.StoreMetadataRequest.MetadataContent;

@Component
public class PdmWorkflow {
    public static final Logger LOG = Logger.getLogger(PdmWorkflow.class);

    private MetadataRepository metadataRepository;
    private BusinessOpportunityGenerator boGenerator;
    private PdmProcessor pdmProcessor;
    
    private Marshaller marshaller;
    
    @Value("${directory.bo}")
    private String boOutputDir;

    private static int boSerialNumber = 1;

    @Autowired
    public PdmWorkflow(Marshaller marshaller, MetadataRepository metadataRepository, BusinessOpportunityGenerator generator,
            PdmProcessor pdmProcessor) {
        this.marshaller = marshaller;
        this.metadataRepository = metadataRepository;
        this.boGenerator = generator;
        this.pdmProcessor = pdmProcessor;
    }
    
    @PostConstruct
    public void afterInjection() {
        try {
            LOG.info("BO output dir: "+ new File(boOutputDir).getCanonicalPath());
        } catch (IOException e) {}
    }

    public void addMetadata(String id, MetadataContent metadataContent) {
        metadataRepository.addMetadata(id, metadataContent);
    }
    
    private String getBoOutputPathname() {
        LocalDateTime now = new LocalDateTime();
        String pathStr = now.toString(DateTimeFormat.forPattern("YYYYMMdd"));
        String fileStr = now.toString(DateTimeFormat.forPattern("YYYYMMddHHmm"));
        return String.format("%s/%s-%06d.xml", pathStr, fileStr, boSerialNumber);
    }

    public PdmExecutionResult generateBoXml(String metadataId) {
        MetadataContent metadata = metadataRepository.getMetadata(metadataId);
        BusinessOpportunity bo = boGenerator.transform(metadata.getDatiBO());
        String fname = getBoOutputPathname();
        File file = new File(FilenameUtils.concat(boOutputDir, fname));
        try {
            FileUtils.forceMkdir(file.getParentFile());
            FileWriter writer = new FileWriter(file);
            StreamResult result = new StreamResult(writer);
            marshaller.marshal(bo, result);
            boSerialNumber++;
            LOG.info("Generated "+ file.getName());
            
            return pdmProcessor.executePdm(file.getCanonicalPath());
            
        } catch (IOException e) {
            LOG.error("error writing "+ fname);
            throw new PdmException("Error creating BO");
        }
    }
    
}
