package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.io.File;
import util.*;
import modelo.*;


public class Porcentajes extends JDialog{

    private CampoTextoUtil cuadroUser, cuadoEmpDoc;
    private JLabel textoU, textoED;
    private BotonUtil botonAceptar;
    private MiniVentanaUtil panelForm;

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

    public Porcentajes(JFrame pantallaIT){
        super(pantallaIT, "Descuentos Usuarios", true);

        //Config
        Color   azulCuadros = new Color(0x0D47A1); // para el borde
        Color   ColorVentanaC = new Color (0XE3F2FD);
        Color   azulPastelC = new Color(0xBBDEFB); // para el cuadro del form 
        Font    FuenteTexto = fuenteGlacial("res/GlacialIndifference-Regular.otf", 15, Font.BOLD); // Para los texto de encima de los cuadros 
        Font    FuentesCampos = fuenteGlacial("res/GlacialIndifference-Regular.otf", 13, Font.BOLD); //para lo de adentro de los campitos
        Font    fuenteTitle = new Font("Dialog", Font.BOLD, 20); 
        
        setSize(396,420);
        setLocationRelativeTo(pantallaIT);
        getContentPane().setBackground(ColorVentanaC);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //para que el X de la mini ventana funcione
        setLayout(null);

        panelForm = new MiniVentanaUtil(30, ColorVentanaC, 380, 430, true, ColorVentanaC);
        panelForm.setBounds(0,0,380,430);
        panelForm.setLayout(null);
        add(panelForm);

        JLabel textoT = new JLabel ("Ingrese Descuentos a Aplicar:", SwingConstants.CENTER);
        textoT.setFont(fuenteTitle);
        textoT.setBounds(0,20,380,30);
        panelForm.add(textoT);

        //texto con Ingrese usuario y cedula
        textoU = new JLabel ("Descuento Estudiantes");
        textoU.setFont(FuenteTexto);
        textoU.setBounds(40,70,380,20);
        panelForm.add(textoU);

        textoU = new JLabel ("- 10% al 20% -");
        textoU.setFont(FuenteTexto);
        textoU.setBounds(40,90,380,20);
        panelForm.add(textoU);

        //cuadro/campo en donde lo puede escribir
        cuadroUser = new CampoTextoUtil(15, azulPastelC, 300, 25, azulPastelC, Color.darkGray, FuentesCampos);
        cuadroUser.setBounds(40,110,300,40);
        panelForm.add(cuadroUser);

        //texto con Ingrese nueva cont
        textoED = new JLabel ("Descuento Empleados y Docentes");
        textoED.setFont(FuenteTexto);
        textoED.setBounds(40, 165, 300, 20);
        panelForm.add(textoED);

        textoED = new JLabel ("- 30% al 60% -");
        textoED.setFont(FuenteTexto);
        textoED.setBounds(40, 185, 300, 20);
        panelForm.add(textoED);

        //cuadro/campo en donde lo puede escribir
        cuadoEmpDoc = new CampoTextoUtil(15, azulPastelC, 300, 25, azulPastelC, Color.darkGray, FuentesCampos);
        cuadoEmpDoc.setBounds(40,205,300,40);
        panelForm.add(cuadoEmpDoc);

        botonAceptar = new BotonUtil("Aceptar", azulCuadros, ColorVentanaC, 20, FuenteTexto, 150, 45);
        botonAceptar.setBounds(115, 315, 150, 45);
        panelForm.add(botonAceptar);

        getRootPane().setDefaultButton(botonAceptar); //para que el enter funcione como boton

        
    }

    public BotonUtil getBotonAceptar(){
        return botonAceptar;
    }

    public CampoTextoUtil getcuadroUser(){
        return cuadroUser;
    }

    public CampoTextoUtil getCuadroEmpyDocente(){
        return cuadoEmpDoc;
    }

}
