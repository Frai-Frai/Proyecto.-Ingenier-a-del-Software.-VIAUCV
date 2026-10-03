package util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.util.function.Consumer;

public class InterfazUtil {

    /**
     * PERSONALIZACION
     * 
     * @param opcionesMenu Array con textos de opciones del menú.
     * @param colorFondo Color de fondo del panel lateral.
     * @param accionClick Interfaz funcional para manejar los clics de las opciones del menú.
     * @return JPanel configurado para agregarse a la vista.
     */
    public static JPanel crearPanelMenu(String[] opcionesMenu, Color colorFondo, Consumer<String> accionClick) {
        Font fuenteBold = cargarFuente("res/GlacialIndifference-Bold.otf", 18f, Font.BOLD);

        JPanel panelMenu = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(colorFondo != null ? colorFondo : new Color(165, 205, 235));
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

        // Logo
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

        // Opciones del menú dinámicas
        if (opcionesMenu != null) {
            for (int i = 0; i < opcionesMenu.length; i++) {
                String textoOpcion = opcionesMenu[i];

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

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (accionClick != null) {
                            accionClick.accept(textoOpcion);
                        }
                    }
                });

                panelMenu.add(lblOpcion);

                if (i < opcionesMenu.length - 1) {
                    panelMenu.add(Box.createRigidArea(new Dimension(0, 18)));
                }
            }
        }

        // Botón "Cerrar sesión" separado más abajo
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

            @Override
            public void mouseClicked(MouseEvent e) {
                if (accionClick != null) {
                    accionClick.accept("Cerrar sesión");
                }
            }
        });
        panelMenu.add(lblCerrarSesion);
        panelMenu.add(Box.createVerticalGlue());

        return panelMenu;
    }

    /**
     * Método auxiliar para cargar fuentes con seguridad.
     */
    private static Font cargarFuente(String ruta, float tamano, int estilo) {
        try {
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, new File(ruta));
            return fuente.deriveFont(estilo, tamano);
        } catch (Exception e) {
            return new Font("SansSerif", estilo, (int) tamano);
        }
    }
}