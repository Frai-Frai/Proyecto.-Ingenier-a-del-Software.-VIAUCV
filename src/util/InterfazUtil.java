package util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

public class InterfazUtil extends JFrame {

    private BotonUtil btnAnterior;
    private BotonUtil btnSiguiente;
    private JPanel panelBuses;

    public InterfazUtil() {
        setTitle("Registro de Unidades - UCV");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Fuentes regular y bold (tamaño 18f)
        Font fuenteRegular = cargarFuente("res/GlacialIndifference-Regular.otf", 18f, Font.PLAIN);
        Font fuenteBold = cargarFuente("res/GlacialIndifference-Bold.otf", 18f, Font.BOLD);

        // 1. PANEL IZQUIERDO (MENÚ LATERAL)
        JPanel panelMenu = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(165, 205, 235));
                int margen = 12;
                int ancho = getWidth() - (margen * 2);
                int alto = getHeight() - (margen * 2);
                RoundRectangle2D rect = new RoundRectangle2D.Float(margen, margen, ancho, alto, 45, 45);
                g2.fill(rect);
                g2.dispose();
            }
        };
        panelMenu.setPreferredSize(new Dimension(320, 0));
        panelMenu.setOpaque(false);
        panelMenu.setBorder(new EmptyBorder(35, 25, 35, 25));
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        
        JLabel lblLogo = new JLabel();
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);
        try {
            ImageIcon iconoOriginal = new ImageIcon("res/LogoViaUCV.png");
            Image img = iconoOriginal.getImage();
            int anchoDeseado = 220;
            int anchoOrig = iconoOriginal.getIconWidth();
            int altoOrig = iconoOriginal.getIconHeight();
            int altoDeseado = (anchoOrig > 0) ? (altoOrig * anchoDeseado) / anchoOrig : 50;
            Image imagenEscalada = img.getScaledInstance(anchoDeseado, altoDeseado, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(imagenEscalada));
        } catch (Exception e) {
            lblLogo.setText("  LogoViaUCV");
        }
        
        panelMenu.add(lblLogo);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 30)));

        // Opciones principales
        String[] opciones = {
            "Planificar itinerario", "Registrar personal", "Pasajeros diarios",
            "Gestión de unidades", "Generar reporte"
        };

        for (int i = 0; i < opciones.length; i++) {
            String textoOpcion = opciones[i];

            JLabel lblOpcion = new JLabel(textoOpcion);
            lblOpcion.setFont(fuenteBold);
            lblOpcion.setBorder(new EmptyBorder(0, 15, 0, 0));
            lblOpcion.setAlignmentX(Component.LEFT_ALIGNMENT);
            lblOpcion.setCursor(new Cursor(Cursor.HAND_CURSOR));

            lblOpcion.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    lblOpcion.setForeground(new Color(30, 85, 145));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    lblOpcion.setForeground(Color.BLACK);
                }
            });

            panelMenu.add(lblOpcion);
            
            if (i < opciones.length - 1) {
                panelMenu.add(Box.createRigidArea(new Dimension(0, 18)));
            }
        }

        // Espacio mayor para separar y empujar "Cerrar sesión" más hacia abajo
        panelMenu.add(Box.createRigidArea(new Dimension(0, 80)));
        
        JLabel lblCerrarSesion = new JLabel("Cerrar sesión");
        lblCerrarSesion.setFont(fuenteBold);
        lblCerrarSesion.setBorder(new EmptyBorder(0, 15, 0, 0));
        lblCerrarSesion.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));

        lblCerrarSesion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblCerrarSesion.setForeground(new Color(30, 85, 145));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblCerrarSesion.setForeground(Color.BLACK);
            }
        });
        panelMenu.add(lblCerrarSesion);

        panelMenu.add(Box.createVerticalGlue());

        // 2. PANEL DERECHO TOTAL
        JPanel panelDerechoTotal = new JPanel(new BorderLayout());
        panelDerechoTotal.setBackground(new Color(240, 243, 246));
        panelDerechoTotal.setBorder(new EmptyBorder(12, 12, 12, 12));

        // PANEL SUPERIOR (CABECERA)
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

        // Espacio reservado para títulos a futuro
        JLabel tituloSuperior = new JLabel(" ", JLabel.CENTER);
        tituloSuperior.setFont(fuenteRegular.deriveFont(20f));
        tituloSuperior.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelNorteSuperior.add(tituloSuperior, BorderLayout.NORTH);

        // Línea divisoria negra conservada
        JPanel lineaDivisoria = new JPanel();
        lineaDivisoria.setBackground(Color.BLACK);
        lineaDivisoria.setPreferredSize(new Dimension(0, 1));
        panelNorteSuperior.add(lineaDivisoria, BorderLayout.CENTER);

        panelSuperior.add(panelNorteSuperior, BorderLayout.NORTH);

        // CONTENEDOR CENTRAL DE LOS BUSES CON FLECHAS
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

        JPanel panelSuperiorWrapper = new JPanel(new BorderLayout());
        panelSuperiorWrapper.setOpaque(false);
        panelSuperiorWrapper.setBorder(new EmptyBorder(0, 0, 12, 0));
        panelSuperiorWrapper.add(panelSuperior, BorderLayout.CENTER);

        // PANEL CENTRAL VACÍO (ESTRUCTURA BASE)
        JPanel panelDerechaCentro = new JPanel();
        panelDerechaCentro.setOpaque(false);

        panelDerechoTotal.add(panelSuperiorWrapper, BorderLayout.NORTH);
        panelDerechoTotal.add(panelDerechaCentro, BorderLayout.CENTER);

        add(panelMenu, BorderLayout.WEST);
        add(panelDerechoTotal, BorderLayout.CENTER);
    }

    private Font cargarFuente(String ruta, float tamano, int estilo) {
        try {
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, new File(ruta));
            return fuente.deriveFont(estilo, tamano);
        } catch (Exception e) {
            return new Font("SansSerif", estilo, (int) tamano);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfazUtil().setVisible(true);
        });
    }
}