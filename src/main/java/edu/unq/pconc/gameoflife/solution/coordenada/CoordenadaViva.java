package edu.unq.pconc.gameoflife.solution.coordenada;

import edu.unq.pconc.gameoflife.solution.GameOfLifeGrid;

import java.util.List;

public class CoordenadaViva extends Coordenada {
    public CoordenadaViva(int columna, int fila) {
        super(columna, fila);
    }

    @Override
    public void updateGeneracion(GameOfLifeGrid gameOfLifeGrid, List<Coordenada> celdasVivas) {
        System.out.println("* Update para coordenada viva");
        System.out.println("* Obteniendo vecinos");
        List<Coordenada> vecinos = getVecinosVivos(gameOfLifeGrid);
        if (vecinos.size()>=2 && vecinos.size() <=3){
            System.out.println("* Vecinos entre 2 y 3 se agrega coordenada viva");
            celdasVivas.add(this);
        }else {
            System.out.println("* Vecinos menor a 2 o mayor a 3 se agrega coordenada muerta");
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
