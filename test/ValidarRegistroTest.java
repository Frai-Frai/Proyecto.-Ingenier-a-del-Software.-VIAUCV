package test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.io.BufferedWriter;

import modelo.UniversidadDAO;
import modelo.UsuarioDAO;
import modelo.Validador_Registro;

public class ValidarRegistroTest {

    @TempDir  //para el txt temporal
    Path carpetaTemp;
    
    private Validador_Registro validar;
    private UsuarioDAO usuarioDAO;
    private UniversidadDAO universidadDAO;
    
    @BeforeEach 
    public void setUP(){
        //Archivos temporales
        File archivoUsuarios = carpetaTemp.resolve("usuarios.txt").toFile();
        File archivoUCV= carpetaTemp.resolve("universidad.txt").toFile();

        //Inicializar los archivos temporales 
        try (FileWriter w = new FileWriter((archivoUsuarios))){
            w.write("");
        }catch (Exception e){
            System.out.println("Error al crear el archivo temporal de usuarios: "+e.getMessage());
        }


        try (FileWriter w = new FileWriter((archivoUCV))){
            w.write("");
        }catch (Exception e){
            System.out.println("Error al crear el archivo temporal de la universidad: "+e.getMessage());
        }

        usuarioDAO= new UsuarioDAO(archivoUsuarios.getAbsolutePath());
        universidadDAO= new UniversidadDAO(archivoUCV.getAbsolutePath());

       validar= new Validador_Registro(usuarioDAO, universidadDAO);
    }


    @Test
    void todosLosCamposDelFormularioVacíos(){
        String resultado= validar.validarRegistro("","","","","","");
        assertEquals("Por favor, complete todos lo campos del formulario.", resultado);
    }

    @Test
    void NombreVacio(){
        String resultado= validar.validarRegistro("12345678","","yuki@gmail.com","Estudiante","123456","123456");
        assertEquals("- El campo de nombre y apellido no puede estar vacío.\n", resultado);
    }

    @Test 
    void NombreIInvalido(){
        String resultado= validar.validarRegistro("12345678","Usuario 123","yuki@gmail.com","Estudiante","123456","123456");
        assertEquals("- El nombre de usuario no permite caracteres especiales.\n", resultado);
    }

    @Test 
    void CorreoVacio(){
        String resultado= validar.validarRegistro("12345678","Daniela Doe","","Estudiante","123456","123456");
        assertEquals("- El campo de correo no puede estar vacío.\n", resultado);
    }

    @Test 
    void CorreoInvalido(){
        String resultado= validar.validarRegistro("12345678","Daniela Doe","Mariam@com","Estudiante","123456","123456");
        assertEquals("- El correo ingresado no tiene un formato válido.\n", resultado);
    }

    @Test 
    void cedulaVacia(){
        String resultado= validar.validarRegistro("","Mariam Battika","Mariam@gmail.com","Estudiante","123456","123456");
        assertEquals("- El campo de cédula no puede estar vacío.\n", resultado);
    }

    @Test 
    void cedulaInvalida(){
        String resultado= validar.validarRegistro("3244f56t","Rosa Duran","rosa@hotmal.com","Docente","123456","123456");
        assertEquals("- La cédula solo debe contener números.\n", resultado);
    }

    @Test 
    void contrasenaVacia(){
        String resultado= validar.validarRegistro("12345678","Daniela Doe","yuki@gmail.com","Estudiante","","123456");
        assertTrue(resultado.contains("- El campo de la contraseña no puede estar vacío.\n"));
    }   

    @Test
    void contrasenaInvalida(){
        String resultado= validar.validarRegistro("12345678","Daniela Doe","yuki@gmail.com","Estudiante","123","123");
        assertEquals("- La contraseña debe tener al menos 6 caracteres.\n", resultado);
    }

    @Test 
    void contrasenaDistintas(){
        String resultado= validar.validarRegistro("12345678","Maria Perez","yuki@gmail.com","Estudiante","99999999","ClaveValida123");
        assertEquals("- Las contraseñas no coinciden.\n", resultado);
    }

    @Test 
    void cedulaYaRegistrada() throws Exception{

        //se simula la base de datos agregando un usuario
        File archivouCV= carpetaTemp.resolve("universidad.txt").toFile();
        try(FileWriter w = new FileWriter((archivouCV))){
            w.write("32480946|daniedani@gmail.com|Estudiante");
        }

        File archivoUsuarios = carpetaTemp.resolve("usuarios.txt").toFile();
        try(FileWriter w = new FileWriter((archivoUsuarios))){
            w.write("32480946|Daniela Doe|yuki@gmail.com|Estudiante|123456|0.0");
        }

        usuarioDAO= new UsuarioDAO(archivoUsuarios.getAbsolutePath());
        universidadDAO= new UniversidadDAO(archivouCV.getAbsolutePath());
        validar= new Validador_Registro(usuarioDAO, universidadDAO);

        String resultado= validar.validarRegistro("32480946","Daniela Doe","yuki@gmail.com","Estudiante","123456","123456");
        assertEquals("- El usuario con la cédula ingresada ya se encuentra registrado.\n", resultado);
    }

    @Test 
    void cedulaNoRegistrada() throws IOException{
        String resultado= validar.validarRegistro("87654321","Abraham Garcia","yuki3@gmail.com","Estudiante","123456","123456");
        assertEquals("- La cédula no se encuentra registrada en la base de datos de la UCV.\n", resultado);
    }

    @Test 
    void casoExitoso() throws IOException{
        //simulacion de la base de datos agregando un usuario (no esta en la bd de la web, pero si en la de la UCV)
        File archivouCV= carpetaTemp.resolve("universidad.txt").toFile();
        try(BufferedWriter w = new BufferedWriter(new FileWriter(archivouCV, false))){
            w.write("15289746|diana@hotmail.com|Docente");
        }

        File archivoUsuarios = carpetaTemp.resolve("usuarios.txt").toFile();
        try(BufferedWriter w = new BufferedWriter(new FileWriter(archivoUsuarios, false))){
            w.write(""); // no registrado aun
        }

        usuarioDAO= new UsuarioDAO(archivoUsuarios.getAbsolutePath());
        universidadDAO= new UniversidadDAO(archivouCV.getAbsolutePath());
        validar= new Validador_Registro(usuarioDAO, universidadDAO);


        String resultado= validar.validarRegistro("15289746","Diana Ramos","diana@hotmail.com","Docente","diana51","diana51");
        assertEquals("Registro exitoso.", resultado.trim());
    }

}