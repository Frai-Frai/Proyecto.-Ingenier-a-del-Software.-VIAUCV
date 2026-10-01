package vista;
import javax.swing.*;
import java.awt.*;
import java.io.File;

import util.*;

public class RecuperarCont extends JDialog{

    private CampoTextoUtil cuadroCedula;
    private CampoTextoUtil cuadroOTP;
    private CampoContrasenaUtil cuadroNewClave;
    private CampoContrasenaUtil cuadroConfClave;
    private BotonUtil botonAceptar;

    private JLabel textoC, textoOTP, textoNC, textoCNC;
    private MiniVentanaUtil panelForm;
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

        panelForm = new MiniVentanaUtil(30, ColorVentanaC, 380, 430, true, ColorVentanaC);
        panelForm.setBounds(0,0,380,430);
        panelForm.setLayout(null);
        add(panelForm);

        JLabel textoT = new JLabel ("Restablecer Contraseña:", SwingConstants.CENTER);
        textoT.setFont(fuenteTitle);
        textoT.setBounds(0,20,380,30);
        panelForm.add(textoT);

        //SE MUESTRA DE 1ERO
        //texto con Ingrese usuario y cedula
        textoC = new JLabel ("Ingrese Cédula:");
        textoC.setFont(FuenteTexto);
        textoC.setBounds(40,70,380,20);
        panelForm.add(textoC);

        //cuadro/campo en donde lo puede escribir
        cuadroCedula = new CampoTextoUtil(15, azulPastelC, 300, 25, azulPastelC, Color.darkGray, FuentesCampos);
        cuadroCedula.setBounds(40,95,300,40);
        panelForm.add(cuadroCedula);

        //SE MUESTRA DE 2DO 
        textoOTP = new JLabel("Ingrese el código de 6 dígitos:");
        textoOTP.setFont(FuenteTexto);
        textoOTP.setBounds(40, 110, 300, 20);
        textoOTP.setVisible(false); //Para que este oculto al inicio
        panelForm.add(textoOTP);

        cuadroOTP = new CampoTextoUtil(15, azulPastelC, 300, 25, azulPastelC, Color.darkGray, FuentesCampos);
        cuadroOTP.setBounds(40, 130, 300, 40);
        cuadroOTP.setVisible(false);
        panelForm.add(cuadroOTP);

        //SE MUESTRAN DE 3ERO 
        //texto con Ingrese nueva cont
        textoNC = new JLabel ("Nueva Contraseña:");
        textoNC.setFont(FuenteTexto);
        textoNC.setBounds(40, 170, 300, 20);
        textoNC.setVisible(false); //Para que este oculto al inicio
        panelForm.add(textoNC);

        //cuadro/campo en donde lo puede escribir
        cuadroNewClave = new CampoContrasenaUtil(15, azulPastelC, 300, 25, azulPastelC, Color.darkGray, FuentesCampos);
        cuadroNewClave.setBounds(40,190,300,40);
        cuadroNewClave.setVisible(false); //Para que este oculto al inicio
        panelForm.add(cuadroNewClave);

        //texto con confirme la nueva cont
        textoCNC = new JLabel ("Confirmar Contraseña:");
        textoCNC.setFont(FuenteTexto);
        textoCNC.setBounds(40, 240, 300, 20);
        textoCNC.setVisible(false); //Para que este oculto al inicio
        panelForm.add(textoCNC);

        //cuadro/campo en donde lo puede escribir
        cuadroConfClave = new CampoContrasenaUtil(15, azulPastelC, 300, 25, azulPastelC, Color.darkGray, FuentesCampos);
        cuadroConfClave.setBounds(40, 265, 300, 40);
        cuadroConfClave.setVisible(false); //Para que este oculto al inicio
        panelForm.add(cuadroConfClave);

        //Boton con varios usos
        botonAceptar = new BotonUtil("Enviar Código", azulCuadros, ColorVentanaC, 20, FuenteTexto, 150, 45);
        botonAceptar.setBounds(115, 315, 150, 45);
        panelForm.add(botonAceptar);

        getRootPane().setDefaultButton(botonAceptar); //para que el enter funcione como boton
    }
    
    public void mostrarPaso2(){
        if(textoC != null) textoC.setVisible(false);
        if(cuadroCedula != null) cuadroCedula.setVisible(false);

        if(textoOTP != null) textoOTP.setVisible(true);
        if(cuadroOTP != null) cuadroOTP.setVisible(true);
        if(botonAceptar != null) botonAceptar.setText("Verificar Código");
        
        //para que se vuelvan a mostrat los textos
        panelForm.revalidate();
        panelForm.repaint(); 
    }

    public void mostrarPaso3(){
        if(textoOTP != null) textoOTP.setVisible(false);
        if(cuadroOTP != null) cuadroOTP.setVisible(false);

        if(textoNC != null) textoNC.setVisible(true);
        if(cuadroNewClave != null) cuadroNewClave.setVisible(true);
        if(textoCNC != null) textoCNC.setVisible(true);
        if(cuadroConfClave != null) cuadroConfClave.setVisible(true);
        if(botonAceptar != null) botonAceptar.setText("Actualizar Clave");
        
        //para que se vuelvan a mostrat los textos
        panelForm.revalidate();
        panelForm.repaint();
    }

    public BotonUtil getBotonAceptar(){
        return botonAceptar;
    }

    public CampoTextoUtil getcuadroCedula(){
        return cuadroCedula;
    }

    public CampoTextoUtil getCuadroOTP(){
        return cuadroOTP;
    }

    public CampoContrasenaUtil getCuadroNewC(){
        return cuadroNewClave;
    }

    public CampoContrasenaUtil getCuadroConfNewC(){
        return cuadroConfClave;
    }

}
