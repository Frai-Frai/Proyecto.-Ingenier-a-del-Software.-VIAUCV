package vista;

import util.*;
import modelo.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.function.Consumer;

public class InterfazConductor extends JFrame {

    private Usuario ConductorActual;
    private JPanel panelDerechaCentro, panelContenidoBlanco;
    private MiniVentanaUtil menuIzq, Bienvenida;
    
    private BotonUtil btnAnterior;
    private BotonUtil btnSiguiente;
    
    private JLabel opItinerariodelasemana, opCerrarS;
    
    private Consumer<String> ClickR;

    //tipografias
    private Font fuenteGlacial(String ruta, float tamano, int estilo){
        try {
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, new File(ruta));
            return fuente.deriveFont(estilo, tamano);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la fuente: " + e.getMessage());
            return new Font("SansSerif", estilo, (int) tamano);
        }
    }

    private Font League(String ruta, float tamano, int estilo){
        try {
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, new File(ruta));
            return fuente.deriveFont(estilo, tamano);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la fuente: " + e.getMessage());
            return new Font("SansSerif", estilo, (int) tamano);
        }
    }
    
    public void setClickRuta(Consumer<String> accion) {
        this.ClickR = accion;
    }

    public InterfazConductor(Usuario conductor) {

        this.ConductorActual = conductor;
        setTitle("VIAUCV - Interfaz de Conductor");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // fuentes 
        Font fuenteT = League("res/LeagueSpartan-Bold.otf", 22f, Font.BOLD); 
        Font fuenteSubT = fuenteGlacial("res/GlacialIndifference-Bold.otf", 16, Font.BOLD); 
        Font fuenteLink = fuenteGlacial("res/GlacialIndifference-Bold.otf", 16f, Font.BOLD);
        Font fuenteSubLink = fuenteGlacial("res/GlacialIndifference-Bold.otf", 14f, Font.BOLD);
        Font fuenteRegularBtn = fuenteGlacial("res/GlacialIndifference-Regular.otf", 12f, Font.PLAIN);
        
        // colores de todo
        Color azulCuadros = new Color(0x0D47A1); 
        Color colormenu = Color.WHITE; 
        Color colorFondo = new Color(0xD6EBF7); 
        Color colorWelcome = new Color(134, 185, 222); 

        // menu de la izquierda
        JPanel panelMenu = new JPanel(new BorderLayout());
        panelMenu.setOpaque(true);
        panelMenu.setBackground(colorFondo);
        panelMenu.setBorder(new EmptyBorder(12, 12, 12, 0));

        menuIzq = new MiniVentanaUtil(30, colormenu, 240, 700, false, colormenu);
        menuIzq.setPreferredSize(new Dimension(240, 0));
        menuIzq.setLayout(null);

        // Logo
        ImageIcon logoViaUCV = new ImageIcon("res/LogoViaUCV.png");
        Image imgRedimensionada = logoViaUCV.getImage().getScaledInstance(160, -1, Image.SCALE_SMOOTH); 
        JLabel logo = new JLabel(new ImageIcon(imgRedimensionada));   
        logo.setBounds(30, 20, 160, 100);    
        menuIzq.add(logo);
        
        // Itinerario de la semana
        opItinerariodelasemana = new JLabel("Itinerario de la semana");
        opItinerariodelasemana.setBounds(30, 145, 180, 30);
        new TextosInteractivosUtil(opItinerariodelasemana, "Itinerario de la semana", Color.BLACK, azulCuadros, fuenteLink);
        menuIzq.add(opItinerariodelasemana);

        // Cerrar sesion
        opCerrarS = new JLabel("Cerrar sesión");
        opCerrarS.setBounds(30, 195, 180, 30);
        new TextosInteractivosUtil(opCerrarS, "Cerrar sesión", Color.BLACK, azulCuadros, fuenteLink);
        menuIzq.add(opCerrarS);

        panelMenu.add(menuIzq, BorderLayout.CENTER);
        add(panelMenu, BorderLayout.WEST);
        
        // Panel derecho total
        JPanel panelDerechoTotal = new JPanel(new BorderLayout());
        panelDerechoTotal.setBackground(colorFondo);
        panelDerechoTotal.setBorder(new EmptyBorder(12, 12, 12, 12));

        // Panel de bienvenida 
        Bienvenida = new MiniVentanaUtil(30, colorWelcome, 0, 130, false, colorWelcome);
        Bienvenida.setPreferredSize(new Dimension(0, 130));
        Bienvenida.setLayout(null);

        // Obtener nombre del conductor
        String nombreConductor = (conductor != null) ? conductor.getNombreUsuario() : "Conductor";

        JPanel panelBloqueTextos = new JPanel();
        panelBloqueTextos.setLayout(new BoxLayout(panelBloqueTextos, BoxLayout.Y_AXIS));
        panelBloqueTextos.setOpaque(false);
        panelBloqueTextos.setBounds(35, 30, 640, 80);

        // linea de arriba
        JPanel panelSaludo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelSaludo.setOpaque(false);

        JLabel signoExclamacionInicio = new JLabel("\u00A1");
        signoExclamacionInicio.setFont(new Font("SansSerif", Font.BOLD, 22));
        signoExclamacionInicio.setForeground(Color.WHITE);
        panelSaludo.add(signoExclamacionInicio);

        JLabel textoSaludoCentral = new JLabel("Hola, conductor(a) " + nombreConductor + "!");
        textoSaludoCentral.setFont(fuenteT);
        textoSaludoCentral.setForeground(Color.WHITE);
        panelSaludo.add(textoSaludoCentral);

        panelBloqueTextos.add(panelSaludo);
        panelBloqueTextos.add(Box.createRigidArea(new Dimension(0, 8)));

        //linea de abajo 
        JPanel panelSubtitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelSubtitulo.setOpaque(false);

        JLabel signoInicio = new JLabel("\u00BF");
        signoInicio.setFont(new Font("SansSerif", Font.BOLD, 16));
        signoInicio.setForeground(Color.WHITE);
        panelSubtitulo.add(signoInicio);

        JLabel textoCentral = new JLabel("Listo(a) para tu jornada");
        textoCentral.setFont(fuenteSubT);
        textoCentral.setForeground(Color.WHITE);
        panelSubtitulo.add(textoCentral);

        JLabel signoFin = new JLabel("?");
        signoFin.setFont(new Font("SansSerif", Font.BOLD, 16));
        signoFin.setForeground(Color.WHITE);
        panelSubtitulo.add(signoFin);

        panelBloqueTextos.add(panelSubtitulo);
        Bienvenida.add(panelBloqueTextos);

        JPanel panelSuperiorWrapper = new JPanel(new BorderLayout());
        panelSuperiorWrapper.setOpaque(false);
        panelSuperiorWrapper.setBorder(new EmptyBorder(0, 0, 12, 0));
        panelSuperiorWrapper.add(Bienvenida, BorderLayout.CENTER);

        // Panel central de contenido 
        panelDerechaCentro = new JPanel(new BorderLayout());
        panelDerechoTotal.setBackground(colorFondo);
        panelDerechaCentro.setOpaque(false);
        panelDerechaCentro.setBorder(new EmptyBorder(10, 2, 2, 2));

        // Panel blanco compacto con bordes redondeados 
        panelContenidoBlanco = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Relleno blanco con esquinas redondeadas
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 24, 24);
                
                // Borde grueso azul 
                g2.setColor(colorWelcome);
                g2.setStroke(new BasicStroke(5.5f));
                g2.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 24, 24);
                
                g2.dispose();
            }
        };
        panelContenidoBlanco.setOpaque(false);
        panelContenidoBlanco.setLayout(null);
        panelContenidoBlanco.setPreferredSize(new Dimension(0, 390)); 

        JPanel panelCentroWrapper = new JPanel(new GridBagLayout());
        panelCentroWrapper.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 10, 5, 10);
        panelCentroWrapper.add(panelContenidoBlanco, gbc);

        panelDerechaCentro.add(panelCentroWrapper, BorderLayout.CENTER);

        // Panel inferior para botones 
        JPanel panelContenedorBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.setColor(new Color(180, 210, 230));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
                g2.dispose();
            }
        };

        panelContenedorBotones.setOpaque(false);
        panelContenedorBotones.setBorder(new EmptyBorder(12, 25, 12, 25));

        ImageIcon iconoFlechaDer = null;
        ImageIcon iconoFlechaIzq = null;
        try {
            ImageIcon originalFlechaDer = new ImageIcon("res/flecha.jpeg");
            Image scaledDer = originalFlechaDer.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            iconoFlechaDer = new ImageIcon(scaledDer);
            
            ImageIcon originalFlechaIzq = new ImageIcon("res/flechacontraria.jpeg");
            Image scaledIzq = originalFlechaIzq.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            iconoFlechaIzq = new ImageIcon(scaledIzq);
        } catch (Exception e) {}

        // Botones 
        btnAnterior = new BotonUtil("", Color.WHITE, Color.BLACK, 15, fuenteRegularBtn, 46, 46);
        if (iconoFlechaIzq != null) btnAnterior.setIcon(iconoFlechaIzq);
        else btnAnterior.setText("<");

        btnSiguiente = new BotonUtil("", Color.WHITE, Color.BLACK, 15, fuenteRegularBtn, 46, 46);
        if (iconoFlechaDer != null) btnSiguiente.setIcon(iconoFlechaDer);
        else btnSiguiente.setText(">");

        panelContenedorBotones.add(btnAnterior);
        panelContenedorBotones.add(btnSiguiente);

        JPanel panelInferiorWrapper = new JPanel(new GridBagLayout());
        panelInferiorWrapper.setOpaque(false);

        GridBagConstraints gbcBoton = new GridBagConstraints();
        gbcBoton.insets = new Insets(2, 0, 8, 0);
        panelInferiorWrapper.add(panelContenedorBotones, gbcBoton);

        panelDerechaCentro.add(panelInferiorWrapper, BorderLayout.SOUTH);

        panelDerechoTotal.add(panelSuperiorWrapper, BorderLayout.NORTH);
        panelDerechoTotal.add(panelDerechaCentro, BorderLayout.CENTER);

        add(panelDerechoTotal, BorderLayout.CENTER);
    }
    
    // Getters 
    public JPanel getPanelContenidoBlanco(){
         return panelContenidoBlanco; 
    }

    public BotonUtil getBtnAnterior(){
         return btnAnterior; 
    }

    public BotonUtil getBtnSiguiente(){ 
        return btnSiguiente; 
    }

    public Usuario getConductorActual(){ 
        return ConductorActual; 
    }

    public JLabel getOpItinerariodelasemana(){ 
        return opItinerariodelasemana; 
    }

    public JLabel getOpCerrarS(){ 
        return opCerrarS;
    }
    
<<<<<<< HEAD
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfazConductor ventana = new InterfazConductor(null);
            ventana.setVisible(true);
        });
    }
=======
>>>>>>> 9a28f687c11840305afcd4fa3b691df938a7c205
}