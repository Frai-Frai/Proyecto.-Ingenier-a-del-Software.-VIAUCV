package vista;

import util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import modelo.Usuario;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class PasarelaPago extends JDialog {

    //tipografias
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

    private Usuario pasajero;
    private MenuUtil comboOperadora, comboTipoCedula, comboBanco;
    private CampoTextoUtil txtTelefono, txtCedula, txtMonto;
    private BotonUtil botonPagar;
    private Point pointClick = new Point();

    public PasarelaPago(JFrame pantallaPadre, Usuario pasajero) {
        this.pasajero = pasajero;

        super(pantallaPadre, "Pasarela de Pago - Pago Móvil", true);
        setSize(480, 520);
        setLocationRelativeTo(pantallaPadre);
        
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        // fuentes
        Font fuenteTitulo = League("res/LeagueSpartan-Bold.otf", 22f, Font.PLAIN);
        Font fuenteSubT = fuenteGlacial("res/GlacialIndifference-Bold.otf", 14f, Font.BOLD);
        Font fuenteLetras = fuenteGlacial("res/GlacialIndifference-Regular.otf", 13f, Font.PLAIN);

        // Paleta de colores
        Color colorFondo = new Color(0xD6EBF7);
        Color azulOscuro = new Color(0x0D47A1); 
        Color bordeColor = new Color(180, 210, 235);

        // Panel principal con borde
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

        // para que se pueda arrastrar la ventana
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

        // panel sup para el título y el botón para cerrar la mini ventana
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);
        panelSuperior.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel lblTituloVentana = new JLabel("Pasarela de Pago", SwingConstants.CENTER);
        lblTituloVentana.setFont(fuenteTitulo);
        lblTituloVentana.setForeground(azulOscuro);
        panelSuperior.add(lblTituloVentana, BorderLayout.CENTER);

        // Botón para cerrar
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

        // Panel con los campos
        JPanel panelCentral = new JPanel(null);
        panelCentral.setOpaque(false);

        // telefono
        JLabel lblTelefono = new JLabel("Número de Teléfono:");
        lblTelefono.setFont(fuenteSubT);
        lblTelefono.setForeground(azulOscuro);
        lblTelefono.setBounds(10, 5, 400, 25);
        panelCentral.add(lblTelefono);

        String[] operadoras = {"0414", "0424", "0412", "0422", "0416", "0426"};
        comboOperadora = new MenuUtil(operadoras, 15, Color.WHITE, azulOscuro, bordeColor, fuenteLetras);
        comboOperadora.setBounds(10, 32, 95, 35);
        panelCentral.add(comboOperadora);

        // Campo de texto de teléfono 
        txtTelefono = new CampoTextoUtil(15, bordeColor, 320, 35, Color.WHITE, azulOscuro, fuenteLetras);
        txtTelefono.setBounds(112, 32, 320, 35);
        panelCentral.add(txtTelefono);

        // cedula con los tipos, que si V o E y el numero de la cedula como tal
        JLabel lblCedula = new JLabel("Cédula de Identidad:");
        lblCedula.setFont(fuenteSubT);
        lblCedula.setForeground(azulOscuro);
        lblCedula.setBounds(10, 75, 400, 25);
        panelCentral.add(lblCedula);

        String[] tiposCedula = {"V", "E", "P", "R", "J", "G"};
        comboTipoCedula = new MenuUtil(tiposCedula, 15, Color.WHITE, azulOscuro, bordeColor, fuenteLetras);
        comboTipoCedula.setBounds(10, 102, 65, 35);
        panelCentral.add(comboTipoCedula);

        // Campo de texto de cédula
        txtCedula = new CampoTextoUtil(15, bordeColor, 350, 35, Color.WHITE, azulOscuro, fuenteLetras);
        txtCedula.setBounds(82, 102, 350, 35);
        panelCentral.add(txtCedula);

        // bancos
        JLabel lblBanco = new JLabel("Banco:");
        lblBanco.setFont(fuenteSubT);
        lblBanco.setForeground(azulOscuro);
        lblBanco.setBounds(10, 145, 400, 25);
        panelCentral.add(lblBanco);

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
        comboBanco = new MenuUtil(bancos, 15, Color.WHITE, azulOscuro, bordeColor, fuenteLetras);
        comboBanco.setBounds(10, 172, 422, 35);
        panelCentral.add(comboBanco);

        // monto
        JLabel lblMonto = new JLabel("Monto a Pagar:");
        lblMonto.setFont(fuenteSubT);
        lblMonto.setForeground(azulOscuro);
        lblMonto.setBounds(10, 215, 400, 25);
        panelCentral.add(lblMonto);

        // Campo de texto de monto 
        txtMonto = new CampoTextoUtil(15, bordeColor, 422, 35, Color.WHITE, azulOscuro, fuenteLetras);
        txtMonto.setBounds(10, 242, 422, 35);
        panelCentral.add(txtMonto);

        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        // botón para pagar
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSur.setOpaque(false);
        panelSur.setBorder(new EmptyBorder(5, 0, 0, 0));

        botonPagar = new BotonUtil("Pagar", azulOscuro, Color.WHITE, 14, fuenteSubT, 140, 38);
        panelSur.add(botonPagar);

        panelPrincipal.add(panelSur, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    public void setMontoCalculado(String montoPagar){
        txtMonto.setText(montoPagar);
        txtMonto.setEnabled(false);//para q no pueda editar el monto
    }

    // Getters
    public String getOperadora(){ 
        return (String) comboOperadora.getSelectedItem(); 
    }

    public String getTelefono(){ 
        return txtTelefono.getText().trim(); 
    }

    public String getTipoCedula(){ 
        return (String) comboTipoCedula.getSelectedItem(); 
    }

    public String getCedula(){ 
        return txtCedula.getText().trim(); 
    }

    public String getBanco(){ 
        return (String) comboBanco.getSelectedItem(); 
    }

    public String getMonto(){ 
        return txtMonto.getText().trim(); 
    }

    public BotonUtil getBotonPagar(){ 
        return botonPagar; 
    }
   
}