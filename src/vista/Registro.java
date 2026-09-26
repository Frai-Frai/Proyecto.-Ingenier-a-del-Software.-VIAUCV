import javax.swing.*;
import java.awt.*;

public class Registro extends JFrame{

    public Registro(){

        setTitle ("VIAUCV");
        setSize(200,350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(41, 128, 185)); //color fondo
        setLayout(new GridBagLayout());// el cuadro del formulario al inicio

        CuadroCentro panelForm = new CuadroCentro(40, Color.WHITE);
        panelForm.setPreferredSize(new Dimension(400, 400)); // Tamaño del cuadro
        panelForm.setLayout(null);

        JLabel titulo = new JLabel("¿No tienes cuenta? ¡Créala!");// el texto q muestra eso
        titulo.setBounds(110, 20, 200, 30);
        panelForm.add(titulo);

        //Nombre
        JLabel correoR = new JLabel("Correo Electrónico");
        correoR.setBounds(40, 70, 150, 20);
        panelForm.add(correoR);

        //Crea cuadrito para que el user pueda poner su nombre
        JTextField cuadritoCorreo = new JTextField();
        cuadritoCorreo.setBounds(40, 90, 300, 30);
        panelForm.add(cuadritoCorreo);

        //cedula
        JLabel cedulaR = new JLabel("Cédula de Identidad");
        cedulaR.setBounds(40, 130, 150, 20);
        panelForm.add(cedulaR);

        //Crea cuadrito para que el user pueda poner su cedula
        JTextField cuadritoCedula = new JTextField();
        cuadritoCedula.setBounds(40, 150, 300, 30);
        panelForm.add(cuadritoCedula);

        //contraseña
        JLabel contraseñaR = new JLabel("Contraseña");
        contraseñaR.setBounds(40, 190, 300, 20);
        panelForm.add(contraseñaR);

        //Crea cuadrito para que el user pueda poner su contraseña
        JPasswordField cuadritoContraseña = new JPasswordField();
        cuadritoContraseña.setBounds(40, 210, 300, 30);
        panelForm.add(cuadritoContraseña);

        //botoncito de registro

        JButton Registrar = new JButton("Crear Cuenta");
        Registrar.setBounds(100, 290, 180, 40);
        panelForm.add(Registrar);
        
        add(panelForm);
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
