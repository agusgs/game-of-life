package edu.unq.pconc.gameoflife.solution.celda;

import edu.unq.pconc.gameoflife.solution.Coordenada;
import edu.unq.pconc.gameoflife.solution.GameOfLifeGrid;
import edu.unq.pconc.gameoflife.solution.exceptions.LaCoordenadaCaeFueraDelTableroException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Celda {
    int columna;
    int fila;

    public Celda(int columna, int fila){
        this.columna = columna;
        this.fila = fila;
    }

    public abstract void updateGeneracion(GameOfLifeGrid gameOfLifeGrid, Map<Coordenada, Celda> tableroNuevo);

    protected synchronized List<Celda> getVecinosVivos(GameOfLifeGrid gameOfLifeGrid) {
        List<Celda> vecinos = this.getVecinos(gameOfLifeGrid);
        return vecinos.stream().filter(Celda::estaViva).collect(Collectors.toList());
    }

    public abstract boolean estaViva();

    protected List<Celda> getVecinos(GameOfLifeGrid gameOfLifeGrid) {
        List<Celda> vecinos = new ArrayList<>();
        int columna;
        int fila;

        columna = this.columna;
        fila = this.fila + 1;
        agregarVecinoEn(gameOfLifeGrid, vecinos, columna, fila);

        columna = this.columna + 1;
        fila = this.fila + 1;
        agregarVecinoEn(gameOfLifeGrid, vecinos, columna, fila);

        columna = this.columna + 1;
        fila = this.fila;
        agregarVecinoEn(gameOfLifeGrid, vecinos, columna, fila);

        columna = this.columna + 1;
        fila = this.fila - 1;
        agregarVecinoEn(gameOfLifeGrid, vecinos, columna, fila);

        columna = this.columna;
        fila = this.fila - 1;
        agregarVecinoEn(gameOfLifeGrid, vecinos, columna, fila);

        columna = this.columna -1;
        fila = this.fila - 1;
        agregarVecinoEn(gameOfLifeGrid, vecinos, columna, fila);

        columna = this.columna -1;
        fila = this.fila;
        agregarVecinoEn(gameOfLifeGrid, vecinos, columna, fila);

        columna = this.columna - 1;
        fila = this.fila + 1;
        agregarVecinoEn(gameOfLifeGrid, vecinos, columna, fila);

        return vecinos;
    }

    private void agregarVecinoEn(GameOfLifeGrid gameOfLifeGrid, List<Celda> vecinos, int columna, int fila) {
        try {
            vecinos.add(gameOfLifeGrid.getCeldaEnCoordenada(columna, fila));
        }catch (LaCoordenadaCaeFueraDelTableroException e){
            // No tiene vecino de ese lado por lo que no hago nada :)
        }
    }
}
