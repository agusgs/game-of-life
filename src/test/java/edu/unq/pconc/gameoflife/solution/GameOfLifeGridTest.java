package edu.unq.pconc.gameoflife.solution;

import edu.unq.pconc.gameoflife.solution.exceptions.LaCoordenadaCaeFueraDelTableroException;
import org.junit.Ignore;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.awt.*;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

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
    public void noSePuedeBuscarUnaCeldaQueNoEstaEnElTablero(){
        GameOfLifeGrid grid = new GameOfLifeGrid(1, 2, 2);

        try {
            grid.getCell(12,110);
            failBecauseExceptionWasNotThrown(LaCoordenadaCaeFueraDelTableroException.class);
        }catch (LaCoordenadaCaeFueraDelTableroException e){
            // OK
        }
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
    public void siAUnaCeldaVivaLeSeteoEstadoVivaNoCambia(){
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


    @Test
    public void laDimensionDeUnGridEsIgualASuAlturaPorSuAnchura(){
        int ancho = 12;
        int alto = 7;
        GameOfLifeGrid grid = new GameOfLifeGrid(1, ancho, alto);

        Dimension dimensionEsperada = new Dimension(12, 7);

        assertThat(grid.getDimension()).isEqualTo(dimensionEsperada);
    }

    @Test
    public void cambiarLaDimensionDeLaGrillaLaModifica(){
        int anchoOriginal = 12;
        int altoOriginal = 7;
        GameOfLifeGrid grid = new GameOfLifeGrid(1, anchoOriginal, altoOriginal);
        Dimension dimensionOriginal = new Dimension(12, 7);

        int anchoNuevo = 28;
        int altoNuevo = 12;
        grid.resize(anchoNuevo, altoNuevo);
        Dimension nuevaDimensionEsperada = new Dimension(anchoNuevo, altoNuevo);

        assertThat(grid.getDimension()).isNotEqualTo(dimensionOriginal);
        assertThat(grid.getDimension()).isEqualTo(nuevaDimensionEsperada);
    }

    @Test
    public void siAgrandoUnaGrillaLaConfiguracionOriginalDeCeldasVivasYMuertasSeMantiene(){
        GameOfLifeGrid grid = new GameOfLifeGrid(1, 2, 2);
        grid.setCell(0,0, true);
        grid.setCell(1,1, true);

        grid.resize(3, 3);

        assertThat(grid.getCell(0, 0)).isTrue();
        assertThat(grid.getCell(0, 1)).isFalse();
        assertThat(grid.getCell(1, 0)).isFalse();
        assertThat(grid.getCell(1, 1)).isTrue();
    }

    @Test
    public void siAchicoUnaGrillaSinComprometerCeldasVivasSeMantieneLaConfiguracionDeVivasYMuertas(){

        GameOfLifeGrid grid = new GameOfLifeGrid(1, 3, 3);
        grid.setCell(0,0, true);
        grid.setCell(2,2, true);

        grid.resize(2, 2);

        assertThat(grid.getCell(0, 0)).isTrue();
        assertThat(grid.getCell(0, 1)).isFalse();
        assertThat(grid.getCell(0, 2)).isFalse();
        assertThat(grid.getCell(1, 0)).isFalse();
        assertThat(grid.getCell(1, 1)).isFalse();
        assertThat(grid.getCell(1, 2)).isFalse();
        assertThat(grid.getCell(2, 0)).isFalse();
        assertThat(grid.getCell(2, 1)).isFalse();
        assertThat(grid.getCell(2, 2)).isTrue();
    }

    @Test
    public void siAchicoUnaGrillaComprometiendoCeldasVivasEstasDejanDeExistir(){
        GameOfLifeGrid grid = new GameOfLifeGrid(1, 4, 4);
        grid.setCell(0,0, true);
        grid.setCell(3,3, true);

        grid.resize(2, 2);

        assertThat(grid.getCell(0, 0)).isTrue();

        try {
            assertThat(grid.getCell(3, 3)).isTrue();
            failBecauseExceptionWasNotThrown(LaCoordenadaCaeFueraDelTableroException.class);
        }catch (LaCoordenadaCaeFueraDelTableroException e){
            // OK
        }
    }

    @Test
    public void cuandoHagoClearDeLaGrillaTodasLasCeldasPasanAMuertas(){
        GameOfLifeGrid grid = new GameOfLifeGrid(1, 2, 2);

        grid.setCell(0, 0, true);
        grid.setCell(0, 1, true);
        grid.setCell(1, 0, true);
        grid.setCell(1, 1, true);

        grid.clear();

        assertThat(grid.getCell(0, 0)).isFalse();
        assertThat(grid.getCell(0, 1)).isFalse();
        assertThat(grid.getCell(1, 0)).isFalse();
        assertThat(grid.getCell(1, 1)).isFalse();
    }

    @Test
    public void siTengoUnaCeldaSinVecinosVivosCuandoSeHaceNextMuereYHayUnaNuevaGeneracion(){
        GameOfLifeGrid grid = new GameOfLifeGrid(1, 2, 2);

        grid.setCell(0, 1, true);

        grid.next();

        assertThat(grid.getCell(0, 1)).isFalse();
        assertThat(grid.getCell(0, 0)).isFalse();
        assertThat(grid.getCell(1, 0)).isFalse();
        assertThat(grid.getCell(1, 1)).isFalse();
        assertThat(grid.getGenerations()).isEqualTo(1);
    }

    @Test
    public void siTengoUnaCeldaConMenosDeDosVecinosCuangoSeHaceNextMuereYHayUnaNuevaGeneracion(){
        GameOfLifeGrid grid = new GameOfLifeGrid(1, 2, 2);

        grid.setCell(0, 1, true);
        grid.setCell(0, 0, true);

        grid.next();

        assertThat(grid.getCell(0, 0)).isFalse();
        assertThat(grid.getCell(0, 1)).isFalse();
        assertThat(grid.getCell(1, 0)).isFalse();
        assertThat(grid.getCell(1, 1)).isFalse();
        assertThat(grid.getGenerations()).isEqualTo(1);
    }

    @Test
    public void SiTengoUnaCeldaVivaCon2VecinosCuandoSeHaceNextViveYHayUnaNuevaGeneracion(){
        GameOfLifeGrid grid = new GameOfLifeGrid(1, 2, 2);

        grid.setCell(0, 0, true);
        grid.setCell(0, 1, true);
        grid.setCell(1, 0, true);

        grid.next();

        assertThat(grid.getCell(0, 0)).isTrue();
        assertThat(grid.getCell(0, 1)).isTrue();
        assertThat(grid.getCell(1, 0)).isTrue();
        assertThat(grid.getCell(1, 1)).isTrue();
        assertThat(grid.getGenerations()).isEqualTo(1);
    }

    @Test
    public void SiTengoUnaCeldaVivaCon3VecinosCuandoSeHaceNextViveYHayUnaNuevaGeneracion(){
        GameOfLifeGrid grid = new GameOfLifeGrid(1, 2, 2);

        grid.setCell(0, 0, true);
        grid.setCell(0, 1, true);
        grid.setCell(1, 0, true);
        grid.setCell(1, 1, true);

        grid.next();

        assertThat(grid.getCell(0, 0)).isTrue();
        assertThat(grid.getCell(0, 1)).isTrue();
        assertThat(grid.getCell(1, 0)).isTrue();
        assertThat(grid.getCell(1, 1)).isTrue();
        assertThat(grid.getGenerations()).isEqualTo(1);
    }

    @Test
    public void siTengoUnaCeldaVivaConMasDe3VecinosMuereDeSobrepoblacion(){

        GameOfLifeGrid grid = new GameOfLifeGrid(1, 3, 3);

        grid.setCell(0, 2, true);
        grid.setCell(2, 2, true);
        grid.setCell(1, 1, true);
        grid.setCell(2, 0, true);
        grid.setCell(0, 0, true);

        grid.next();

        assertThat(grid.getCell(0, 0)).isFalse();
        assertThat(grid.getCell(0, 1)).isTrue();
        assertThat(grid.getCell(0, 2)).isFalse();
        assertThat(grid.getCell(1, 0)).isTrue();
        assertThat(grid.getCell(1, 1)).isFalse();
        assertThat(grid.getCell(1, 2)).isTrue();
        assertThat(grid.getCell(2, 0)).isFalse();
        assertThat(grid.getCell(2, 1)).isTrue();
        assertThat(grid.getCell(2, 2)).isFalse();
        assertThat(grid.getGenerations()).isEqualTo(1);
    }

    @Test
    public void siTengoUnaCeldaMuertaConTresVecinosVivosLaReviven(){
        GameOfLifeGrid grid = new GameOfLifeGrid(1, 3, 3);

        grid.setCell(2, 2, true);
        grid.setCell(2, 0, true);
        grid.setCell(0, 0, true);

        grid.next();

        assertThat(grid.getCell(0, 0)).isFalse();
        assertThat(grid.getCell(0, 1)).isFalse();
        assertThat(grid.getCell(0, 2)).isFalse();
        assertThat(grid.getCell(1, 0)).isFalse();
        assertThat(grid.getCell(1, 1)).isTrue();
        assertThat(grid.getCell(1, 2)).isFalse();
        assertThat(grid.getCell(2, 0)).isFalse();
        assertThat(grid.getCell(2, 1)).isFalse();
        assertThat(grid.getCell(2, 2)).isFalse();
        assertThat(grid.getGenerations()).isEqualTo(1);
    }

    @Test
    public void nextConcurrente(){
        GameOfLifeGrid grid = new GameOfLifeGrid(10, 60, 40);

        grid.setCell(1, 0, true);
        grid.setCell(1, 1, true);
        grid.setCell(1, 2, true);

        IntStream.range(0, 10).forEach(value -> {
            grid.next();
        });

        assertThat(grid.getCell(1, 0)).isTrue();
        assertThat(grid.getCell(1, 1)).isTrue();
        assertThat(grid.getCell(1, 2)).isTrue();
    }

    //------Tests para el 100%----------//

    @Test
    public void gameOfLifeGridConstructor(){
        GameOfLifeGrid grid = new GameOfLifeGrid();

        assertThat(grid.getGenerations()).isEqualTo(0);
        assertThat(grid.getDimension()).isEqualTo(new Dimension(0,0));
    }

    @Test
    public void setThreads(){
        GameOfLifeGrid grid = new GameOfLifeGrid();
        grid.setThreads(5);

        assertThat(grid.getThreadsSize()).isEqualTo(5);
    }

}