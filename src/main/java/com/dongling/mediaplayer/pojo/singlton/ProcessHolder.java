package com.dongling.mediaplayer.pojo.singlton;

import java.util.Objects;

public class ProcessHolder {

    private static Process process = null;

    public synchronized static void start(ProcessBuilder processBuilder) throws Exception {
        if (Objects.nonNull(process) && process.isAlive()) {
            process.destroy();
        }
        process = processBuilder.start();
    }

    public synchronized static void stopProcess() throws Exception {
        if (Objects.nonNull(process) && process.isAlive()) {
            process.destroy();
        }
        process = null;
    }

}
