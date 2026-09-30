package controlador;
import vista.*;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Controlador_RecuperarCont {

    private RecuperarCont miniVentana;
    private int pasoActual = 1; // para cambiar de codigo, a nueva clave y asi
    private String codigoOTP;
    
    public Controlador_RecuperarCont(RecuperarCont miniVentana){
        this.miniVentana = miniVentana;

        /*this.miniVentana.getBotonAceptar().addActionListener(e -> {
            procesarCambioClave();
        });*/

        this.miniVentana.getBotonAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarPaso();
            }
        });
    }

    private String generaraOTP(){
        Random codigo = new Random();
        int num = 100000 + codigo.nextInt(900000);
        return String.valueOf(num);
    }

    private void procesarPaso(){
        if(pasoActual == 1){
            //String correo = 
        }
    }

    private void procesarCambioClave() {
        //System.out.println("Validando campos y cambiando contraseña...");
        
        // Aquí luego va la lógica (comparar si las claves son iguales, etc.)
        
        // Al terminar, cerramos la mini ventana automáticamente
        this.miniVentana.dispose(); 
    }

}
