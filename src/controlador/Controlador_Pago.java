package controlador;


import modelo.*;
import vista.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class Controlador_Pago {

    private PasarelaPago vista;
    private Usuario pasajero;
    private Validador_PagoMovil valida;
    private double montoFijo;

    public Controlador_Pago(PasarelaPago vista, Usuario pasajero, String tarifaBase){
        this. vista = vista;
        this.pasajero = pasajero;
        this.valida = new Validador_PagoMovil();

        PorcentajeTarifasDAO tarifaDao = new PorcentajeTarifasDAO();
        this.montoFijo = tarifaDao.calcularCobroSolidario(pasajero.getRol(), tarifaBase);
        this.vista.setMontoCalculado(String.valueOf(montoFijo));//se le pasa el valor fijo a la pantalla yse bloquea para q no se pueda cambiar
        
        this.vista.getBotonPagar().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                procesarPago();
            }
        });
    }

    private void procesarPago(){
        String num = vista.getTelefono();
        String cedula = vista.getCedula();
        String monto = vista.getMonto();

        String resultValida = valida.ValidarDatos(num, cedula, monto);

        if(resultValida.equals("Pago Exitoso")){
            JOptionPane.showMessageDialog(vista, "Transacción aprobada\nSu reserva ha sido confirmada.", "Pago Exitoso", JOptionPane.INFORMATION_MESSAGE);
            vista.dispose();

            //porsia se pone lo de q muestre el ticker, va aqui y tmbn la logica para q lo mande a las rutas y asi
        }else{
            JOptionPane.showMessageDialog(vista, resultValida, "Datos Inválidos", JOptionPane.WARNING_MESSAGE);
        }
    }
}
