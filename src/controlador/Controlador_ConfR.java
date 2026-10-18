package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

import modelo.*;
import vista.ConfirmarReserva;

public class Controlador_ConfR {

    private ConfirmarReserva vista;
    private Usuario pasajero;
    private UsuarioDAO pasajeroDao = new UsuarioDAO();
    private double montoFijo;
    private PorcentajeTarifasDAO tarifaDao = new PorcentajeTarifasDAO();
    private  ItinerarioModelo rutaEscogida;

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
                rutaEscogida= ruta; //guarda esa ruta completa
                break; // ya encontro la tarifa de ese destino escogido
            }
        }

        montoFijo = tarifaDao.calcularCobroSolidario(pasajero.getRol(), tarifaInicial);

        //monto insuficiente
        if(pasajero.getSaldo() < montoFijo){
            JOptionPane.showMessageDialog(vista,"Saldo insuficiente para realizar la transacción. Por favor, recargue en su monedero "+montoFijo+"bs", "ATENCIÓN", JOptionPane.WARNING_MESSAGE); 
        } else {
            JOptionPane.showMessageDialog(vista,"Pago exitoso de "+montoFijo+"bs", "ÉXITO", JOptionPane.INFORMATION_MESSAGE); 
            pasajeroDao.descontarSaldo(pasajero, montoFijo);
            Itdao.restarCupos(rutaEscogida);

            //guarda en la bd de reservas por usuario
            ReservaDAO rDao = new ReservaDAO();
            Reserva nuevaReserva = new Reserva(pasajero.getCedula(), rutaEscogida);
            rDao.registrarReserva(nuevaReserva);
        }
    }

}
