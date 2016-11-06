package edu.unq.pconc.gameoflife.solution;

import edu.unq.pconc.gameoflife.CellGrid;
import edu.unq.pconc.gameoflife.solution.coordenada.Coordenada;
import edu.unq.pconc.gameoflife.solution.coordenada.CoordenadaMuerta;
import edu.unq.pconc.gameoflife.solution.exceptions.LaCoordenadaBuscadaNoEstaEnElTablero;
import edu.unq.pconc.gameoflife.solution.exceptions.LaCoordenadaCaeFueraDelTableroException;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameOfLifeGrid implements CellGrid {

    private List<Coordenada> celdas;
    private List<ThreadRunner> threads;
    private int ancho;
    private int alto;
    private int generations;
    private int threadActual;

    public GameOfLifeGrid(int threads, int ancho, int alto){
        this.threads = crearThreads(threads);
        this.threadActual = 0;
        this.ancho = ancho;
        this.alto = alto;
        this.celdas = configuracionInicial(ancho, alto);
    }

    public GameOfLifeGrid(){
        generations = 0;
        ancho = 0;
        alto = 0;
        threads = new ArrayList<>();
        threadActual = 0;
        celdas = configuracionInicial(0, 0);
    }

    private List<ThreadRunner> crearThreads(int cantidadDeThreads) {
        List<ThreadRunner> threads = new ArrayList<>();
        IntStream.range(0, cantidadDeThreads).forEach(valor -> threads.add(new ThreadRunner()));
        return threads;
    }

    private List<Coordenada> configuracionInicial(int ancho, int alto) {
        List<Coordenada> coordenadas = new ArrayList<>();
        int anchoParaStream = ancho + 1;
        int altoParaStream = alto + 1;

        IntStream.range(0, anchoParaStream).forEach(coordenadaAncho -> {
            IntStream.range(0, altoParaStream).forEach(coordenadaAlto -> {
                coordenadas.add(new CoordenadaMuerta(coordenadaAncho, coordenadaAlto));
            });
        });
        return coordenadas;
    }

    @Override
    public boolean getCell(int col, int row) {
        return busquedaDeCoordenada(col, row).orElseThrow(() -> new LaCoordenadaCaeFueraDelTableroException(col, row)).estaViva();
    }

    @Override
    public void setCell(int col, int row, boolean estadoASetear) {
        boolean estadoExistente = this.getCell(col, row);
        if(estadoASetear && !estadoExistente){
            revivirCelda(col, row);
        }
        if(!estadoASetear && estadoExistente){
            matarCelda(col, row);
        }
    }

    private void matarCelda(int col, int row) {
        Coordenada coordenada = getCoordenada(col, row);
        coordenada.morite(this);
    }

    private void revivirCelda(int col, int row) {
        Coordenada coordenada = getCoordenada(col, row);
        coordenada.revivi(this);
    }

    public Coordenada getCoordenada(int col, int row) {
        return busquedaDeCoordenada(col, row).orElseThrow(() -> new LaCoordenadaCaeFueraDelTableroException(col, row));
    }

    private Optional<Coordenada> busquedaDeCoordenada(int col, int row) {
        return celdas.stream().filter(coordenada -> coordenada.estaEnPunto(col, row)).findFirst();
    }

    @Override
    public Dimension getDimension() {
        return new Dimension(ancho, alto);
    }

    @Override
    public void resize(int ancho, int largo) {
        // seteo ancho y largo nuevo
        this.ancho = ancho;
        this.alto = largo;

        // creo las celdas nuevas
        List<Coordenada> coordenadas = configuracionInicial(ancho, largo);

        // reemplazo las viejas por las nuevas y me guardo las viejas
        List<Coordenada> celdasViejas = this.celdas;
        this.celdas = coordenadas;

        // filtro por coordenada en plano y copio la configuracion vieja que entra en el plano nuevo
        celdasViejas
                .stream()
                .filter(coordenada -> coordenada.estaEnPlano(ancho, largo))
                .collect(Collectors.toList())
                .forEach(this::setCoordenada);

    }

    @Override
    public void setThreads(int cantidadDeThreads) {
        this.threads = crearThreads(cantidadDeThreads);
        this.threadActual = 0;
    }

    @Override
    public void clear() {
        this.celdas = this.configuracionInicial(this.ancho, this.alto);
    }

    @Override
    public int getGenerations() {
        return generations;
    }

    @Override
    public synchronized void next() {
        List<Coordenada> configuracionNueva = new ArrayList<>();

        this.paralelizarNext(configuracionNueva);

        this.celdas = configuracionNueva;
        this.generations++;
    }

    private boolean terminoProceso() {
        return this.threads.parallelStream().allMatch(ThreadRunner::termino);
    }

    private void paralelizarNext(List<Coordenada> configuracionNueva) {
        int totalCeldas = celdas.size();

        celdas.stream().forEach(coordenada -> this.nextThread().add(coordenada));
        threads.stream().forEach(thread -> thread.start(this, configuracionNueva));
    }

    private ThreadRunner nextThread() {
        this.threadActual = this.threadActual + 1;
        if(this.threadActual >= threads.size()){
            threadActual = 0;
        }
        return threads.get(threadActual);
    }

    public void setCoordenada(Coordenada coordenada) {
        eliminarCoordenadaEnElLugarDe(coordenada);
        celdas.add(coordenada);
    }

    private void eliminarCoordenadaEnElLugarDe(Coordenada coordenada) {
        Coordenada coordenadaEncontrada = this.celdas
                .stream()
                .filter(sCoordenada -> sCoordenada.estaEnElMismoPunto(coordenada))
                .findFirst()
                .orElseThrow(LaCoordenadaBuscadaNoEstaEnElTablero::new);

        this.celdas.remove(coordenadaEncontrada);
    }
}