package controlador;

import vista.Registro;
import vista.Inicio;
import vista.InicioSesion;
import vista.RecuperarCont;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class Controlador_IniciodeSesion {

    private InicioSesion pantallaIS;

    public Controlador_IniciodeSesion(InicioSesion pantallaIS){
        this.pantallaIS = pantallaIS;
        this.pantallaIS.getlinkRegistarse().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println("¡CLICK DETECTADO EN EL TEXTO!");
                irAlRegistro();
            }
        });

        this.pantallaIS.getEncabezado().getInicio().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                volverAlInicio();
            }
        });

        this.pantallaIS.getRestaurarContrasena().addMouseListener(new MouseAdapter() { //para que el link de rest cont sirva
            @Override
            public void mousePressed(MouseEvent e) {
                abrirRecuperarClave();
            }
        });

        this.pantallaIS.getEncabezado().getSobreNosotros().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mostrarMensajeUs();
            }
        });
    }

    private void irAlRegistro() {
        Registro pantallaR = new Registro();
        new Controlador_Registro(pantallaR); // para que funcione
        pantallaR.setVisible(true);
        pantallaIS.dispose(); // Cerramos Inicio de Sesión
    }

    private void volverAlInicio() {
        Inicio pantallaI = new Inicio();       // Crea la pantalla de home
        new Controlador_Inicio(pantallaI);     // para que funcione
        pantallaI.setVisible(true);          //  La muestra
        pantallaIS.dispose();                  //  Cierra la ventana de Inicio de Sesión 
    }

    private void abrirRecuperarClave() {
        RecuperarCont miniVentana = new RecuperarCont(pantallaIS); //crea la mini ventana
        new Controlador_RecuperarCont(miniVentana); //para que la pantalla al q lo redirija sirva
        miniVentana.setVisible(true); //se muestra
    }

    private void mostrarMensajeUs(){
        JOptionPane.showMessageDialog(pantallaIS, " Fraidis Franco. Correo: fraidisf@gmail.com\n Mariam Battika. Correo: mariammbattikaahochee@gmail.com\n Eliany Morales. Correo: morales.eliany28@gmail.com", "Sobre Nosotros", JOptionPane.INFORMATION_MESSAGE);

    }
}
