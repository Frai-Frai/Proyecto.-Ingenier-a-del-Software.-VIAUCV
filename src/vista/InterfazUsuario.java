package vista;

import util.*;
import modelo.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent; 


public class InterfazUsuario extends JFrame {

    private Usuario pasajeroActual;
    private JPanel panelDerechaCentro, panelRutasGrid;
    private MiniVentanaUtil menuIzq, Bienvenida;
    
    private BotonUtil btnAnterior;
    private BotonUtil btnSiguiente;
    
    private JLabel opRutasDisponibles, opRutasUrbanas, opRutasExtraurbanas, opVisualizarRecorridos, opMonedero, opCerrarS;
    
    private Consumer<String> ClickR;

    public void setClickRuta(Consumer<String> accion) {
        this.ClickR = accion;
    }

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

    public InterfazUsuario(Usuario pasajero) {

        this.pasajeroActual = pasajero;
        setTitle("VIAUCV - Interfaz Usuario");
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
        
        // "Rutas disponibles"
        opRutasDisponibles = new JLabel("Rutas disponibles");
        opRutasDisponibles.setBounds(30, 140, 180, 30);
        opRutasDisponibles.setFont(fuenteLink);
        opRutasDisponibles.setForeground(Color.BLACK);
        menuIzq.add(opRutasDisponibles);
        
        // Sub opción: Rutas urbanas 
        opRutasUrbanas = new JLabel("• Rutas urbanas");
        opRutasUrbanas.setBounds(45, 175, 165, 25);
        new TextosInteractivosUtil(opRutasUrbanas, "• Rutas urbanas", Color.BLACK, azulCuadros, fuenteSubLink);
        menuIzq.add(opRutasUrbanas);

        // Sub opción: Rutas extraurbanas
        opRutasExtraurbanas = new JLabel("• Rutas extraurbanas");
        opRutasExtraurbanas.setBounds(45, 210, 165, 25);
        new TextosInteractivosUtil(opRutasExtraurbanas, "• Rutas extraurbanas", Color.BLACK, azulCuadros, fuenteSubLink);
        menuIzq.add(opRutasExtraurbanas);

        // link de visualizar recorrido 
        opVisualizarRecorridos = new JLabel("Visualizar recorridos");
        opVisualizarRecorridos.setBounds(30, 255, 180, 30);
        new TextosInteractivosUtil(opVisualizarRecorridos, "Visualizar recorridos", Color.BLACK, azulCuadros, fuenteLink);
        menuIzq.add(opVisualizarRecorridos);

        // para poder ver el monedero personal
        opMonedero = new JLabel("Monedero");
        opMonedero.setBounds(30, 305, 180, 30);
        new TextosInteractivosUtil(opMonedero, "Monedero", Color.BLACK, azulCuadros, fuenteLink);
        menuIzq.add(opMonedero);

        // para poder cerrar sesion
        opCerrarS = new JLabel("Cerrar sesión");
        opCerrarS.setBounds(30, 450, 180, 30);
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

        // Obtener nombre del pasajero
        String nombreUsuario = (pasajero != null) ? pasajero.getNombreUsuario() : "Usuario";

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

        JLabel textoSaludoCentral = new JLabel("Hola, pasajero(a) " + nombreUsuario + "!");
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

        JLabel textoCentral = new JLabel("A d\u00f3nde quieres ir hoy");
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

        // Panel central de contenido sin fondo 
        panelDerechaCentro = new JPanel(new BorderLayout());
        panelDerechoTotal.setBackground(colorFondo);
        panelDerechaCentro.setOpaque(false);
        panelDerechaCentro.setBorder(new EmptyBorder(22, 2, 2, 2));

        // Cuadrícula de 2 filas y 5 columnas 
        panelRutasGrid = new JPanel(new GridLayout(2, 5, 12, 2));
        panelRutasGrid.setOpaque(false);

        panelDerechaCentro.add(panelRutasGrid, BorderLayout.CENTER);

        // Panel inferior para botones
        JPanel panelContenedorBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 4)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(new Color(180, 210, 230));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
            }
        };

        panelContenedorBotones.setOpaque(false);
        panelContenedorBotones.setBorder(new EmptyBorder(3, 10, 3, 10));

        ImageIcon iconoFlechaDer = null;
        ImageIcon iconoFlechaIzq = null;
        try {
            ImageIcon originalFlechaDer = new ImageIcon("res/flecha.jpeg");
            Image scaledDer = originalFlechaDer.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
            iconoFlechaDer = new ImageIcon(scaledDer);
            
            ImageIcon originalFlechaIzq = new ImageIcon("res/flechacontraria.jpeg");
            Image scaledIzq = originalFlechaIzq.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
            iconoFlechaIzq = new ImageIcon(scaledIzq);
        } catch (Exception e) {}

        btnAnterior = new BotonUtil("", Color.WHITE, Color.BLACK, 15, fuenteRegularBtn, 38, 38);
        if (iconoFlechaIzq != null) btnAnterior.setIcon(iconoFlechaIzq);
        else btnAnterior.setText("<");

        btnSiguiente = new BotonUtil("", Color.WHITE, Color.BLACK, 15, fuenteRegularBtn, 38, 38);
        if (iconoFlechaDer != null) btnSiguiente.setIcon(iconoFlechaDer);
        else btnSiguiente.setText(">");

        panelContenedorBotones.add(btnAnterior);
        panelContenedorBotones.add(btnSiguiente);

        JPanel panelInferiorWrapper = new JPanel(new GridBagLayout());
        panelInferiorWrapper.setOpaque(false);
        panelInferiorWrapper.add(panelContenedorBotones);

        panelDerechaCentro.add(panelInferiorWrapper, BorderLayout.SOUTH);

        panelDerechoTotal.add(panelSuperiorWrapper, BorderLayout.NORTH);
        panelDerechoTotal.add(panelDerechaCentro, BorderLayout.CENTER);

        add(panelDerechoTotal, BorderLayout.CENTER);
    }

    
    public JPanel crearTarjetaRuta(String nombreDestino){
        JPanel tarjeta = new JPanel();
        tarjeta.setPreferredSize(new Dimension(165, 210)); 
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setOpaque(false);
        tarjeta.setBorder(new EmptyBorder(0, 0, 0, 0));
        tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblImagen = new JLabel();
        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            ImageIcon iconOriginal = new ImageIcon("res/" + nombreDestino.replaceAll("\\s+", "") + ".jpeg");
            if (iconOriginal.getIconWidth() <= 0) {
                iconOriginal = new ImageIcon("res/Lugar.jpeg");
            }
            ImageIcon iconoCircular = crearImagenCircular(iconOriginal.getImage(), 120);
            lblImagen.setIcon(iconoCircular);
        } catch (Exception e) {
            try {
                ImageIcon defaultIcon = new ImageIcon("res/Lugar.jpeg");
                ImageIcon iconoCircular = crearImagenCircular(defaultIcon.getImage(), 120);
                lblImagen.setIcon(iconoCircular);
            } catch (Exception ex) {
                lblImagen.setText("[ Imagen ]");
            }
        }

        Component espacioRigido = Box.createRigidArea(new Dimension(0, 15));

        JPanel panelNombre = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 6)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(134, 185, 222)); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.dispose();
            }
        };
        panelNombre.setOpaque(false);
        panelNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Forzar tamaño fijo máximo 
        Dimension dimensionPastilla = new Dimension(150, 34);
        panelNombre.setPreferredSize(dimensionPastilla);
        panelNombre.setMaximumSize(dimensionPastilla);

        Font fuenteGlacialBold = fuenteGlacial("res/GlacialIndifference-Bold.otf", 16f, Font.BOLD);
        JLabel lblTitulo = new JLabel(nombreDestino);
        lblTitulo.setFont(fuenteGlacialBold);
        lblTitulo.setForeground(Color.BLACK);
        panelNombre.add(lblTitulo);

        panelNombre.addMouseListener(new MouseAdapter() {
            @Override 

            public void mousePressed(MouseEvent e){
                if(ClickR != null){
                    ClickR.accept(nombreDestino);
                }
            }
        });
        
        tarjeta.add(lblImagen);
        tarjeta.add(espacioRigido);
        tarjeta.add(panelNombre);

        return tarjeta;
    }

    private ImageIcon crearImagenCircular(Image imgOriginal, int diametro) {
        BufferedImage imagenBuffer = new BufferedImage(diametro, diametro, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imagenBuffer.createGraphics();
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        Ellipse2D.Double formaCircular = new Ellipse2D.Double(0, 0, diametro, diametro);
        g2.setClip(formaCircular);
        
        g2.drawImage(imgOriginal, 0, 0, diametro, diametro, null);
        g2.dispose();
        
        return new ImageIcon(imagenBuffer);
    }

    public void actualizarRutasVista(List <String> destinosPagina, int elemMax){
        panelRutasGrid.removeAll(); //borra las tarjetas de las rutas q estaban antes

        for(String destino : destinosPagina){
            JPanel tarjetaRuta = crearTarjetaRuta(destino);
            panelRutasGrid.add(tarjetaRuta);
        }

        int orden = destinosPagina.size(); //mantener todo en orden bonito
        for(int i = orden; i < elemMax; i++){
            panelRutasGrid.add(new JLabel());
        }

        panelRutasGrid.revalidate();
        panelRutasGrid.repaint();
    }
    
    //getters 
    public JPanel getPanelRutasGrid(){
         return panelRutasGrid; 
    }

    public BotonUtil getBtnAnterior(){
         return btnAnterior; 
    }

    public BotonUtil getBtnSiguiente(){ 
        return btnSiguiente; 
    }

    public Usuario getPasajeroActual(){ 
        return pasajeroActual; 
    }

    public JLabel getOpRutasDisponibles(){ 
        return opRutasDisponibles; 
    }

    public JLabel getOpRutasUrbanas(){ 
        return opRutasUrbanas; 
    }

    public JLabel getOpRutasExtraurbanas(){ 
        return opRutasExtraurbanas; 
    }

    public JLabel getOpVisualizarRecorridos(){ 
        return opVisualizarRecorridos; 
    }

    public JLabel getOpMonedero(){ 
        return opMonedero;
    }

    public JLabel getOpCerrarS(){ 
        return opCerrarS;
    }

}