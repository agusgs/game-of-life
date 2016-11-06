package edu.unq.pconc.gameoflife.solution.exceptions;

public class LaCoordenadaBuscadaNoEstaEnElTablero extends RuntimeException{
    public LaCoordenadaBuscadaNoEstaEnElTablero(){
        super("La coordenada que esta buscando no esta en el tablero");
    }
}
