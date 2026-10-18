package controlador;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.awt.Image;
import modelo.Usuario;
import vista.MonederoReal;
import vista.Reservas;

public class Controlador_Reservas{
    private Reservas vista;
    private Usuario pasajero;

    public Controlador_Reservas(Reservas vista, Usuario pasajero){

        this.vista = vista;
        this.pasajero = pasajero;

        this.vista.getBotonTicket().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MostrarTicket();
            }
        });
    }

    private void MostrarTicket() {
        // revisa que fila selecciono el user en la tabla
        int filaSeleccionada = vista.getFilaSeleccionada();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor, seleccione una reserva de la lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // se extraen los datos de la ruta seleccionada
        String[] datosReserva = vista.getDatosSeleccionados();
        if(datosReserva == null) return; // para asegurarse de que no sean nulos
        
        // Asumiendo el orden de tus columnas: [Placa, Tipo, Destino, Partida, Llegada, Día, Horario]
        String placa = datosReserva[0];
        String destino = datosReserva[2];
        String dia = datosReserva[5];
        String horario = datosReserva[6];

        //Se crea el ticket y muestra el QR
        try {
            ImageIcon iconoOriginal = new ImageIcon("res/Ticket.jpeg"); 
            Image imgTicket = iconoOriginal.getImage().getScaledInstance(220, 320, Image.SCALE_SMOOTH);
            ImageIcon iconoMini = new ImageIcon(imgTicket);

            String mensajeInfo = "    TICKET DE RESERVA   \n\n" +
                                 "Pasajero: " + pasajero.getNombreUsuario() + "\n" +
                                 "Destino: " + destino + "\n" +
                                 "Placa: " + placa + "\n" +
                                 "Día y Hora: " + dia + " - " + horario + "\n\n" +
                                 "              ¡Buen viaje!";

            JOptionPane.showMessageDialog(vista,mensajeInfo, "Tu Ticket Virtual",JOptionPane.INFORMATION_MESSAGE, iconoMini);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Reserva hacia: " + destino + " (" + dia + ")"); //por si la imagen no carga
        }
    }
    
}
