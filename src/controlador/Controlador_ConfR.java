package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import modelo.ItinerarioDAO;
import modelo.ItinerarioModelo;
import modelo.Usuario;
import vista.ConfirmarReserva;
import vista.MonederoReal;
import vista.PasarelaPago;

public class Controlador_ConfR {

    private ConfirmarReserva vista;
    private Usuario pasajero;

    PasarelaPago pantallaP = new PasarelaPago(vista, pasajero);
    public Controlador_ConfR(ConfirmarReserva vista, Usuario pasajero){
        this.vista = vista;
        this.pasajero = pasajero;

        this.vista.getBtnReservar().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                irAPago();
            }
        });
    }
    
    public void irAPago(){
        this.vista.setVisible(false);
        this.vista.dispose();

        String placa = vista.getPlacaConfirmada();
        String horario = vista.getHorarioConfirmado();

        //como en el confirmar reserva el dia y la hora se almacenaron juntos, aqui se separan 
        String[] partesHorario = horario.split(" - ");
        String dia = partesHorario[0].trim();
        String hora = partesHorario.length > 1 ? partesHorario[1].trim() : "";

        String tarifaInicial = "0.0"; // valor por defecto aqui
        ItinerarioDAO Itdao = new ItinerarioDAO();

        for(ItinerarioModelo ruta : Itdao.obtenerRutas()){ //busca la ruta escogida con el dao
            if(ruta.getPlacaAsignada().equals(placa) && ruta.getDia().equals(dia) && ruta.getHora().equals(hora)){
                tarifaInicial = ruta.getTarifa();
                break; // ya encontro la tarifa de ese destino escogido
            }
        }
        PasarelaPago pantallaP = new PasarelaPago(vista, pasajero);
        new Controlador_Pago(pantallaP, pasajero, tarifaInicial);
        pantallaP.setVisible(true);
    }

}
