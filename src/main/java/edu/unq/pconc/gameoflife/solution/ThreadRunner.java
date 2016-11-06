package edu.unq.pconc.gameoflife.solution;

import edu.unq.pconc.gameoflife.solution.coordenada.Coordenada;

import java.util.ArrayList;
import java.util.List;

public class ThreadRunner {
    private List<Coordenada> celdas;
    private Thread thread;

    public ThreadRunner(){
        celdas = new ArrayList<>();
    }

    public void add(Coordenada coordenada) {
        this.celdas.add(coordenada);
    }

    public void start(GameOfLifeGrid gameOfLifeGrid, List<Coordenada> configuracionNueva) {
        this.thread = new Thread(
                () -> celdas.stream().forEach(coordenada -> {
                    System.out.println("* Updeteando coordenada x=" + coordenada.getColumna() + " y=" + coordenada.getFila());
                    System.out.println("*");

                    coordenada.updateGeneracion(gameOfLifeGrid, configuracionNueva);

                    System.out.println("*");
                    System.out.println("* Fin del update");

                }));
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
