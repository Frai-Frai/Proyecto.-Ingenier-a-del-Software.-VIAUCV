package vista;

import modelo.*;

import java.io.File;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.border.EmptyBorder;

import controlador.Controlador_Itinerario;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.awt.geom.RoundRectangle2D;
import java.time.DayOfWeek;
import javax.swing.*;
import java.awt.*;
import util.*;

public class Itinerario extends JFrame {
    
    private BotonUtil botonP, botonFlechaD, botonFlechaI, botonER;
    private JPanel panelPines;
    private MiniVentanaUtil menuIzq, Bienvenida, panelRutas, panelForm;
    private JLabel  opPlanificar, opRegistrarP, opPDiarios, opGestionU, opGenR, opCerrarS;
    private CampoTextoUtil cuadritoPlaca, cuadritoTipoRuta, cuadritoDestino, cuadritoPtoLlegada, cuadritoPtoPartida, cuadritoHora, cuadritoDia, cuadritoConductor;

    //Tipografia 
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

    public Itinerario(Usuario admin){

        setTitle ("VIAUCV - ADMINISTRADOR -- PLANIFICAR ITINERARIO");
        setSize(1000, 870); 
        setMinimumSize(new Dimension(980, 750)); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0xE1F5FE));

        Font    fuenteT = League("res/LeagueSpartan-Bold.otf", 22, Font.BOLD); 
        Font    fuenteSubT = fuenteGlacial("res/GlacialIndifference-Bold.otf", 16, Font.BOLD); 
        Font    fuenteNormal = fuenteGlacial("res/GlacialIndifference-Bold.otf", 14, Font.BOLD); 
        Font    fuenteLetras = fuenteGlacial("res/GlacialIndifference-Bold.otf", 16, Font.BOLD);
        Font    fuenteLink = fuenteGlacial("res/GlacialIndifference-Bold.otf", 16, Font.BOLD);
        Color   azulCuadros = new Color(0x0D47A1); 
        Color   Colorwelcome = new Color(154, 195, 220);
        Color   Colormenu = new Color(214, 233, 245);

        int anchoUtilInicial = getWidth() - 290;
        int altoUtilInicial = getContentPane().getHeight();

        // Panel superior de bienvenida
        Bienvenida = new MiniVentanaUtil(30, Colorwelcome, anchoUtilInicial, 120, false, Colorwelcome);
        Bienvenida.setBounds(270, 20, anchoUtilInicial, 120);
        Bienvenida.setLayout(null);
        add(Bienvenida);

        String nombreAdmin = admin.getNombreUsuario();
        JLabel saludo = new JLabel("¡Hola, administrador(a) " + nombreAdmin + "!");
        saludo.setFont(fuenteT);
        saludo.setForeground(Color.WHITE);
        saludo.setBounds(30, 30, 500, 30);
        Bienvenida.add(saludo);

        JLabel subtitulo = new JLabel("¡Sigue manteniendo todo en control!");
        subtitulo.setFont(fuenteSubT);
        subtitulo.setForeground(Color.WHITE);
        subtitulo.setBounds(30, 65, 500, 20);
        Bienvenida.add(subtitulo);

        // Menú lateral izquierdo
        menuIzq = new MiniVentanaUtil(30, Colormenu, 240, altoUtilInicial - 40, false, Colormenu);
        menuIzq.setLayout(null);
        menuIzq.setBounds(20, 20, 240, altoUtilInicial - 40); 
        add(menuIzq);

        // Panel superior blanco de rutas (altura fija de 170)
        int altoPanelRutas = 170;
        panelRutas = new MiniVentanaUtil(20, new Color(245, 245, 245), anchoUtilInicial, altoPanelRutas, true, Colorwelcome);
        panelRutas.setBounds(270, 155, anchoUtilInicial, altoPanelRutas);
        panelRutas.setLayout(null);
        add(panelRutas);

        // Título del panel superior con fuente bold aplicada
        JLabel tituloSuperior = new JLabel("Itinerario Semanal", SwingConstants.CENTER);
        tituloSuperior.setFont(fuenteLetras);
        tituloSuperior.setForeground(azulCuadros);
        tituloSuperior.setBounds(0, 10, anchoUtilInicial, 20);
        panelRutas.add(tituloSuperior);

        JPanel lineaDivisoria = new JPanel();
        lineaDivisoria.setBackground(Color.BLACK);
        lineaDivisoria.setBounds(30, 35, anchoUtilInicial - 60, 1);
        panelRutas.add(lineaDivisoria);
        
        int posYElementos = (altoPanelRutas - 40) / 2 + 10;
        
        // Botón de flecha izquierda
        ImageIcon iconoFlechaI = new ImageIcon("res/flechacontraria.jpeg");
        Image imgFlechaI = iconoFlechaI.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH);
        botonFlechaI = new BotonUtil("", Color.WHITE, Color.BLACK, 12, fuenteT, 40, 40);
        botonFlechaI.setIcon(new ImageIcon(imgFlechaI));
        botonFlechaI.setBounds(15, posYElementos, 40, 40);
        panelRutas.add(botonFlechaI);

        // Botón de flecha derecha
        ImageIcon iconoFlechaD = new ImageIcon("res/flecha.jpeg");
        Image imgFlechaD = iconoFlechaD.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH);
        botonFlechaD = new BotonUtil("", Color.WHITE, Color.BLACK, 12, fuenteT, 40, 40);
        botonFlechaD.setIcon(new ImageIcon(imgFlechaD));
        botonFlechaD.setBounds(anchoUtilInicial - 55, posYElementos, 40, 40);
        panelRutas.add(botonFlechaD);

        panelPines = new JPanel(null); 
        panelPines.setBounds(65, 45, anchoUtilInicial - 130, altoPanelRutas - 50);
        panelPines.setOpaque(false); 
        panelRutas.add(panelPines);

        // Ancho del panel del formulario central
        int anchoFormInicial = anchoUtilInicial - 220; 
        int altoFormInicial = altoUtilInicial - 355;
        panelForm = new MiniVentanaUtil(30, Color.WHITE, anchoFormInicial, altoFormInicial, true, azulCuadros);
        panelForm.setBounds(270, 335, anchoFormInicial, altoFormInicial);
        panelForm.setLayout(null);
        add(panelForm);
        
        // Saber el día de la semana
        LocalDate hoy = LocalDate.now();
        LocalDate inicioS = hoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate finS = inicioS.plusDays(6); 
       
        JLabel Semana = new JLabel("Semana: " + inicioS + " - " + finS);
        Semana.setBounds(35, 15, 300, 25);
        Semana.setFont(fuenteSubT);
        Semana.setForeground(Color.BLACK);
        panelForm.add(Semana);

        // Cálculo para centrar verticalmente el bloque de campos con interlineado de 1.5 cm (~56 px)
        int espacioY = 56; 
        int alturaTotalBloque = (7 * espacioY) + 32; 
        int yInicio = (altoFormInicial - alturaTotalBloque) / 2 + 15; 
        
        int xLabel = 35;
        int anchoLabel = 250; // Ampliado para que quepa perfectamente el texto en Bold sin truncarse
        int xCuadro = xLabel + anchoLabel + 15;
        int anchoCuadro = anchoFormInicial - xCuadro - 35; 

        JLabel placa = new JLabel("Transporte Asignado (Placa): ");
        placa.setBounds(xLabel, yInicio, anchoLabel, 28);
        placa.setFont(fuenteLetras);
        panelForm.add(placa);

        cuadritoPlaca = new CampoTextoUtil(10, azulCuadros, anchoCuadro, 32, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoPlaca.setBounds(xCuadro, yInicio, anchoCuadro, 32);
        panelForm.add(cuadritoPlaca);

        JLabel Tpruta = new JLabel("Tipo de Ruta: ");
        Tpruta.setBounds(xLabel, yInicio + espacioY, anchoLabel, 28);
        Tpruta.setFont(fuenteLetras);
        panelForm.add(Tpruta);

        cuadritoTipoRuta = new CampoTextoUtil(10, azulCuadros, anchoCuadro, 32, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoTipoRuta.setBounds(xCuadro, yInicio + espacioY, anchoCuadro, 32);
        panelForm.add(cuadritoTipoRuta);

        JLabel destino = new JLabel("Destino: ");
        destino.setBounds(xLabel, yInicio + (espacioY * 2), anchoLabel, 28);
        destino.setFont(fuenteLetras);
        panelForm.add(destino);

        cuadritoDestino = new CampoTextoUtil(10, azulCuadros, anchoCuadro, 32, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoDestino.setBounds(xCuadro, yInicio + (espacioY * 2), anchoCuadro, 32);
        panelForm.add(cuadritoDestino);

        JLabel ptoPartida = new JLabel("Punto de Partida: ");
        ptoPartida.setBounds(xLabel, yInicio + (espacioY * 3), anchoLabel, 28);
        ptoPartida.setFont(fuenteLetras);
        panelForm.add(ptoPartida);

        cuadritoPtoPartida = new CampoTextoUtil(10, azulCuadros, anchoCuadro, 32, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoPtoPartida.setBounds(xCuadro, yInicio + (espacioY * 3), anchoCuadro, 32);
        panelForm.add(cuadritoPtoPartida);

        JLabel ptollegada = new JLabel("Punto de Llegada: ");
        ptollegada.setBounds(xLabel, yInicio + (espacioY * 4), anchoLabel, 28);
        ptollegada.setFont(fuenteLetras);
        panelForm.add(ptollegada);

        cuadritoPtoLlegada = new CampoTextoUtil(10, azulCuadros, anchoCuadro, 32, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoPtoLlegada.setBounds(xCuadro, yInicio + (espacioY * 4), anchoCuadro, 32);
        panelForm.add(cuadritoPtoLlegada);

        JLabel dia = new JLabel("Día de la Semana: ");
        dia.setBounds(xLabel, yInicio + (espacioY * 5), anchoLabel, 28);
        dia.setFont(fuenteLetras);
        panelForm.add(dia);

        cuadritoDia = new CampoTextoUtil(10, azulCuadros, anchoCuadro, 32, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoDia.setBounds(xCuadro, yInicio + (espacioY * 5), anchoCuadro, 32);
        panelForm.add(cuadritoDia);

        JLabel hora = new JLabel("Hora: ");
        hora.setBounds(xLabel, yInicio + (espacioY * 6), anchoLabel, 28);
        hora.setFont(fuenteLetras);
        panelForm.add(hora);

        cuadritoHora = new CampoTextoUtil(10, azulCuadros, anchoCuadro, 32, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoHora.setBounds(xCuadro, yInicio + (espacioY * 6), anchoCuadro, 32);
        panelForm.add(cuadritoHora);
        
        JLabel conductor = new JLabel("Conductor Asignado: ");
        conductor.setBounds(xLabel, yInicio + (espacioY * 7), anchoLabel, 28);
        conductor.setFont(fuenteLetras);
        panelForm.add(conductor);

        cuadritoConductor = new CampoTextoUtil(10, azulCuadros, anchoCuadro, 32, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoConductor.setBounds(xCuadro, yInicio + (espacioY * 7), anchoCuadro, 32);
        panelForm.add(cuadritoConductor);

        // Posición de los botones laterales derechos
        int xBotonesInicial = 270 + anchoFormInicial + 15;

        int posYBotonInicial= 335+altoFormInicial-42;
        int posYERboton= posYBotonInicial-42-10;

        botonER = new BotonUtil("Seleccionar rutas", azulCuadros, Color.WHITE, 15, fuenteSubT, 180, 40);
        botonER.setBounds(xBotonesInicial,posYERboton, 180, 42);

        botonP = new BotonUtil("Subir itinerario", azulCuadros, Color.WHITE, 15, fuenteSubT, 180, 40);
        botonP.setBounds(xBotonesInicial, posYBotonInicial, 180, 42);

        add(botonER);
        add(botonP);

        // Listener dinámico adaptado para redimensionar con la ventana
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int alturaUtil = getContentPane().getHeight();
                int nuevoAnchoUtil = getContentPane().getWidth() - 290;

                if (alturaUtil > 40 && nuevoAnchoUtil > 200) {
                    // Adaptar menú izquierdo
                    menuIzq.setBounds(20, 20, 240, alturaUtil - 40);
                    
                    // Adaptar bienvenida
                    Bienvenida.setBounds(270, 20, nuevoAnchoUtil, 120);
                    
                    // Adaptar panel de rutas superior
                    int nuevoAltoRutas = 170;
                    panelRutas.setBounds(270, 155, nuevoAnchoUtil, nuevoAltoRutas);
                    tituloSuperior.setBounds(0, 10, nuevoAnchoUtil, 20);
                    lineaDivisoria.setBounds(30, 35, nuevoAnchoUtil - 60, 1);
                    
                    int nuevoPosYElem = (nuevoAltoRutas - 40) / 2 + 10;
                    botonFlechaI.setBounds(15, nuevoPosYElem, 40, 40);
                    botonFlechaD.setBounds(nuevoAnchoUtil - 55, nuevoPosYElem, 40, 40);
                    panelPines.setBounds(65, 45, nuevoAnchoUtil - 130, nuevoAltoRutas - 50);

                    // Adaptar tamaño del panel del formulario central
                    int nuevoAnchoForm = nuevoAnchoUtil - 220;
                    int nuevoAltoForm = alturaUtil - 385;
                    panelForm.setBounds(270, 335, nuevoAnchoForm, nuevoAltoForm);

                    // Recalcular el centrado vertical dinámicamente al redimensionar
                    //int nuevoYInicio = (nuevoAltoForm - alturaTotalBloque) / 2 + 15;
                    int nuevoYInicio = 42;
                    int espacioY=45; //para el espacio entre campos del formulario
                    int nuevoAnchoCuadro = nuevoAnchoForm - xCuadro - 35;

                    placa.setBounds(xLabel, nuevoYInicio, anchoLabel, 28);
                    cuadritoPlaca.setBounds(xCuadro, nuevoYInicio, nuevoAnchoCuadro, 32);

                    Tpruta.setBounds(xLabel, nuevoYInicio + espacioY, anchoLabel, 28);
                    cuadritoTipoRuta.setBounds(xCuadro, nuevoYInicio + espacioY, nuevoAnchoCuadro, 32);

                    destino.setBounds(xLabel, nuevoYInicio + (espacioY * 2), anchoLabel, 28);
                    cuadritoDestino.setBounds(xCuadro, nuevoYInicio + (espacioY * 2), nuevoAnchoCuadro, 32);

                    ptoPartida.setBounds(xLabel, nuevoYInicio + (espacioY * 3), anchoLabel, 28);
                    cuadritoPtoPartida.setBounds(xCuadro, nuevoYInicio + (espacioY * 3), nuevoAnchoCuadro, 32);

                    ptollegada.setBounds(xLabel, nuevoYInicio + (espacioY * 4), anchoLabel, 28);
                    cuadritoPtoLlegada.setBounds(xCuadro, nuevoYInicio + (espacioY * 4), nuevoAnchoCuadro, 32);

                    dia.setBounds(xLabel, nuevoYInicio + (espacioY * 5), anchoLabel, 28);
                    cuadritoDia.setBounds(xCuadro, nuevoYInicio + (espacioY * 5), nuevoAnchoCuadro, 32);

                    hora.setBounds(xLabel, nuevoYInicio + (espacioY * 6), anchoLabel, 28);
                    cuadritoHora.setBounds(xCuadro, nuevoYInicio + (espacioY * 6), nuevoAnchoCuadro, 32);

                    conductor.setBounds(xLabel, nuevoYInicio + (espacioY * 7), anchoLabel, 28);
                    cuadritoConductor.setBounds(xCuadro, nuevoYInicio + (espacioY * 7), nuevoAnchoCuadro, 32);

                    // Adaptar posición de los botones laterales derechos
                    int nuevoXBotones = 270 + nuevoAnchoForm + 15;
                    int nuevoPosYBotonP = 335 + nuevoAltoForm-42;
                    int posYBotonER= nuevoPosYBotonP-42-10;
                    botonER.setBounds(nuevoXBotones,posYBotonER, 180, 42);
                    botonP.setBounds(nuevoXBotones, nuevoPosYBotonP, 180, 42);

                    revalidate();
                    repaint();
                }
            }
        });

        ImageIcon logoViaUCV = new ImageIcon("res/LogoViaUCV.png");
        Image imgRedimensionada = logoViaUCV.getImage().getScaledInstance(160, -1, Image.SCALE_SMOOTH); 
        ImageIcon logoRedimensionado = new ImageIcon(imgRedimensionada); 
        JLabel logo = new JLabel(logoRedimensionado);   
        int altoLogo = logoRedimensionado.getIconHeight();  
        logo.setBounds(20, 20, 160, altoLogo);    
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

        getRootPane().setDefaultButton(botonP);
    }

    public BotonUtil getBotonFlechaI(){ 
        return botonFlechaI; 
    }

    public BotonUtil getBotonFlechaD(){
        return botonFlechaD; 
    }

    public BotonUtil getBotonP(){ 
        return botonP;
    }
    
    public BotonUtil getEliminarRuta(){ 
        return botonER;
    }
    
    public CampoTextoUtil getCuadritoPlaca(){ 
        return cuadritoPlaca;
    }
    
    public CampoTextoUtil getCuadritoTipoRuta(){ 
        return cuadritoTipoRuta;
    }

    public CampoTextoUtil getCuadritoDestino(){
        return cuadritoDestino; 
    }

    public CampoTextoUtil getCuadritoPtoPartida(){ 
        return cuadritoPtoPartida; 
    }

    public CampoTextoUtil getCuadritoPtoLlegada(){ 
        return cuadritoPtoLlegada; 
    }

    public CampoTextoUtil getCuadritoDia(){
        return cuadritoDia; 
    }

    public CampoTextoUtil getCuadritoHora(){
        return cuadritoHora; 
    }

    public CampoTextoUtil getCuadritoConductor(){ 
        return cuadritoConductor; 
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario admin = new Usuario("01236547", "adminprueba", "correo@gmail.com", "Administrador", "admin12", 0.0);
            Itinerario vista = new Itinerario(admin);
            new Controlador_Itinerario(vista);
            vista.setVisible(true);
        });
    }
}