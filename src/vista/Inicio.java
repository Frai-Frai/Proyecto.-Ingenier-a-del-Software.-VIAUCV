package vista;
import javax.swing.*;

import util.BotonUtil;
import util.EncabezadoUtil;
import java.awt.*;
import java.io.File;


public class Inicio extends JFrame {

    private BotonUtil botonR;
    private BotonUtil botonI;
    private EncabezadoUtil encabezado;
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
        setSize(1000,680);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 680));
        setLocationRelativeTo(null);
       
        Color   azulCuadros = new Color(0x0D47A1); // para el cuadro de registrar o inicio de sesion
        Color   azulPastelC = new Color(0xBBDEFB); // para el cuadro del encabezado
        Font    fuenteLetras = fuenteGlacial("res/GlacialIndifference-Bold.otf", 17, Font.PLAIN);
        
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

        //conetenedor de ambos botones
        JPanel panelBontones= new JPanel(null); //Para juntarlos y centrarlos
        panelBontones.setOpaque(false);

        Dimension tamano= new Dimension(170, 50);

        botonR = new BotonUtil("Registrarse", azulCuadros, Color.WHITE, 20, fuenteLetras, 180, 40);
        //Registrar.setBounds(210, 600, 200, 50);
        botonR.setPreferredSize(tamano);
        
        botonI = new BotonUtil("Iniciar Sesión", azulCuadros, Color.WHITE, 20, fuenteLetras, 180, 40);
        //IniciarS.setBounds(520, 600, 200, 50);
        botonI.setPreferredSize(tamano);

        panelBontones.add(botonR);
        panelBontones.add(botonI);

        //Listener por porcentajes se me acaban las opciones funciona por favor
        panelBontones.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override 
            public void componentResized(java.awt.event.ComponentEvent e){
                int ancho= panelBontones.getWidth();
                int alto= panelBontones.getHeight();

                //Posiciones
                int regX= (int) (ancho*0.15f); //12%
                int iniX= (int) (ancho*0.34f); //30%
                int posY= (int) (alto*0.67f);  //65%
                botonR.setBounds(regX, posY, tamano.width, tamano.height);
                botonI.setBounds(iniX, posY, tamano.width, tamano.height);      
            }
        });
        
        //encabezado
        panelFondo.setLayout(new BorderLayout()); 
        encabezado = new EncabezadoUtil(azulPastelC);
        panelFondo.add(encabezado, BorderLayout.NORTH);
        panelFondo.add(panelBontones,BorderLayout.CENTER);
    }

    public BotonUtil getbotonI(){
        return botonI;
    }

    public BotonUtil getbotonR(){
        return botonR;
    }

    public EncabezadoUtil getEncabezado() {
        return encabezado;
    }
    public static void main(String[] args){
        Inicio interfazI = new Inicio();
        interfazI.setVisible(true);
    }

}