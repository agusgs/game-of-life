package edu.unq.pconc.gameoflife.solution;

import edu.unq.pconc.gameoflife.solution.celda.Celda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ThreadRunner {
    private List<Celda> celdas;
    private Thread thread;

    public ThreadRunner(){
        celdas = new ArrayList<>();
    }

    public void add(Celda celda) {
        this.celdas.add(celda);
    }

    public void start(GameOfLifeGrid gameOfLifeGrid, Map<Coordenada, Celda> configuracionNueva) {
        this.thread = new Thread(
                () -> celdas.stream().forEach(coordenada -> coordenada.updateGeneracion(gameOfLifeGrid, configuracionNueva)));
        thread.start();
    }

    public void joinThread() {
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
