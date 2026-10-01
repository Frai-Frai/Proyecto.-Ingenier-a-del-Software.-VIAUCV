package test;

import modelo.Validador_IniciodeSesion;
import modelo.UsuarioDAO;
import modelo.Usuario;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ValidarInicioSesionTest {

    private Validador_IniciodeSesion validadorVacio;

    @Before
    public void setUp() {
        validadorVacio = new Validador_IniciodeSesion(null);
    }

    @Test
    public void testFactor1AmbosCamposVacios() {
        String resultado = validadorVacio.validarInicioSesion("", "");
        assertEquals("Por favor, complete todos lo campos del formulario.", resultado);
    }

    @Test
    public void testFactor2UsuarioVacio() {
        String resultado = validadorVacio.validarInicioSesion("", "MiPassword123");
        assertEquals("- El campo de cédula no puede estar vacío.\n", resultado);
    }

    @Test
    public void testFactor3ContrasenaVacia() {
        String resultado = validadorVacio.validarInicioSesion("123456", "");
        assertEquals("- El campo de la contraseña no puede estar vacío.\n", resultado);
    }

    @Test
    public void testFactor4UsuarioIncorrecto() {
        UsuarioDAO daoSimulado = new UsuarioDAO() {
            @Override
            public Usuario buscarPorCedula(String cedula) {
                return null;
            }
        };

        Validador_IniciodeSesion validador = new Validador_IniciodeSesion(daoSimulado);
        String resultado = validador.validarInicioSesion("99999999", "ClaveValida123");
        assertEquals("- El usuario con la cédula ingresada no se encuentra registrado.\n", resultado);
    }

    @Test
    public void testFactor5ContrasenaIncorrecta() {
        UsuarioDAO daoSimulado = new UsuarioDAO() {
            @Override
            public Usuario buscarPorCedula(String cedula) {
                // Constructor completo de usuario
                return new Usuario("123456", "Juan", "juan@mail.com", "Cliente", "ClaveCorrecta123", 0.0);
            }
        };

        Validador_IniciodeSesion validador = new Validador_IniciodeSesion(daoSimulado);
        String resultado = validador.validarInicioSesion("123456", "ClaveFalsa");
        assertEquals("- La contraseña ingresada es incorrecta.\n", resultado);
    }

    @Test
    public void testFactor6InicioExitoso() {
        UsuarioDAO daoSimulado = new UsuarioDAO() {
            @Override
            public Usuario buscarPorCedula(String cedula) {
                // Constructor completo de usuario
                return new Usuario("123456", "Juan", "juan@mail.com", "Cliente", "ClaveCorrecta123", 0.0);
            }
        };

        Validador_IniciodeSesion validador = new Validador_IniciodeSesion(daoSimulado);
        String resultado = validador.validarInicioSesion("123456", "ClaveCorrecta123");
        assertEquals("Ingreso exitoso.", resultado);
    }
}