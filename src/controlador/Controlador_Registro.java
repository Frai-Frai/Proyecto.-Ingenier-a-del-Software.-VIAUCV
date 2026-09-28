package controlador;
import vista.Registro;
import vista.Inicio;
import vista.InicioSesion;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controlador_Registro{

    private Registro pantallaR;

    public Controlador_Registro(Registro pantallaR){
        this.pantallaR = pantallaR;

        this.pantallaR.getbotonR().addActionListener(e -> {
            registrarUser();
        });

        this.pantallaR.getIrAIniciarS().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                irAInicioSesion();
            }
        });

        this.pantallaR.getEncabezado().getInicio().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                volverAlInicio();
            }
        });
        
    }

    private void irAInicioSesion() {
        InicioSesion pantallaIS = new InicioSesion(); // 1. Crea la ventana
        new Controlador_IniciodeSesion(pantallaIS);     // 2. Le conecta su cerebro
        pantallaIS.setVisible(true);                  // 3. La muestra
        pantallaR.dispose();                        // 4. Cierra la de Registro actual
    }

    private void volverAlInicio() {
        Inicio ventanaInicio = new Inicio();       // 1. Crea la pantalla principal
        new Controlador_Inicio(ventanaInicio);     // 2. Le conecta su cerebro
        ventanaInicio.setVisible(true);            // 3. La muestra
        pantallaR.dispose();                       // 4. Cierra la ventana de Registro actual (usando tu variable pantallaR)
    }

   private void registrarUser(){
        Inicio pantallaI = new Inicio();
        new Controlador_Inicio(pantallaI);
        pantallaI.setVisible(true);
        pantallaR.dispose();
    }
    
}
