package edu.unq.pconc.gameoflife.solution.exceptions;

public class LaCeldaQueSeQuiereMatarEstaMuertaException extends RuntimeException {
    public LaCeldaQueSeQuiereMatarEstaMuertaException(){
        super("La celda viva que se quiere matar esta muerta");
    }
}
