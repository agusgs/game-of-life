package edu.unq.pconc.gameoflife.solution;

public class Coordenada {
    private int coordenadaAncho;
    private int coordenadaAlto;

    public Coordenada(int coordenadaAncho, int coordenadaAlto) {
        this.coordenadaAncho = coordenadaAncho;
        this.coordenadaAlto = coordenadaAlto;
    }

    @Override
    public int hashCode() {
        return 31 * coordenadaAncho + coordenadaAlto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordenada coordenada = (Coordenada) o;

        return this.coordenadaAncho == coordenada.coordenadaAncho && this.coordenadaAlto == coordenada.coordenadaAlto;
    }
}
