package edu.unq.pconc.gameoflife.solution;

public class Coordenada {
    private int col;
    private int row;

    public Coordenada(int col, int row){
        this.col = col;
        this.row = row;
    }

    public boolean estaEn(int col, int row) {
        return this.col == col && this.row == row;
    }
}
