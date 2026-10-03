package controlador;

import vista.Registro;
import vista.RegistroUnidades;
import vista.Inicio;
import vista.InicioSesion;
import vista.RecuperarCont;
import modelo.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Controlador_IniciodeSesion {

    private InicioSesion pantallaIS;
    private UsuarioDAO EncontroUser;

    public Controlador_IniciodeSesion(InicioSesion pantallaIS){
        this.pantallaIS = pantallaIS;
        this.EncontroUser = new UsuarioDAO();


        this.pantallaIS.getBotonIngresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
        this.pantallaIS.getlinkRegistarse().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println("¡CLICK DETECTADO EN EL TEXTO!");
                irAlRegistro();
            }
        });

        this.pantallaIS.getEncabezado().getInicio().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                volverAlInicio();
            }
        });

        this.pantallaIS.getRestaurarContrasena().addMouseListener(new MouseAdapter() { //para que el link de rest cont sirva
            @Override
            public void mousePressed(MouseEvent e) {
                abrirRecuperarClave();
            }
        });

        this.pantallaIS.getEncabezado().getSobreNosotros().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mostrarMensajeUs();
            }
        });
    }

    private void iniciarSesion(){
        String User = pantallaIS.getCuadroUser().getText().trim();
        String cont = pantallaIS.getCuadroCont().getText().trim();
        Usuario UserAu = null;
        Validador_IniciodeSesion valida = new Validador_IniciodeSesion(EncontroUser);
        List <Usuario> AllUsers = EncontroUser.obtenerUsuarios();
       
        for( Usuario u : AllUsers){

            if(u.getCedula().equals(User) || u.getNombreUsuario().equals(User)){

                if(u.getClave().equals(cont)){
                UserAu =  u;
                break;
                }
            } 
        }

        String hola = valida.validarInicioSesion(User, cont);

        if(hola.equalsIgnoreCase("Ingreso exitoso.")){
            String rol = UserAu.getRol();

            pantallaIS.dispose(); // cierra la del login y abre la q corresponde
            
            if(rol.equalsIgnoreCase("Administrador")){
                //abre las interfaces del admin, por ahora solo la de itinerario y gestionar unidades
                RegistroUnidades RUnidades = new RegistroUnidades();
                RUnidades.setVisible(true);

            }else if(rol.equalsIgnoreCase("Conductor")){
                //abre las interfaces del conductor

            }else if(rol.equalsIgnoreCase("Estudiante")){
                //abre las interfaces del estudiante
            }else if(rol.equalsIgnoreCase("Docente")){
                //abre las interfaces del profe
            }else if(rol.equalsIgnoreCase("Público General")){
                //abre las interfaces del publico general
            }

        }else{
            JOptionPane.showMessageDialog(pantallaIS, hola, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void irAlRegistro() {
        Registro pantallaR = new Registro();
        new Controlador_Registro(pantallaR); // para que funcione
        pantallaR.setVisible(true);
        pantallaIS.dispose(); // Cerramos Inicio de Sesión
    }

    private void volverAlInicio() {
        Inicio pantallaI = new Inicio();       // Crea la pantalla de home
        new Controlador_Inicio(pantallaI);     // para que funcione
        pantallaI.setVisible(true);          //  La muestra
        pantallaIS.dispose();                  //  Cierra la ventana de Inicio de Sesión 
    }

    private void abrirRecuperarClave() {
        RecuperarCont miniVentana = new RecuperarCont(pantallaIS); //crea la mini ventana
        new Controlador_RecuperarCont(miniVentana); //para que la pantalla al q lo redirija sirva
        miniVentana.setVisible(true); //se muestra
    }

    private void mostrarMensajeUs(){
        JOptionPane.showMessageDialog(pantallaIS, " Fraidis Franco. Correo: fraidisf@gmail.com\n Mariam Battika. Correo: mariammbattikaahochee@gmail.com\n Eliany Morales. Correo: morales.eliany28@gmail.com", "Sobre Nosotros", JOptionPane.INFORMATION_MESSAGE);
    }
}
