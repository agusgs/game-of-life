package edu.unq.pconc.gameoflife.solution;

import edu.unq.pconc.gameoflife.solution.exceptions.LaCoordenadaCaeFueraDelTableroException;

import java.util.ArrayList;
import java.util.List;

public class Coordenada {
    private int columna;
    private int fila;

    public Coordenada(int columna, int fila){
        this.columna = columna;
        this.fila = fila;
    }

    public boolean estaEnPunto(int col, int row) {
        return this.columna == col && this.fila == row;
    }

    public boolean estaEnPlano(int ancho, int largo) {
        return this.columna <= ancho || this.fila <= largo;
    }

    public void updateGeneracion(GameOfLifeGrid gameOfLifeGrid, List<Coordenada> celdasVivas) {
        List<Coordenada> vecinos = getVecinos(gameOfLifeGrid);
        if (vecinos.size()>=2 && vecinos.size() <=3){
            celdasVivas.add(this);
        }
    }

    private List<Coordenada> getVecinos(GameOfLifeGrid gameOfLifeGrid) {
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
            if(gameOfLifeGrid.getCell(columna, fila)){
                vecinos.add(new Coordenada(columna, fila));
            }
        }catch (LaCoordenadaCaeFueraDelTableroException e){
            // No tiene vecino de ese lado por lo que no hago nada :)
        }
    }

}
