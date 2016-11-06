package edu.unq.pconc.gameoflife.solution.coordenada;

import edu.unq.pconc.gameoflife.solution.GameOfLifeGrid;

import java.util.List;

public class CoordenadaMuerta extends Coordenada {
    public CoordenadaMuerta(int ancho, int alto) {
        super(ancho, alto);
    }

    @Override
    public void updateGeneracion(GameOfLifeGrid gameOfLifeGrid, List<Coordenada> celdasVivas) {
        System.out.println("* Update para coordenada muerta");
        System.out.println("* Obteniendo vecinos");
        List<Coordenada> vecinosVivos = getVecinosVivos(gameOfLifeGrid);
        if (vecinosVivos.size() == 3){
            System.out.println("* Vecinos igual a 3 se agrega coordenada viva");
            celdasVivas.add(new CoordenadaViva(this.columna, this.fila));
        }else {
            System.out.println("* Vecinos distingo de 3 se agrega coordenada muerta");
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
