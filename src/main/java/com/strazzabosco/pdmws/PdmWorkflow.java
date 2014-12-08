package com.strazzabosco.pdmws;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Component
public class PdmWorkflow {
    public static final Logger LOG = Logger.getLogger(PdmWorkflow.class);

    private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private TransformerFactory transformerFactory =  TransformerFactory.newInstance();

    @Value("${directory.bo}")
    private String boOutputDir;

    private static int boSerialNumber = 1;

    
    public Document parseMetadataXml(byte[] xmlData) {
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new ByteArrayInputStream(xmlData));
        } catch (ParserConfigurationException e) {
            LOG.error("Cannot create xml parser", e);
            throw new PdmException("Error parsing metadataContent");
        } catch (SAXException e) {
            LOG.error("Cannot parse xml", e);
            throw new PdmException("Error parsing metadataContent");
        } catch (IOException e) {
            LOG.error("Cannot parse xml", e);
            throw new PdmException("Error parsing metadataContent");
        }
    }

    public void generateBoXml(Document metadataXml) {
        String dateStr = new LocalDate().toString(ISODateTimeFormat.basicDate());
        String fname = String.format("%s-%06d.xml", dateStr, boSerialNumber);
        File file = new File(FilenameUtils.concat(boOutputDir, fname));
        try {
            FileUtils.deleteQuietly(file);
            FileUtils.write(file, getStringFromDocument(metadataXml));
            boSerialNumber++;
        } catch (IOException e) {
            throw new PdmException("Error creating BO "+ fname);
        }
    }
    
    public String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch(TransformerException ex) {
            LOG.error("XML tranformation error", ex);
            throw new PdmException("Error transforming metadata to BO");
        }
    } 
}
