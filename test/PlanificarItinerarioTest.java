package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import modelo.*;

public class PlanificarItinerarioTest {

    UnidadesDAO uniDao;
    ItinerarioDAO itiDao;
    Validador_Itinerario valida = new Validador_Itinerario(itiDao, uniDao);
    
    @Test 
    void PlacaVacia(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo(" ", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("El campo de placa no puede estar vacío.", resultado);
    }

    @Test 
    void PlacaInvalida(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("AF1__58", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("Error: Placa con caracteres no permitidos.", resultado);
    }

    @Test 
    void RutaVacia(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", " ", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("El campo de tipo de ruta no puede estar vacío.", resultado);
    }

    @Test 
    void RutaInvalida(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "urbanaa", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("El campo de tipo de ruta debe ser 'Urbana' o 'Urbana'.", resultado);
    }

    @Test 
    void DestinoVacio(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", " ", "Ciudad Universitaria", "Metro de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("El campo de destino no puede estar vacío.", resultado);
    }

    @Test 
    void DestinoInvalido(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo_Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("El destino no permite caracteres especiales (Excepto guiones y espacios).", resultado);
    }

    @Test 
    void PtoPartidaVacio(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde ", " ", "Metro de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("El campo de punto de partida no puede estar vacío.", resultado);
    }

    @Test 
    void PtoPartidaInvalido(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad_Universitaria", "Metro de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("El punto de partida no permite caracteres especiales (Excepto guiones y espacios).", resultado);
    }

    @Test 
    void PtoLlegadaVacio(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", " ", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("El campo de punto de llegada no puede estar vacío.", resultado);
    }

    @Test 
    void PtoLlegadaInvalido(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro14 de Palo Verde", "Martes", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("El punto de llegada no permite caracteres especiales (Excepto guiones y espacios).", resultado);
    }

    @Test 
    void DiaVacio(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", " ", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("El campo de día no puede estar vacío.", resultado);
    }

    @Test
    void DiaInvalido(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Domingo", "3:00pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("El campo de día debe ser un día de la semana válido (Lunes, Martes, Miércoles, Jueves, Viernes).", resultado);
    }

    @Test 
    void HoraVacia(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Lunes", " ", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("El campo de hora no puede estar vacío.", resultado);
    }

    @Test 
    void HoraInvalida(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Lunes", "3 pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("El campo de hora debe tener un formato válido (HH:mm)(am/pm).", resultado);
    }

    @Test 
    void ConductorVacio(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Lunes", "3:00 pm", " ");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("El campo de conductor no puede estar vacío.", resultado);
    }

    @Test 
    void ConductorInvalido(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Lunes", "3:00 pm", "V-31485799");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("La cédula del conductor solo debe contener números.", resultado);
    }

    @Test 
    void PlacaNoRegistrada(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("616167", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Lunes", "3:00 pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("La placa asignada no existe en la base de datos.", resultado);
    }

    @Test 
    void UnidadInactiva(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("444567", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Lunes", "3:00 pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("La unidad ingresada no se encuentra activa.", resultado);
    }

    @Test 
    void ConductorNoRegistrado(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "Palo Verde", "Ciudad Universitaria", "Metro de Palo Verde", "Lunes", "3:00 pm", "16785444");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("La cédula del conductor no existe en la base de datos.", resultado);
    }

    @Test 
    void SolapamientoItinerario(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("676767", "Urbana", "La Candelaria", "Ciudad Universitaria", "Sambil de la Candelaria", "Lunes", "3:00 pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("El itinerario se solapa con otro itinerario existente.", resultado);
    }

    @Test 
    void RegistroExitoso(){
        ItinerarioModelo itinerarioM = new ItinerarioModelo("B38921", "Urbana", "Catia", "Tierra de Nadie UCV", "Centro de Catia", "Lunes", "12:00 pm", "16578923");
        String resultado = valida.validarItinerario(itinerarioM);
        assertEquals("Ruta válida", resultado);
    }

}
