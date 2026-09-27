package util;
import java.awt.*;
import javax.swing.*;
import java.io.File;

public class EncabezadoUtil extends JPanel{
    private MiniVentanaUtil encabezado;
    private JLabel sobreNosotros;
    private JLabel inicio;   
    private JLabel logo; 
    private static final String rutaLogo= "res/LogoViaUCV.png"; //Ruta del logo

    public EncabezadoUtil(Color colorFondo){
        //Hacemos el contenedor que se asegura de ponerlo en el lugar adecuado
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBorder (BorderFactory.createEmptyBorder(30,30,10,30));

        encabezado=new MiniVentanaUtil (25, colorFondo, 1000 , 75, false, null); //Ya predefinidos (solo cambia el color)
        encabezado.setLayout(new BorderLayout(15,0)); //Ubica los elemntos izq o der 
        encabezado.setBorder((BorderFactory.createEmptyBorder(8,25,6,35))); //Padding interno de la barra
        encabezado.setMinimumSize(new Dimension(1000,80));
        
         //Letra League Spartan que usamos en canva (pa q se vea bnito)
        Font fuente= fuenteSpartan("res/LeagueSpartan-Bold.otf", 16, Font.PLAIN);


        //Parte izq (logo e inicio)
        JPanel parteIzq= new JPanel(new GridBagLayout()); //separar el logo y el texto de inicio (pero todo dentro de un cuadro)
        parteIzq.setOpaque(false);

        //Parte donde se alinea todo
        GridBagConstraints gbcIzq= new GridBagConstraints();
        gbcIzq.gridy=0;

        //Logo ViaUCV
        logo=new JLabel();

        try {
            ImageIcon logoViaUCV= new ImageIcon(rutaLogo);
            Image imgRedimensionada= logoViaUCV.getImage().getScaledInstance(160, -1, Image.SCALE_SMOOTH); //el -1 para omitir el alto por ahora
            logo.setIcon(new ImageIcon(imgRedimensionada));
        } catch (Exception e) {
            System.err.println("No se pudo cargar el logo: "+e.getMessage());
        }

        gbcIzq.insets=new Insets(0,0,30,15);
        parteIzq.add(logo,gbcIzq); //Anadimos al panel izq, los insets aplican solo al logo

        //texto
        inicio= new JLabel ("Inicio  ");
        inicio.setFont(fuente);
        inicio.setForeground(new Color(9, 39, 84)); //azul oscuro
        inicio.setCursor(new Cursor(Cursor.HAND_CURSOR)); //la manito cuando pasaas por encima

        gbcIzq.insets= new Insets(5,0,0,0);
        parteIzq.add(inicio,gbcIzq);//Anadimos al panel izq, aplican solo al inicio

        //panel completo
        encabezado.add(parteIzq, BorderLayout.WEST);

        //sobre nosotros
        sobreNosotros= new JLabel("Sobre Nosotros  ");
        sobreNosotros.setFont(fuente);
        sobreNosotros.setForeground(new Color(9, 39, 84)); //azul oscuro
        sobreNosotros.setCursor(new Cursor(Cursor.HAND_CURSOR)); //la manito cuando pasaas por encima
        encabezado.add(sobreNosotros,BorderLayout.EAST); //anadir

        //Efecto interactivo sobreNosotos y sobre inicio
        sobreNosotros.addMouseListener(new java.awt.event.MouseAdapter (){
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event){
                sobreNosotros.setForeground(new Color(98, 156, 197));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event){
                sobreNosotros.setForeground(new Color(9, 39, 84));
            }

            @Override 
            public void mousePressed(java.awt.event.MouseEvent event){
                sobreNosotros.setForeground(new Color(98, 156, 197));
            }

            @Override 
            public void mouseReleased(java.awt.event.MouseEvent event){
                sobreNosotros.setForeground(new Color(9, 39, 84));
            }

        });

            inicio.addMouseListener(new java.awt.event.MouseAdapter (){
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event){
                inicio.setForeground(new Color(98, 156, 197));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event){
                inicio.setForeground(new Color(9, 39, 84));
            }

            @Override 
            public void mousePressed(java.awt.event.MouseEvent event){
                inicio.setForeground(new Color(98, 156, 197));
            }

            @Override 
            public void mouseReleased(java.awt.event.MouseEvent event){
                inicio.setForeground(new Color(9, 39, 84));
            }

        });

        //anadir el encabezado a todo este contenedor transparente
        add(encabezado);
    }

    
    //Letra League Spartan que usamos en canva (pa q se vea bnito)
    private Font fuenteSpartan(String ruta, float tamano, int estilo){
        try {
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, new File(ruta));
            return fuente.deriveFont(estilo, tamano);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la fuente: " + e.getMessage());
            return new Font("SansSerif", estilo, (int) tamano);
        }
    }


}
