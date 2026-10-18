package modelo;

import java.io.*;
import java.util.*;

public class ReservaDAO {
    private String rutaBD;

    public ReservaDAO(String ruta){
        this.rutaBD=ruta;
    }

    public ReservaDAO(){
        this.rutaBD="data/BDReservas.txt";
    }

    public List<Reserva> buscarPorCedula (String cedula){
        List<Reserva> lista=new ArrayList<>();
        File f= new File(rutaBD);

        if(!f.exists()){
            return lista; //si el archivo no existe 
        }

        try(BufferedReader b=new BufferedReader(new FileReader(f))){
            String linea;

            while((linea = b.readLine()) !=null){ //recorre toda la lista
                if(linea.trim().isEmpty()) continue;

                String[] datos= linea.split("\\|");

                if (datos.length==9){
                    String cedulaU= datos[0].trim(); //donde esta la cedula 

                    //comparar la cedula para ver sus reservas
                    if(cedula.equalsIgnoreCase(cedulaU.trim())){
                        String placa= datos[1].trim();
                        String tipoRuta= datos[2].trim();
                        String destino= datos[3].trim();
                        String puntoPartida= datos[4].trim();
                        String puntoLLegada=datos[5].trim();
                        String dia= datos[6].trim();
                        String hora= datos[7].trim();
                        String conductor= datos[8].trim();

                        ItinerarioModelo i= new ItinerarioModelo(placa,tipoRuta,destino,puntoPartida,puntoLLegada,dia,hora,conductor,"","");

                        Reserva r= new Reserva(cedulaU,i);
                        lista.add(r); //anadir a la lista si es la cedula que se busca
                    }
                } 

            }

        }catch(IOException e){
            System.out.println("Error al filtrar por cédula: "+e.getMessage());
        }
        return lista; //Retorno lista de las reservas de ese usuario
    }

}
