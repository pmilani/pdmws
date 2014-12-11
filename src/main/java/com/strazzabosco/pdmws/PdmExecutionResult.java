package com.strazzabosco.pdmws;

import java.util.List;

public final class PdmExecutionResult {
    public final int exitCode;
    public final boolean acquired;
    public final String message;

    public PdmExecutionResult(int exitCode, List<String> outputLines) {
        this.exitCode = exitCode;
        if (outputLines.isEmpty()) {
            acquired = false;
            message = "";
        } else {
            message = outputLines.get(0);
            acquired = Boolean.parseBoolean(outputLines.get(0));
        }
    }

}