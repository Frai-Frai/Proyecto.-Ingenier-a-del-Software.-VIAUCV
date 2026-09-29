package vista;
import javax.swing.*;
import java.awt.*;
import java.io.File;

import util.*;

public class RecuperarCont extends  JDialog{

    private CampoTextoUtil cuadroUser;
    private CampoContrasenaUtil cuadroNewClave;
    private CampoContrasenaUtil cuadroConfClave;
    private BotonUtil botonAceptar;

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

    public RecuperarCont (JFrame PantallaIS){ //PantallaIS, es la ventana padre, tipo de donde viene o se llamo la mini ventanita

        super (PantallaIS, "Recuperar Contraseña", true); //bloque la ventana de al fondo mientras restaura la contraseña
        
        //Config
        Color   azulCuadros = new Color(0x0D47A1); // para el borde
        Color   ColorVentanaC = new Color (0XE3F2FD);
        Color   azulPastelC = new Color(0xBBDEFB); // para el cuadro del form 
        Font    FuenteTexto = fuenteGlacial("res/GlacialIndifference-Regular.otf", 15, Font.BOLD); // Para los texto de encima de los cuadros 
        Font    FuentesCampos = fuenteGlacial("res/GlacialIndifference-Regular.otf", 13, Font.BOLD); //para lo de adentro de los campitos
        Font    fuenteTitle = new Font("Dialog", Font.BOLD, 20); 
        
        setSize(396,420);
        setLocationRelativeTo(PantallaIS);
        getContentPane().setBackground(ColorVentanaC);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //para que el X de la mini ventana funcione
        setLayout(null);

        MiniVentanaUtil panelForm = new MiniVentanaUtil(30, ColorVentanaC, 380, 430, true, ColorVentanaC);
        panelForm.setBounds(0,0,380,430);
        add(panelForm);

        JLabel textoT = new JLabel ("Restablecer Contraseña:", SwingConstants.CENTER);
        textoT.setFont(fuenteTitle);
        textoT.setBounds(0,20,380,30);
        panelForm.add(textoT);

        //texto con Ingrese usuario y cedula
        JLabel textoU = new JLabel ("Ingrese Usuario o Cédula:");
        textoU.setFont(FuenteTexto);
        textoU.setBounds(40,70,380,20);
        panelForm.add(textoU);

        //cuadro/campo en donde lo puede escribir
        cuadroUser = new CampoTextoUtil(15, azulPastelC, 300, 25, azulPastelC, Color.darkGray, FuentesCampos);
        cuadroUser.setBounds(40,95,300,40);
        panelForm.add(cuadroUser);

        //texto con Ingrese nueva cont
        JLabel textoNC = new JLabel ("Nueva Contraseña:");
        textoNC.setFont(FuenteTexto);
        textoNC.setBounds(40, 150, 300, 20);
        panelForm.add(textoNC);

        //cuadro/campo en donde lo puede escribir
        cuadroNewClave = new CampoContrasenaUtil(15, azulPastelC, 300, 25, azulPastelC, Color.darkGray, FuentesCampos);
        cuadroNewClave.setBounds(40,175,300,40);
        panelForm.add(cuadroNewClave);

        //texto con confirme la nueva cont
        JLabel textoCNC = new JLabel ("Confirmar Contraseña:");
        textoCNC.setFont(FuenteTexto);
        textoCNC.setBounds(40, 230, 300, 20);
        panelForm.add(textoCNC);

        //cuadro/campo en donde lo puede escribir
        cuadroConfClave = new CampoContrasenaUtil(15, azulPastelC, 300, 25, azulPastelC, Color.darkGray, FuentesCampos);
        cuadroConfClave.setBounds(40, 255, 300, 40);
        panelForm.add(cuadroConfClave);

        //Boton para guardar nueva clave
        botonAceptar = new BotonUtil("Aceptar", azulCuadros, ColorVentanaC, 20, FuenteTexto, 150, 45);
        botonAceptar.setBounds(115, 315, 150, 45);
        panelForm.add(botonAceptar);
    }
    
    public BotonUtil getBotonAceptar(){
        return botonAceptar;
    }

    public CampoTextoUtil getCuadroUser(){
        return cuadroUser;
    }

    public CampoContrasenaUtil getCuadroNewC(){
        return cuadroNewClave;
    }

    public CampoContrasenaUtil getCuadroConfNewC(){
        return cuadroConfClave;
    }

}
