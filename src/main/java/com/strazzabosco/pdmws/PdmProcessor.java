package com.strazzabosco.pdmws;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Invokes the external PDM doc loading process
 */
@Component
public class PdmProcessor {
    private final static Logger LOG = Logger.getLogger(PdmProcessor.class);

    private static final int PROCESS_TIMEOUT = 60000;
    
    private DefaultExecutor executor;
    private ExecuteWatchdog watchdog;

    @Value("${pdm.command}")
    private String pdmCommand;
    
    public PdmProcessor() {
        executor = new DefaultExecutor();
        watchdog = new ExecuteWatchdog(PROCESS_TIMEOUT);
        executor.setWatchdog(watchdog);
        ExecuteStreamHandler streamHandler = new PdmStreamHandler();
        //executor.setStreamHandler(streamHandler);
        
        try {
            LOG.debug("Working dir: "+ executor.getWorkingDirectory().getCanonicalPath());
        } catch (IOException e) {}
    }
    
    public void invokePdm(String boPath) {
        CommandLine cmdLine = new CommandLine(pdmCommand);
        cmdLine.addArgument(boPath);
        
        try {
            int exitCode = executor.execute(cmdLine);
        } catch (ExecuteException e) {
            LOG.error("invokePdm failed", e);
            throw new PdmExecutionException();
        } catch (IOException e) {
            LOG.error("invokePdm failed", e);
            throw new PdmExecutionException();
        }
    }

    static class PdmStreamHandler implements ExecuteStreamHandler {

        @SuppressWarnings("unused")
        private OutputStream pdmInput;

        @SuppressWarnings("unused")
        private InputStream pdmOutput;
        
        @SuppressWarnings("unused")
        private InputStream pdmError;

        @Override
        public void setProcessErrorStream(InputStream is) throws IOException {
            pdmError = is;
        }

        @Override
        public void setProcessInputStream(OutputStream os) throws IOException {
            pdmInput = os;
        }

        @Override
        public void setProcessOutputStream(InputStream is) throws IOException {
            pdmOutput = is;
        }

        @Override
        public void start() throws IOException {
        }

        @Override
        public void stop() throws IOException {
        }
        
    }


}
