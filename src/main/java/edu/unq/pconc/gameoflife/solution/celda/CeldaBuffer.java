package edu.unq.pconc.gameoflife.solution.celda;

import edu.unq.pconc.gameoflife.solution.Coordenada;
import edu.unq.pconc.gameoflife.solution.GameOfLifeGrid;

import java.util.*;

public class CeldaBuffer {
    private GameOfLifeGrid gameOfLifeGrid;
    private Map<Coordenada, Celda> nuevaConfiguracion;
    private List<Celda> celdas;

    public CeldaBuffer(GameOfLifeGrid gameOfLifeGrid) {
        this.nuevaConfiguracion = new Hashtable<>();
        this.celdas = new ArrayList<>();
        this.gameOfLifeGrid = gameOfLifeGrid;
    }

    public void setNuevaConfiguracion(Map<Coordenada, Celda> nuevaConfiguracion) {
        this.nuevaConfiguracion = nuevaConfiguracion;
    }

    public synchronized void addAll(Collection<Celda> values) {
        this.celdas.addAll(values);
        this.notify();
    }

    public boolean tieneTrabajoPendiente() {
        return !this.celdas.isEmpty();
    }

    public synchronized void procesarCelda() {
        while(!tieneTrabajoPendiente()){
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Celda celda = this.celdas.get(0);
        this.celdas.remove(celda);

        celda.updateGeneracion(gameOfLifeGrid, nuevaConfiguracion);
        gameOfLifeGrid.celdaProcesada();

    }
}
