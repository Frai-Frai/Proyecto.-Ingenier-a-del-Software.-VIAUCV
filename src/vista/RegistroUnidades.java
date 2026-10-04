package vista;

import util.*;
import modelo.*;
import controlador.*;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RegistroUnidades extends JFrame {

    private Usuario adminActual;
    private JPanel panelFormularioFlotante;
    private JPanel panelDerechaCentro;
    private JPanel panelBuses; 
    private Controlador_GestionUnidades controlador;

    private int paginaActual = 0;
    private final int ELEMENTOS_POR_PAGINA = 7;
    private List<UnidadBus> listaCompletaBuses = new ArrayList<>();

    private BotonUtil btnAnterior;
    private BotonUtil btnSiguiente;

    private CampoTextoUtil txtPlaca;
    private CampoTextoUtil txtModelo;
    private CampoTextoUtil txtCapacidad;
    private BotonUtil btnRegistrar;

    private JLabel opPlanificar, opRegistrarP, opPDiarios, opGestionU, opGenR, opCerrarS;
    
    public RegistroUnidades(Usuario admin) {

        this.adminActual = admin;
        setTitle("Registro de Unidades - UCV");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Font fuenteBold = cargarFuente("res/GlacialIndifference-Bold.otf", 18f, Font.BOLD);
        Font fuenteLink = cargarFuente("res/GlacialIndifference-Bold.otf", 16f, Font.BOLD);
        Color azulCuadros = new Color(0x0D47A1); 
        Color Colormenu = new Color(214, 233, 245);
        
        JPanel panelMenu = new JPanel(new BorderLayout());
        panelMenu.setOpaque(false);
        panelMenu.setBorder(new EmptyBorder(12, 12, 12, 0)); // Margen exterior

        MiniVentanaUtil menuIzq = new MiniVentanaUtil(30, Colormenu, 240, 700, false, Colormenu);
        menuIzq.setPreferredSize(new Dimension(240, 0)); // Fija el ancho en el BorderLayout
        menuIzq.setLayout(null);

        // Logo
        ImageIcon logoViaUCV = new ImageIcon("res/LogoViaUCV.png");
        Image imgRedimensionada = logoViaUCV.getImage().getScaledInstance(160, -1, Image.SCALE_SMOOTH); 
        JLabel logo = new JLabel(new ImageIcon(imgRedimensionada));   
        logo.setBounds(30, 20, 160, 100);    
        menuIzq.add(logo);
        
        // Opciones del menú
        opPlanificar = new JLabel("Planificar Itinerario");
        opPlanificar.setBounds(30, 150, 180, 30);
        new TextosInteractivosUtil(opPlanificar, "Planificar Itinerario", Color.BLACK, azulCuadros, fuenteLink);
        menuIzq.add(opPlanificar);
        
        opRegistrarP = new JLabel("Registrar Personal");
        opRegistrarP.setBounds(30, 200, 180, 30);
        new TextosInteractivosUtil(opRegistrarP, "Registrar Personal", Color.BLACK, azulCuadros, fuenteLink);
        menuIzq.add(opRegistrarP);
        
        opPDiarios = new JLabel("Pasajeros Diarios");
        opPDiarios.setBounds(30, 250, 180, 30);
        new TextosInteractivosUtil(opPDiarios, "Pasajeros Diarios", Color.BLACK, azulCuadros, fuenteLink);
        menuIzq.add(opPDiarios);

        opGestionU = new JLabel("Gestión de Unidades");
        opGestionU.setBounds(30, 300, 180, 30);
        new TextosInteractivosUtil(opGestionU, "Gestión de Unidades", Color.BLACK, azulCuadros, fuenteLink);
        menuIzq.add(opGestionU);

        opGenR = new JLabel("Generar Reporte");
        opGenR.setBounds(30, 350, 180, 30);
        new TextosInteractivosUtil(opGenR, "Generar Reporte", Color.BLACK, azulCuadros, fuenteLink);
        menuIzq.add(opGenR);

        opCerrarS = new JLabel("Cerrar Sesión");
        opCerrarS.setBounds(30, 450, 180, 30);
        new TextosInteractivosUtil(opCerrarS, "Cerrar Sesión", Color.BLACK, azulCuadros, fuenteLink);
        menuIzq.add(opCerrarS);

        panelMenu.add(menuIzq, BorderLayout.CENTER);
        add(panelMenu, BorderLayout.WEST);
        
        // Panel derecho
        JPanel panelDerechoTotal = new JPanel(new BorderLayout());
        panelDerechoTotal.setBackground(new Color(205, 228, 238));
        panelDerechoTotal.setBorder(new EmptyBorder(12, 12, 12, 12));

        // Panel superior
        JPanel panelSuperior = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                RoundRectangle2D rect = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.fill(rect);
                
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(rect);
                g2.dispose();
            }
        };
        panelSuperior.setPreferredSize(new Dimension(0, 210));
        panelSuperior.setOpaque(false);
        panelSuperior.setBorder(new EmptyBorder(15, 25, 15, 25));
        panelSuperior.setLayout(new BorderLayout());
        
        JPanel panelNorteSuperior = new JPanel(new BorderLayout());
        panelNorteSuperior.setOpaque(false);
        panelNorteSuperior.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel tituloSuperior = new JLabel("Unidades registradas", JLabel.CENTER);
        tituloSuperior.setFont(fuenteBold.deriveFont(20f));
        tituloSuperior.setForeground(new Color(40, 100, 160));
        tituloSuperior.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelNorteSuperior.add(tituloSuperior, BorderLayout.NORTH);

        JPanel lineaDivisoria = new JPanel();
        lineaDivisoria.setBackground(Color.BLACK);
        lineaDivisoria.setPreferredSize(new Dimension(0, 1));
        panelNorteSuperior.add(lineaDivisoria, BorderLayout.CENTER);

        panelSuperior.add(panelNorteSuperior, BorderLayout.NORTH);

        // donde se ven los buses
        JPanel panelCentroBusesConFlechas = new JPanel(new BorderLayout(10, 0));
        panelCentroBusesConFlechas.setOpaque(false);

        ImageIcon iconoFlechaDer = null;
        ImageIcon iconoFlechaIzq = null;
        try {
            ImageIcon originalFlechaDer = new ImageIcon("res/flecha.jpeg");
            Image scaledDer = originalFlechaDer.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
            iconoFlechaDer = new ImageIcon(scaledDer);
            
            ImageIcon originalFlechaIzq = new ImageIcon("res/flechacontraria.jpeg");
            Image scaledIzq = originalFlechaIzq.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
            iconoFlechaIzq = new ImageIcon(scaledIzq);
        } catch (Exception e) {}

        Font fuenteRegularBtn = cargarFuente("res/GlacialIndifference-Regular.otf", 12f, Font.PLAIN);

        btnAnterior = new BotonUtil("", new Color(245, 245, 245), Color.BLACK, 15, fuenteRegularBtn, 55, 55);
        if (iconoFlechaIzq != null) btnAnterior.setIcon(iconoFlechaIzq);
        else btnAnterior.setText("<");
        
        JPanel panelBtnIzqWrapper = new JPanel(new GridBagLayout());
        panelBtnIzqWrapper.setOpaque(false);
        panelBtnIzqWrapper.add(btnAnterior);

        btnSiguiente = new BotonUtil("", new Color(245, 245, 245), Color.BLACK, 15, fuenteRegularBtn, 55, 55);
        if (iconoFlechaDer != null) btnSiguiente.setIcon(iconoFlechaDer);
        else btnSiguiente.setText(">");

        JPanel panelBtnDerWrapper = new JPanel(new GridBagLayout());
        panelBtnDerWrapper.setOpaque(false);
        panelBtnDerWrapper.add(btnSiguiente);

        panelBuses = new JPanel(new GridLayout(1, 7, 15, 0));
        panelBuses.setOpaque(false);
        panelBuses.setBorder(new EmptyBorder(5, 0, 5, 0));

        panelCentroBusesConFlechas.add(panelBtnIzqWrapper, BorderLayout.WEST);
        panelCentroBusesConFlechas.add(panelBuses, BorderLayout.CENTER);
        panelCentroBusesConFlechas.add(panelBtnDerWrapper, BorderLayout.EAST);

        panelSuperior.add(panelCentroBusesConFlechas, BorderLayout.CENTER);

        btnSiguiente.addActionListener(e -> {
            int totalPaginas = (int) Math.ceil((double) listaCompletaBuses.size() / ELEMENTOS_POR_PAGINA);
            if (totalPaginas == 0) totalPaginas = 1;
            if (paginaActual < totalPaginas - 1) {
                paginaActual++;
                redibujarPaginaBuses();
            }
        });

        btnAnterior.addActionListener(e -> {
            if (paginaActual > 0) {
                paginaActual--;
                redibujarPaginaBuses();
            }
        });

        JPanel panelSuperiorWrapper = new JPanel(new BorderLayout());
        panelSuperiorWrapper.setOpaque(false);
        panelSuperiorWrapper.setBorder(new EmptyBorder(0, 0, 12, 0));
        panelSuperiorWrapper.add(panelSuperior, BorderLayout.CENTER);

        panelContenedorCentral();

        panelDerechoTotal.add(panelSuperiorWrapper, BorderLayout.NORTH);
        panelDerechoTotal.add(panelDerechaCentro, BorderLayout.CENTER);

        add(panelDerechoTotal, BorderLayout.CENTER);
    }

    public void setControlador(Controlador_GestionUnidades controlador){
        this.controlador = controlador;
    }
    public void actualizarPanelBuses(List<UnidadBus> listaUnidades) {
        this.listaCompletaBuses = listaUnidades;
        this.paginaActual = 0; 
        redibujarPaginaBuses();
    }

    private void redibujarPaginaBuses() {
        panelBuses.removeAll();

        ImageIcon iconoBus = null;
        try {
            ImageIcon originalIcon = new ImageIcon("res/IconBus.jpeg");
            Image scaledImg = originalIcon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            iconoBus = new ImageIcon(scaledImg);
        } catch (Exception e) {}

        int inicio = paginaActual * ELEMENTOS_POR_PAGINA;
        int fin = Math.min(inicio + ELEMENTOS_POR_PAGINA, listaCompletaBuses.size());

        List<UnidadBus> unidadesPagina = new ArrayList<>();
        if (inicio < listaCompletaBuses.size()) {
            unidadesPagina = listaCompletaBuses.subList(inicio, fin);
        }

        for (UnidadBus unidad : unidadesPagina) {
            JPanel panelItemBus = new JPanel();
            panelItemBus.setLayout(new BoxLayout(panelItemBus, BoxLayout.Y_AXIS));
            panelItemBus.setOpaque(false);
            panelItemBus.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            JLabel lblIcono = new JLabel(iconoBus);
            lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel lblPlaca = new JLabel(unidad.getPlaca());
            lblPlaca.setFont(cargarFuente("res/GlacialIndifference-Bold.otf", 13f, Font.BOLD));
            lblPlaca.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            panelItemBus.add(Box.createVerticalGlue());
            panelItemBus.add(lblIcono);
            panelItemBus.add(Box.createRigidArea(new Dimension(0, 6)));
            panelItemBus.add(lblPlaca);
            panelItemBus.add(Box.createVerticalGlue());

            panelItemBus.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mostrarMenuFlotanteEstado(panelItemBus, unidad.getPlaca());
                }
            });
            
            panelBuses.add(panelItemBus);
        }

        int anadidos = unidadesPagina.size();
        for (int i = anadidos; i < ELEMENTOS_POR_PAGINA; i++) {
            panelBuses.add(new JLabel());
        }

        panelBuses.revalidate();
        panelBuses.repaint();
    }

    private void mostrarMenuFlotanteEstado(Component invoker, String placaBus) {
        Font fuenteRegular = cargarFuente("res/GlacialIndifference-Regular.otf", 15f, Font.PLAIN);
        Font fuenteBold = cargarFuente("res/GlacialIndifference-Bold.otf", 15f, Font.BOLD);

        JDialog popupDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), false);
        popupDialog.setUndecorated(true);
        popupDialog.setBackground(new Color(0, 0, 0, 0));

        JPanel panelContenidoPopup = new JPanel();
        panelContenidoPopup.setLayout(new BoxLayout(panelContenidoPopup, BoxLayout.Y_AXIS));
        panelContenidoPopup.setBorder(new EmptyBorder(12, 12, 12, 12));
        panelContenidoPopup.setBackground(new Color(165, 205, 235));

        JLabel lblInfo = new JLabel("Bus: " + placaBus);
        lblInfo.setFont(fuenteBold.deriveFont(13f));
        lblInfo.setForeground(new Color(30, 80, 135));
        lblInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] opcionesEstado = {
            "Seleccione estado operativo", 
            "Activo", 
            "En mantenimiento", 
            "Fuera de servicio"
        };
        
        MenuUtil menuEstado = new MenuUtil(
            opcionesEstado, 
            15, 
            Color.WHITE, 
            Color.GRAY, 
            new Color(150, 150, 150), 
            fuenteRegular.deriveFont(14f)
        );
        
        Dimension tamanoCombo = new Dimension(260, 42);
        menuEstado.setPreferredSize(tamanoCombo);
        menuEstado.setMaximumSize(tamanoCombo);
        menuEstado.setAlignmentX(Component.LEFT_ALIGNMENT);

        menuEstado.addActionListener(e -> {
            String nuevoEstado = (String) menuEstado.getSelectedItem();
            if (nuevoEstado != null && !nuevoEstado.equals("Seleccione estado operativo")) {
                controlador.cambiarEstadoBus(placaBus, nuevoEstado);
                popupDialog.dispose();
            }
        });

        panelContenidoPopup.add(lblInfo);
        panelContenidoPopup.add(Box.createRigidArea(new Dimension(0, 8)));
        panelContenidoPopup.add(menuEstado);

        popupDialog.add(panelContenidoPopup);
        popupDialog.pack();

        Point p = invoker.getLocationOnScreen();
        popupDialog.setLocation(p.x, p.y + invoker.getHeight());

        popupDialog.addWindowFocusListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowLostFocus(java.awt.event.WindowEvent e) {
                popupDialog.dispose();
            }
        });

        popupDialog.setVisible(true);
    }

    private void panelContenedorCentral() {
        panelDerechaCentro = new JPanel(new GridBagLayout());
        panelDerechaCentro.setOpaque(false);
        panelDerechaCentro.setBorder(new EmptyBorder(15, 15, 15, 15));

        Font fuenteRegular = cargarFuente("res/GlacialIndifference-Regular.otf", 15f, Font.PLAIN);
        Font fuenteBold = cargarFuente("res/GlacialIndifference-Bold.otf", 15f, Font.BOLD);

        GridBagConstraints gbcCentral = new GridBagConstraints();
        gbcCentral.weighty = 1.0;
        gbcCentral.fill = GridBagConstraints.BOTH;

        panelFormularioFlotante = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RoundRectangle2D rectRedondeado = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.setColor(new Color(165, 205, 235));
                g2.fill(rectRedondeado);
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(rectRedondeado);
                g2.dispose();
            }
        };
        panelFormularioFlotante.setOpaque(false);
        panelFormularioFlotante.setBorder(new EmptyBorder(25, 35, 15, 35));
        panelFormularioFlotante.setLayout(new BorderLayout());

        JLabel lblTituloRegistro = new JLabel("Registrar nueva unidad");
        lblTituloRegistro.setFont(fuenteBold.deriveFont(26f));
        lblTituloRegistro.setForeground(new Color(30, 80, 135));
        panelFormularioFlotante.add(lblTituloRegistro, BorderLayout.NORTH);

        JPanel panelCentroCamposBtn = new JPanel();
        panelCentroCamposBtn.setLayout(new BoxLayout(panelCentroCamposBtn, BoxLayout.Y_AXIS));
        panelCentroCamposBtn.setOpaque(false);

        txtPlaca = new CampoTextoUtil(15, new Color(150, 150, 150), 0, 48, Color.WHITE, Color.GRAY, fuenteRegular.deriveFont(15f));
        txtPlaca.setText("Placa");
        configurarPlaceholder(txtPlaca, "Placa");

        txtModelo = new CampoTextoUtil(15, new Color(150, 150, 150), 0, 48, Color.WHITE, Color.GRAY, fuenteRegular.deriveFont(15f));
        txtModelo.setText("Modelo");
        configurarPlaceholder(txtModelo, "Modelo");

        txtCapacidad = new CampoTextoUtil(15, new Color(150, 150, 150), 0, 48, Color.WHITE, Color.GRAY, fuenteRegular.deriveFont(15f));
        txtCapacidad.setText("Capacidad de pasajeros");
        configurarPlaceholder(txtCapacidad, "Capacidad de pasajeros");

        Dimension campoSize = new Dimension(Integer.MAX_VALUE, 48);
        txtPlaca.setMaximumSize(campoSize);
        txtModelo.setMaximumSize(campoSize);
        txtCapacidad.setMaximumSize(campoSize);
        txtPlaca.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtModelo.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtCapacidad.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnRegistrar = new BotonUtil("Registrar unidad", new Color(40, 100, 160), Color.WHITE, 15, fuenteBold.deriveFont(15f), 0, 48);
        btnRegistrar.setMaximumSize(campoSize);
        btnRegistrar.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Vincular acción del botón al controlador
        btnRegistrar.addActionListener(e -> controlador.ValidarRegistroUnidad());

        int interlineado1_5cm = 55;

        panelCentroCamposBtn.add(Box.createVerticalGlue());
        panelCentroCamposBtn.add(txtPlaca);
        panelCentroCamposBtn.add(Box.createRigidArea(new Dimension(0, interlineado1_5cm)));
        panelCentroCamposBtn.add(txtModelo);
        panelCentroCamposBtn.add(Box.createRigidArea(new Dimension(0, interlineado1_5cm)));
        panelCentroCamposBtn.add(txtCapacidad);
        panelCentroCamposBtn.add(Box.createRigidArea(new Dimension(0, interlineado1_5cm)));
        panelCentroCamposBtn.add(btnRegistrar);
        panelCentroCamposBtn.add(Box.createVerticalGlue());

        panelFormularioFlotante.add(panelCentroCamposBtn, BorderLayout.CENTER);

        gbcCentral.gridx = 0;
        gbcCentral.weightx = 0.38;
        gbcCentral.insets = new Insets(0, 0, 0, 10);
        panelDerechaCentro.add(panelFormularioFlotante, gbcCentral);

        JPanel panelDerechaCentroInterno = new JPanel(new BorderLayout());
        panelDerechaCentroInterno.setOpaque(false);
        panelDerechaCentroInterno.setBorder(new EmptyBorder(5, 0, 5, 0));

        JPanel panelImagenBus = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                
                int w = getWidth();
                int h = getHeight();
                int margen = 10;
                int arc = 30;

                try {
                    ImageIcon iconOriginal = new ImageIcon("res/BusPic.jpeg");
                    Image img = iconOriginal.getImage();

                    RoundRectangle2D rectRedondeado = new RoundRectangle2D.Float(margen, margen, w - (margen * 2), h - (margen * 2), arc, arc);
                    g2.setClip(rectRedondeado);

                    int imgAncho = img.getWidth(null);
                    int imgAlto = img.getHeight(null);
                    
                    if (imgAncho > 0 && imgAlto > 0) {
                        double escala = Math.min((double) (w - (margen * 2)) / imgAncho, (double) (h - (margen * 2)) / imgAlto);
                        int nuevoAncho = (int) (imgAncho * escala);
                        int nuevoAlto = (int) (imgAlto * escala);
                        int imgX = margen + ((w - (margen * 2)) - nuevoAncho) / 2;
                        int imgY = margen + ((h - (margen * 2)) - nuevoAlto) / 2;

                        g2.drawImage(img, imgX, imgY, nuevoAncho, nuevoAlto, null);
                    }
                    g2.setClip(null);
                } catch (Exception e) {
                    g2.setColor(new Color(120, 120, 120));
                    g2.setFont(fuenteBold.deriveFont(14f));
                    g2.drawString("[ Imagen no encontrada: res/BusPic.jpeg ]", 40, h / 2);
                }
                g2.dispose();
            }
        };
        panelImagenBus.setOpaque(false);
        panelImagenBus.setLayout(new BorderLayout());

        BotonUtil btnEstadoOperativo = new BotonUtil("Unidades en Estado Operativo", new Color(40, 100, 160), Color.WHITE, 15, fuenteBold.deriveFont(15f), 0, 48);
        btnEstadoOperativo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        btnEstadoOperativo.addActionListener(e -> controlador.mostrarVentanaBusesActivos());

        JPanel panelBtnEstadoWrapper = new JPanel();
        panelBtnEstadoWrapper.setLayout(new BoxLayout(panelBtnEstadoWrapper, BoxLayout.X_AXIS));
        panelBtnEstadoWrapper.setOpaque(false);
        panelBtnEstadoWrapper.setBorder(new EmptyBorder(12, 0, 0, 0));
        
        btnEstadoOperativo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBtnEstadoWrapper.add(btnEstadoOperativo);

        panelDerechaCentroInterno.add(panelImagenBus, BorderLayout.CENTER);
        panelDerechaCentroInterno.add(panelBtnEstadoWrapper, BorderLayout.SOUTH);

        gbcCentral.gridx = 1;
        gbcCentral.weightx = 0.62;
        gbcCentral.insets = new Insets(0, 10, 0, 0);
        panelDerechaCentro.add(panelDerechaCentroInterno, gbcCentral);
    }

    private Font cargarFuente(String ruta, float tamano, int estilo) {
        try {
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, new File(ruta));
            return fuente.deriveFont(estilo, tamano);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la fuente " + ruta + ": " + e.getMessage());
            return new Font("SansSerif", estilo, (int) tamano);
        }
    }

    private void configurarPlaceholder(JTextField campo, String placeholderText) {
        campo.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholderText)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setForeground(Color.GRAY);
                    campo.setText(placeholderText);
                }
            }
        });
    }

    // Getters para acceder a los campos 
    public CampoTextoUtil getTxtPlaca(){ 
        return txtPlaca; 
    }

    public CampoTextoUtil getTxtModelo(){ 
        return txtModelo; 
    }

    public CampoTextoUtil getTxtCapacidad(){ 
        return txtCapacidad; 
    }

    public Usuario getAdminActual(){
        return adminActual;
    }

    public JLabel getOpPlanificar(){ 
        return opPlanificar; 
    }

    public JLabel getOpRegistrarP(){ 
        return opRegistrarP; 
    }

    public JLabel getOpPDiarios(){ 
        return opPDiarios; 
    }

    public JLabel getOpGestionU(){ 
        return opGestionU; 
    }

    public JLabel getOpGenR(){ 
        return opGenR; 
    }

    public JLabel getOpCerrarS(){ 
        return opCerrarS; 
    }

   /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistroUnidades vista = new RegistroUnidades();
            new Controlador_GestionUnidades(vista);
            vista.setVisible(true);
        });
    }*/
}