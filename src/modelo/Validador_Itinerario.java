package modelo;

public class Validador_Itinerario {
    private ItinerarioDAO itinerarioDao;
    private UnidadesDAO unidadesDao;

    public Validador_Itinerario(ItinerarioDAO itinerarioDao, UnidadesDAO unidadesDao, UsuarioDAO usuarioDao) {
        this.itinerarioDao = itinerarioDao;
        this.unidadesDao = unidadesDao;
    }

    public String validarItinerario(ItinerarioModelo itinerario) {
        //Todos los campos vacios
        if(itinerario.getPlacaAsignada().trim().isEmpty() && itinerario.getDestino().trim().isEmpty() && itinerario.getPuntoPartida().trim().isEmpty() && itinerario.getPuntoLLegada().trim().isEmpty() && itinerario.getConductor().trim().isEmpty()) {
            return "Por favor, complete todos lo campos del formulario.";
        }  

        StringBuilder errores= new StringBuilder();

        //placa vacia, caracteres que no corresponde, longitud invalida
        if(itinerario.getPlacaAsignada().trim().isEmpty()){
            errores.append("- El campo de placa no puede estar vacío.\n");
        }else if (!itinerario.getPlacaAsignada().trim().matches("^[a-zA-Z0-9]+$")) {
            errores.append("- Error: Placa con caracteres no permitidos.\n");
        }

        //destino vacio, formato invalido
        if(itinerario.getDestino().trim().isEmpty()){
            errores.append("- El campo de destino no puede estar vacío.\n");
        }else if(!itinerario.getDestino().trim().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s-]+$")){
            errores.append("- El destino no permite caracteres especiales (Excepto guiones y espacios).\n");
        }

        //punto de partida vacio, fromato invalido (digitos y caracteres especiales)
        if(itinerario.getPuntoPartida().trim().isEmpty()){
            errores.append("- El campo de punto de partida no puede estar vacío.\n");
        }else if(!itinerario.getPuntoPartida().trim().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s-]+$")){
            errores.append( "- El punto de partida no permite caracteres especiales (Excepto guiones y espacios).\n");
        }

        //punto de llegada vacio, formato invalido (digitos y caracteres especiales)
        if(itinerario.getPuntoLLegada().trim().isEmpty()){
            errores.append("- El campo de punto de llegada no puede estar vacío.\n");
        }else if(!itinerario.getPuntoLLegada().trim().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s-]+$")){
            errores.append( "- El punto de llegada no permite caracteres especiales (Excepto guiones y espacios).\n");
        }

        //conductor vacio (cedula), formato invalido (solo numeros)
        if(itinerario.getConductor().trim().isEmpty()){
            errores.append("- El campo de conductor no puede estar vacío.\n");
        }else if(!itinerario.getConductor().trim().matches("\\d+")){
            errores.append("- La cédula del conductor solo debe contener números.\n");
        }


        if(errores.length()>0){
            return errores.toString();
        }

        //despues de llenar el formulario con los datos correctos
        if (unidadesDao.buscarPorPlaca(itinerario.getPlacaAsignada()) == null) {
            return "- La placa asignada no existe en la base de datos.";
        }

        //si la unidad no esta activa
        if(!unidadesDao.estaActivo(itinerario.getPlacaAsignada())){
            return "- La unidad ingresada no se encuentra activa.";
        }

        UsuarioDAO usuarioDao = new UsuarioDAO();

        //el conductor no existe en la base de datos de la pagina
        if(usuarioDao.buscarPorCedula(itinerario.getConductor()) == null){
            return "- La cédula del conductor no existe en la base de datos.";
        }

        //el itinerario solapa con otro itinerario existente
        if(itinerarioDao.itinerarioSolapado(itinerario)){
            return "- El itinerario se solapa con otro itinerario existente.";
        }

        return "Ruta válida";
    }

}
