package vista;
import javax.swing.*;

import util.BotonUtil;
import util.EncabezadoUtil;
import java.awt.*;
import java.io.File;


public class Inicio extends JFrame {

    private BotonUtil botonR;
    private BotonUtil botonI;
    //Tipografia 
    private Font fuenteGlacial(String ruta, float tamano, int estilo){
        try {
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, new File(ruta));
            return fuente.deriveFont(estilo, tamano);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la fuente: " + e.getMessage());
            return new Font("SansSerif", estilo, (int) tamano);
        }
    }

    public Inicio(){

        //lo de arribita y otras config
        setTitle ("VIAUCV");
        setSize(800,720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 720));
        setLocationRelativeTo(null);
       
        Color   azulCuadros = new Color(0x0D47A1); // para el cuadro de registrar o inicio de sesion
        Color   azulPastelC = new Color(0xBBDEFB); // para el cuadro del encabezado
        Font    fuenteLetras = fuenteGlacial("res/GlacialIndifference-Bold.otf", 16, Font.PLAIN);
        
        //fondito
        ImageIcon imagenFondo = new ImageIcon("res/FondoInicio.png");
        JPanel panelFondo = new JPanel(new BorderLayout()){
            @Override 
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        //para que se vea
        setContentPane(panelFondo);

        botonR = new BotonUtil("Registrarse", azulCuadros, Color.WHITE, 20, fuenteLetras, 180, 40);
        botonR.setBounds(210, 600, 200, 50);
        panelFondo.add(botonR);

        botonI = new BotonUtil("Iniciar Sesión", azulCuadros, Color.WHITE, 20, fuenteLetras, 180, 40);
        botonI.setBounds(520, 600, 200, 50);
        panelFondo.add(botonI);

        //encabezado
        panelFondo.setLayout(new BorderLayout()); //para que pegue el encabezado a la parte superior de la pagina
        EncabezadoUtil encabezado = new EncabezadoUtil(azulPastelC);
        panelFondo.add(encabezado, BorderLayout.NORTH);

    }

    public BotonUtil getbotonI(){
        return botonI;
    }

    public BotonUtil getbotonR(){
        return botonR;
    }
    public static void main(String[] args){
        Inicio interfazI = new Inicio();
        interfazI.setVisible(true);
    }

}
    
