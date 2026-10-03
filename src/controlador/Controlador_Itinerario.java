package controlador;

import vista.*;
import modelo.Validador_Itinerario;
import modelo.ItinerarioModelo;

import modelo.ItinerarioDAO;
import modelo.UnidadesDAO;
import modelo.UsuarioDAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

        this.pantalla.getEliminarRuta().addMouseListener(new MouseAdapter() { //para que el boton de seleccionar ruta sirva
            @Override
            public void mousePressed (MouseEvent e) {
                System.out.println("--> BOTÓN SELECCIONAR RUTAS PRESIONADO");
                abrirEliminarRuta();
            }
        }); //ESTO Y LO DE ABAJO FUE LO QUE INTENE HACER Y ME ENREDE
    }

    private void ValidarItinerario() {
        

        ItinerarioDAO itinerarioDao = new ItinerarioDAO();
        UnidadesDAO unidadesDao = new UnidadesDAO();
        UsuarioDAO usuarioDao = new UsuarioDAO();

        Validador_Itinerario validador = new Validador_Itinerario(itinerarioDao, unidadesDao, usuarioDao);

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
        } else {
            JOptionPane.showMessageDialog(pantalla, resultado, "Error de validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Presionar el boton de registrar itinerario verifico todo

    private void abrirEliminarRuta() {
        ItinerarioDAO itinerarioDao = new ItinerarioDAO();
        List<ItinerarioModelo> listaRutasBD = itinerarioDao.obtenerRutas();

        if (listaRutasBD == null || listaRutasBD.isEmpty()) {
            JOptionPane.showMessageDialog(pantalla, "No hay rutas registradas para eliminar.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // miniventana pasándole el padre y la lista de objetos de la BD
        EliminarRuta miniVentana = new EliminarRuta(pantalla, listaRutasBD);

        //la lógica del botón "Eliminar" que está dentro de la miniventana
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
                    boolean sinProblema= itinerarioDao.eliminarRuta(i); //Para saber si no hubo problema con el i usado, elima una a una
                                                                        //las rutas marcadas 

                    if(!sinProblema){
                        exito=false; //no se elimina
                    }
                }
                
                if (exito) {
                    JOptionPane.showMessageDialog(miniVentana, "Rutas eliminadas exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    miniVentana.dispose(); // Cierra la miniventana
                } else {
                    JOptionPane.showMessageDialog(miniVentana, "Error al eliminar registros.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 3. Muestras la ventana emergente
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
