package controlador;

import vista.Registro;
import vista.Inicio;
import vista.InicioSesion;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modelo.*;
import javax.swing.JOptionPane;

public class Controlador_Registro{

    private Registro pantallaR;

    public Controlador_Registro(Registro pantallaR){
        this.pantallaR = pantallaR;

        this.pantallaR.getbotonR().addActionListener(e -> {
            registrarUser();
        });

        this.pantallaR.getIrAIniciarS().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                irAInicioSesion();
            }
        });

        this.pantallaR.getEncabezado().getInicio().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                volverAlInicio();
            }
        });

        this.pantallaR.getEncabezado().getSobreNosotros().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mostrarMensajeUs();
            }
        });
        
    }

    private void registrarUser(){
        String nombre = pantallaR.getNombre().getText().trim();
        String correo = pantallaR.getCorreo().getText().trim();
        String cedula = pantallaR.getCedula().getText().trim();
        String contraseña = new String(pantallaR.getContraseña().getPassword());
        String confCont = new String(pantallaR.getConfContraseña().getPassword());
        String rol = pantallaR.getMenu().getSelectedItem().toString();

        UsuarioDAO Userdao = new UsuarioDAO();
        UniversidadDAO Unidao = new UniversidadDAO();
        Validador_Registro valida = new Validador_Registro(Userdao,Unidao);

        //revisa que todos los campos ingresados sean correctos
        String Registrar = valida.validarRegistro(cedula, nombre, correo, rol, contraseña, confCont);

        if(Registrar.equalsIgnoreCase("Registro exitoso.")){ //Si son válidos, se crea el nuevo usuario con la info agg antes

            Usuario nuevoUser = new Usuario(cedula, nombre, correo, rol, contraseña, 0);
            boolean guardadoExitoso = Userdao.anadirUsuario(nuevoUser);

            if(guardadoExitoso){//despues de que le de a registarse lo manda a inicio 
                JOptionPane.showMessageDialog(pantallaR, "Registro Exitoso, ¡Bienvenido a ViaUCV!", "ÉXITO", JOptionPane.INFORMATION_MESSAGE);
                Inicio pantallaI = new Inicio();
                new Controlador_Inicio(pantallaI);
                pantallaI.setVisible(true);
                pantallaR.dispose();
            }else{
                JOptionPane.showMessageDialog(pantallaR, "Se ha presentado un error con el registro, intente de nuevo", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }else{
             JOptionPane.showMessageDialog(pantallaR, Registrar, "ATENCIÓN", JOptionPane.WARNING_MESSAGE);
        }
    
    }

    private void irAInicioSesion() {
        InicioSesion pantallaIS = new InicioSesion();   // Crea la pantalla de home
        new Controlador_IniciodeSesion(pantallaIS);     //para que funcione
        pantallaIS.setVisible(true);                 // La muestra
        pantallaR.dispose();                           // Cierra la de Registro actual
    }

    private void volverAlInicio() {
        Inicio pantallaI = new Inicio();
        new Controlador_Inicio(pantallaI);
        pantallaI.setVisible(true);
        pantallaR.dispose();                     //Cierra la ventana de Registro 
    }

    private void mostrarMensajeUs(){
        JOptionPane.showMessageDialog(pantallaR, " Fraidis Franco. Correo: fraidisf@gmail.com\n Mariam Battika. Correo: mariammbattikaahochee@gmail.com\n Eliany Morales. Correo: morales.eliany28@gmail.com", "Sobre Nosotros", JOptionPane.INFORMATION_MESSAGE);
    } 
}
