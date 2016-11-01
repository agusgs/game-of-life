package edu.unq.pconc.gameoflife.solution;

import edu.unq.pconc.gameoflife.CellGrid;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameOfLifeGrid implements CellGrid {

    private ArrayList<Coordenada> celdasVivas;
    private int threads;
    private int ancho;
    private int largo;
    private int generations;
    private boolean[][] grid;

    public GameOfLifeGrid(int threads, int ancho, int largo){
        this.threads = threads;
        this.ancho = ancho;
        this.largo = largo;
        this.celdasVivas = new ArrayList<>();
    }

    public GameOfLifeGrid(){
        generations = 0;
    }

    @Override
    public boolean getCell(int col, int row) {
        return celdasVivas.parallelStream().findFirst().filter(coordenada -> coordenada.estaEn(col, row)).isPresent();
    }

    @Override
    public void setCell(int col, int row, boolean cell) {
        this.grid[col][row] = cell;
    }

    @Override
    public Dimension getDimension() {
        return new Dimension(this.grid.length, this.grid[0].length);
    }

    @Override
    public void resize(int cellCols, int cellRows) {}

    @Override
    public void setThreads(int threads) {
        this.threads = threads;
    }

    @Override
    public void clear() {
        int cellCols = this.grid.length;
        int cellRows = this.grid[0].length;
        this.grid = new boolean[cellCols][cellRows];
    }

    @Override
    public int getGenerations() {
        return generations;
    }

    @Override
    public void next() {
        //FALTA DIVIDIR LABURO ENTRE THREADS

        int cellCols = this.grid.length;
        int cellRows = this.grid[0].length;
        for (int c = 0; c < cellCols; c++) {
            for (int r = 0; r < cellRows; r++) {
                this.updateCellState(c,r);
            }
        }

        this.generations++;
    }

    private synchronized void updateCellState(int c, int r) {
        boolean cell = getCell(c,r);
        List<Boolean> neighbours = this.getNeighbours(c,r);
        long neighboursAlive = neighbours.stream().filter(n -> n).count();

        if (cell){
            if (neighboursAlive < 2 || neighboursAlive > 3){
                this.setCell(c,r,false);
            }
        }else{
            if (neighboursAlive == 3){
                this.setCell(c,r,true);
            }
        }
    }

    private List<Boolean> getNeighbours(int c, int r) {
        //FALTA CHEQUEAR QUE NO SE CAIGA DEL TABLERO

        List<Boolean> result = new ArrayList<>();
        result.add(getCell(c-1,r-1));
        result.add(getCell(c,r-1));
        result.add(getCell(c+1,r-1));
        result.add(getCell(c-1,r));
        result.add(getCell(c+1,r));
        result.add(getCell(c-1,r+1));
        result.add(getCell(c,r+1));
        result.add(getCell(c+1,r+1));

        return result;
    }
}