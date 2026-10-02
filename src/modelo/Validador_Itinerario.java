package modelo;

public class Validador_Itinerario {
    private ItinerarioDAO itinerarioDao;
    private UnidadesDAO unidadesDao;

    public Validador_Itinerario(ItinerarioDAO itinerarioDao, UnidadesDAO unidadesDao) {
        this.itinerarioDao = itinerarioDao;
        this.unidadesDao = unidadesDao;
    }

    public String validarItinerario(ItinerarioModelo itinerario) {
        //Todos los campos vacios
        if(itinerario.getPlacaAsignada()==" " && itinerario.getTipoRuta()==" " && itinerario.getDestino()==" " && itinerario.getPuntoPartida()==" " && itinerario.getPuntoLLegada()==" " && itinerario.getDia()==" " && itinerario.getHora()==" " && itinerario.getConductor()==" ") {
            return "Por favor, complete todos lo campos del formulario.";
        }  

        StringBuilder errores= new StringBuilder();

        //placa vacia, caracteres que no corresponde, longitud invalida
        if(itinerario.getPlacaAsignada().trim().isEmpty()){
            errores.append("- El campo de placa no puede estar vacío.\n");
        }else if (!itinerario.getPlacaAsignada().trim().matches("^[a-zA-Z0-9]+$")) {
            errores.append("- Error: Placa con caracteres no permitidos.\n");
        }

        //tipo de rura vacio, formato invalido (no es ninguna de las dos)
        if(itinerario.getTipoRuta().trim().isEmpty()){
            errores.append("- El campo de tipo de ruta no puede estar vacío.\n");
        }else if(!itinerario.getTipoRuta().equalsIgnoreCase("Urbana") && !itinerario.getTipoRuta().equalsIgnoreCase("Extraurbana")){
            errores.append("- El campo de tipo de ruta debe ser 'Urbana' o 'Extraurbana'.\n");
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

        //dia vacio, dia de semana invalido
        if(itinerario.getDia().trim().isEmpty()){
            errores.append("- El campo de día no puede estar vacío.\n");
        }else if(itinerario.getDia()!="Lunes" && itinerario.getDia()!="Martes" && itinerario.getDia()!="Miércoles" && itinerario.getDia()!="Jueves" && itinerario.getDia()!="Viernes"){
            errores.append("- El campo de día debe ser un día de la semana válido (Lunes, Martes, Miércoles, Jueves, Viernes).\n");
        }

        //hora vacia, formato invalido, mayor a 4
        if(itinerario.getHora().trim().isEmpty()){
            errores.append("- El campo de hora no puede estar vacío.\n");
        }else if(!itinerario.getHora().matches("^([01]?\\d|2[0-3]):[0-5]\\d$")){
            errores.append("- El campo de hora debe tener un formato válido (HH:mm).\n");
        }else if(itinerario.getHora().length()>4){
            errores.append("- El campo de hora debe contener solo una hora establecida de una ruta específica.\n");
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

        UsuarioDAO usuarioDao = new UsuarioDAO();;

        //el conductor no existe en la base de datos de la pagina
        if(usuarioDao.buscarPorCedula(itinerario.getConductor()) == null){
            return "- La cédula del conductor no existe en la base de datos.";
        }

        //el itinerario solapa con otro itinerario existente
        if(!itinerarioDao.itinerarioSolapado(itinerario)){
            return "- El itinerario se solapa con otro itinerario existente.";
        }

        return "Ruta válida";
    }

}
