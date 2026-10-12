package modelo;

public class Validador_Porcentajes {
    PorcentajeTarifasDAO porcentajeDAO;

    public Validador_Porcentajes(PorcentajeTarifasDAO pDAO){
        this.porcentajeDAO=pDAO;
    }

    public String validarPorcentaje(String pEstudiante, String pEmpleado){

        if(pEstudiante.isEmpty() && pEmpleado.isEmpty()){
            return "Por favor, ingrese la escala del cobro solidario para cada usuario.";
        }

        StringBuilder errores= new StringBuilder();

        if(pEstudiante.isEmpty()){
            errores.append("- El campo de estudiante no puede estar vacío (Si no hay cambios, conserve el valor anterior).\n");
        }else if(!pEstudiante.trim().matches("\\d+(\\.\\d+)?")){
            errores.append("- El porcentaje solo debe contener valores numéricos.\n");
        }else if(!pEstudiante.isEmpty()){
            double porcentaje= Double.parseDouble(pEstudiante.trim());

            if(porcentaje<10.0 || porcentaje>20.0){
                errores.append("- Ingrese un porcentaje entre el rango (10-20).\n");
            }


        }

        if(pEmpleado.isEmpty()){
            errores.append("- El campo de empleado no puede estar vacío (Si no hay cambios, conserve el valor anterior).\n");
        }else if(!pEmpleado.trim().matches("\\d+(\\.\\d+)?")){
            errores.append("- El porcentaje solo debe contener valores numéricos (o '.' para los decimales).\n");//formato double
        }else if(!pEmpleado.isEmpty()){
            double porcentaje= Double.parseDouble(pEmpleado.trim());

            if(porcentaje<30.0 || porcentaje>60){
                errores.append("- Ingrese un porcentaje entre el rango (30-60).\n");
            }
        }

        if(errores.length()>0){
            return errores.toString();
        }


        return "Porcentaje válido";
    }
    
}
