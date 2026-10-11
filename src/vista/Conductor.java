package vista;
import javax.swing.*;
import java.awt.*;

public class Conductor extends JFrame {
    
    private ImageIcon imagenFondo;

    public Conductor(String rutaImagen) {

        // Config de la pestaña
        setTitle("Conductor - VIAUCV");
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centra la ventana en la pantalla

        imagenFondo = new ImageIcon(rutaImagen);

        // Crear el panel que dibuja la imagen de fondo
        JPanel panelFondo = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    // Dibuja la imagen adaptándose al tamaño actual de la ventana
                    g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        this.add(panelFondo);      // Añadir a la ventana
    }
}
