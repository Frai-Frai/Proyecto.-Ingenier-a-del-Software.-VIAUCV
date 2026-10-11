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
        }else if(!pEstudiante.trim().matches("\\d+")){
            errores.append("- El porcentaje solo debe contener valores numéricos.\n");
        }else if(!pEstudiante.isEmpty()){
            int porcentaje= Integer.parseInt(pEstudiante.trim());

            if(porcentaje<10 || porcentaje>20){
                errores.append("- Ingrese un porcentaje entre el rango (10-20).\n");
            }

        }

        if(pEmpleado.isEmpty()){
            errores.append("- El campo de empleado no puede estar vacío (Si no hay cambios, conserve el valor anterior).\n");
        }else if(!pEmpleado.trim().matches("\\d+")){
            errores.append("- El porcentaje solo debe contener valores numéricos.\n");
        }else if(!pEmpleado.isEmpty()){
            int porcentaje= Integer.parseInt(pEmpleado.trim());

            if(porcentaje<30 || porcentaje>60){
                errores.append("- Ingrese un porcentaje entre el rango (30-60).\n");
            }
        }

        if(errores.length()>0){
            return errores.toString();
        }


        return "Porcentaje válido";
    }
    
}
