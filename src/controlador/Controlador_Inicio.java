package controlador;

import vista.Inicio;
import vista.InicioSesion;
import vista.Registro;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
public class Controlador_Inicio {
    private Inicio pantallaI;

    public Controlador_Inicio(Inicio pantallaI){
        this.pantallaI = pantallaI;

        this.pantallaI.getbotonR().addActionListener(e -> {abrirRegistro();});
        this.pantallaI.getbotonI().addActionListener(e ->{ abrirInicioSesion();});
        
        this.pantallaI.getEncabezado().getSobreNosotros().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mostrarMensajeUs();
            }
        });
    }

    private void abrirInicioSesion() {
        InicioSesion pantallaIS = new InicioSesion();
        new Controlador_IniciodeSesion(pantallaIS); // pa q funcione el inicio de sesion
        pantallaIS.setVisible(true);
        pantallaI.dispose();
    }

    private void abrirRegistro() {
        Registro pantallaR = new Registro();
        new Controlador_Registro(pantallaR); // para q funcione el registro
        pantallaR.setVisible(true);
        pantallaI.dispose(); // Cierra el inicio
    }

    private void mostrarMensajeUs(){
        JOptionPane.showMessageDialog(pantallaI, " Fraidis Franco. Correo: fraidisf@gmail.com\n Mariam Battika. Correo: mariammbattikaahochee@gmail.com\n Eliany Morales. Correo: morales.eliany28@gmail.com", "Sobre Nosotros", JOptionPane.INFORMATION_MESSAGE);

    }
}
