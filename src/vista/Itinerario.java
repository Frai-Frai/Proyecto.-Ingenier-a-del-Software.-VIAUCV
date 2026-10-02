package vista;

import modelo.*;

import java.io.File;
import javax.swing.border.EmptyBorder;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.awt.geom.RoundRectangle2D;
import java.time.DayOfWeek;
import javax.swing.*;
import java.awt.*;
import util.*;

public class Itinerario extends JFrame {
    
    private BotonUtil botonP, botonFlechaD, botonFlechaI;
    private JPanel panelPines;
   // private JLabel  CerrarS, placa, tipoRuta, destino, ptollegada, ptosalida, conductor;
    private JLabel  opPlanificar, opRegistrarP, opPDiarios, opGestionU, opGenR, opCerrarS;
    private CampoTextoUtil cuadritoPlaca, cuadritoTipoRuta, cuadritoDestino, cuadritoPtoLlegada, cuadritoPtoPartida, cuadritoHora, cuadritoDia,cuadritoConductor;
    //private CampoContrasenaUtil cuadritoContraseña, cuadritoConfirmar;

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
        setSize(1000,750);
        setMinimumSize(new Dimension(950,680)); //para que no se achique menos de esto
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color (0xE1F5FE));

        Font    fuenteT =  League("res/LeagueSpartan-Bold.otf", 22, Font.BOLD); // fuentes y asi
        Font    fuenteSubT = new Font("res/GlacialIndifference-Bold.otf", Font.BOLD, 16); // tipografias
        Font    fuenteNormal = fuenteGlacial("res/GlacialIndifference-Regular.otf", 14, Font.PLAIN); 
        Font    fuenteLetras = fuenteGlacial("res/GlacialIndifference-Regular.otf", 16, Font.PLAIN);
        Font    fuenteLink = fuenteGlacial("res/GlacialIndifference-Bold.otf", 16, Font.BOLD);
        Color   azulCuadros = new Color(0x0D47A1); // para el cuadro de crear cuenta
        Color   azulPastelC = new Color(0xBBDEFB); // para el cuadro del form 
        Color   Colormenu = new Color(214, 233, 245);
        Color   Colorwelcome = new Color (154, 195, 220);
               // IrAInicio = new JLabel();

        //panelcito de arriba de welcome
        MiniVentanaUtil Bienvenida = new MiniVentanaUtil(30, Colorwelcome, 750, 120, false, Colorwelcome);
        Bienvenida.setBounds(270,20,730,120);
        add(Bienvenida);

        //lo que dentro del panel
        String nombreAdmin = admin.getNombreUsuario();
        JLabel saludo = new JLabel("¡Hola, administrador(a) " + nombreAdmin + "!");
        saludo.setFont(fuenteT);
        saludo.setForeground(Color.WHITE);
        saludo.setBounds(30,30,500,30);
        Bienvenida.add(saludo);

        JLabel subtitulo = new JLabel("¡Sigue manteniendo todo en control!");
        subtitulo.setFont(fuenteSubT);
        subtitulo.setForeground(Color.WHITE);
        subtitulo.setBounds(30, 65, 500, 20);
        Bienvenida.add(subtitulo);

        //menu de la izq
        MiniVentanaUtil menuIzq = new MiniVentanaUtil(20, Colormenu, 240, 665, false, null);
        menuIzq.setLayout(null);
        menuIzq.setBounds(20, 20, 240, 665);
        add(menuIzq);

        ImageIcon logoViaUCV= new ImageIcon("res/LogoViaUCV.png");
        Image imgRedimensionada= logoViaUCV.getImage().getScaledInstance(160, -1, Image.SCALE_SMOOTH); //el -1 para omitir el alto por ahora
        ImageIcon logoRedimensionado= new ImageIcon(imgRedimensionada); //redimensiono la imagen
        JLabel logo= new JLabel(logoRedimensionado);   //creo el "texto"
        int alto= logoRedimensionado.getIconHeight();  //calculo el alto de la imagen segun el ancho que coloque
        logo.setBounds(20,20,160,alto);    // posicion personalizada
        menuIzq.add(logo);
        
        //Opciones del menu
        opPlanificar = new JLabel("Planificar Itinerario");
        opPlanificar.setBounds(50,150,180,30);
        new TextosInteractivosUtil(opPlanificar, "Planificar Itinerario", Color.DARK_GRAY, azulCuadros, fuenteLink);
        menuIzq.add(opPlanificar);
        
        opRegistrarP = new JLabel("Registrar Personal");
        opRegistrarP.setBounds(50,200,180,30);
        new TextosInteractivosUtil(opRegistrarP, "Registrar Personal", Color.DARK_GRAY, azulCuadros, fuenteLink);
        menuIzq.add(opRegistrarP);
        
        opPDiarios = new JLabel("Pasajeros Diarios");
        opPDiarios.setBounds(50,250,180,30);
        new TextosInteractivosUtil(opPDiarios, "Pasajeros Diarios", Color.DARK_GRAY, azulCuadros, fuenteLink);
        menuIzq.add(opPDiarios);

        opGestionU = new JLabel("Gestión de Unidades");
        opGestionU.setBounds(50,300,180,30);
        new TextosInteractivosUtil(opGestionU, "Gestión de Unidades", Color.DARK_GRAY, azulCuadros, fuenteLink);
        menuIzq.add(opGestionU);

        opGenR = new JLabel("Generar Reporte");
        opGenR.setBounds(50,350,180,30);
        new TextosInteractivosUtil(opGenR, "Generar Reporte", Color.DARK_GRAY, azulCuadros, fuenteLink);
        menuIzq.add(opGenR);

        opCerrarS = new JLabel("Cerrar Sesión");
        opCerrarS.setBounds(50,450,180,30);
        new TextosInteractivosUtil(opCerrarS, "Cerrar Sesión", Color.DARK_GRAY, azulCuadros, fuenteLink);
        menuIzq.add(opCerrarS);

        //lista de rutas en horizontal

        MiniVentanaUtil panelRutas = new MiniVentanaUtil(20, new Color(245, 245, 245), 730, 120, true, Colorwelcome);
        panelRutas.setBounds(270, 160, 730, 120);
        panelRutas.setLayout(null);
        add(panelRutas);

        JLabel tituloSuperior = new JLabel("Itinerario Semanal", SwingConstants.CENTER);
        tituloSuperior.setFont(fuenteLetras);
        tituloSuperior.setForeground(azulCuadros);
        tituloSuperior.setBounds(0,10,730,20);
        panelRutas.add(tituloSuperior);

        JPanel lineaDivisoria = new JPanel();
        lineaDivisoria.setBackground(Color.BLACK);
        lineaDivisoria.setBounds(30,35,670,1);
        panelRutas.add(lineaDivisoria);
        
        botonFlechaI = new BotonUtil("<", Color.WHITE, Color.BLACK,12, fuenteT, 40, 40);
        botonFlechaI.setBounds(10,55,40,40);
        panelRutas.add(botonFlechaI);

        botonFlechaD = new BotonUtil(">", Color.WHITE, Color.BLACK, 12, fuenteT, 40, 40);
        botonFlechaD.setBounds(688,55,40,40);
        panelRutas.add(botonFlechaD);

        panelPines = new JPanel(null); //donde van las rutas, se crea sin nada pq en el contendedor se va a rellenar
        panelPines.setBounds(60,40,610,70);
        panelPines.setOpaque(false); // transaparente para q se vea lo blanco de atras
        panelRutas.add(panelPines);
        
        //COMPLETAR ESTO

        MiniVentanaUtil panelForm = new MiniVentanaUtil(30, Color.WHITE, 750, 390, true, azulCuadros);
        panelForm.setBounds(270,300,525,380);
        panelForm.setLayout(null);
        add(panelForm);
        
        //Saber el dia de la semana

        LocalDate hoy = LocalDate.now();
        LocalDate inicioS = hoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));//ver que dia es hoy y buscar la fecha del lunes de esa semana
        LocalDate finS = inicioS.plusDays(6); //ver que dia es hoy y sumarle 6 para saber el dia que termina esa semana
       
        JLabel Semana = new JLabel("Semana: " + inicioS + " - " + finS);
        Semana.setBounds(30,10,300,30);
        Semana.setFont(fuenteSubT);
        Semana.setForeground(Color.BLACK);//color
        panelForm.add(Semana);

        //Campos internos

        //placa
        JLabel placa = new JLabel("Transporte Asignado (Placa): ");
        placa.setBounds(30,55,200,30);
        placa.setFont(fuenteLetras);
        panelForm.add(placa);

        cuadritoPlaca = new CampoTextoUtil(10, azulCuadros, 300, 30, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoPlaca.setBounds(250,55,240,32);
        panelForm.add(cuadritoPlaca);

        //tipo ruta
        JLabel Tpruta = new JLabel("Tipo de Ruta: ");
        Tpruta.setBounds(30,95,200,30);
        Tpruta.setFont(fuenteLetras);
        panelForm.add(Tpruta);

        cuadritoTipoRuta = new CampoTextoUtil(10, azulCuadros, 300, 30, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoTipoRuta.setBounds(250,95,240,32);
        panelForm.add(cuadritoTipoRuta);

        //pto de llegada
        JLabel destino = new JLabel("Destino: ");
        destino.setBounds(30,135,200,30);
        destino.setFont(fuenteLetras);
        panelForm.add(destino);

        cuadritoDestino = new CampoTextoUtil(10, azulCuadros, 300, 30, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoDestino.setBounds(250,135,240,32);
        panelForm.add(cuadritoDestino);

        //pto de partida

        JLabel ptoPartida = new JLabel("Punto de Partida: ");
        ptoPartida.setBounds(30,175,200,30);
        ptoPartida.setFont(fuenteLetras);
        panelForm.add(ptoPartida);

        cuadritoPtoPartida = new CampoTextoUtil(10, azulCuadros, 300, 30, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoPtoPartida.setBounds(250,175,240,32);
        panelForm.add(cuadritoPtoPartida);

        //pto de llegada
        JLabel ptollegada = new JLabel("Punto de Llegada: ");
        ptollegada.setBounds(30,215,200,30);
        ptollegada.setFont(fuenteLetras);
        panelForm.add(ptollegada);

        cuadritoPtoLlegada = new CampoTextoUtil(10, azulCuadros, 300, 30, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoPtoLlegada.setBounds(250,215,240,32);
        panelForm.add(cuadritoPtoLlegada);

        //Hora
        JLabel dia = new JLabel("Día de la Semana: ");
        dia.setBounds(30,255,200,30);
        dia.setFont(fuenteLetras);
        panelForm.add(dia);

        cuadritoDia = new CampoTextoUtil(10, azulCuadros, 300, 30, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoDia.setBounds(250,255,240,30);
        panelForm.add(cuadritoDia);

        //Hora
        JLabel hora = new JLabel("Hora: ");
        hora.setBounds(30,295,200,30);
        hora.setFont(fuenteLetras);
        panelForm.add(hora);

        cuadritoHora = new CampoTextoUtil(10, azulCuadros, 300, 30, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoHora.setBounds(250,295,240,30);
        panelForm.add(cuadritoHora);
        
        //conductor
        JLabel conductor = new JLabel("Conductor Asignado: ");
        conductor.setBounds(30,332,200,30);
        conductor.setFont(fuenteLetras);
        panelForm.add(conductor);

        cuadritoConductor = new CampoTextoUtil(10, azulCuadros, 300, 30, Color.WHITE, Color.BLACK, fuenteNormal);
        cuadritoConductor.setBounds(250,335,240,32);
        panelForm.add(cuadritoConductor);

        botonP = new BotonUtil("Subir itinerario", azulCuadros, Color.WHITE, 15, fuenteSubT, 180, 40);
        botonP.setBounds(805, 635, 180, 45);
        add(botonP);

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
          //  Usuario admin = new Usuario(null);
            Usuario admin = new Usuario("01236547", "adminprueba", "correo@gmail.com", "Administrador", "admin12", 0.0);
            new Itinerario(admin).setVisible(true);
        });
    }

}   
    


