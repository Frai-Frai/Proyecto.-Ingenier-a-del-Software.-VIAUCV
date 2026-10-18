package controlador;

import modelo.Usuario;
import vista.ConfirmarReserva;
import vista.*;
import vista.InterfazUsuario;
import vista.SeleccionarHorario;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import javax.swing.*;

public class Controlador_Horario {

    private SeleccionarHorario vista;
    private Usuario pasajero;

    public Controlador_Horario(SeleccionarHorario vista, Usuario pasajero){
        this.vista = vista;
        this.pasajero = pasajero;

        this.vista.getBotonAceptar().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                irAConfirmR();
            }
        });
    }

    private void irAConfirmR(){
        JTable tabla = vista.getTablaSeleccionada();
        int filaSelect = -1; // para que busque el cuadrito de seleccion que esta en la posi 0
        
        for(int i = 0; i < tabla.getRowCount(); i++){
            Boolean select = (Boolean) tabla.getValueAt(i, 0);
            if(select != null && select){
                filaSelect = i;
                break; // se sale porque ya encontro el horario seleccionada
            }
        }

        if(filaSelect == -1){
            JOptionPane.showMessageDialog(vista, "Por favor seleccione un horario", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cupos = tabla.getValueAt(filaSelect, 2).toString();

        try{
            int cantCupos = Integer.parseInt(cupos.trim());
            
            if(cantCupos <= 0){
                JOptionPane.showMessageDialog(vista, "No hay cupos disponibles", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(vista, "Error de lectura en los cupos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String placa = tabla.getValueAt(filaSelect, 1).toString();
        String tipoRuta = tabla.getValueAt(filaSelect, 3).toString();
        String destino = tabla.getValueAt(filaSelect, 4).toString();
        String ptoPartida = tabla.getValueAt(filaSelect, 5).toString();
        String ptoLlegada = tabla.getValueAt(filaSelect, 6).toString();
        String dia = tabla.getValueAt(filaSelect, 7).toString();
        String hora = tabla.getValueAt(filaSelect, 8).toString(); 
        
        String horarioCompleto = dia + " - " + hora;

        this.vista.setVisible(false);
        this.vista.dispose();
        ConfirmarReserva pantallaCR = new ConfirmarReserva(pasajero);
        pantallaCR.setPlaca(placa);
        pantallaCR.setCuposDisponibles(cupos);
        pantallaCR.setTipoRuta(tipoRuta);
        pantallaCR.setDestino(destino);
        pantallaCR.setPuntoPartida(ptoPartida);
        pantallaCR.setPuntoLlegada(ptoLlegada);
        pantallaCR.setHorario(horarioCompleto);

        new Controlador_ConfR(pantallaCR, pasajero);
        pantallaCR.setVisible(true);
    }

}
