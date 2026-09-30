package controlador;
import vista.*;
import javax.swing.JOptionPane;

import modelo.Usuario;
import modelo.UsuarioDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random; // genera un codigo random para poder cambiar la contraseña

public class Controlador_RecuperarCont {

    private RecuperarCont miniVentana;
    private int pasoActual = 1; // para cambiar de codigo, a nueva clave y asi
    private String codigoOTP;
    private UsuarioDAO infoDao = new UsuarioDAO();

    private Usuario EncontroUser;

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
            String cedula = miniVentana.getcuadroCedula().getText().trim();

            if(cedula.isEmpty()){
                JOptionPane.showMessageDialog(miniVentana, "Por favor ingrese su cédula", "ATENCIÓN", JOptionPane.WARNING_MESSAGE);
                return;
            }

            EncontroUser = infoDao.buscarPorCedula(cedula); // busca esa cedula en la bd a ver si esta registrado en la web
            
            if(EncontroUser == null){ // no encontro la cedula en la bd, el user no existe
                JOptionPane.showMessageDialog(miniVentana, "Usuario no registrado", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //si llegó a aqui es que la cedula si existe en la bd
            //se procede a generar el codigo

            codigoOTP = generaraOTP();

            System.out.println("Enviando código al correo " + EncontroUser.getCorreo() + "...");
            System.out.println("Su código de recuperación es: " + codigoOTP + "\n");

            miniVentana.mostrarPaso2(); // para que pueda ingresar el codigo otp
            pasoActual = 2;
        }else if (pasoActual == 2){
            String codeIngresado = miniVentana.getCuadroOTP().getText().trim();

            if(codeIngresado.equals(codigoOTP)){//el otp ingresado fue el correcto, ahora si deja cambiar la contraseña
                miniVentana.mostrarPaso3();
                pasoActual = 3;
            }
        }else if(pasoActual == 3){
            String NClave = new String(miniVentana.getCuadroNewC().getPassword());
            String confClave = new String(miniVentana.getCuadroConfNewC().getPassword());
            
            if(NClave.trim().isEmpty() || confClave.trim().isEmpty()){
                JOptionPane.showMessageDialog(miniVentana, "Favor ingrese la nueva contraseña, los campos no pueden estar vacíos", "ALERTA", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if(NClave.equals(confClave)){

                EncontroUser.setClave(NClave); // se cambia la clave del user en la bd
                boolean actualizado = infoDao.actualizarDatos(EncontroUser);

                if(actualizado){
                    JOptionPane.showMessageDialog(miniVentana, "Contraseña Actualizada", "ÉXITO", JOptionPane.INFORMATION_MESSAGE);
                    miniVentana.dispose(); //ya se puede cerrar la ventanita
                } else {
                    JOptionPane.showMessageDialog(miniVentana, "Error al intentar cambiar la contraseña, favor intente de nuevo", "ALERTA", JOptionPane.ERROR_MESSAGE);
                }
            
            }else{
                JOptionPane.showMessageDialog(miniVentana, "Las contraseñas no coinciden", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
