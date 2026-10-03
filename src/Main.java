import controlador.Controlador_Inicio;
import vista.Inicio;
public class Main {
    public static void main(String[] args) {
        
        Inicio pantallaInicio = new Inicio();
        Controlador_Inicio controlador = new Controlador_Inicio(pantallaInicio);
        pantallaInicio.setVisible(true);
       
    }  
}
