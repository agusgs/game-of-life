package edu.unq.pconc.gameoflife.solution.exceptions;

public class LaCoordenadaCaeFueraDelTableroException extends RuntimeException {
    public LaCoordenadaCaeFueraDelTableroException(Integer ancho, Integer largo){
        super("La coordenada x=" + ancho.toString() + " y=" + largo.toString() + " no esta en el tablero");
    }
}
