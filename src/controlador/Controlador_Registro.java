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
        InicioSesion pantallaIS = new InicioSesion();   // Crea la pantalla de home
        new Controlador_IniciodeSesion(pantallaIS);     //para que funcione
        pantallaIS.setVisible(true);                 // La muestra
        pantallaR.dispose();                           // Cierra la de Registro actual
    }

    private void volverAlInicio() {
        Inicio ventanaInicio = new Inicio();       // Crea la pantalla de home
        new Controlador_Inicio(ventanaInicio);     //para que funcione
        ventanaInicio.setVisible(true);         //La muestra
        pantallaR.dispose();                       //Cierra la ventana de Registro 
    }

   private void registrarUser(){
        Inicio pantallaI = new Inicio();
        new Controlador_Inicio(pantallaI);
        pantallaI.setVisible(true);
        pantallaR.dispose();
    }
    
}
