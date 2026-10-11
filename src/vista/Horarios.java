package vista;

import util.*;
import modelo.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class Horarios extends JFrame {

    private Usuario pasajeroActual;
    private JPanel panelDerechoTotal, panelDerechaCentro, panelImagenSuperior, panelBlancoDias, panelBlancoHorarios;
    private JLabel lblTituloDestino;
    private MiniVentanaUtil menuIzq;
    private BotonUtil btnAnterior, btnSiguiente;
    private BotonUtil btnHorarioAnterior, btnHorarioSiguiente;
    
    private JLabel opRutasDisponibles, opRutasUrbanas, opRutasExtraurbanas, opVisualizarRecorridos, opMonedero, opCerrarS;

    public Horarios(Usuario pasajero) {

        this.pasajeroActual = pasajero;
        setTitle("VIAUCV - HORARIOS");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tipografías 
        Font fuenteTituloGrande = League("res/LeagueSpartan-Bold.otf", 36f, Font.PLAIN);
        Font fuenteLink = fuenteGlacial("res/GlacialIndifference-Bold.otf", 16f, Font.BOLD);
        Font fuenteSubLink = fuenteGlacial("res/GlacialIndifference-Bold.otf", 14f, Font.BOLD);
        Font fuenteRegularBtn = fuenteGlacial("res/GlacialIndifference-Regular.otf", 12f, Font.PLAIN);
        
        // Paleta de colores
        Color azulCuadros = new Color(0x0D47A1); 
        Color colormenu = Color.WHITE; 
        Color colorFondo = new Color(0xD6EBF7); 

        // Panel izquierdo
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
        
        // Subopción: Rutas urbanas 
        opRutasUrbanas = new JLabel("• Rutas urbanas");
        opRutasUrbanas.setBounds(45, 175, 165, 25);
        new TextosInteractivosUtil(opRutasUrbanas, "• Rutas urbanas", Color.BLACK, azulCuadros, fuenteSubLink);
        menuIzq.add(opRutasUrbanas);

        // Subopción: Rutas extraurbanas
        opRutasExtraurbanas = new JLabel("• Rutas extraurbanas");
        opRutasExtraurbanas.setBounds(45, 210, 165, 25);
        new TextosInteractivosUtil(opRutasExtraurbanas, "• Rutas extraurbanas", Color.BLACK, azulCuadros, fuenteSubLink);
        menuIzq.add(opRutasExtraurbanas);

        // "Visualizar recorridos" 
        opVisualizarRecorridos = new JLabel("Visualizar recorridos");
        opVisualizarRecorridos.setBounds(30, 255, 180, 30);
        new TextosInteractivosUtil(opVisualizarRecorridos, "Visualizar recorridos", Color.BLACK, azulCuadros, fuenteLink);
        menuIzq.add(opVisualizarRecorridos);

        // "Monedero" 
        opMonedero = new JLabel("Monedero");
        opMonedero.setBounds(30, 305, 180, 30);
        new TextosInteractivosUtil(opMonedero, "Monedero", Color.BLACK, azulCuadros, fuenteLink);
        menuIzq.add(opMonedero);

        // "Cerrar sesión" 
        opCerrarS = new JLabel("Cerrar sesión");
        opCerrarS.setBounds(30, 450, 180, 30);
        new TextosInteractivosUtil(opCerrarS, "Cerrar sesión", Color.BLACK, azulCuadros, fuenteLink);
        menuIzq.add(opCerrarS);

        panelMenu.add(menuIzq, BorderLayout.CENTER);
        add(panelMenu, BorderLayout.WEST);
        
        // Panel derecho total
        panelDerechoTotal = new JPanel(new BorderLayout());
        panelDerechoTotal.setBackground(colorFondo);
        panelDerechoTotal.setBorder(new EmptyBorder(12, 12, 12, 12));

        // Contenedor norte para la imagen 
        JPanel panelNorteTotal = new JPanel();
        panelNorteTotal.setLayout(new BoxLayout(panelNorteTotal, BoxLayout.Y_AXIS));
        panelNorteTotal.setOpaque(false);
        panelNorteTotal.setBorder(new EmptyBorder(0, 10, 15, 0));

        // Panel superior para la imagen 
        panelImagenSuperior = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                
                try {
                    ImageIcon iconCiudad = new ImageIcon("res/ciudad.jpeg");
                    Image imgCiudad = iconCiudad.getImage();
                    g2.setClip(new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30));
                    g2.drawImage(imgCiudad, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {}
                
                g2.dispose();
            }
        };
        panelImagenSuperior.setPreferredSize(new Dimension(0, 260));
        panelImagenSuperior.setOpaque(false);
        panelNorteTotal.add(panelImagenSuperior);

        panelNorteTotal.add(Box.createRigidArea(new Dimension(0, 25)));

        lblTituloDestino = new JLabel(" "); 
        lblTituloDestino.setFont(fuenteTituloGrande);
        lblTituloDestino.setForeground(Color.BLACK);
        panelNorteTotal.add(lblTituloDestino);

        panelDerechoTotal.add(panelNorteTotal, BorderLayout.NORTH);

        // Panel central
        panelDerechaCentro = new JPanel();
        panelDerechaCentro.setLayout(new BoxLayout(panelDerechaCentro, BoxLayout.Y_AXIS));
        panelDerechaCentro.setOpaque(false);
        
        panelDerechaCentro.add(Box.createRigidArea(new Dimension(0, 25)));

        // Carga de iconos de flechas 
        ImageIcon iconoFlechaDer = null;
        ImageIcon iconoFlechaIzq = null;
        try {
            ImageIcon originalFlechaDer = new ImageIcon("res/flecha.jpeg");
            Image scaledDer = originalFlechaDer.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
            iconoFlechaDer = new ImageIcon(scaledDer);
            
            ImageIcon originalFlechaIzq = new ImageIcon("res/flechacontraria.jpeg");
            Image scaledIzq = originalFlechaIzq.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
            iconoFlechaIzq = new ImageIcon(scaledIzq);
        } catch (Exception e) {
            System.out.println("Error al cargar las imágenes de flechas: " + e.getMessage());
        }

        // --- 1. PANEL SUPERIOR (Días) ---
        panelBlancoDias = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);
                
                g2.setColor(new Color(210, 225, 240));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 28, 28);
                
                g2.dispose();
            }
        };
        panelBlancoDias.setOpaque(false);
        panelBlancoDias.setPreferredSize(new Dimension(0, 290));
        panelBlancoDias.setMaximumSize(new Dimension(2000, 290));
        panelBlancoDias.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel panelContenedorBotonesDias = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 4)) {
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
        panelContenedorBotonesDias.setOpaque(false);
        panelContenedorBotonesDias.setBorder(new EmptyBorder(3, 10, 3, 10));

        btnAnterior = new BotonUtil("", Color.WHITE, Color.BLACK, 15, fuenteRegularBtn, 38, 38);
        if (iconoFlechaIzq != null) btnAnterior.setIcon(iconoFlechaIzq);
        else btnAnterior.setText("<");

        btnSiguiente = new BotonUtil("", Color.WHITE, Color.BLACK, 15, fuenteRegularBtn, 38, 38);
        if (iconoFlechaDer != null) btnSiguiente.setIcon(iconoFlechaDer);
        else btnSiguiente.setText(">");

        panelContenedorBotonesDias.add(btnAnterior);
        panelContenedorBotonesDias.add(btnSiguiente);

        JPanel wrapperSurDias = new JPanel(new GridBagLayout());
        wrapperSurDias.setOpaque(false);
        wrapperSurDias.add(panelContenedorBotonesDias);
        panelBlancoDias.add(wrapperSurDias, BorderLayout.SOUTH);
        panelDerechaCentro.add(panelBlancoDias);

        panelDerechaCentro.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- 2. PANEL INFERIOR (Horarios) ---
        panelBlancoHorarios = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(new Color(236, 245, 252));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                
                g2.setColor(new Color(210, 225, 240));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());
                
                g2.dispose();
            }
        };
        panelBlancoHorarios.setOpaque(false);
        panelBlancoHorarios.setPreferredSize(new Dimension(0, 160));
        panelBlancoHorarios.setMaximumSize(new Dimension(2000, 160));
        panelBlancoHorarios.setBorder(new EmptyBorder(8, 20, 8, 20));

        JPanel panelContenedorBotonesHorarios = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 4)) {
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
        panelContenedorBotonesHorarios.setOpaque(false);
        panelContenedorBotonesHorarios.setBorder(new EmptyBorder(3, 10, 3, 10));

        btnHorarioAnterior = new BotonUtil("", Color.WHITE, Color.BLACK, 15, fuenteRegularBtn, 38, 38);
        if (iconoFlechaIzq != null) btnHorarioAnterior.setIcon(iconoFlechaIzq);
        else btnHorarioAnterior.setText("<");

        btnHorarioSiguiente = new BotonUtil("", Color.WHITE, Color.BLACK, 15, fuenteRegularBtn, 38, 38);
        if (iconoFlechaDer != null) btnHorarioSiguiente.setIcon(iconoFlechaDer);
        else btnHorarioSiguiente.setText(">");

        panelContenedorBotonesHorarios.add(btnHorarioAnterior);
        panelContenedorBotonesHorarios.add(btnHorarioSiguiente);

        JPanel wrapperSurHorarios = new JPanel(new GridBagLayout());
        wrapperSurHorarios.setOpaque(false);
        wrapperSurHorarios.add(panelContenedorBotonesHorarios);
        panelBlancoHorarios.add(wrapperSurHorarios, BorderLayout.SOUTH);

        panelDerechaCentro.add(panelBlancoHorarios);

        panelDerechoTotal.add(panelDerechaCentro, BorderLayout.CENTER);
        add(panelDerechoTotal, BorderLayout.CENTER);
    }

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

    public Usuario getPasajeroActual() { return pasajeroActual; }
    public JPanel getPanelBlancoDias() { return panelBlancoDias; }
    public JPanel getPanelBlancoHorarios() { return panelBlancoHorarios; }
    public JLabel getLblTituloDestino() { return lblTituloDestino; }
    public BotonUtil getBtnAnterior() { return btnAnterior; }
    public BotonUtil getBtnSiguiente() { return btnSiguiente; }
    public BotonUtil getBtnHorarioAnterior() { return btnHorarioAnterior; }
    public BotonUtil getBtnHorarioSiguiente() { return btnHorarioSiguiente; }

    public JLabel getOpRutasDisponibles() { return opRutasDisponibles; }
    public JLabel getOpRutasUrbanas() { return opRutasUrbanas; }
    public JLabel getOpRutasExtraurbanas() { return opRutasExtraurbanas; }
    public JLabel getOpVisualizarRecorridos() { return opVisualizarRecorridos; }
    public JLabel getOpMonedero() { return opMonedero; }
    public JLabel getOpCerrarS() { return opCerrarS; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Horarios vista = new Horarios(null);
            vista.getLblTituloDestino().setText(" ");
            vista.setVisible(true);
        });
    }
}