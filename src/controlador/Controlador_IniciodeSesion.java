package controlador;
import vista.Registro;
import vista.Inicio;
import vista.InicioSesion;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    }

    private void irAlRegistro() {
        Registro pantallaR = new Registro();
        new Controlador_Registro(pantallaR); // Conectamos su cerebro
        pantallaR.setVisible(true);
        pantallaIS.dispose(); // Cerramos Inicio de Sesión
    }

    private void volverAlInicio() {
        Inicio pantallaI = new Inicio();       // 1. Crea la pantalla principal
        new Controlador_Inicio(pantallaI);     // 2. Le conecta su cerebro
        pantallaI.setVisible(true);            // 3. La muestra
        pantallaIS.dispose();                      // 4. Cierra la ventana de Inicio de Sesión actual
    }
}
