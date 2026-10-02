package controlador;

import vista.Itinerario;
import vista.InicioSesion;

import modelo.Validador_Itinerario;
import modelo.ItinerarioModelo;

import modelo.ItinerarioDAO;
import modelo.UnidadesDAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
                irInicioSesion();
            }
        });

        this.pantalla.getBotonP().addActionListener(e -> {
                ValidarItinerario();
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
        pantalla.getCuadritoHorario().getText().trim(),  //CAMBIAR A LA DE DIA
        pantalla.getCuadritoHorario().getText().trim(), //CAMBIAR A LA DE HORA
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


    private void irInicioSesion(){
        System.out.println("Cambiando a pantalla de inicio de sesión...");
        this.pantalla.dispose();
        InicioSesion pantallaIS = new InicioSesion();
        new Controlador_IniciodeSesion(pantallaIS);
        pantallaIS.setVisible(true);
    }

}
