import vista.Registro;
import vista.InicioSesion;
;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenido a ViaUCV");
        Registro interfazR = new Registro();
        interfazR.setVisible(false);

        InicioSesion Inicio = new InicioSesion();
        Inicio.setVisible(true);
    }  
}
