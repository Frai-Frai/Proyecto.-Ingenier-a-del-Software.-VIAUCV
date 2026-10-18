package modelo;

public class Reserva {
    private String cedulaUsuario;
    private ItinerarioModelo itinerarioReservado;

    public Reserva(String cedula, ItinerarioModelo itinerario){
        this.cedulaUsuario=cedula;
        this.itinerarioReservado=itinerario;
    }

    public String getCedulaU(){
        return cedulaUsuario;
    }

    public ItinerarioModelo getItinerario(){
        return itinerarioReservado;
    }

}
