package edu.unq.pconc.gameoflife.solution.celda;

import edu.unq.pconc.gameoflife.solution.Coordenada;
import edu.unq.pconc.gameoflife.solution.GameOfLifeGrid;

import java.util.List;
import java.util.Map;

public class CeldaViva extends Celda {
    public CeldaViva(int columna, int fila) {
        super(columna, fila);
    }

    @Override
    public synchronized void updateGeneracion(GameOfLifeGrid gameOfLifeGrid, Map<Coordenada, Celda> tableroNuevo) {
        List<Celda> vecinos = getVecinosVivos(gameOfLifeGrid);

        if (vecinos.size()>=2 && vecinos.size() <=3){
            tableroNuevo.put(new Coordenada(this.columna, this.fila), this);
        }else {
            tableroNuevo.put(new Coordenada(this.columna, this.fila), new CeldaMuerta(this.columna, this.fila));
        }
    }

    @Override
    public boolean estaViva() {
        return true;
    }
}
