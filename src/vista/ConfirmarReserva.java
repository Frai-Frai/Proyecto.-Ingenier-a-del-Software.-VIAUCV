package vista;

import util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class ConfirmarReserva extends JFrame {

    private JLabel lblPlaca, lblCuposDisponibles, lblTipoRuta, lblDestino, lblPuntoPartida, lblPuntoLlegada, lblHorario;
    private BotonUtil btnReservar;
    private Point pointClick = new Point();

    public ConfirmarReserva() {
        setTitle("Confirmar reserva");
        setSize(480, 370);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        // Tipografías
        Font fuenteTitulo = League("res/LeagueSpartan-Bold.otf", 22f, Font.BOLD);
        Font fuenteGlacialBold = fuenteGlacial("res/GlacialIndifference-Bold.otf", 14f, Font.BOLD);
        Font fuenteBtn = fuenteGlacial("res/GlacialIndifference-Bold.otf", 14f, Font.BOLD);

        // Paleta de colores
        Color colorFondo = new Color(0xD6EBF7);
        Color azulOscuro = new Color(0x0D47A1); 
        Color colorAzulDinamico = new Color(0x2E86C1); 

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Relleno de fondo
                g2.setColor(colorFondo);
                g2.fillRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 24, 24);
                
                // Borde azul oscuro 
                g2.setColor(azulOscuro);
                g2.setStroke(new BasicStroke(5.5f));
                g2.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 24, 24);
                
                g2.dispose();
            }
        };
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(new EmptyBorder(18, 22, 18, 22));

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

        // Panel superior para el título y el botón de cierre 
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);
        panelSuperior.setBorder(new EmptyBorder(0, 0, 12, 0));

        JLabel lblTituloVentana = new JLabel("Confirmar reserva", SwingConstants.CENTER);
        lblTituloVentana.setFont(fuenteTitulo);
        lblTituloVentana.setForeground(Color.BLACK);
        panelSuperior.add(lblTituloVentana, BorderLayout.CENTER);

        // Botón de cierre X
        JLabel btnCerrar = new JLabel("X");
        btnCerrar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnCerrar.setForeground(azulOscuro);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.setBorder(new EmptyBorder(0, 5, 0, 0));
        btnCerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { dispose(); }
            @Override
            public void mouseEntered(MouseEvent e) { btnCerrar.setForeground(Color.RED); }
            @Override
            public void mouseExited(MouseEvent e) { btnCerrar.setForeground(azulOscuro); }
        });
        panelSuperior.add(btnCerrar, BorderLayout.EAST);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // Panel central con los datos
        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.setOpaque(false);
        panelDatos.setBorder(new EmptyBorder(5, 10, 5, 10));

        // Creación de líneas 
        lblPlaca = crearLineaDato(panelDatos, "Placa (transporte asignado): ", fuenteGlacialBold, colorAzulDinamico);
        lblCuposDisponibles = crearLineaDato(panelDatos, "Cupos disponibles: ", fuenteGlacialBold, colorAzulDinamico);
        lblTipoRuta = crearLineaDato(panelDatos, "Tipo de ruta: ", fuenteGlacialBold, colorAzulDinamico);
        lblDestino = crearLineaDato(panelDatos, "Destino: ", fuenteGlacialBold, colorAzulDinamico);
        lblPuntoPartida = crearLineaDato(panelDatos, "Punto de partida: ", fuenteGlacialBold, colorAzulDinamico);
        lblPuntoLlegada = crearLineaDato(panelDatos, "Punto de llegada: ", fuenteGlacialBold, colorAzulDinamico);
        lblHorario = crearLineaDato(panelDatos, "Horario: ", fuenteGlacialBold, colorAzulDinamico);

        panelPrincipal.add(panelDatos, BorderLayout.CENTER);

        // Panel inferior con el botón "Pagar"
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSur.setOpaque(false);
        panelSur.setBorder(new EmptyBorder(10, 0, 5, 0));

        btnReservar = new BotonUtil("Pagar", new Color(134, 185, 222), Color.BLACK, 16, fuenteBtn, 140, 38);
        panelSur.add(btnReservar);

        panelPrincipal.add(panelSur, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    // Método auxiliar para que el texto fijo y el dinámico usen la tipografía 
    private JLabel crearLineaDato(JPanel contenedor, String textoFijo, Font fuenteGlacial, Color colorValor) {
        JPanel linea = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 4));
        linea.setOpaque(false);

        JLabel lblFijo = new JLabel(textoFijo);
        lblFijo.setFont(fuenteGlacial);
        lblFijo.setForeground(Color.BLACK);

        JLabel lblDinamico = new JLabel();
        lblDinamico.setFont(fuenteGlacial); 
        lblDinamico.setForeground(colorValor);

        linea.add(lblFijo);
        linea.add(lblDinamico);
        contenedor.add(linea);

        return lblDinamico;
    }

    public void setPlaca(String placa) { lblPlaca.setText(placa); }
    public void setCuposDisponibles(String cupos) { lblCuposDisponibles.setText(cupos); }
    public void setTipoRuta(String tipoRuta) { lblTipoRuta.setText(tipoRuta); }
    public void setDestino(String destino) { lblDestino.setText(destino); }
    public void setPuntoPartida(String puntoPartida) { lblPuntoPartida.setText(puntoPartida); }
    public void setPuntoLlegada(String puntoLlegada) { lblPuntoLlegada.setText(puntoLlegada); }
    public void setHorario(String horario) { lblHorario.setText(horario); }

    public BotonUtil getBtnReservar() { return btnReservar; }

    private Font fuenteGlacial(String ruta, float tamano, int estilo){
        try {
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, new File(ruta));
            return fuente.deriveFont(estilo, tamano);
        } catch (Exception e) {
            return new Font("SansSerif", estilo, (int) tamano);
        }
    }

    private Font League(String ruta, float tamano, int estilo){
        try {
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, new File(ruta));
            return fuente.deriveFont(estilo, tamano);
        } catch (Exception e) {
            return new Font("SansSerif", estilo, (int) tamano);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConfirmarReserva ventanaPrueba = new ConfirmarReserva();
            ventanaPrueba.setPlaca("");
            ventanaPrueba.setCuposDisponibles("");
            ventanaPrueba.setTipoRuta("");
            ventanaPrueba.setDestino("");
            ventanaPrueba.setPuntoPartida("");
            ventanaPrueba.setPuntoLlegada("");
            ventanaPrueba.setHorario("");
            ventanaPrueba.setVisible(true);
        });
    }
}