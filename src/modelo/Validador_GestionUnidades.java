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
        }else if (!unidad.getPlaca().trim().matches("^([a-zA-Z]+\\d+[a-zA-Z\\d]*|\\d+[a-zA-Z]+[a-zA-Z\\d]*)$")) {
            errores.append("- Error: Placa con caracteres no permitidos.\n");
        }else if(unidad.getPlaca().length() < 3 || unidad.getPlaca().length() > 8) {
            errores.append ("- Error: Longitud de placa inválida.\n");
        }

        //capacidad vacia
        if(unidad.getCapacidad().trim().isEmpty()){
            errores.append("- El campo de capacidad no puede estar vacío.\n");
        }else if(!unidad.getCapacidad().trim().matches("\\d+")){ //si no esta vacia, entonces 
            errores.append("La capacidad no debe contener caracteres no numéricos o especiales.");
        }else{   //Capacida numerica para las comparaciones
            int capacidad;
            capacidad= Integer.parseInt(unidad.getCapacidad().trim());

            //capacidad fuera de rango
            if(capacidad<20 || capacidad>50){
                errores.append("- Ingrese un número de capacidad entre el rango (20-50).\n");
            }
        }

        if(unidad.getModelo().trim().isEmpty()){
            errores.append("- El campo de modelo no puede estar vacío.\n");
        }

        if(errores.length()>0){
            errores.toString();
            return errores.toString();
        }

        //Despues de que todo es correcto en el formulario

        //si ya esta registrada
        if(unidadDao.buscarPorPlaca(unidad.getPlaca())!=null){
            return "- La placa ingresada ya se encuentra registrada en la base de datos.";
        }

        return "Registro exitoso";
    }

}
