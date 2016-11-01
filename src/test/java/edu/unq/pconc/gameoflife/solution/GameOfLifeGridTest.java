package edu.unq.pconc.gameoflife.solution;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameOfLifeGridTest {

    @Test
    public void cuandoSeCreaNoTieneCeldasVivas(){
        GameOfLifeGrid grid = new GameOfLifeGrid(1, 2, 2);
        assertThat(grid.getCell(0,0)).isFalse();
    }

}