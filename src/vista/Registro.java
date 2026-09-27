package vista;
import javax.swing.*;
import java.awt.*;
import util.*;
public class Registro extends JFrame{

    public Registro(){

        setTitle ("VIAUCV");
        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        ImagenFondo fondo = new ImagenFondo("InterfazViaUCV.png"); // Pon el nombre exacto de tu imagen
        fondo.setLayout(new GridBagLayout()); // Le damos el poder de centrar
        setContentPane(fondo);

        CuadroCentro panelForm = new CuadroCentro(40, Color.WHITE);
        panelForm.setPreferredSize(new Dimension(400, 400)); // Tamaño del cuadro
        panelForm.setLayout(null);
      
        JLabel titulo = new JLabel("¿No tienes cuenta? ¡Créala!");// el texto q muestra eso
        titulo.setBounds(110, 20, 200, 30);
        panelForm.add(titulo);

        Font fuenteNormal = new Font("SansSerif", Font.PLAIN, 14);
        Color bordeGris = new Color(200, 200, 200);
        Color azulViaUCV = new Color(41, 128, 185);

        //Nombre
        JLabel correoR = new JLabel("Correo Electrónico");
        correoR.setBounds(40, 70, 150, 20);
        panelForm.add(correoR);

        //Crea cuadrito para que el user pueda poner su nombre
        CampoTextoUtil cuadritoCorreo = new CampoTextoUtil(15, bordeGris, 300, 30, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoCorreo.setBounds(40, 90, 300, 30);
        panelForm.add(cuadritoCorreo);

        //cedula
        JLabel cedulaR = new JLabel("Cédula de Identidad");
        cedulaR.setBounds(40, 130, 150, 20);
        panelForm.add(cedulaR);

        //Crea cuadrito para que el user pueda poner su cedula
        CampoTextoUtil cuadritoCedula = new CampoTextoUtil(15, bordeGris, 300, 30, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoCedula.setBounds(40, 150, 300, 30);
        panelForm.add(cuadritoCedula);

        //contraseña
        JLabel contraseñaR = new JLabel("Contraseña"); 
        contraseñaR.setBounds(40, 190, 300, 20);
        panelForm.add(contraseñaR);

        //Crea cuadrito para que el user pueda poner su contraseña
        JPasswordField cuadritoContraseña = new JPasswordField(); //CAMBIAR AKIII
        cuadritoContraseña.setBounds(40, 210, 300, 30);
        panelForm.add(cuadritoContraseña);

        //botoncito de registro

        BotonUtil Registrar = new BotonUtil("Crear Cuenta", azulViaUCV, Color.WHITE, 20, new Font("SansSerif", Font.BOLD, 14), 180, 40);
        Registrar.setBounds(100, 290, 180, 40);
        Registrar.setBounds(100, 290, 180, 40);
        panelForm.add(Registrar);
        
        add(panelForm);
    }

    class ImagenFondo extends JPanel{
        private Image imagen;

        public ImagenFondo(String InterfazViaUCV){

            java.net.URL ruta = getClass().getResource(InterfazViaUCV);
            
            //Verifica si la encontró, lO USAMO PA VE Q PASO CON EL FONDO Q NO SE PONIA
            if (ruta != null) {
                imagen = new ImageIcon(ruta).getImage();
            }  else {
                // Si sale este mensaje en la terminal, hay un error en el nombre o ubicación
                System.out.println(" Java no encontró la imagen: " + InterfazViaUCV);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagen != null) {
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            }
        }
            
    }
    class CuadroCentro extends JPanel{
        private int radio;
        private Color colorCuadro;

        public CuadroCentro(int radio, Color color) {
            this.radio = radio;
            this.colorCuadro = color;
            setOpaque(false); // para que las esquinas no se vean  grises
        }

        @Override
        protected void paintComponent(Graphics graficos){
            Graphics2D grafico = (Graphics2D) graficos;
            grafico.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            grafico.setColor(colorCuadro);
            grafico.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);
            super.paintComponent(graficos);
        }
    }

    //  vamos a crear las cajas. En Java, los textos fijos son JLabel y las cajas para escribir son JTextField
    public static void main(String[] args){
        Registro interfazR = new Registro();
        interfazR.setVisible(true);
    }

}

