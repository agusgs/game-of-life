package edu.unq.pconc.gameoflife.solution;

import edu.unq.pconc.gameoflife.solution.coordenada.Coordenada;

import java.util.ArrayList;
import java.util.List;

public class ThreadRunner {
    private List<Coordenada> celdas;
    private boolean termino;

    public ThreadRunner(){
        termino = false;
        celdas = new ArrayList<>();
    }

    public void add(Coordenada coordenada) {
        this.celdas.add(coordenada);
    }

    public void start(GameOfLifeGrid gameOfLifeGrid, List<Coordenada> configuracionNueva) {
        this.termino = false;
        Thread thread = new Thread(
                () -> celdas.stream().forEach(
                        coordenada -> {
                            coordenada.updateGeneracion(gameOfLifeGrid, configuracionNueva);
                            this.termino = true;
                        }
                ));
        thread.start();
    }

    public boolean termino() {
        return termino;
    }
}
