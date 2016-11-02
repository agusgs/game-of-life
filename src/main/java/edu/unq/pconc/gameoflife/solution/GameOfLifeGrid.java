package edu.unq.pconc.gameoflife.solution;

import edu.unq.pconc.gameoflife.CellGrid;
import edu.unq.pconc.gameoflife.solution.exceptions.LaCeldaQueSeQuiereMatarEstaMuertaException;
import edu.unq.pconc.gameoflife.solution.exceptions.LaCoordenadaCaeFueraDelTableroException;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameOfLifeGrid implements CellGrid {

    private ArrayList<Coordenada> celdasVivas;
    private int threads;
    private int ancho;
    private int alto;
    private int generations;
    private boolean[][] grid;

    public GameOfLifeGrid(int threads, int ancho, int alto){
        this.threads = threads;
        this.ancho = ancho;
        this.alto = alto;
        this.celdasVivas = new ArrayList<>();
    }

    public GameOfLifeGrid(){
        generations = 0;
    }

    @Override
    public boolean getCell(int col, int row) {
        validarCoordenadaEnTablero(col, row);
        return busquedaDeCoordenada(col, row).isPresent();
    }

    private void validarCoordenadaEnTablero(int columna, int fila) {
        if(columna > this.ancho || fila > this.alto){
            throw new LaCoordenadaCaeFueraDelTableroException(columna, fila);
        }
    }

    @Override
    public void setCell(int col, int row, boolean estadoASetear) {
        boolean estadoExistente = this.getCell(col, row);
        if(estadoASetear && !estadoExistente){
            crearNuevaCeldaViva(col, row);
        }
        if(!estadoASetear && estadoExistente){
            borrarCeldaViva(col, row);
        }
    }

    private void borrarCeldaViva(int col, int row) {
        celdasVivas.remove(
                busquedaDeCoordenada(col, row)
                        .orElseThrow(LaCeldaQueSeQuiereMatarEstaMuertaException::new)
        );
    }

    private void crearNuevaCeldaViva(int col, int row) {
        celdasVivas.add(new Coordenada(col, row));
    }

    private Optional<Coordenada> busquedaDeCoordenada(int col, int row) {
        return celdasVivas.parallelStream().filter(coordenada -> coordenada.estaEnPunto(col, row)).findFirst();
    }

    @Override
    public Dimension getDimension() {
        return new Dimension(ancho, alto);
    }

    @Override
    public void resize(int ancho, int largo) {
        eliminarCoordenadasExcedentes(ancho, largo);
        this.ancho = ancho;
        this.alto = largo;
    }

    private void eliminarCoordenadasExcedentes(int ancho, int largo) {
        if(ancho < this.ancho || largo < this.alto){
            this.celdasVivas.removeAll(
                    this.celdasVivas
                            .parallelStream()
                            .filter(coordenada -> !coordenada.estaEnPlano(ancho, largo))
                            .collect(Collectors.toList())
            );
        }
    }

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