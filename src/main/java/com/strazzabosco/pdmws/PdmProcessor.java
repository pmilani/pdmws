package com.strazzabosco.pdmws;

import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Invokes the external PDM doc loading process
 */
@Component
public class PdmProcessor {

    private static final int PROCESS_TIMEOUT = 60000;
    
    private DefaultExecutor executor;
    private ExecuteWatchdog watchdog;

    @Value("${pdm.command}")
    private String pdmCommand;
    
    public PdmProcessor() {
        executor = new DefaultExecutor();
        watchdog = new ExecuteWatchdog(PROCESS_TIMEOUT);
        executor.setWatchdog(watchdog);
    }

    public void invokePdm() {
        CommandLine cmdLine = new CommandLine("${pdmCommand}");
        cmdLine.addArgument("${xmlFile}");
        
        DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();
        try {
            executor.execute(cmdLine, handler);
            handler.waitFor();
        } catch (ExecuteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
