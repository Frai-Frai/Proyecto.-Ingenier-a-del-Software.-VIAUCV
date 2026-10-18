package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import modelo.Validador_Porcentajes;


public class ValidarPorcentajeTest {

    @Test 
    public  void campoEstudianteVacio(){
        Validador_Porcentajes validar=new Validador_Porcentajes(null);
        String resultado= validar.validarPorcentaje("", "35");
        assertEquals("- El campo de estudiante no puede estar vacío (Si no hay cambios, conserve el valor anterior).\n",resultado);
    }

    @Test 
    public void campoEmpleadoVacio(){
        Validador_Porcentajes validar=new Validador_Porcentajes(null);
        String resultado= validar.validarPorcentaje("10", "");
        assertEquals("- El campo de empleado no puede estar vacío (Si no hay cambios, conserve el valor anterior).\n",resultado);
    }

    @Test 
    public void campoEstudianteFormatoInvalido(){
        Validador_Porcentajes validar=new Validador_Porcentajes(null);
        String resultado= validar.validarPorcentaje("16f", "35");
        assertEquals("- El porcentaje solo debe contener valores numéricos (o '.' para los decimales).\n", resultado);
    }

    @Test 
    public void campoEmpleadoFormatoInvalido(){
        Validador_Porcentajes validar=new Validador_Porcentajes(null);
        String resultado= validar.validarPorcentaje("16", "30.o");
        assertEquals("- El porcentaje solo debe contener valores numéricos (o '.' para los decimales).\n", resultado);
    }

    @Test
    public void campoEstudianteFueraDeRango(){
        Validador_Porcentajes validar=new Validador_Porcentajes(null);
        String resultado= validar.validarPorcentaje("30", "35");
        assertEquals("- Ingrese un porcentaje entre el rango (10-20).\n", resultado);      
    }

    @Test
    public void campoEmpleadoFueraDeRango(){
        Validador_Porcentajes validar=new Validador_Porcentajes(null);
        String resultado= validar.validarPorcentaje("10", "25");
        assertEquals("- Ingrese un porcentaje entre el rango (30-60).\n", resultado);      
    }

    @Test
    public void formatoCorrecto(){
        Validador_Porcentajes validar=new Validador_Porcentajes(null);
        String resultado= validar.validarPorcentaje("15", "35");
        assertEquals("Porcentaje válido", resultado);      
    }

}
