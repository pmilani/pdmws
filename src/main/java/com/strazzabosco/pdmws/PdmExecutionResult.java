package com.strazzabosco.pdmws;

import java.util.List;

public final class PdmExecutionResult {
    public final int exitCode;
    public final String message;

    public PdmExecutionResult(int exitCode, List<String> lines) {
        this.exitCode = exitCode;
        this.message = parsePdmRawOutput(lines);
    }

    private String parsePdmRawOutput(List<String> lines) {
        // TODO 
        return lines.get(0);
    }
}