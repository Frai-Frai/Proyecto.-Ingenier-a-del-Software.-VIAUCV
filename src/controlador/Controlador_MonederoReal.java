package controlador;

import modelo.*;
import vista.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controlador_MonederoReal{

    private MonederoReal vista;
    private Usuario pasajero;

    public Controlador_MonederoReal(MonederoReal vista) {
        this.vista = vista;
        this.pasajero = vista.getPasajeroActual();

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
