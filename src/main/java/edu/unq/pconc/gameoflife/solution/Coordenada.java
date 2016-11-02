package edu.unq.pconc.gameoflife.solution;

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
}
