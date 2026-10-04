package modelo;

public class Validador_GestionUnidades {
    private UnidadesDAO unidadDao;

    public Validador_GestionUnidades(UnidadesDAO unidadDao){
        this.unidadDao=unidadDao;
    }

    public String validar_RegistroUnidad(UnidadBus unidad){
        //Todos los campos vacios
        if(unidad.getPlaca().trim().isEmpty() && unidad.getModelo().trim().isEmpty() && unidad.getEstado().trim().isEmpty() && unidad.getCapacidad().trim().isEmpty()){
            return "Por favor, complete todos lo campos del formulario.";
        }

        StringBuilder errores= new StringBuilder();

        //placa vacia, caracteres que no corresponde, longitud invalida
        if(unidad.getPlaca().trim().isEmpty()){
            errores.append("- El campo de placa no puede estar vacío.\n");
        }else if (!unidad.getPlaca().trim().matches("^[a-zA-Z0-9]+$")) {
            errores.append("- Error: Placa con caracteres no permitidos.\n");
        }else if(unidad.getPlaca().length() < 3 || unidad.getPlaca().length() > 8) {
            errores.append ("- Error: Longitud de placa inválida.\n");
        }

        //capacidad vacia
        if(unidad.getCapacidad().trim().isEmpty()){
            errores.append("- El campo de capacidad no puede estar vacío.\n");
        }else { //si no esta vacia, entonces 

            //Capacida numerica para las comparaciones
            int capacidad;
            try {
                capacidad= Integer.parseInt(unidad.getCapacidad());
            } catch (NumberFormatException e) {
                return "La capacidad no debe contener caracteres no numéricos o especiales.";
            }

            //capacidad fuera de rango
            if(capacidad<20 || capacidad>36){
                errores.append("- Ingrese un número de capacidad entre el rango (20-36).\n");
            }

        }

        if(errores.length()>0){
            errores.toString();
        }

        //Despues de que todo es correcto en el formulario

        //si ya esta registrada
        if(unidadDao.buscarPorPlaca(unidad.getPlaca())!=null){
            return "- La placa ingresada ya se encuentra registrada en la base de datos.";
        }

        return "Registro exitoso";
    }

}
