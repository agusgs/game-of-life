package edu.unq.pconc.gameoflife.solution;

import edu.unq.pconc.gameoflife.solution.celda.CeldaBuffer;

public class ThreadRunner extends Thread {
    private final CeldaBuffer buffer;

    public ThreadRunner(CeldaBuffer buffer){
        this.buffer = buffer;
    }

    public void run() {
        while (true) {
            buffer.procesarCelda();
        }
    }
}
