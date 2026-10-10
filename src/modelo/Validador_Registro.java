package modelo;

public class Validador_Registro {
    private UsuarioDAO usuarioDAO;
    private UniversidadDAO uniDAO;

    public Validador_Registro(UsuarioDAO u, UniversidadDAO universidadDAO){
        this.usuarioDAO=u;
        this.uniDAO= universidadDAO;
    }

    public String validarRegistro(String campoCedula, String nombre, String correo, String rol, String campoClave, String confirmarClave){

        //Campos vacios
        //todos
        if(campoCedula.trim().isEmpty() && campoClave.trim().isEmpty() && nombre.trim().isEmpty() && rol.trim().isEmpty() && correo.trim().isEmpty() && confirmarClave.trim().isEmpty()){
            return "Por favor, complete todos lo campos del formulario.";
        }

        StringBuilder errores= new StringBuilder();

        if(nombre.trim().isEmpty()){
            errores.append("- El campo de nombre y apellido no puede estar vacío.\n");
        }else if(!nombre.trim().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")){
            errores.append( "- El nombre de usuario no permite caracteres especiales.\n");
        }

        //correo vacio, correo formato incorrecto
        if(correo.trim().isEmpty()){
            errores.append("- El campo de correo no puede estar vacío.\n");
        }else if(!correo.trim().matches("^[a-zA-Z\\d]+@(gmail|hotmail|ciencias|ucv|cienciasucv)\\.com$")){
            errores.append("- El correo ingresado no tiene un formato válido.\n");
        }
        
        
        //cedula vacia, cedula llena, cedula invalida
        if(campoCedula.trim().isEmpty()){
            errores.append("- El campo de cédula no puede estar vacío.\n");
        }else if(!campoCedula.trim().matches("\\d+")){
            errores.append("- La cédula solo debe contener números.\n");
        }else{
            int cedulaNumerica= Integer.parseInt(campoCedula.trim());
            if(cedulaNumerica < 1000000 || cedulaNumerica> 99999999){
            errores.append("- La cédula debe estar dentro del rango [1.000.000 -99.999.999]\n");
            }
        }
        


        //contrasena vacia,por lo menos 6 de length y menor a 20
        if(campoClave.trim().isEmpty()){
            errores.append("- El campo de la contraseña no puede estar vacío.\n");
        }else if(campoClave.trim().length()< 6){
            errores.append("- La contraseña debe tener al menos 6 caracteres.\n");
        }else if(campoClave.trim().length()> 20){
            errores.append("- La contraseña debe ser menor a 20 caracteres.\n");
        }

        if(!confirmarClave.equals(campoClave)){
            errores.append("- Las contraseñas no coinciden.\n");
        };


        if(errores.length()>0){
            return errores.toString();

        }

        //si todo es valido, evaluamos con la bd
        //si no existe
        if(usuarioDAO.buscarPorCedula(campoCedula.trim())!=null){
            return "- El usuario con la cédula ingresada ya se encuentra registrado.\n";
        }

        //si es estudiante, profesor, admin o conductor, se busca en la de la universidad
        if(rol.equalsIgnoreCase("Estudiante") || rol.equalsIgnoreCase("Docente") 
            || rol.equalsIgnoreCase("Administrador") || rol.equalsIgnoreCase("Conductor")){

            Usuario usuarioUCV= uniDAO.buscarPorCedula(campoCedula); //ahora si busca en la BD de la UCV

            //buscar en la BD de la UCV
            if(usuarioUCV==null){
                return "- La cédula no se encuentra registrada en la base de datos de la UCV.\n";
            }

            //rol erroneo
            if(!usuarioUCV.getRol().equalsIgnoreCase(rol)){
                return "- El rol que ha seleccionado ("+rol+") no coincide con su registro en la UCV ("+usuarioUCV.getRol()+").\n";
            }

        }

        //si todo salio bien
        return "Registro exitoso.";
    }
}
