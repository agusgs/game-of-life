package edu.unq.pconc.gameoflife.solution.coordenada;

import edu.unq.pconc.gameoflife.solution.GameOfLifeGrid;

import java.util.List;

public class CoordenadaViva extends Coordenada {
    public CoordenadaViva(int columna, int fila) {
        super(columna, fila);
    }

    @Override
    public void updateGeneracion(GameOfLifeGrid gameOfLifeGrid, List<Coordenada> celdasVivas) {
        List<Coordenada> vecinos = getVecinosVivos(gameOfLifeGrid);
        if (vecinos.size()>=2 && vecinos.size() <=3){
            celdasVivas.add(this);
        }else {
            celdasVivas.add(new CoordenadaMuerta(this.columna, this.fila));
        }
    }

    @Override
    public boolean estaViva() {
        return true;
    }

    @Override
    public void revivi(GameOfLifeGrid gameOfLifeGrid) {
        // Ya esta viva no hace nada.
    }

    @Override
    public void morite(GameOfLifeGrid gameOfLifeGrid) {
        gameOfLifeGrid.setCoordenada(new CoordenadaMuerta(this.columna, this.fila));
    }
}
