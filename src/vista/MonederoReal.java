package vista;

import util.*;
import modelo.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class MonederoReal extends JFrame {

    private Usuario pasajeroActual;
    private JPanel panelDerechoTotal, panelDerechaCentro, panelImagenSuperior, panelBlancoIzquierda, panelBlancoDerecha;
    private JLabel lblTituloDestino;
    private MiniVentanaUtil menuIzq;
    
    // Componentes de pago móvil integrados
    private MenuUtil comboOperadora, comboTipoCedula, comboBanco;
    private CampoTextoUtil txtTelefono, txtCedula, txtMonto;
    private BotonUtil botonPagar;
    
    private JLabel opRutasDisponibles, opRutasUrbanas, opRutasExtraurbanas, opVisualizarRecorridos, opMonedero, opCerrarS;

    public MonederoReal(Usuario pasajero) {

        this.pasajeroActual = pasajero;
        setTitle("VIAUCV - MONEDERO");
        setSize(1200, 830); 
        setMinimumSize(new Dimension(1100, 750));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tipografías 
        Font fuenteTituloGrande = League("res/LeagueSpartan-Bold.otf", 36f, Font.PLAIN);
        Font fuenteTituloPanel = League("res/LeagueSpartan-Bold.otf", 24f, Font.PLAIN);
        Font fuenteSubT = fuenteGlacial("res/GlacialIndifference-Bold.otf", 14f, Font.BOLD);
        Font fuenteLetras = fuenteGlacial("res/GlacialIndifference-Regular.otf", 13f, Font.PLAIN);
        Font fuenteLink = fuenteGlacial("res/GlacialIndifference-Bold.otf", 16f, Font.BOLD);
        Font fuenteSubLink = fuenteGlacial("res/GlacialIndifference-Bold.otf", 14f, Font.BOLD);
        
        // Paleta de colores
        Color azulCuadros = new Color(0x0D47A1); 
        Color colormenu = Color.WHITE; 
        Color colorFondo = new Color(0xD6EBF7); 
        Color bordeColor = new Color(180, 210, 235);

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
        panelNorteTotal.setBorder(new EmptyBorder(0, 10, 12, 0)); 

        // Panel superior para imagen 
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
        panelImagenSuperior.setPreferredSize(new Dimension(0, 210)); 
        panelImagenSuperior.setOpaque(false);
        panelNorteTotal.add(panelImagenSuperior);

        lblTituloDestino = new JLabel(" "); 
        lblTituloDestino.setFont(fuenteTituloGrande);
        lblTituloDestino.setForeground(Color.BLACK);
        lblTituloDestino.setPreferredSize(new Dimension(0, 0)); 
        panelNorteTotal.add(lblTituloDestino);

        panelDerechoTotal.add(panelNorteTotal, BorderLayout.NORTH);

        // Panel central con 
        panelDerechaCentro = new JPanel(new GridBagLayout());
        panelDerechaCentro.setOpaque(false);

        // --- 1. PANEL BLANCO IZQUIERDO ---
        panelBlancoIzquierda = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 28, 28);
                
                g2.setColor(azulCuadros);
                g2.setStroke(new BasicStroke(5.5f));
                g2.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 28, 28);
                
                g2.dispose();
            }
        };
        panelBlancoIzquierda.setOpaque(false);
        panelBlancoIzquierda.setLayout(new GridBagLayout());
        
        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.gridx = 0;
        gbcForm.anchor = GridBagConstraints.WEST;
        gbcForm.fill = GridBagConstraints.HORIZONTAL;
        gbcForm.weightx = 1.0;

        // Título
        gbcForm.gridy = 0;
        gbcForm.anchor = GridBagConstraints.NORTHWEST;
        gbcForm.insets = new Insets(18, 30, 0, 30);
        JLabel lblRecargarMonedero = new JLabel("Recargar monedero");
        lblRecargarMonedero.setFont(fuenteTituloPanel);
        lblRecargarMonedero.setForeground(azulCuadros);
        panelBlancoIzquierda.add(lblRecargarMonedero, gbcForm);

        // Espaciador superior
        gbcForm.gridy = 1;
        gbcForm.weighty = 1.0;
        JPanel panelEspaciadorArriba = new JPanel();
        panelEspaciadorArriba.setOpaque(false);
        panelBlancoIzquierda.add(panelEspaciadorArriba, gbcForm);

        gbcForm.weighty = 0.0;
        gbcForm.anchor = GridBagConstraints.WEST;

        // Teléfono
        gbcForm.gridy = 2;
        gbcForm.insets = new Insets(0, 30, 4, 30);
        JLabel lblTelefono = new JLabel("Número de Teléfono:");
        lblTelefono.setFont(fuenteSubT);
        lblTelefono.setForeground(azulCuadros);
        panelBlancoIzquierda.add(lblTelefono, gbcForm);

        gbcForm.gridy = 3;
        gbcForm.insets = new Insets(0, 30, 12, 30);
        JPanel panelFilaTel = new JPanel(new BorderLayout(10, 0));
        panelFilaTel.setOpaque(false);
        
        String[] operadoras = {"0414", "0424", "0412", "0422", "0416", "0426"};
        comboOperadora = new MenuUtil(operadoras, 15, Color.WHITE, azulCuadros, bordeColor, fuenteLetras);
        comboOperadora.setPreferredSize(new Dimension(100, 42));
        panelFilaTel.add(comboOperadora, BorderLayout.WEST);

        txtTelefono = new CampoTextoUtil(15, bordeColor, 280, 42, Color.WHITE, azulCuadros, fuenteLetras);
        panelFilaTel.add(txtTelefono, BorderLayout.CENTER);
        panelBlancoIzquierda.add(panelFilaTel, gbcForm);

        // Cédula
        gbcForm.gridy = 4;
        gbcForm.insets = new Insets(0, 30, 4, 30);
        JLabel lblCedula = new JLabel("Cédula de Identidad:");
        lblCedula.setFont(fuenteSubT);
        lblCedula.setForeground(azulCuadros);
        panelBlancoIzquierda.add(lblCedula, gbcForm);

        gbcForm.gridy = 5;
        gbcForm.insets = new Insets(0, 30, 12, 30);
        JPanel panelFilaCedula = new JPanel(new BorderLayout(10, 0));
        panelFilaCedula.setOpaque(false);

        String[] tiposCedula = {"V", "E", "P", "R", "J", "G"};
        comboTipoCedula = new MenuUtil(tiposCedula, 15, Color.WHITE, azulCuadros, bordeColor, fuenteLetras);
        comboTipoCedula.setPreferredSize(new Dimension(100, 42));
        panelFilaCedula.add(comboTipoCedula, BorderLayout.WEST);

        txtCedula = new CampoTextoUtil(15, bordeColor, 310, 42, Color.WHITE, azulCuadros, fuenteLetras);
        panelFilaCedula.add(txtCedula, BorderLayout.CENTER);
        panelBlancoIzquierda.add(panelFilaCedula, gbcForm);

        // Banco
        gbcForm.gridy = 6;
        gbcForm.insets = new Insets(0, 30, 4, 30);
        JLabel lblBanco = new JLabel("Banco:");
        lblBanco.setFont(fuenteSubT);
        lblBanco.setForeground(azulCuadros);
        panelBlancoIzquierda.add(lblBanco, gbcForm);

        gbcForm.gridy = 7;
        gbcForm.insets = new Insets(0, 30, 12, 30);
        String[] bancos = {
            "SELECCIONE EL BANCO",
            "(0156) 100% BANCO, BANCO UNIVERSAL",
            "(0172) BANCAMIGA BANCO UNIVERSAL, C.A.",
            "(0114) BANCARIBE C.A. BANCO UNIVERSAL",
            "(0171) BANCO ACTIVO",
            "(0134) BANESCO BANCO UNIVERSAL",
            "(0128) BANCO CARONI C.A. BANCO UNIVERSAL",
            "(0177) BANCO DE LA FANB",
            "(0146) BANCO DE LA GENTE EMPRENDEDORA",
            "(0102) BANCO DE VENEZUELA",
            "(0163) BANCO DEL TESORO",
            "(0175) BANCO DIGITAL DE LOS TRABAJADORES",
            "(0178) BANCO DIGITAL N58",
            "(0115) BANCO EXTERIOR C.A. BANCO UNIVERSAL",
            "(0105) BANCO MERCANTIL",
            "(0191) BANCO NACIONAL DE CREDITO",
            "(0138) BANCO PLAZA, BANCO UNIVERSAL",
            "(0108) BANCO PROVINCIAL",
            "(0137) BANCO SOFITASA",
            "(0168) BANCRECER, S.A. BANCO MICROFINANCIERO",
            "(0174) BANPLUS BANCO UNIVERSAL, C.A.",
            "(0151) BFC BANCO FONDO COMUN",
            "(0157) DEL SUR BANCO UNIVERSAL, C.A.",
            "(0601) INSTITUTO MUNICIPAL DE CREDITO",
            "(0169) R4, BANCO MICROFINANCIERO, C.A.",
            "(0104) VENEZOLANO DE CREDITO"
        };
        comboBanco = new MenuUtil(bancos, 15, Color.WHITE, azulCuadros, bordeColor, fuenteLetras);
        comboBanco.setPreferredSize(new Dimension(0, 42));
        panelBlancoIzquierda.add(comboBanco, gbcForm);

        // Monto
        gbcForm.gridy = 8;
        gbcForm.insets = new Insets(0, 30, 4, 30);
        JLabel lblMonto = new JLabel("Monto a Recargar:");
        lblMonto.setFont(fuenteSubT);
        lblMonto.setForeground(azulCuadros);
        panelBlancoIzquierda.add(lblMonto, gbcForm);

        gbcForm.gridy = 9;
        gbcForm.insets = new Insets(0, 30, 18, 30);
        txtMonto = new CampoTextoUtil(15, bordeColor, 380, 42, Color.WHITE, azulCuadros, fuenteLetras);
        panelBlancoIzquierda.add(txtMonto, gbcForm);

        // Espaciador medio-bajo para que cuadre todo
        gbcForm.gridy = 10;
        gbcForm.weighty = 0.5;
        JPanel panelEspaciadorMedioBajo = new JPanel();
        panelEspaciadorMedioBajo.setOpaque(false);
        panelBlancoIzquierda.add(panelEspaciadorMedioBajo, gbcForm);

        // Botón Recargar
        gbcForm.gridy = 11;
        gbcForm.weighty = 0.0;
        gbcForm.insets = new Insets(0, 30, 22, 30);
        JPanel panelContenedorBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panelContenedorBoton.setOpaque(false);
        
        botonPagar = new BotonUtil("Recargar", azulCuadros, Color.WHITE, 14, fuenteSubT, 160, 42);
        panelContenedorBoton.add(botonPagar);
        panelBlancoIzquierda.add(panelContenedorBoton, gbcForm);

        // Espaciador inferior izquierdo
        gbcForm.gridy = 12;
        gbcForm.weighty = 1.0;
        JPanel panelEspaciadorAbajo = new JPanel();
        panelEspaciadorAbajo.setOpaque(false);
        panelBlancoIzquierda.add(panelEspaciadorAbajo, gbcForm);

        GridBagConstraints gbcIzq = new GridBagConstraints();
        gbcIzq.gridx = 0;
        gbcIzq.gridy = 0;
        gbcIzq.weightx = 0.6; 
        gbcIzq.weighty = 1.0;
        gbcIzq.fill = GridBagConstraints.BOTH;
        gbcIzq.insets = new Insets(15, 15, 15, 10); 
        panelDerechaCentro.add(panelBlancoIzquierda, gbcIzq);

        // --- 2. PANEL BLANCO DERECHO ---
        panelBlancoDerecha = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 28, 28);
                
                g2.setColor(azulCuadros);
                g2.setStroke(new BasicStroke(5.5f));
                g2.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 28, 28);
                
                g2.dispose();
            }
        };
        panelBlancoDerecha.setOpaque(false);
        panelBlancoDerecha.setLayout(new GridBagLayout());
        
        GridBagConstraints gbcDer = new GridBagConstraints();
        gbcDer.gridx = 0;
        gbcDer.anchor = GridBagConstraints.CENTER;
        gbcDer.fill = GridBagConstraints.HORIZONTAL;
        gbcDer.weightx = 1.0;

        // Texto superior del panel derecho
        gbcDer.gridy = 0;
        gbcDer.insets = new Insets(30, 20, 5, 20);
        JLabel lblUstedCuenta = new JLabel("Usted cuenta con", SwingConstants.CENTER);
        lblUstedCuenta.setFont(fuenteTituloPanel);
        lblUstedCuenta.setForeground(azulCuadros);
        panelBlancoDerecha.add(lblUstedCuenta, gbcDer);

        // Espaciador central en el panel derecho
        gbcDer.gridy = 1;
        gbcDer.weighty = 1.0;
        JPanel panelEspaciadorDer = new JPanel();
        panelEspaciadorDer.setOpaque(false);
        panelBlancoDerecha.add(panelEspaciadorDer, gbcDer);

        gbcDer.gridy = 2;
        gbcDer.weighty = 0.0;
        gbcDer.insets = new Insets(5, 20, 30, 20);
        
        JPanel panelTextoInferior = new JPanel();
        panelTextoInferior.setLayout(new BoxLayout(panelTextoInferior, BoxLayout.Y_AXIS));
        panelTextoInferior.setOpaque(false);

        JLabel lblMonedero = new JLabel("en su monedero", SwingConstants.CENTER);
        lblMonedero.setFont(fuenteTituloPanel);
        lblMonedero.setForeground(azulCuadros);
        lblMonedero.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblVirtual = new JLabel("virtual", SwingConstants.CENTER);
        lblVirtual.setFont(fuenteTituloPanel);
        lblVirtual.setForeground(azulCuadros);
        lblVirtual.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelTextoInferior.add(lblMonedero);
        panelTextoInferior.add(Box.createVerticalStrut(5)); 
        panelTextoInferior.add(lblVirtual);

        panelBlancoDerecha.add(panelTextoInferior, gbcDer);

        GridBagConstraints gbcDerContenedor = new GridBagConstraints();
        gbcDerContenedor.gridx = 1;
        gbcDerContenedor.gridy = 0;
        gbcDerContenedor.weightx = 0.4; 
        gbcDerContenedor.weighty = 1.0;
        gbcDerContenedor.fill = GridBagConstraints.BOTH;
        gbcDerContenedor.insets = new Insets(15, 10, 15, 15); 
        panelDerechaCentro.add(panelBlancoDerecha, gbcDerContenedor);

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
    public JLabel getLblTituloDestino() { return lblTituloDestino; }
    public JPanel getPanelBlancoIzquierda() { return panelBlancoIzquierda; }
    public JPanel getPanelBlancoDerecha() { return panelBlancoDerecha; }

    public String getOperadora() { return (String) comboOperadora.getSelectedItem(); }
    public String getTelefono() { return txtTelefono.getText().trim(); }
    public String getTipoCedula() { return (String) comboTipoCedula.getSelectedItem(); }
    public String getCedula() { return txtCedula.getText().trim(); }
    public String getBanco() { return (String) comboBanco.getSelectedItem(); }
    public String getMonto() { return txtMonto.getText().trim(); }
    public BotonUtil getBotonPagar() { return botonPagar; }

    public JLabel getOpRutasDisponibles() { return opRutasDisponibles; }
    public JLabel getOpRutasUrbanas() { return opRutasUrbanas; }
    public JLabel getOpRutasExtraurbanas() { return opRutasExtraurbanas; }
    public JLabel getOpVisualizarRecorridos() { return opVisualizarRecorridos; }
    public JLabel getOpMonedero() { return opMonedero; }
    public JLabel getOpCerrarS() { return opCerrarS; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MonederoReal vista = new MonederoReal(null);
            vista.setVisible(true);
        });
    }
}