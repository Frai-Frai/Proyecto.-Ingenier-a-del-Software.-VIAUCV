package modelo;

public class ItinerarioModelo {
    String placaAsignada;
    String tipoRuta;
    String destino;
    String puntoPartida;
    String puntoLLegada;
    String dia;
    String hora;
    String conductor;
    String tarifaBaseMunicipal;

    public ItinerarioModelo(String placaAsignada, String tipoRuta, String destino, String puntoPartida, String puntoLLegada, String dia, String hora, String Conductor, String tarifaMunicipal) {
        this.placaAsignada = placaAsignada;
        this.tipoRuta = tipoRuta;
        this.destino = destino;
        this.puntoPartida = puntoPartida;
        this.puntoLLegada = puntoLLegada;
        this.dia = dia;
        this.hora = hora;
        this.conductor = Conductor;
        this.tarifaBaseMunicipal= tarifaMunicipal;
    }

    //Getters
    public String getPlacaAsignada() {
        return placaAsignada;
    }

    public String getTipoRuta() {
        return tipoRuta;
    }

    public String getDestino() {
        return destino;
    }

    public String getPuntoPartida() {
        return puntoPartida;
    }

    public String getPuntoLLegada() {
        return puntoLLegada;
    }

    public String getDia() {
        return dia;
    }

    public String getHora() {
        return hora;
    }

    public String getConductor() {
        return conductor;
    }

    public String getTarifa(){
        return tarifaBaseMunicipal;
    }

    public String EscribirFormatoTXT(){
        return placaAsignada +"|"+ tipoRuta +"|"+ destino +"|"+ puntoPartida +"|"+ puntoLLegada +"|"+ dia +"|"+ hora +"|"+ conductor;
    }

}
