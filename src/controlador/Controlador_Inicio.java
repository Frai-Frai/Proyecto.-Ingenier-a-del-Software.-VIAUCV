package controlador;

import vista.Inicio;
import vista.InicioSesion;
import vista.Registro;

public class Controlador_Inicio {
    private Inicio pantallaI;

    public Controlador_Inicio(Inicio pantallaI){
        this.pantallaI = pantallaI;

        this.pantallaI.getbotonR().addActionListener(e -> {abrirRegistro();});
        this.pantallaI.getbotonI().addActionListener(e ->{ abrirInicioSesion();});
    }

    private void abrirInicioSesion() {
        InicioSesion pantallaIS = new InicioSesion();
        new Controlador_IniciodeSesion(pantallaIS); // pa q funcione el inicio de sesion
        pantallaIS.setVisible(true);
        pantallaI.dispose();
    }

    private void abrirRegistro() {
        Registro pantallaR = new Registro();
        new Controlador_Registro(pantallaR); // pa q funcione el registro
        pantallaR.setVisible(true);
        pantallaI.dispose(); // Cierra el inicio
    }
}
