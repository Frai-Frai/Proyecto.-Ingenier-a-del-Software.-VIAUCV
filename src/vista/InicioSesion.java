package vista;
import javax.swing.*;
import java.awt.*;
import java.io.File;

//Import utilidades (Estilos del boton y del campo)
import util.BotonUtil;
import util.MiniVentanaUtil;
import util.CampoTextoUtil;
import util.CampoContrasenaUtil;

//Inicio sesion

public class InicioSesion extends JFrame {

    //Campos y botones
    private JTextField campoUsuario;
    private JPasswordField contrasena; 
    private JButton boton;

    //fuente de letra "Glcial Indifference"
    private Font fuenteGlacial(String ruta, float tamano, int estilo){
        try {
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, new File(ruta));
            return fuente.deriveFont(estilo, tamano);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la fuente: " + e.getMessage());
            return new Font("SansSerif", estilo, (int) tamano);
        }
    }


    public InicioSesion(){

        //Ventana general
        setTitle("ViaUCV - Inicio de Sesión");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //aqui pongo la imagen del fondo desde res
        ImageIcon imgFondo= new ImageIcon ("res/Fondo3InterfazViaUCV.png");
        JPanel panelFondo = new JPanel(){
            @Override 
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(imgFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(new BorderLayout());

        //Agrega encabezado de arriba 


        //Logo ViaUCV
        ImageIcon logoViaUCV= new ImageIcon("res/LogoViaUCV.png");
        Image imgRedimensionada= logoViaUCV.getImage().getScaledInstance(160, -1, Image.SCALE_SMOOTH);
        ImageIcon logoRedimensionado= new ImageIcon(imgRedimensionada);
        JLabel logo= new JLabel(logoRedimensionado);
        int alto= logoRedimensionado.getIconHeight();
        logo.setBounds(25,10,160,alto);


        //Panel pequeno con los campos y boton
        MiniVentanaUtil miniVentana = new MiniVentanaUtil(30, Color.WHITE, 400, 450, true, new Color(2,58,144));


        //Dentro de la miniventana
        JLabel titulo= new JLabel("Iniciar Sesión", JLabel .LEFT);
        titulo.setFont(new Font ("Open Sans", Font.BOLD, 35));
        titulo.setBounds(40,80,320,40);
        miniVentana.add(titulo);
        miniVentana.add(logo); //Anadir el logo de la pag

        //Campo de usuario 
        Font fuente1 = fuenteGlacial("res/GlacialIndifference-Regular.otf", 19, Font.PLAIN);

        JLabel subtitulo1= new JLabel("Ingresa tu usuario o cédula", JLabel.LEFT);
        subtitulo1.setFont(fuente1);
        subtitulo1.setBounds(40,145,320,30);
        miniVentana.add(subtitulo1);

        //Meter estilo de campo
        Font fuenteCampo= new Font("Arial", Font.PLAIN, 15);
        campoUsuario= new CampoTextoUtil(15, new Color(140,203,249), 320, 25, Color.WHITE, Color.BLACK, fuenteCampo);
        campoUsuario.setBounds(38,180,320,45);
        miniVentana.add(campoUsuario);

        //Campo de contarsena
        JLabel subtitulo2= new JLabel("Ingresa tu contraseña", JLabel.LEFT);
        subtitulo2.setFont(fuente1);
        subtitulo2.setBounds(40,245,320,30);
        miniVentana.add(subtitulo2);

        //Ingresar estilo de campo
        contrasena= new CampoContrasenaUtil(15, new Color(140,203,249), 320, 25, Color.WHITE, Color.BLACK, fuenteCampo);
        contrasena.setBounds(38,280,320,45);
        miniVentana.add(contrasena);


        //Boton ingresar + estilo de boton
        Font fuente2 = fuenteGlacial("res/GlacialIndifference-Bold.otf", 16, Font.PLAIN);

        boton= new BotonUtil("Ingresar", new Color(2,58,144), Color.WHITE, 25, fuente2, 10, 60);
        boton.setBounds(95, 360, 200, 50);
        miniVentana.add(boton);

        //Para poner la mini ventana a la derecha
        JPanel panelderecho= new JPanel(new GridBagLayout());
        panelderecho.setOpaque(false);

        GridBagConstraints gbcDerecho = new GridBagConstraints();
        gbcDerecho.insets = new Insets(80, 0, 0, 180);
        panelderecho.setOpaque(false);
        panelderecho.add(miniVentana,gbcDerecho);
        

        panelFondo.add(panelderecho,BorderLayout.EAST);
        add(panelFondo);

    }

}
