package edu.unq.pconc.gameoflife.solution.coordenada;

import edu.unq.pconc.gameoflife.solution.GameOfLifeGrid;
import edu.unq.pconc.gameoflife.solution.exceptions.LaCoordenadaCaeFueraDelTableroException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Coordenada {
    int columna;
    int fila;

    public Coordenada(int columna, int fila){
        this.columna = columna;
        this.fila = fila;
    }

    public abstract void updateGeneracion(GameOfLifeGrid gameOfLifeGrid, List<Coordenada> celdasVivas);

    protected synchronized List<Coordenada> getVecinosVivos(GameOfLifeGrid gameOfLifeGrid) {
        List<Coordenada> vecinos = this.getVecinos(gameOfLifeGrid);
        return vecinos.stream().filter(Coordenada::estaViva).collect(Collectors.toList());
    }

    public abstract boolean estaViva();
    public abstract void revivi(GameOfLifeGrid gameOfLifeGrid);
    public abstract void morite(GameOfLifeGrid gameOfLifeGrid);

    public boolean estaEnPunto(int col, int row) {
        return this.columna == col && this.fila == row;
    }

    public boolean estaEnPlano(int ancho, int largo) {
        return this.columna <= ancho && this.fila <= largo;
    }

    protected List<Coordenada> getVecinos(GameOfLifeGrid gameOfLifeGrid) {
        List<Coordenada> vecinos = new ArrayList<>();
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

    private void agregarVecinoEn(GameOfLifeGrid gameOfLifeGrid, List<Coordenada> vecinos, int columna, int fila) {
        try {
            System.out.println("* Buscando vecino col=" + columna + " row=" + fila);
            vecinos.add(gameOfLifeGrid.getCoordenada(columna, fila));
            System.out.println("* Vevino encontrado");
        }catch (LaCoordenadaCaeFueraDelTableroException e){
            // No tiene vecino de ese lado por lo que no hago nada :)
        }
    }

    public boolean estaEnElMismoPunto(Coordenada coordenada) {
        return this.columna == coordenada.getColumna() && this.fila == coordenada.getFila();
    }

    public int getColumna() {
        return columna;
    }

    public int getFila() {
        return fila;
    }
}
