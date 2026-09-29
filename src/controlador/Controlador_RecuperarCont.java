package controlador;
import vista.*;

public class Controlador_RecuperarCont {

    private RecuperarCont miniVentana;

    public Controlador_RecuperarCont(RecuperarCont miniVentana){
        this.miniVentana = miniVentana;

        this.miniVentana.getBotonAceptar().addActionListener(e -> {
            procesarCambioClave();
        });
    }

    private void procesarCambioClave() {
        //System.out.println("Validando campos y cambiando contraseña...");
        
        // Aquí luego va la lógica (comparar si las claves son iguales, etc.)
        
        // Al terminar, cerramos la mini ventana automáticamente
        this.miniVentana.dispose(); 
    }
}
