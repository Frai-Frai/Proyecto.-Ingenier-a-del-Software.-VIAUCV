package controlador;

import modelo.*;
import vista.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class Controlador_InterfazUsuario {

    private InterfazUsuario vista;
    private Usuario pasajero;
    private int paginaActual = 0;
    private final int ELEMENTOS_POR_PAGINA = 10; // 5 columnas x 2 filas
    private List<String> listaCompletaDestinosUrbanos = new ArrayList<>();
    private List<String> listaCompletaDestinosExtraurbanos = new ArrayList<>();
    private String tipoRutaActual = "Urbana";

    public Controlador_InterfazUsuario(InterfazUsuario vista) {
        this.vista = vista;
        pasajero = vista.getPasajeroActual();
        inicializarControlador();

        this.vista.getOpCerrarS().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                irInicio();
            }
        });

        this.vista.getOpRutasUrbanas().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tipoRutaActual = "Urbana";
                paginaActual = 0;
                redibujarPaginaRutas();
            }
        });

        this.vista.getOpRutasExtraurbanas().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tipoRutaActual = "Extraurbana";
                paginaActual = 0;
                redibujarPaginaRutas();
            }
        });

        this.vista.getOpMonedero().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                abrirMonedero();
            }
        });

        this.vista.getBtnVisualizarReservas().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                abrirReservas();
            }
        });
        
    }

    private void inicializarControlador() {
        cargarRutasDesdeBD();

        // Listeners para los botones de paginación
        vista.getBtnSiguiente().addActionListener(e -> {
            List<String> listaActiva = obtenerLista();
            int totalPaginas = (int) Math.ceil((double) listaActiva.size() / ELEMENTOS_POR_PAGINA);
            if (totalPaginas == 0) totalPaginas = 1;
            
            if (paginaActual < totalPaginas - 1) {
                paginaActual++;
                redibujarPaginaRutas();
            }
        });

        vista.getBtnAnterior().addActionListener(e -> {
            if (paginaActual > 0) {
                paginaActual--;
                redibujarPaginaRutas();
            }
        });

        vista.setClickRuta(destinoClick -> {
            
            ItinerarioDAO itinerarioDao = new ItinerarioDAO();
            List<ItinerarioModelo> listaRutasBD = itinerarioDao.obtenerRutas();
            List<String[]> listaEnSt = new ArrayList<>();
            //UnidadesDAO buscarUnidad = new UnidadesDAO();
            
            if(listaRutasBD != null){
                for(ItinerarioModelo ruta : listaRutasBD){
                    if(ruta.getDestino() != null && ruta.getDestino().equals(destinoClick)){
                        String[] fila = new String[8];
                        fila[0] = ruta.getPlacaAsignada(); // Placa
                        fila[1] = String.valueOf(ruta.getCupos()); // Cupos
                        fila[2] = ruta.getTipoRuta(); // Tipo ruta
                        fila[3] = ruta.getDestino(); // Destino
                        fila[4] = ruta.getPuntoPartida(); // Partida
                        fila[5] = ruta.getPuntoLLegada(); // Llegada
                        fila[6] = ruta.getDia(); // Día
                        fila[7] = ruta.getHora(); // Horario

                        listaEnSt.add(fila);
                    }
                }
            }

            SeleccionarHorario abririH = new SeleccionarHorario(vista, listaEnSt);
            new Controlador_Horario(abririH, pasajero);
            abririH.setVisible(true);
        });
    }

    private void cargarRutasDesdeBD() {
        try {
            ItinerarioDAO dao = new ItinerarioDAO();
            List<ItinerarioModelo> listaRutas = dao.obtenerRutas();
            listaCompletaDestinosUrbanos.clear();
            listaCompletaDestinosExtraurbanos.clear();

            if (listaRutas != null) {
                for (ItinerarioModelo ruta : listaRutas) {

                    // Filtrar rutas urbanas y extraurbanas por separado
                    if (ruta.getTipoRuta() != null && ruta.getTipoRuta().equalsIgnoreCase("Urbana")) {
                        if(ruta.getDestino() != null && !listaCompletaDestinosUrbanos.contains(ruta.getDestino())) {
                            listaCompletaDestinosUrbanos.add(ruta.getDestino());
                        }
                    }else if(ruta.getTipoRuta() != null && ruta.getTipoRuta().equalsIgnoreCase("Extraurbana")){
                        if(ruta.getDestino() != null && !listaCompletaDestinosExtraurbanos.contains(ruta.getDestino()))
                            listaCompletaDestinosExtraurbanos.add(ruta.getDestino());
                    }

                }
            }

            paginaActual = 0;
            redibujarPaginaRutas();

        } catch (Exception e) {
            System.out.println("Error al cargar las rutas urbanas desde la base de fila: " + e.getMessage());
        }
    }

    private List<String> obtenerLista(){
        if (tipoRutaActual.equals("Urbana")) {
            return listaCompletaDestinosUrbanos;
        } else {
            return listaCompletaDestinosExtraurbanos;
        }
    }

    private void redibujarPaginaRutas() {
        List <String> ListaActual = obtenerLista();
        int inicio = paginaActual * ELEMENTOS_POR_PAGINA;
        int fin = Math.min(inicio + ELEMENTOS_POR_PAGINA, ListaActual.size());

        List<String> destinosPagina = new ArrayList<>();

        if (inicio < ListaActual.size()) {
            destinosPagina = ListaActual.subList(inicio, fin);
        }

        vista.actualizarRutasVista(destinosPagina, ELEMENTOS_POR_PAGINA);

    }

    public void abrirMonedero(){
        this.vista.setVisible(false);
        this.vista.dispose();
        MonederoReal pantallaM = new MonederoReal(pasajero);
        new Controlador_MonederoReal(pantallaM);
        pantallaM.setVisible(true);
    }

    public void AbrirRutaM(String tipo){ // abra el tipo de ruta a la q el user de click
        this.tipoRutaActual = tipo;
        this.paginaActual = 0;
        redibujarPaginaRutas();
    }

    public void abrirReservas(){
        ReservaDAO RDao = new ReservaDAO();
        List<Reserva> listaR = RDao.buscarPorCedula(pasajero.getCedula());
        List <String[]> filasR = new ArrayList<>();

        if (listaR != null) {
            for (Reserva r : listaR) {
                ItinerarioModelo iti = r.getItinerario();

                String[] fila = {
                    iti != null ? iti.getPlacaAsignada() : " ",  // [0] Placa
                    iti != null ? iti.getTipoRuta() : " ",       // [1] Tipo de ruta
                    iti != null ? iti.getDestino() : " ",        // [2] Destino
                    iti != null ? iti.getPuntoPartida() : " ",   // [3] Punto de partida
                    iti != null ? iti.getPuntoLLegada() : " ",   // [4] Punto de llegada
                    iti != null ? iti.getDia() : " ",            // [5] Día
                    iti != null ? iti.getHora() : " "            // [6] Horario
                };
                filasR.add(fila);
            }
        }

        Reservas reserva = new Reservas(vista, filasR);
        new Controlador_Reservas(reserva, pasajero);
        reserva.setVisible(true);
    }

    private void irInicio(){
        this.vista.setVisible(false);
        this.vista.dispose();
        Inicio pantallaI = new Inicio();
        new Controlador_Inicio(pantallaI);
        pantallaI.setVisible(true);
    }
}