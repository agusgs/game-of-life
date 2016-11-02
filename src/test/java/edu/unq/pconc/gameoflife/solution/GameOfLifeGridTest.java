package edu.unq.pconc.gameoflife.solution;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameOfLifeGridTest {

    @Test
    public void cuandoSeCreaNoTieneCeldasVivas(){
        GameOfLifeGrid grid = new GameOfLifeGrid(1, 2, 2);

        assertThat(grid.getCell(0,0)).isFalse();
        assertThat(grid.getCell(0,1)).isFalse();
        assertThat(grid.getCell(1,0)).isFalse();
        assertThat(grid.getCell(1,1)).isFalse();
    }

    @Test
    public void siAUnaCeldaMuertaLeSeteoEstadoVivaPasaAEstarViva(){
        GameOfLifeGrid grid = new GameOfLifeGrid(1, 2, 2);


        assertThat(grid.getCell(0,1)).isFalse();
        grid.setCell(0, 1, true);
        assertThat(grid.getCell(0,1)).isTrue();
    }

    @Test
    public void siAUnaCeldaVivaLeSeteoEstadoMuertaPasaAEstarMuerta(){
        GameOfLifeGrid grid = new GameOfLifeGrid(1, 2, 2);

        grid.setCell(0, 1, true);
        assertThat(grid.getCell(0,1)).isTrue();

        grid.setCell(0, 1, false);
        assertThat(grid.getCell(0,1)).isFalse();
    }

    @Test
    public void siAUnaCeldaVivaLeSeteoEstadoVidaNoCambia(){
        GameOfLifeGrid grid = new GameOfLifeGrid(1, 2, 2);

        grid.setCell(0, 1, true);
        assertThat(grid.getCell(0,1)).isTrue();

        grid.setCell(0, 1, true);
        assertThat(grid.getCell(0,1)).isTrue();
    }

    @Test
    public void siAUnaCeldaMuertaLeSeteoEstadoMuertaNoCambia(){
        GameOfLifeGrid grid = new GameOfLifeGrid(1, 2, 2);

        grid.setCell(0, 1, false);
        assertThat(grid.getCell(0,1)).isFalse();

        grid.setCell(0, 1, false);
        assertThat(grid.getCell(0,1)).isFalse();
    }

}