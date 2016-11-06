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
    public synchronized boolean getCell(int col, int row) {
        System.out.println("- Buscando celda columna:" + col + " fila:" + row);

        boolean cell = busquedaDeCoordenada(col, row).orElseThrow(() -> new LaCoordenadaCaeFueraDelTableroException(col, row)).estaViva();

        System.out.println("-");
        System.out.println("- Se encontro celda con estado " + (cell?"viva":"falsa"));
        System.out.println("-");
        System.out.println("- Busqueda de celda finalizada");

        return cell;
    }

    @Override
    public synchronized void setCell(int col, int row, boolean estadoASetear) {
        System.out.println("- Seteando col:" + col + " row:" + row);
        System.out.println("-");

        boolean estadoExistente = this.getCell(col, row);
        if(estadoASetear && !estadoExistente){
            revivirCelda(col, row);
        }
        if(!estadoASetear && estadoExistente){
            matarCelda(col, row);
        }

        System.out.println("-");
        System.out.println("- Seteo finalizado exitosamente");
    }

    private void matarCelda(int col, int row) {
        Coordenada coordenada = getCoordenada(col, row);
        coordenada.morite(this);

        System.out.println("- Se mato la celda");
    }

    private void revivirCelda(int col, int row) {
        Coordenada coordenada = getCoordenada(col, row);
        coordenada.revivi(this);

        System.out.println("- Se revivio la celda");
    }

    public Coordenada getCoordenada(int col, int row) {
        System.out.println("- Buscando coordenada con columna:" + col + " y fila:" + row);
        System.out.println("-");

        Coordenada coordenada = busquedaDeCoordenada(col, row).orElseThrow(() -> {
            System.out.println("- La coordenada buscada no pertenece al tablero");
            System.out.println("-");
            System.out.println("- Busqueda de coordenada finalizada");

            return new LaCoordenadaCaeFueraDelTableroException(col, row);
        });

        System.out.println("Se encontro celda con estado " + (coordenada.estaViva()?"viva":"falsa"));
        System.out.println("-");
        System.out.println("- Busqueda de coordenada finalizada");

        return coordenada;
    }

    private Optional<Coordenada> busquedaDeCoordenada(int col, int row) {
        return celdas.stream().filter(coordenada -> coordenada.estaEnPunto(col, row)).findFirst();
    }

    @Override
    public Dimension getDimension() {
        System.out.println("- Calculando dimension");
        System.out.println("-");

        Dimension dimension = new Dimension(ancho, alto);

        System.out.println("- Dimension calculada");
        System.out.println("-");
        System.out.println("- Retornando dimension");

        return dimension;
    }

    @Override
    public synchronized void resize(int ancho, int largo) {
        System.out.println("- Comenzando resize a ancho:" + ancho + " largo:" + largo);
        System.out.println("-");

        // seteo ancho y largo nuevo
        this.ancho = ancho;
        this.alto = largo;
        System.out.println("- Ancho y largo seteados en el objeto");

        // creo las celdas nuevas
        List<Coordenada> coordenadas = configuracionInicial(ancho, largo);
        System.out.println("- Coordenadas default calculadas para las nuevas dimensiones");

        // reemplazo las viejas por las nuevas y me guardo las viejas
        List<Coordenada> celdasViejas = this.celdas;
        this.celdas = coordenadas;
        System.out.println("- Reemplazo por nuevas coordenadas en el objeto");

        // filtro por coordenada en plano y copio la configuracion vieja que entra en el plano nuevo
        System.out.println("- Copiando la configuracion vieja en la nueva");
        celdasViejas
                .stream()
                .filter(coordenada -> coordenada.estaEnPlano(ancho, largo))
                .collect(Collectors.toList())
                .forEach(this::setCoordenada);
        System.out.println("- Copiado finalizado");
        System.out.println("-");
        System.out.println("- Resize finalizado");
    }

    @Override
    public synchronized void setThreads(int cantidadDeThreads) {
        System.out.println("- Seteando nueva cantidad de threads a: " + cantidadDeThreads);
        System.out.println("-");
        System.out.println("- Creando runners nuevos");

        this.threads = crearThreads(cantidadDeThreads);
        this.threadActual = 0;

        System.out.println("-");
        System.out.println("- Seteo de threads exitoso");
    }

    @Override
    public void clear() {
        System.out.println("- Comenzando limpieza de tablero");
        System.out.println("-");

        this.celdas = this.configuracionInicial(this.ancho, this.alto);

        System.out.println("-");
        System.out.println("- Limpieza de tablero finalizada");
    }

    @Override
    public int getGenerations() {
        System.out.println("- Obteniendo numero de generaciones");
        System.out.println("-");
        System.out.println("- Numero de generaciones obtenido: " + generations);
        System.out.println("-");
        System.out.println("- Retornando numero de generaciones obtenido");

        return generations;
    }

    @Override
    public synchronized void next() {
        System.out.println("- Comenzando next");
        System.out.println("-");

        List<Coordenada> configuracionNueva = new ArrayList<>();

        this.paralelizarNext(configuracionNueva);

        System.out.println("- Asignando configuracion nueva");
        this.celdas = configuracionNueva;

        System.out.println("- Agregando nueva generacion");
        this.generations++;

        System.out.println("-");
        System.out.println("- Next finalizado con exito");
    }

    private void paralelizarNext(List<Coordenada> configuracionNueva) {
        System.out.println("- Efectuando next de manera concurrente");
        System.out.println("- Repartiendo trabajo entre los " + threads.size() + " disponibles");

        celdas.stream().forEach(coordenada -> this.nextThread().add(coordenada));

        System.out.println("- Corriendo los threds disponibles");
        threads.stream().forEach(thread -> thread.start(this, configuracionNueva));

        System.out.println("- Joineando threads");
        threads.forEach(ThreadRunner::joinThread);
    }

    private ThreadRunner nextThread() {
        this.threadActual = this.threadActual + 1;
        if(this.threadActual >= threads.size()){
            threadActual = 0;
        }
        System.out.println("- Siguiente thread: " + threadActual);
        return threads.get(threadActual);
    }

    public void setCoordenada(Coordenada coordenada) {
        System.out.println("- Seteando nueva coordenada en x=" + coordenada.getColumna() + " y=" + coordenada.getFila());
        System.out.println("-");

        eliminarCoordenadaEnElLugarDe(coordenada);
        celdas.add(coordenada);
        System.out.println("- Agregando coordenada nueva");


        System.out.println("-");
        System.out.println("- Coordenada seteada exitosamente");
    }

    private void eliminarCoordenadaEnElLugarDe(Coordenada coordenada) {
        System.out.println("- Eliminando coordenada existente en la posicion");
        Coordenada coordenadaEncontrada = this.celdas
                .stream()
                .filter(sCoordenada -> sCoordenada.estaEnElMismoPunto(coordenada))
                .findFirst()
                .orElseThrow(() -> {
                    System.out.println("- Imposible eliminar coordenada ya que no exite en el tablero");
                    System.out.println("-");
                    System.out.println("- Coordenada no seteada por error");

                    return new LaCoordenadaBuscadaNoEstaEnElTablero();
                });

        this.celdas.remove(coordenadaEncontrada);
        System.out.println("- Coordenada eliminada correctamente");
    }
}