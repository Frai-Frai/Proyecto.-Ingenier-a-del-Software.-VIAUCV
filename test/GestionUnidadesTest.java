package test;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import modelo.*;

public class GestionUnidadesTest {

    private Validador_GestionUnidades Validador;

    @Before
    public void setUp() {
        Validador = new Validador_GestionUnidades(new UnidadesDAO());
    }

    @Test
    public void testFactor1_TodosCamposVacios() {
        UnidadBus unidad = new UnidadBus(" ", " ", " ", " ");
        String resultado = Validador.validar_RegistroUnidad(unidad);
        assertEquals("Por favor, complete todos lo campos del formulario.", resultado);
    }

    @Test
    public void testFactor2_PlacaVacia() {
        UnidadBus unidad = new UnidadBus(" ", "Modelo", "36", "Activo");
        String resultado = Validador.validar_RegistroUnidad(unidad);
        assertEquals("- El campo de placa no puede estar vacío.\n", resultado);
    }

    @Test
    public void testFactor3_PlacaInvalida() {
        UnidadBus unidad = new UnidadBus("A___87Q", "Modelo", "36", "Activo");
        String resultado = Validador.validar_RegistroUnidad(unidad);
        assertEquals("- Error: Placa con caracteres no permitidos.\n", resultado);
    }

    @Test
    public void testFactor4_PlacaInvalidaLong() {
        UnidadBus unidad = new UnidadBus("ABC123A78954A", "Modelo", "36", "Activo");
        String resultado = Validador.validar_RegistroUnidad(unidad);
        assertEquals("- Error: Longitud de placa inválida.\n", resultado);
    }

    @Test
    public void testFactor5_ModeloVacio() {//FALTA
        UnidadBus unidad = new UnidadBus("ABC123A", " ", "36", "Activo");
        String resultado = Validador.validar_RegistroUnidad(unidad);
        assertEquals("- El campo de modelo no puede estar vacío.\n", resultado);
    }

    @Test
    public void testFactor6_CapacidadVacia() {
        UnidadBus unidad = new UnidadBus("ABC123A", "Modelo", " ", "Activo");
        String resultado = Validador.validar_RegistroUnidad(unidad);
        assertEquals("- El campo de capacidad no puede estar vacío.\n", resultado);
    }

    @Test
    public void testFactor7_CapacidadInvalida() {
        UnidadBus unidad = new UnidadBus("ABC123A", "Modelo", "A85", "Activo");
        String resultado = Validador.validar_RegistroUnidad(unidad);
        assertEquals("La capacidad no debe contener caracteres no numéricos o especiales.", resultado);
    }

    @Test
    public void testFactor8_CapacidadMayor() {
        UnidadBus unidad = new UnidadBus("ABC123A", "Modelo", "85", "Activo");
        String resultado = Validador.validar_RegistroUnidad(unidad);
        assertEquals("- Ingrese un número de capacidad entre el rango (20-36).\n", resultado);
    }

    @Test
    public void testFactor9_PlacaRegistrada() {
        UnidadBus unidad = new UnidadBus("B79525", "Modelo", "36", "Activo");
        String resultado = Validador.validar_RegistroUnidad(unidad);
        assertEquals("- La placa ingresada ya se encuentra registrada en la base de datos.", resultado);
    }

    @Test
    public void testFactor10_RegistroExitoso() {
        UnidadBus unidad = new UnidadBus("ABC123A", "Modelo", "36", "Activo");
        String resultado = Validador.validar_RegistroUnidad(unidad);
        assertEquals("Registro exitoso", resultado);
    }
}