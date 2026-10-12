package vista;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import util.*;

public class CodigoVerificacion extends JDialog {

    private JTextField cuadroCodigo;
    private BotonUtil botonAceptar;
    private JLabel textoMensaje;
    private MiniVentanaUtil panelForm;
    private Point pointClick = new Point();
    
    // Tipografía 
    private Font fuenteGlacial(String ruta, float tamano, int estilo){
        try {
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, new File(ruta));
            return fuente.deriveFont(estilo, tamano);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la fuente: " + e.getMessage());
            return new Font("SansSerif", estilo, (int) tamano);
        }
    }

    public CodigoVerificacion(JFrame ventanaPadre) {
        super(ventanaPadre, "Verificación", true);
        
        // Configuración de colores y tipografías
        Color azulCuadros = new Color(0x0D47A1); 
        Color colorVentanaC = new Color(0XE3F2FD);
        Color azulPastelC = new Color(0xBBDEFB); 
        Font fuenteTexto = fuenteGlacial("res/GlacialIndifference-Regular.otf", 15, Font.BOLD);
        Font fuentesCampos = fuenteGlacial("res/GlacialIndifference-Regular.otf", 13, Font.BOLD);
        Font fuenteTitle = new Font("Dialog", Font.BOLD, 20); 
        
        setSize(406, 320);
        setLocationRelativeTo(ventanaPadre);
        
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal con el borde 
        JPanel panelPrincipal = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Relleno de fondo
                g2.setColor(colorVentanaC);
                g2.fillRoundRect(3, 3, getWidth() - 7, getHeight() - 7, 24, 24);
                
                // Borde azul oscuro grueso
                g2.setColor(azulCuadros);
                g2.setStroke(new BasicStroke(5.5f));
                g2.drawRoundRect(3, 3, getWidth() - 7, getHeight() - 7, 24, 24);
                
                g2.dispose();
            }
        };
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(new EmptyBorder(12, 12, 12, 12));

        // Listener para permitir arrastrar la ventana
        panelPrincipal.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                pointClick.setLocation(e.getX(), e.getY());
            }
        });
        panelPrincipal.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                setLocation(getLocation().x + e.getX() - pointClick.x, getLocation().y + e.getY() - pointClick.y);
            }
        });

        panelForm = new MiniVentanaUtil(30, colorVentanaC, 380, 310, true, colorVentanaC);
        panelForm.setBounds(10, 10, 380, 300);
        panelForm.setLayout(null);
        panelPrincipal.add(panelForm);

        JLabel textoT = new JLabel("Verificar Código", SwingConstants.CENTER);
        textoT.setFont(fuenteTitle);
        textoT.setBounds(0, 20, 380, 30);
        panelForm.add(textoT);

        // Texto descriptivo
        textoMensaje = new JLabel("Ingrese el código de verificación:");
        textoMensaje.setFont(fuenteTexto);
        textoMensaje.setBounds(40, 75, 300, 20);
        panelForm.add(textoMensaje);

        // Campo de texto 
        cuadroCodigo = new JTextField();
        cuadroCodigo.setFont(fuentesCampos);
        cuadroCodigo.setForeground(Color.darkGray);
        cuadroCodigo.setBackground(azulPastelC);
        cuadroCodigo.setCaretColor(azulCuadros);
        cuadroCodigo.setBounds(40, 105, 300, 40);
        cuadroCodigo.setOpaque(true);
        
        // Borde redondeado personalizado para los campo
        cuadroCodigo.setBorder(BorderFactory.createCompoundBorder(
            new BordeRedondeado(15, azulPastelC),
            new EmptyBorder(5, 12, 5, 12)
        ));
        panelForm.add(cuadroCodigo);

        // Botón de acción
        botonAceptar = new BotonUtil("Confirmar", azulCuadros, colorVentanaC, 20, fuenteTexto, 150, 42);
        botonAceptar.setBounds(115, 175, 150, 42);
        panelForm.add(botonAceptar);

        add(panelPrincipal);
        getRootPane().setDefaultButton(botonAceptar);
    }
    
    // Clase interna para dibujar el borde redondeado del campo de texto 
    private static class BordeRedondeado extends AbstractBorder {
        private int radio;
        private Color color;

        public BordeRedondeado(int radio, Color color) {
            this.radio = radio;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width - 1, height - 1, radio, radio);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radio / 2, this.radio / 2, this.radio / 2, this.radio / 2);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = this.radio / 2;
            insets.top = insets.bottom = this.radio / 2;
            return insets;
        }
    }
    
    // Getters puros para la vista
    public String getCodigo() {
        return cuadroCodigo.getText().trim();
    }

    public BotonUtil getBotonAceptar() {
        return botonAceptar;
    }

    public JTextField getCuadroCodigo() {
        return cuadroCodigo;
    }
   /*  public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CodigoVerificacion ventana = new CodigoVerificacion(null);
            ventana.setVisible(true);
        });
    }*/
}