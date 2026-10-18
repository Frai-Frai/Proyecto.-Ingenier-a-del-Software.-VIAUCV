package controlador;

import modelo.*;
import util.*;
import vista.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class Controlador_MonederoReal{

    private MonederoReal vista;
    private Usuario pasajero;

    public Controlador_MonederoReal(MonederoReal vista) {
        this.vista = vista;
        this.pasajero = vista.getPasajeroActual();

        this.vista.getBotonPagar().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ValidarMonedero();
            }
        });
        this.vista.getOpCerrarS().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                irInicio();
            }
        });

        this.vista.getOpRutasUrbanas().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                abrirRutas("Urbana");
            }
        });

        this.vista.getOpRutasExtraurbanas().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                abrirRutas("Extraurbana");
            }
        });

    }

    public void ValidarMonedero(){
        Validador_Monedero valida = new Validador_Monedero();
        
        String telefono = vista.getTelefono();
        String cedula   = vista.getCedula();
        String monto    = vista.getMonto();

        String resultado = valida.ValidarDatos(telefono, cedula, monto, pasajero);

        if (resultado.equals("Pago exitoso")) {
            double recarga = Double.parseDouble(monto.trim().replace(".", "."));
            UsuarioDAO userDao = new UsuarioDAO();
            userDao.recargarSaldo(pasajero, recarga);
            JOptionPane.showMessageDialog(vista, "Recarga realizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            //actualiza el monto en la pantalla
            //vista.getSaldoMonto().setText(String.valueOf(pasajero.getSaldo()));
        } else {
            JOptionPane.showMessageDialog(vista, resultado, "Error en el proceso de recarga, intente de nuevo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirRutas(String tipoRuta){
        this.vista.setVisible(false);
        this.vista.dispose();
        InterfazUsuario pantallaR = new InterfazUsuario(pasajero);
        Controlador_InterfazUsuario controlador = new Controlador_InterfazUsuario(pantallaR);
        controlador.AbrirRutaM(tipoRuta);//le pasa que tipo de rutas quiere abrir
        pantallaR.setVisible(true);
    }
    private void irInicio(){
        this.vista.setVisible(false);
        this.vista.dispose();
        Inicio pantallaI = new Inicio();
        new Controlador_Inicio(pantallaI);
        pantallaI.setVisible(true);
    }
}
