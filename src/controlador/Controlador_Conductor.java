package controlador;

import modelo.*;
import vista.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controlador_Conductor {

    private InterfazConductor vista;
    private Usuario conductor;

    public Controlador_Conductor(InterfazConductor vista) {
        this.vista = vista;
        this.conductor = vista.getConductorActual();

        this.vista.getOpCerrarS().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                irInicio();
            }
        });

    }

    private void irInicio(){
        this.vista.setVisible(false);
        this.vista.dispose();
        Inicio pantallaI = new Inicio();
        new Controlador_Inicio(pantallaI);
        pantallaI.setVisible(true);
    }
}