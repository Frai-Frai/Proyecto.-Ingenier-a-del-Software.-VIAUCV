package vista;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import util.*;
import modelo.*;

public class EliminarRuta extends JDialog {
    private BotonUtil botonElim, botonCancelar;
    private List <JCheckBox> listaSeleccionadas;
    private List <ItinerarioModelo> listaRutas;

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

    public EliminarRuta(JFrame pantallaIT, List <ItinerarioModelo> rutasRegistradas){
        super(pantallaIT, "Seleccionar rutas", true);

        this.listaRutas= rutasRegistradas;
        this.listaSeleccionadas= new ArrayList<>();

        setSize(600, 550);
        setResizable(false);
        setLocationRelativeTo(pantallaIT); // centra la ventanita encima del itinerario
        setLayout(new BorderLayout());

        Font    fuenteT =  League("res/LeagueSpartan-Bold.otf", 20, Font.BOLD); // fuentes y asi
        Font    fuenteSubT = new Font("res/GlacialIndifference-Bold.otf", Font.BOLD, 16); // tipografias
        Font    fuenteLetras = fuenteGlacial("res/GlacialIndifference-Regular.otf", 16, Font.PLAIN);
        
        Color   azulCuadros = new Color(0x0D47A1); // para el cuadro de crear cuenta
        Color   azulPastelC = new Color(0xBBDEFB); // para el cuadro del form 
        Color   Colorwelcome = new Color (154, 195, 220);
        
        getContentPane().setBackground(azulPastelC);

        MiniVentanaUtil panelFondo = new MiniVentanaUtil(30, azulPastelC, 400, 420, true, Colorwelcome);
        panelFondo.setBounds(15,20,400,420);
        panelFondo.setLayout(null);
        getContentPane().add(panelFondo, BorderLayout.CENTER);

        JLabel titulo = new JLabel("Seleccione rutas a eliminar:");
        titulo.setFont(fuenteT);
        titulo.setForeground(azulCuadros);
        titulo.setBounds(50,30,480,40);
        panelFondo.add(titulo);

        //cuadraditos en donde se selecciona

        JPanel panelSelect = new JPanel();
        panelSelect.setLayout(new BoxLayout(panelSelect, BoxLayout.Y_AXIS));
        panelSelect.setBackground(Color.WHITE);
        panelSelect.setBorder(new EmptyBorder(10,10,10,10));

        if(rutasRegistradas != null && !rutasRegistradas.isEmpty()){
            for(ItinerarioModelo ruta : rutasRegistradas){
                //lo que se muestra en checkout
                String infoRuta = String.format("Placa: %s| Ruta: %s. %s | De %s a %s | %s %s | Conductor: %s",
                ruta.getPlacaAsignada(), ruta.getTipoRuta(), ruta.getDestino(), ruta.getPuntoPartida(), ruta.getPuntoLLegada(), ruta.getDia(), ruta.getHora(), ruta.getConductor()
                );
                JCheckBox select = new JCheckBox(infoRuta);
                select.setBackground(Color.WHITE);
                select.setFont(fuenteLetras);
                select.setFocusPainted(false);

                //checkbox
                listaSeleccionadas.add(select);
                panelSelect.add(select);
                panelSelect.add(Box.createRigidArea(new Dimension(0,8)));

            }
        }else{
            JLabel nada = new JLabel("No hay rutas registradas.");
            nada.setFont(fuenteSubT);
            panelSelect.add(nada);
        }


        //barrita para desplazar por si hay muchas rutas

        JScrollPane scroll = new JScrollPane(panelSelect);
        scroll.setBounds(50,90,480,300);
        scroll.setBorder(BorderFactory.createLineBorder(azulPastelC, 2));
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panelFondo.add(scroll);

        //botones

        botonElim = new BotonUtil("Eliminar", azulCuadros, Color.WHITE, 14, fuenteSubT, 150, 40);
        botonElim.setBounds(80, 430, 150, 40);
        panelFondo.add(botonElim);

        botonCancelar = new BotonUtil("Cancelar", azulCuadros, Color.WHITE, 14, fuenteSubT, 150, 40);
        botonCancelar.setBounds(320,430,160,45);
        botonCancelar.addActionListener(e-> dispose()); //cierra 

        panelFondo.add(botonElim);
        panelFondo.add(botonCancelar);

    }

    public List<ItinerarioModelo> getRutasSeleccionadas(){
        List<ItinerarioModelo>  seleccionadas= new ArrayList<>();
            for(int i=0; i<listaSeleccionadas.size();i++){
                if(listaSeleccionadas.get(i).isSelected()){
                    seleccionadas.add(listaRutas.get(i));
                }
            }
        return seleccionadas;
    }

    public BotonUtil getbotonElim(){ 
        return botonElim; 
    }

    public BotonUtil getbotonCancelar(){ 
        return botonCancelar; 
    }
}
