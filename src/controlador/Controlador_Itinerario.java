package controlador;

import vista.*;
import modelo.Validador_Itinerario;
import modelo.ItinerarioModelo;

import modelo.ItinerarioDAO;
import modelo.UnidadesDAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Controlador_Itinerario {

    private Itinerario pantalla; 

    public Controlador_Itinerario(Itinerario pantalla ){
        System.out.println("Controlador_Itinerario iniciado");
        this.pantalla = pantalla;

        this.pantalla.getOpCerrarS().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Cerrando sesión...");
                irInicio();
            }
        });

        this.pantalla.getBotonP().addActionListener(e -> {
                ValidarItinerario();
        });

        this.pantalla.getEliminarRuta().addMouseListener(new MouseAdapter() { 
            @Override
            public void mousePressed (MouseEvent e) {
                System.out.println("--> BOTÓN SELECCIONAR RUTAS PRESIONADO");
                abrirEliminarRuta();
            }
        }); 
    }

    private void ValidarItinerario() {
        ItinerarioDAO itinerarioDao = new ItinerarioDAO();
        UnidadesDAO unidadesDao = new UnidadesDAO();

        Validador_Itinerario validador = new Validador_Itinerario(itinerarioDao, unidadesDao);

        ItinerarioModelo itinerario = new ItinerarioModelo(
            pantalla.getCuadritoPlaca().getText().trim(),
            pantalla.getCuadritoTipoRuta().getText().trim(), 
            pantalla.getCuadritoDestino().getText().trim(), 
            pantalla.getCuadritoPtoPartida().getText().trim(), 
            pantalla.getCuadritoPtoLlegada().getText().trim(), 
            pantalla.getCuadritoDia().getText().trim(),  
            pantalla.getCuadritoHora().getText().trim(), 
            pantalla.getCuadritoConductor().getText().trim()
        );

        String resultado = validador.validarItinerario(itinerario);

        if (resultado.equals("Ruta válida")) {
            itinerarioDao.RegistrarRutaEnBD(itinerario);
            JOptionPane.showMessageDialog(pantalla, "Ruta registrada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // 1. Actualizamos el panel superior de destinos automáticamente
            actualizarDestinosEnPantalla(itinerarioDao);

            // 2. Limpiamos todos los campos del formulario
            limpiarCamposFormulario();

        } else {
            JOptionPane.showMessageDialog(pantalla, resultado, "Error de validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para vaciar los campos de texto del formulario tras un registro exitoso
    private void limpiarCamposFormulario() {
        pantalla.getCuadritoPlaca().setText("");
        pantalla.getCuadritoTipoRuta().setText("");
        pantalla.getCuadritoDestino().setText("");
        pantalla.getCuadritoPtoPartida().setText("");
        pantalla.getCuadritoPtoLlegada().setText("");
        pantalla.getCuadritoDia().setText("");
        pantalla.getCuadritoHora().setText("");
        pantalla.getCuadritoConductor().setText("");
    }

    // Método auxiliar para consultar los destinos actuales y refrescar la vista de inmediato
    private void actualizarDestinosEnPantalla(ItinerarioDAO itinerarioDao) {
        List<ItinerarioModelo> listaRutas = itinerarioDao.obtenerRutas();
        List<String> destinos = new ArrayList<>();

        if (listaRutas != null) {
            for (ItinerarioModelo ruta : listaRutas) {
                if (ruta.getDestino() != null && !destinos.contains(ruta.getDestino())) {
                    destinos.add(ruta.getDestino());
                }
            }
        }
        pantalla.actualizarPanelPines(destinos);
    }

    private void abrirEliminarRuta() {
        ItinerarioDAO itinerarioDao = new ItinerarioDAO();
        List<ItinerarioModelo> listaRutasBD = itinerarioDao.obtenerRutas();

        if (listaRutasBD == null || listaRutasBD.isEmpty()) {
            JOptionPane.showMessageDialog(pantalla, "No hay rutas registradas para eliminar.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        EliminarRuta miniVentana = new EliminarRuta(pantalla, listaRutasBD);

        miniVentana.getbotonElim().addActionListener(e -> {
            List<ItinerarioModelo> seleccionadas = miniVentana.getRutasSeleccionadas();

            if (seleccionadas.isEmpty()) {
                JOptionPane.showMessageDialog(miniVentana, "Debe seleccionar al menos una ruta.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(
                miniVentana,
                "¿Desea eliminar las " + seleccionadas.size() + " rutas seleccionadas?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean exito = true;

                for(ItinerarioModelo i: seleccionadas){
                    boolean sinProblema = itinerarioDao.eliminarRuta(i); 

                    if(!sinProblema){
                        exito = false; 
                    }
                }
                
                if (exito) {
                    JOptionPane.showMessageDialog(miniVentana, "Rutas eliminadas exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    miniVentana.dispose(); 
                    
                    actualizarDestinosEnPantalla(itinerarioDao);

                } else {
                    JOptionPane.showMessageDialog(miniVentana, "Error al eliminar registros.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        miniVentana.setVisible(true);
    }

    private void irInicio(){
        System.out.println("Cambiando a pantalla de inicio de sesión...");
        this.pantalla.dispose();
        Inicio pantallaIS = new Inicio();
        new Controlador_Inicio(pantallaIS);
        pantallaIS.setVisible(true);
    }

    
}
