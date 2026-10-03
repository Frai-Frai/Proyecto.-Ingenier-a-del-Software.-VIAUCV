package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import modelo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;

public class PlanificarItinerarioTest {

    @TempDir  //para el txt temporal
    Path carpetaTemp;
    
    private UnidadesDAO uniDao;
    private UsuarioDAO usuDao;
    private ItinerarioDAO itiDao;
    private Validador_Itinerario valida;
    
    @BeforeEach 
    public void setUP(){
        //Archivos temporales
        File archivoUnidades = carpetaTemp.resolve("unidades.txt").toFile();
        File archivoItinerario = carpetaTemp.resolve("itinerarios.txt").toFile();
        File archivoUsuarios = carpetaTemp.resolve("usuarios.txt").toFile();

        //Inicializar los archivos temporales 
        try (FileWriter w = new FileWriter(archivoUsuarios)){
            w.write("");
        } catch (Exception e){
            System.out.println("Error al crear el archivo temporal de usuarios: "+e.getMessage());
        }


        try (FileWriter w = new FileWriter((archivoUnidades))){
            w.write("");
        } catch (Exception e){
            System.out.println("Error al crear el archivo temporal de las unidades: "+e.getMessage());
        }

        try (FileWriter w = new FileWriter(archivoItinerario)){ 
            w.write(""); 
        } catch (Exception e){ 
            System.out.println("Error al crear el archivo temporal de las itinerarios: " + e.getMessage()); 
        }

        usuDao = new UsuarioDAO(archivoUsuarios.getAbsolutePath());
        uniDao = new UnidadesDAO(archivoUnidades.getAbsolutePath());
        itiDao = new ItinerarioDAO(archivoItinerario.getAbsolutePath());

        valida = new Validador_Itinerario(itiDao, uniDao, usuDao);
    }
  
    @Test 
    void PlacaVacia(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo(" ", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- El campo de placa no puede estar vacío.\n", resultado);
    }

    @Test 
    void PlacaInvalida(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("AF1__58", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- Error: Placa con caracteres no permitidos.\n", resultado);
    }

    @Test 
    void RutaVacia(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", " ", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- El campo de tipo de ruta no puede estar vacío.\n", resultado);
    }

    @Test 
    void RutaInvalida(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "urbanaa", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- El campo de tipo de ruta debe ser 'Urbana' o 'Extraurbana'.\n", resultado);
    }

    @Test 
    void DestinoVacio(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", " ", "Ciudad Universitaria", "Metro de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- El campo de destino no puede estar vacío.\n", resultado);
    }

    @Test 
    void DestinoInvalido(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo_Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- El destino no permite caracteres especiales (Excepto guiones y espacios).\n", resultado);
    }

    @Test 
    void PtoPartidaVacio(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde ", " ", "Metro de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- El campo de punto de partida no puede estar vacío.\n", resultado);
    }

    @Test 
    void PtoPartidaInvalido(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad_Universitaria", "Metro de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- El punto de partida no permite caracteres especiales (Excepto guiones y espacios).\n", resultado);
    }

    @Test 
    void PtoLlegadaVacio(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", " ", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- El campo de punto de llegada no puede estar vacío.\n", resultado);
    }

    @Test 
    void PtoLlegadaInvalido(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro14 de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- El punto de llegada no permite caracteres especiales (Excepto guiones y espacios).\n", resultado);
    }

