package vista;
import javax.swing.*;
import java.awt.*;
import util.*;
import java.io.File;
public class Registro extends JFrame{

    //Tipografia de los titulos
    private Font fuenteGlacial(String ruta, float tamano, int estilo){
        try {
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, new File(ruta));
            return fuente.deriveFont(estilo, tamano);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la fuente: " + e.getMessage());
            return new Font("SansSerif", estilo, (int) tamano);
        }
    }

    public Registro(){

        //lo de arribita
        setTitle ("VIAUCV");
        setSize(800,720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        //fondito
        ImageIcon imagenFondo = new ImageIcon("res/FondoRegistro.png");
        JPanel panelFondo = new JPanel(){
            @Override 
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        //para que se vea
        panelFondo.setLayout(new GridBagLayout()); 
        setContentPane(panelFondo);

        Font    fuenteT = new Font("Dialog", Font.BOLD, 30); // tipografias
        Font    fuenteNormal = fuenteGlacial("res/GlacialIndifference-Regular.otf", 14, Font.PLAIN); 
        Font    fuenteLetras = fuenteGlacial("res/GlacialIndifference-Regular.otf", 16, Font.PLAIN);
        Color   azulCuadros = new Color(0x0D47A1); // para el cuadro de crear cuenta
        Color   azulPastelC = new Color(0xBBDEFB); // para el cuadro del form 
        Color   Colormenu = new Color(0xE3F2FD);

        //creacion del cuadro central
        MiniVentanaUtil panelForm = new MiniVentanaUtil(40, azulPastelC, 500, 570, true, azulCuadros );//colorcito TENIA CuadroCentro
      
        JLabel titulo1 = new JLabel("¿No tienes cuenta?");// el texto q muestra eso
        titulo1.setFont(fuenteT);
        titulo1.setHorizontalAlignment(SwingConstants.CENTER);
        titulo1.setBounds(0, 15, 500, 35);
        panelForm.add(titulo1);

        JLabel titulo2 = new JLabel("¡Créala!");// el texto q muestra eso
        titulo2.setFont(fuenteT);
        titulo2.setHorizontalAlignment(SwingConstants.CENTER);
        titulo2.setBounds(0, 50, 500, 35);
        panelForm.add(titulo2);

        //Correo
        JLabel correoR = new JLabel("Correo Electrónico");
        correoR.setBounds(50, 95, 200, 20);
        correoR.setFont(fuenteLetras);
        panelForm.add(correoR);

        //Crea cuadrito para que el user pueda poner su correo
        CampoTextoUtil cuadritoCorreo = new CampoTextoUtil(15, azulCuadros, 300, 30, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoCorreo.setBounds(50, 120, 400, 40);
        panelForm.add(cuadritoCorreo);

        //cedula
        JLabel cedulaR = new JLabel("Cédula de Identidad");
        cedulaR.setBounds(50, 175, 200, 20);
        cedulaR.setFont(fuenteLetras);
        panelForm.add(cedulaR);

        //Crea cuadrito para que el user pueda poner su cedula
        CampoTextoUtil cuadritoCedula = new CampoTextoUtil(15, azulCuadros, 300, 30, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoCedula.setBounds(50, 200, 400, 40);
        panelForm.add(cuadritoCedula);

        //contraseña
        JLabel contraseñaR = new JLabel("Contraseña"); 
        contraseñaR.setBounds(50, 255, 200, 20);
        contraseñaR.setFont(fuenteLetras);
        panelForm.add(contraseñaR);

        //Crea cuadrito para que el user pueda poner su contraseña
        CampoContrasenaUtil cuadritoContraseña = new CampoContrasenaUtil(15, azulCuadros, 300, 30, Color.WHITE, Color.BLACK, fuenteNormal); //CAMBIAR AKIII
        cuadritoContraseña.setBounds(50, 280, 400, 40);
        panelForm.add(cuadritoContraseña);

        //confirmar contraseña
        JLabel confirmarC = new JLabel("Confirmar Contraseña");
        confirmarC.setBounds(50,335,200,20);
        confirmarC.setFont(fuenteLetras);
        panelForm.add(confirmarC);

        //Crea cuadrito para que el user pueda confirmar su contraseña
        CampoContrasenaUtil cuadritoConfirmar = new CampoContrasenaUtil(15, azulCuadros, 300, 30, Color.WHITE, Color.BLACK, fuenteNormal); //CAMBIAR AKIII
        cuadritoConfirmar.setBounds(50, 360, 400, 40);
        panelForm.add(cuadritoConfirmar);

        //texto para que escoja el rol
        JLabel rolUser = new JLabel("Escoja su rol: ");
        rolUser.setFont(fuenteLetras);
        rolUser.setBounds(50, 415, 300, 25);
        panelForm.add(rolUser);

        //cuadro en donde salen las opciones
        String[] opciones= {"Administrador", "Conductor","Estudiante"};
        MenuUtil menu = new MenuUtil(opciones, 15, Colormenu, Color.BLACK, Colormenu, fuenteNormal);
        menu.setFont(fuenteNormal);
        menu.setBackground(Colormenu);
        menu.setBounds(50, 440, 400, 40);
        panelForm.add(menu);

        //botoncito de registro

        BotonUtil Registrar = new BotonUtil("Crear Cuenta", azulCuadros, Color.WHITE, 20, new Font("SansSerif", Font.BOLD, 14), 180, 40);
        Registrar.setBounds(150, 499, 200, 50);
        panelForm.add(Registrar);
        
        add(panelForm);
    }

    public static void main(String[] args){
        Registro interfazR = new Registro();
        interfazR.setVisible(true);
    }
}