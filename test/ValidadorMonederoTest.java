package test;

import org.junit.jupiter.api.Test;
import modelo.Validador_Monedero;
import static org.junit.jupiter.api.Assertions.*;

public class ValidadorMonederoTest {
    @Test 
    public  void camposVacios(){
        Validador_Monedero validar=new Validador_Monedero();
        String resultado= validar.ValidarDatos("","","");
        assertEquals("Por favor, complete todos lo campos del formulario.",resultado);
    }

    @Test 
    public  void campoNumeroVacio(){
        Validador_Monedero validar=new Validador_Monedero();
        String resultado= validar.ValidarDatos("","32499046","80");
        assertEquals("- El campo de número no puede estar vacío.\n",resultado);
    }

    @Test 
    public  void campoCedulaVacia(){
        Validador_Monedero validar=new Validador_Monedero();
        String resultado= validar.ValidarDatos("1592567","","80");
        assertEquals("- El campo de cédula no puede estar vacío.\n",resultado);
    }

    @Test 
    public  void campoMontoVacio(){
        Validador_Monedero validar=new Validador_Monedero();
        String resultado= validar.ValidarDatos("1592567","32499046","");
        assertEquals("- El campo de monto no puede estar vacío.\n",resultado);
    }

    @Test 
    public  void campoNumeroDigitosExtra(){
        Validador_Monedero validar=new Validador_Monedero();
        String resultado= validar.ValidarDatos( "30546789", "32499046","80");
        assertEquals("- El campo de número solo acepta 7 valores numéricos.\n",resultado);
    }

    @Test 
    public  void campoCedulaNoNumerica(){
        Validador_Monedero validar=new Validador_Monedero();
        String resultado= validar.ValidarDatos( "1234567", "3249904f","80");
        assertEquals("- La cédula solo debe contener números.\n",resultado);
    }

    @Test 
    public  void campoMontoNoNumerico(){
        Validador_Monedero validar=new Validador_Monedero();
        String resultado= validar.ValidarDatos( "1234567","32499046","80.o");
        assertEquals("- El monto solo debe contener números (enteros o decimales).\n",resultado);
    }

    @Test 
    public  void campoCedulaFueraDeRango(){
        Validador_Monedero validar=new Validador_Monedero();
        String resultado= validar.ValidarDatos( "1234567", "32499","80");
        assertEquals("- La cédula debe estar dentro del rango [1.000.000 -99.999.999].\n",resultado);
    }

    @Test 
    public  void campoMontoFueraDeRango(){
        Validador_Monedero validar=new Validador_Monedero();
        String resultado= validar.ValidarDatos( "3054678", "32499046","12000");
        assertEquals("- El monto tiene que ser mayor a 0 y menor a 10.000.\n",resultado);
    }

    @Test 
    public  void camposValidos(){
        Validador_Monedero validar=new Validador_Monedero();
        String resultado= validar.ValidarDatos( "3054678", "32499046","80");
        assertEquals("Pago exitoso",resultado);
    }
}
