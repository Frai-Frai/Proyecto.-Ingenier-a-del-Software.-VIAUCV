import vista.Registro;
import controlador.Controlador_Inicio;
import vista.Inicio;
import vista.InicioSesion;


public class Main {
    public static void main(String[] args) {
        
        Inicio pantallaInicio = new Inicio();
        Controlador_Inicio controlador = new Controlador_Inicio(pantallaInicio);
        pantallaInicio.setVisible(true);
       
    }  
}
