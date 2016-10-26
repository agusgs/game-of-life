package edu.unq.pconc.gameoflife.solution;

import edu.unq.pconc.gameoflife.CellGrid;

import java.awt.*;

public class GameOfLifeGrid implements CellGrid{

    private int threads;
    private int generations;

    @Override
    public boolean getCell(int col, int row) {
        return false;
    }

    @Override
    public void setCell(int col, int row, boolean cell) {

    }

    @Override
    public Dimension getDimension() {
        return null;
    }

    @Override
    public void resize(int cellCols, int cellRows) {

    }

    @Override
    public void setThreads(int threads) {
        this.threads = threads;
    }

    @Override
    public void clear() {

    }

    @Override
    public int getGenerations() {
        return generations;
    }

    @Override
    public void next() {

    }
}
