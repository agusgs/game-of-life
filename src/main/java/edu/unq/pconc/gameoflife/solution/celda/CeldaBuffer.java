package edu.unq.pconc.gameoflife.solution.celda;

import edu.unq.pconc.gameoflife.solution.Coordenada;
import edu.unq.pconc.gameoflife.solution.GameOfLifeGrid;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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

    public synchronized void add(Celda celda) {
        this.celdas.add(celda);
        this.notify();
    }

    public boolean tieneTrabajoPendiente() {
        return !this.celdas.isEmpty();
    }

    public void procesarCelda() {
        this.pop().updateGeneracion(gameOfLifeGrid, nuevaConfiguracion);
    }

    private synchronized Celda pop() {
        while(!tieneTrabajoPendiente()){
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Celda celda = this.celdas.get(0);
        this.celdas.remove(celda);
        return celda;
    }
}
