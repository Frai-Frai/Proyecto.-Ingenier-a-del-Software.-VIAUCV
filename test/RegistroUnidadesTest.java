package test;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import controlador.Controlador_RegistroUnidades;

public class RegistroUnidadesTest {

    private Controlador_RegistroUnidades controlador;

    @Before
    public void setUp() {
        controlador = new Controlador_RegistroUnidades(null);
    }

    @Test
    public void testFactor1_TodosCamposVacios() {
        String resultado = controlador.registrarUnidad("", "", "");
        assertEquals("Error: Campos incompletos", resultado);
    }

    @Test
    public void testFactor2_PlacaVacia() {
        String resultado = controlador.registrarUnidad("", "Modelo", "40");
        assertEquals("Error: Campos incompletos", resultado);
    }

    @Test
    public void testFactor3_ModeloVacio() {
        String resultado = controlador.registrarUnidad("ABC123A", "", "40");
        assertEquals("Error: Campos incompletos", resultado);
    }

    @Test
    public void testFactor4_CapacidadVacia() {
        String resultado = controlador.registrarUnidad("ABC123A", "Modelo", "");
        assertEquals("Error: Campos incompletos", resultado);
    }

    @Test
    public void testFactor5_PlacaYModeloVacios() {
        String resultado = controlador.registrarUnidad("", "", "40");
        assertEquals("Error: Campos incompletos", resultado);
    }

    @Test
    public void testFactor6_PlacaYCapacidadVacias() {
        String resultado = controlador.registrarUnidad("", "Modelo", "");
        assertEquals("Error: Campos incompletos", resultado);
    }

    @Test
    public void testFactor7_ModeloYCapacidadVacios() {
        String resultado = controlador.registrarUnidad("ABC123A", "", "");
        assertEquals("Error: Campos incompletos", resultado);
    }

    @Test
    public void testFactor8_PlacaSimbolosNoPermitidos() {
        String resultado = controlador.registrarUnidad("ABC#123", "Modelo", "30");
        assertEquals("Error: Placa con caracteres no permitidos", resultado);
    }

    @Test
    public void testFactor9_PlacaDuplicada() {
        String resultado = controlador.registrarUnidad("123ABC", "Modelo", "35");
        assertEquals("Error: Placa duplicada", resultado);
    }

    @Test
    public void testFactor10_LongitudPlacaInvalida() {
        String resultado = controlador.registrarUnidad("A", "Modelo", "35");
        assertEquals("Error: Longitud de placa inválida", resultado);
    }

    @Test
    public void testFactor11_CapacidadNoNumerica() {
        String resultado = controlador.registrarUnidad("PLACA11", "Modelo", "abc");
        assertEquals("Error: Capacidad con caracteres no numéricos", resultado);
    }

    @Test
    public void testFactor12_CapacidadMenorOIgualCero() {
        String resultado = controlador.registrarUnidad("PLACA12", "Modelo", "-5");
        assertEquals("Error: Capacidad menor o igual a cero", resultado);
    }

    @Test
    public void testFactor13_CapacidadNoEntera() {
        String resultado = controlador.registrarUnidad("PLACA13", "Modelo", "40.5");
        assertEquals("Error: Capacidad con números no enteros", resultado);
    }

    @Test
    public void testFactor14_CapacidadExageradaFueraDeRango() {
        // Generamos una placa única para evitar el choque de placa duplicada
        String placaUnica = "X" + (System.currentTimeMillis() % 100000);
        String resultado = controlador.registrarUnidad(placaUnica, "Modelo", "60");
        assertEquals("Error: Capacidad fuera de rango", resultado);
    }

    @Test
    public void testFactor15_RegistroExitoso() {
        // Generamos una placa única usando el tiempo actual para evitar duplicados en cada ejecución
        String placaUnica = "B" + (System.currentTimeMillis() % 100000);
        String resultado = controlador.registrarUnidad(placaUnica, "Modelo", "45");
        assertEquals("Registro exitoso", resultado);
    }
}