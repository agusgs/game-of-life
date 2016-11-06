package edu.unq.pconc.gameoflife.solution.coordenada;

import edu.unq.pconc.gameoflife.solution.GameOfLifeGrid;

import java.util.List;

public class CoordenadaMuerta extends Coordenada {
    public CoordenadaMuerta(int ancho, int alto) {
        super(ancho, alto);
    }

    @Override
    public void updateGeneracion(GameOfLifeGrid gameOfLifeGrid, List<Coordenada> celdasVivas) {
        List<Coordenada> vecinosVivos = getVecinosVivos(gameOfLifeGrid);
        if (vecinosVivos.size() == 3){
            celdasVivas.add(new CoordenadaViva(this.columna, this.fila));
        }else {
            celdasVivas.add(this);
        }
    }

    public boolean estaViva() {
        return false;
    }

    @Override
    public void revivi(GameOfLifeGrid gameOfLifeGrid) {
        gameOfLifeGrid.setCoordenada(new CoordenadaViva(this.columna, this.fila));
    }

    @Override
    public void morite(GameOfLifeGrid gameOfLifeGrid) {
        // Ya esta muerta no hace nada
    }
}
