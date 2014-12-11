package com.strazzabosco.pdmws;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;
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
    
    @Value("${pdm.command}")
    private String pdmCommand;

    
    private Executor createExecutor(CollectingLogOutputStream output) {
        DefaultExecutor executor = new DefaultExecutor();
        executor.setWatchdog(new ExecuteWatchdog(PROCESS_TIMEOUT));
        executor.setStreamHandler(new PumpStreamHandler(output));
        
        try {
            LOG.debug("Working dir: "+ executor.getWorkingDirectory().getCanonicalPath());
        } catch (IOException e) {}
        
        return executor;
    }
    
    public PdmExecutionResult executePdm(String pathname) {
        CommandLine cmdLine = new CommandLine(pdmCommand);
        cmdLine.addArgument(pathname);

        CollectingLogOutputStream output = new CollectingLogOutputStream();
        Executor executor = createExecutor(output);
        
        try {
            // note this is synchronous for easier manipulation
            int exitCode = executor.execute(cmdLine);
            return new PdmExecutionResult(exitCode, output.getLines());
        } catch (ExecuteException e) {
            LOG.error("invokePdm failed", e);
            throw new PdmExecutionException();
        } catch (IOException e) {
            LOG.error("invokePdm failed", e);
            throw new PdmExecutionException();
        }
    }
    
    private static class CollectingLogOutputStream extends LogOutputStream {
        private final List<String> lines = new LinkedList<String>();
        
        @Override protected void processLine(String line, int level) {
            lines.add(line);
        }
        
        public List<String> getLines() {
            return lines;
        }
    }

}