    @Test 
    void DiaVacio(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", " ", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- El campo de día no puede estar vacío.\n", resultado);
    }

    @Test
    void DiaInvalido(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Domingo", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- El campo de día debe ser un día de la semana válido (Lunes, Martes, Miércoles, Jueves, Viernes).\n", resultado);
    }

    @Test 
    void HoraVacia(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Lunes", " ", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- El campo de hora no puede estar vacío.\n", resultado);
    }

    @Test 
    void HoraInvalida(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Lunes", "3 pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- El campo de hora debe tener un formato válido (HH:mm)(am/pm).\n", resultado);
    }

    @Test 
    void ConductorVacio(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Lunes", "3:00 pm", " ");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- El campo de conductor no puede estar vacío.\n", resultado);
    }

    @Test 
    void ConductorInvalido(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Lunes", "3:00 pm", "V-31485799");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- La cédula del conductor solo debe contener números.\n", resultado);
    }

    //TOCAN BD
    @Test 
    void PlacaNoRegistrada() throws IOException{
        ItinerarioModelo itinerarioM = new ItinerarioModelo("616167", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Lunes", "3:00 pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- La placa asignada no existe en la base de datos.", resultado);
    }

    @Test 
    void UnidadInactiva() throws IOException{
        File archivoUnidades = carpetaTemp.resolve("unidades.txt").toFile();
        try(BufferedWriter w = new BufferedWriter(new FileWriter(archivoUnidades,false))){
            w.write("444567|habibi|50|Fuera de servicio");
        }

        uniDao = new UnidadesDAO(archivoUnidades.getAbsolutePath());
        valida = new Validador_Itinerario(itiDao, uniDao, usuDao);

        ItinerarioModelo itinerarioM = new ItinerarioModelo("444567", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Lunes", "3:00 pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- La unidad ingresada no se encuentra activa.", resultado);
    }

    @Test 
    void ConductorNoRegistrado() throws IOException{
        File archivoUnidades = carpetaTemp.resolve("unidades.txt").toFile();
        try(BufferedWriter w = new BufferedWriter(new FileWriter(archivoUnidades,false))){
            w.write("676767|sicseven|41|Activo");
        }

        uniDao = new UnidadesDAO(archivoUnidades.getAbsolutePath());
        valida = new Validador_Itinerario(itiDao, uniDao, usuDao);

        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Lunes", "3:00 pm", "16785444");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- La cédula del conductor no existe en la base de datos.", resultado);
    }

    @Test 
    void SolapamientoItinerario() throws IOException{
        File archivoUnidades = carpetaTemp.resolve("unidades.txt").toFile();
        try(BufferedWriter w = new BufferedWriter(new FileWriter(archivoUnidades,false))){
            w.write("676767|sicseven|41|Activo");
        }

        File archivoUsuarios = carpetaTemp.resolve("usuarios.txt").toFile();
        try(BufferedWriter w = new BufferedWriter(new FileWriter(archivoUsuarios,false))){
            w.write("16578923|Antonio Espinoza|Antonioespinoza27@hotmail.com|Conductor|busdriver4|0.0");
        }

        File archivoItinerario = carpetaTemp.resolve("itinerarios.txt").toFile();
        try(BufferedWriter w = new BufferedWriter(new FileWriter(archivoItinerario, false))){
            w.write("676767|Urbana|La Candelaria|Ciudad Universitaria|Sambil de la Candelaria|Lunes|3:00 pm|16578923");
        }

        uniDao = new UnidadesDAO(archivoUnidades.getAbsolutePath());
        usuDao = new UsuarioDAO(archivoUsuarios.getAbsolutePath());
        itiDao = new ItinerarioDAO(archivoItinerario.getAbsolutePath());
        valida = new Validador_Itinerario(itiDao, uniDao, usuDao);

        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "La Candelaria", "Ciudad Universitaria", "Sambil de la Candelaria", "Lunes", "3:00 pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("- El itinerario se solapa con otro itinerario existente.", resultado);
    }

    @Test 
    void TodoVacio(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo(" ", " ", " ", " ", " ", " ", " ", " ");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("Por favor, complete todos lo campos del formulario.", resultado);
    }

    @Test
    void RegistroExitoso() throws IOException{

        File archivoUnidades = carpetaTemp.resolve("unidades.txt").toFile();
        try(BufferedWriter w = new BufferedWriter(new FileWriter(archivoUnidades,false))){
            w.write("PLACABU|Encava ENT-610|20|Activo");
        }

        File archivoUsuarios = carpetaTemp.resolve("usuarios.txt").toFile();
        try(BufferedWriter w = new BufferedWriter(new FileWriter(archivoUsuarios,false))){
            w.write("16578923|Antonio Espinoza|Antonioespinoza27@hotmail.com|Conductor|busdriver4|0.0");
        }
        
        uniDao = new UnidadesDAO(archivoUnidades.getAbsolutePath());
        usuDao = new UsuarioDAO(archivoUsuarios.getAbsolutePath());
        valida = new Validador_Itinerario(itiDao, uniDao, usuDao);

        ItinerarioModelo itinerarioM = new ItinerarioModelo("PLACABU", "Urbana", "Catia", "Tierra de Nadie UCV", "Centro de Catia", "Lunes", "12:00 pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("Ruta válida", resultado);
    }

}
