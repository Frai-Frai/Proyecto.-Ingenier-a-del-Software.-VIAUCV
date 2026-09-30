package modelo;

public class Validador_IniciodeSesion {
    private UsuarioDAO usuarioDAO;

    public Validador_IniciodeSesion(UsuarioDAO u){
        this.usuarioDAO=u;
    }

    public String validarInicioSesion(String campoCedula, String campoClave){

        StringBuilder errores= new StringBuilder();

        //Campos vacios

        //ambos
        if(campoCedula.trim().isEmpty() && campoClave.trim().isEmpty()){
            return "Por favor, complete todos lo campos del formulario.";
        }
        
        //cedula vacia, contrasena llena, contrasena invalida
        if(campoCedula.trim().isEmpty()){
            errores.append("- El campo de cédula no puede estar vacío\n");
        }else if(!campoCedula.trim().matches("\\d+")){
            errores.append("- La cédula solo debe contener números\n");
        }

        //usuario lleno, contrasena vacia, usuario invalido
        if(campoClave.trim().isEmpty()){
            errores.append("- El campo de la contraseña no puede estar vacío\n");
        }

        if(errores.length()>0){
            return errores.toString();

        }

        //si todo es valido, evaluamos con la bd
        Usuario usuario= usuarioDAO.buscarPorCedula(campoCedula.trim());
        
        //si no existe
        if(usuario==null){
            return "- El usuario con la cédula ingresada no se encuentra registrado\n";
        }

        //si la clave es incorrecta
        if(!usuario.getClave().equals(campoClave.trim())){
            return "- La contraseña ingresada es incorrecta\n";
        }

        //si todo salio bien
        return "Ingreso exitoso";

        
    }
}
