package controlador;

import modelo.*;
import vista.RutasUrbanas;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ControladorRutasUrbanas {

    private RutasUrbanas vista;
    private int paginaActual = 0;
    private final int ELEMENTOS_POR_PAGINA = 10; // 5 columnas x 2 filas
    private List<String> listaCompletaDestinosUrbanos = new ArrayList<>();

    public ControladorRutasUrbanas(RutasUrbanas vista) {
        this.vista = vista;
        inicializarControlador();
    }

    private void inicializarControlador() {
        cargarRutasUrbanasDesdeBD();

        // Listeners para los botones de paginación
        vista.getBtnSiguiente().addActionListener(e -> {
            int totalPaginas = (int) Math.ceil((double) listaCompletaDestinosUrbanos.size() / ELEMENTOS_POR_PAGINA);
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
    }

    private void cargarRutasUrbanasDesdeBD() {
        try {
            ItinerarioDAO dao = new ItinerarioDAO();
            List<ItinerarioModelo> listaRutas = dao.obtenerRutas();
            listaCompletaDestinosUrbanos.clear();

            if (listaRutas != null) {
                for (ItinerarioModelo ruta : listaRutas) {
                    // Filtrar estrictamente las que sean de tipo Urbana
                    if (ruta.getTipoRuta() != null && ruta.getTipoRuta().equalsIgnoreCase("Urbana")) {
                        if (ruta.getDestino() != null && !listaCompletaDestinosUrbanos.contains(ruta.getDestino())) {
                            listaCompletaDestinosUrbanos.add(ruta.getDestino());
                        }
                    }
                }
            }

            paginaActual = 0;
            redibujarPaginaRutas();

        } catch (Exception e) {
            System.out.println("Error al cargar las rutas urbanas desde la base de datos: " + e.getMessage());
        }
    }

    private void redibujarPaginaRutas() {
        JPanel panelGrid = vista.getPanelRutasGrid();
        panelGrid.removeAll();

        int inicio = paginaActual * ELEMENTOS_POR_PAGINA;
        int fin = Math.min(inicio + ELEMENTOS_POR_PAGINA, listaCompletaDestinosUrbanos.size());

        List<String> destinosPagina = new ArrayList<>();
        if (inicio < listaCompletaDestinosUrbanos.size()) {
            destinosPagina = listaCompletaDestinosUrbanos.subList(inicio, fin);
        }

        for (String destino : destinosPagina) {
            // Llama al método visual de la vista
            JPanel tarjetaRuta = vista.crearTarjetaRuta(destino);
            panelGrid.add(tarjetaRuta);
        }

        // Rellenar espacios vacíos para mantener la simetría 
        int anadidos = destinosPagina.size();
        for (int i = anadidos; i < ELEMENTOS_POR_PAGINA; i++) {
            panelGrid.add(new JLabel());
        }

        panelGrid.revalidate();
        panelGrid.repaint();
    }
}