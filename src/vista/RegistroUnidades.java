package vista;

import util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

public class RegistroUnidades extends JFrame {

    private JPanel panelFormularioFlotante;
    private JPanel panelDerechaCentro;

    public RegistroUnidades() {
        // Configuración básica de la ventana
        setTitle("Registro de Unidades - UCV");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ==========================================
        // 1. PANEL IZQUIERDO: Menú lateral
        // ==========================================
        JPanel panelMenu = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(175, 210, 238));
                int margen = 12;
                int ancho = getWidth() - (margen * 2);
                int alto = getHeight() - (margen * 2);
                RoundRectangle2D rect = new RoundRectangle2D.Float(margen, margen, ancho, alto, 45, 45);
                g2.fill(rect);
                g2.dispose();
            }
        };
        panelMenu.setPreferredSize(new Dimension(280, 0));
        panelMenu.setOpaque(false);
        panelMenu.setBorder(new EmptyBorder(35, 20, 35, 20));
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        
        // --- LOGO PROPORCIONAL ---
        JLabel lblLogo = new JLabel();
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);
        try {
            ImageIcon iconoOriginal = new ImageIcon("res/LogoViaUCV.png");
            Image img = iconoOriginal.getImage();
            int anchoDeseado = 190;
            int anchoOrig = iconoOriginal.getIconWidth();
            int altoOrig = iconoOriginal.getIconHeight();
            int altoDeseado = (anchoOrig > 0) ? (altoOrig * anchoDeseado) / anchoOrig : 50;
            Image imagenEscalada = img.getScaledInstance(anchoDeseado, altoDeseado, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(imagenEscalada));
        } catch (Exception e) {
            lblLogo.setText("  LogoViaUCV");
        }
        
        panelMenu.add(lblLogo);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 35)));

        String[] opciones = {
            "Planificar itinerario", "Registrar personal", "Pasajeros diarios",
            "Gestión de unidades", "Generar reporte", "Cerrar sesión"
        };

        for (int i = 0; i < opciones.length; i++) {
            String textoOpcion = opciones[i];

            if (i == 3) {
                // Opción Activa (Estática con fondo azul más oscuro)
                JLabel lblOpcion = new JLabel(textoOpcion);
                lblOpcion.setFont(lblOpcion.getFont().deriveFont(Font.BOLD, 14f));
                
                JPanel panelActivoWrapper = new JPanel(new BorderLayout());
                panelActivoWrapper.setOpaque(true);
                panelActivoWrapper.setBackground(new Color(140, 185, 220));
                panelActivoWrapper.setBorder(new EmptyBorder(8, 12, 8, 12));
                panelActivoWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                panelActivoWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
                panelActivoWrapper.add(lblOpcion, BorderLayout.CENTER);
                panelMenu.add(panelActivoWrapper);
            } else {
                // Opciones Interactivas (Funcionan como botones sin mensajes molestos)
                JLabel lblOpcion = new JLabel(textoOpcion);
                lblOpcion.setFont(lblOpcion.getFont().deriveFont(14f));
                lblOpcion.setBorder(new EmptyBorder(0, 12, 0, 0));
                lblOpcion.setAlignmentX(Component.LEFT_ALIGNMENT);
                lblOpcion.setCursor(new Cursor(Cursor.HAND_CURSOR));

                lblOpcion.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        lblOpcion.setForeground(new Color(40, 100, 160));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        lblOpcion.setForeground(Color.BLACK);
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        ejecutarAccionMenu(textoOpcion);
                    }
                });

                panelMenu.add(lblOpcion);
            }
            
            if (i < opciones.length - 1) {
                panelMenu.add(Box.createRigidArea(new Dimension(0, 20)));
            }
        }
        panelMenu.add(Box.createVerticalGlue());

        // ==========================================
        // 2. PANEL DERECHO CONTENEDOR GENERAL
        // ==========================================
        JPanel panelDerechoTotal = new JPanel(new BorderLayout());
        panelDerechoTotal.setBackground(new Color(240, 243, 246));
        panelDerechoTotal.setBorder(new EmptyBorder(15, 15, 15, 15));

        // 2.1. PANEL SUPERIOR: Unidades registradas
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
        panelSuperior.setPreferredSize(new Dimension(0, 175));
        panelSuperior.setOpaque(false);
        panelSuperior.setBorder(new EmptyBorder(12, 20, 12, 20));
        panelSuperior.setLayout(new BorderLayout());
        
        JLabel tituloSuperior = new JLabel("Unidades registradas", JLabel.CENTER);
        tituloSuperior.setFont(tituloSuperior.getFont().deriveFont(Font.BOLD, 15f));
        tituloSuperior.setForeground(new Color(40, 100, 160));
        tituloSuperior.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        panelSuperior.add(tituloSuperior, BorderLayout.NORTH);
        
        ImageIcon iconoBus = null;
        try {
            ImageIcon originalIcon = new ImageIcon("res/IconBus.jpeg");
            Image scaledImg = originalIcon.getImage().getScaledInstance(95, 95, Image.SCALE_SMOOTH);
            iconoBus = new ImageIcon(scaledImg);
        } catch (Exception e) {}

        ImageIcon iconoFlecha = null;
        try {
            ImageIcon originalFlecha = new ImageIcon("res/flecha.jpeg");
            Image scaledFlecha = originalFlecha.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            iconoFlecha = new ImageIcon(scaledFlecha);
        } catch (Exception e) {}

        JPanel panelBuses = new JPanel(new GridLayout(1, 6, 10, 0));
        panelBuses.setOpaque(false);
        panelBuses.setBorder(new EmptyBorder(8, 0, 0, 0));
        
        String[] placas = {"123ABC", "456DEF", "789GHI", "024JKL", "135MNO"};
        for (String placa : placas) {
            JPanel panelItemBus = new JPanel();
            panelItemBus.setLayout(new BoxLayout(panelItemBus, BoxLayout.Y_AXIS));
            panelItemBus.setOpaque(false);
            JLabel lblIcono = new JLabel(iconoBus);
            lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel lblPlaca = new JLabel(placa);
            lblPlaca.setFont(lblPlaca.getFont().deriveFont(Font.BOLD, 12f));
            lblPlaca.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelItemBus.add(lblIcono);
            panelItemBus.add(Box.createRigidArea(new Dimension(0, 2)));
            panelItemBus.add(lblPlaca);
            panelBuses.add(panelItemBus);
        }
        
        JPanel panelBtnWrapper = new JPanel(new GridBagLayout());
        panelBtnWrapper.setOpaque(false);
        
        BotonUtil btnMas = new BotonUtil("", new Color(240, 240, 240), Color.BLACK, 15, new Font("Arial", Font.PLAIN, 12), 55, 55);
        if (iconoFlecha != null) btnMas.setIcon(iconoFlecha);
        else btnMas.setText(">");
        panelBtnWrapper.add(btnMas);
        panelBuses.add(panelBtnWrapper);
        
        panelSuperior.add(panelBuses, BorderLayout.CENTER);

        JPanel panelSuperiorWrapper = new JPanel(new BorderLayout());
        panelSuperiorWrapper.setOpaque(false);
        panelSuperiorWrapper.setBorder(new EmptyBorder(0, 0, 15, 0));
        panelSuperiorWrapper.add(panelSuperior, BorderLayout.CENTER);

        // 2.2. PANEL CENTRAL
        JPanel panelContenedorCentral = new JPanel(new GridBagLayout());
        panelContenedorCentral.setOpaque(false);
        GridBagConstraints gbcCentral = new GridBagConstraints();
        gbcCentral.weighty = 1.0;
        gbcCentral.fill = GridBagConstraints.BOTH;

        // --- Izquierda: Formulario ---
        panelFormularioFlotante = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                RoundRectangle2D rectRedondeado = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                
                g2.setColor(new Color(205, 228, 238));
                g2.fill(rectRedondeado);
                
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(rectRedondeado);
                
                g2.dispose();
            }
        };
        panelFormularioFlotante.setOpaque(false);
        panelFormularioFlotante.setBorder(new EmptyBorder(30, 30, 30, 30));
        panelFormularioFlotante.setLayout(new BorderLayout());

        JPanel panelCamposInternos = new JPanel();
        panelCamposInternos.setLayout(new BoxLayout(panelCamposInternos, BoxLayout.Y_AXIS));
        panelCamposInternos.setOpaque(false);

        JLabel lblTituloRegistro = new JLabel("Registrar nueva unidad");
        lblTituloRegistro.setFont(lblTituloRegistro.getFont().deriveFont(Font.BOLD, 22f));
        lblTituloRegistro.setAlignmentX(Component.LEFT_ALIGNMENT);

        Font fuenteCampos = new Font("Arial", Font.PLAIN, 14);
        
        CampoTextoUtil txtPlaca = new CampoTextoUtil(15, new Color(150, 150, 150), 0, 45, Color.WHITE, Color.GRAY, fuenteCampos);
        txtPlaca.setText("Placa");
        configurarPlaceholder(txtPlaca, "Placa");

        CampoTextoUtil txtModelo = new CampoTextoUtil(15, new Color(150, 150, 150), 0, 45, Color.WHITE, Color.GRAY, fuenteCampos);
        txtModelo.setText("Modelo");
        configurarPlaceholder(txtModelo, "Modelo");

        CampoTextoUtil txtCapacidad = new CampoTextoUtil(15, new Color(150, 150, 150), 0, 45, Color.WHITE, Color.GRAY, fuenteCampos);
        txtCapacidad.setText("Capacidad de pasajeros");
        configurarPlaceholder(txtCapacidad, "Capacidad de pasajeros");

        Dimension campoSize = new Dimension(Integer.MAX_VALUE, 45);
        txtPlaca.setMaximumSize(campoSize);
        txtModelo.setMaximumSize(campoSize);
        txtCapacidad.setMaximumSize(campoSize);
        txtPlaca.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtModelo.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtCapacidad.setAlignmentX(Component.LEFT_ALIGNMENT);

        BotonUtil btnRegistrar = new BotonUtil("Registrar unidad", new Color(40, 100, 160), Color.WHITE, 15, new Font("Arial", Font.BOLD, 14), 0, 45);
        btnRegistrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnRegistrar.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelCamposInternos.add(lblTituloRegistro);
        panelCamposInternos.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCamposInternos.add(txtPlaca);
        panelCamposInternos.add(Box.createRigidArea(new Dimension(0, 12)));
        panelCamposInternos.add(txtModelo);
        panelCamposInternos.add(Box.createRigidArea(new Dimension(0, 12)));
        panelCamposInternos.add(txtCapacidad);
        panelCamposInternos.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCamposInternos.add(btnRegistrar);

        panelFormularioFlotante.add(panelCamposInternos, BorderLayout.CENTER);

        gbcCentral.gridx = 0;
        gbcCentral.weightx = 0.42; 
        panelContenedorCentral.add(panelFormularioFlotante, gbcCentral);

        // --- Derecha: Tarjeta con la imagen circular del autobús ---
        panelDerechaCentro = new JPanel(new BorderLayout());
        panelDerechaCentro.setOpaque(false);
        panelDerechaCentro.setBorder(new EmptyBorder(0, 20, 0, 0));

        JPanel panelImagenBus = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                
                int w = getWidth();
                int h = getHeight();

                try {
                    ImageIcon iconOriginal = new ImageIcon("res/BusPic.jpeg");
                    Image img = iconOriginal.getImage();

                    int diametro = Math.min(w, h) - 20;  
                    int xCirculo = (w - diametro) / 2;
                    int yCirculo = (h - diametro) / 2 - 10; 

                    Ellipse2D circulo = new Ellipse2D.Float(xCirculo, yCirculo, diametro, diametro);
                    g2.setClip(circulo);

                    int imgAncho = img.getWidth(null);
                    int imgAlto = img.getHeight(null);
                    if (imgAncho > 0 && imgAlto > 0) {
                        double escala = Math.max((double) diametro / imgAncho, (double) diametro / imgAlto);
                        int nuevoAncho = (int) (imgAncho * escala);
                        int nuevoAlto = (int) (imgAlto * escala);
                        int imgX = xCirculo + (diametro - nuevoAncho) / 2;
                        int imgY = yCirculo + (diametro - nuevoAlto) / 2;

                        g2.drawImage(img, imgX, imgY, nuevoAncho, nuevoAlto, null);
                    }
                    
                    g2.setClip(null);

                } catch (Exception e) {
                    g2.setColor(new Color(120, 120, 120));
                    g2.setFont(new Font("Arial", Font.BOLD, 14));
                    g2.drawString("[ Imagen no encontrada: res/BusPic.jpeg ]", 40, h / 2);
                }

                g2.dispose();
            }
        };
        panelImagenBus.setOpaque(false);
        panelImagenBus.setLayout(new BorderLayout());

        BotonUtil btnEstadoOperativo = new BotonUtil("Unidades y estado operativo", new Color(40, 100, 160), Color.WHITE, 15, new Font("Arial", Font.BOLD, 14), 0, 45);

        JPanel panelBtnEstadoWrapper = new JPanel(new BorderLayout());
        panelBtnEstadoWrapper.setOpaque(false);
        panelBtnEstadoWrapper.setBorder(new EmptyBorder(15, 0, 0, 0));
        panelBtnEstadoWrapper.add(btnEstadoOperativo, BorderLayout.CENTER);

        panelDerechaCentro.add(panelImagenBus, BorderLayout.CENTER);
        panelDerechaCentro.add(panelBtnEstadoWrapper, BorderLayout.SOUTH);

        gbcCentral.gridx = 1;
        gbcCentral.weightx = 0.58; 
        panelContenedorCentral.add(panelDerechaCentro, gbcCentral);

        panelDerechoTotal.add(panelSuperiorWrapper, BorderLayout.NORTH);
        panelDerechoTotal.add(panelContenedorCentral, BorderLayout.CENTER);

        add(panelMenu, BorderLayout.WEST);
        add(panelDerechoTotal, BorderLayout.CENTER);
    }

    // Método listo para que coloques tu lógica de navegación o cambio de vistas
    private void ejecutarAccionMenu(String opcion) {
        switch (opcion) {
            case "Planificar itinerario":
                // TODO: Agregar lógica para abrir Planificar Itinerario
                break;
            case "Registrar personal":
                // TODO: Agregar lógica para abrir Registrar Personal
                break;
            case "Pasajeros diarios":
                // TODO: Agregar lógica para abrir Pasajeros Diarios
                break;
            case "Generar reporte":
                // TODO: Agregar lógica para abrir Generar Reporte
                break;
            case "Cerrar sesión":
                // TODO: Agregar lógica para cerrar sesión
                dispose();
                break;
            default:
                break;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RegistroUnidades().setVisible(true);
        });
    }
}