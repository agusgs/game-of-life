package edu.unq.pconc.gameoflife.solution;

public class Coordenada {
    private int col;
    private int row;

    public Coordenada(){

    }
    public boolean estaEn(int col, int row) {
        return this.col == col && this.row == row;
    }
}
