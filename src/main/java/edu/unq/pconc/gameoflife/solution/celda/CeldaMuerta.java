package edu.unq.pconc.gameoflife.solution.celda;

import edu.unq.pconc.gameoflife.solution.Coordenada;
import edu.unq.pconc.gameoflife.solution.GameOfLifeGrid;

import java.util.List;
import java.util.Map;

public class CeldaMuerta extends Celda {
    public CeldaMuerta(int ancho, int alto) {
        super(ancho, alto);
    }

    @Override
    public synchronized void updateGeneracion(GameOfLifeGrid gameOfLifeGrid, Map<Coordenada, Celda> tableroNuevo) {
        List<Celda> vecinosVivos = getVecinosVivos(gameOfLifeGrid);
        if (vecinosVivos.size() == 3){
            tableroNuevo.put(new Coordenada(this.columna, this.fila), new CeldaViva(this.columna, this.fila));
        }else {
            tableroNuevo.put(new Coordenada(this.columna, this.fila), this);
        }
    }

    public boolean estaViva() {
        return false;
    }
}
