package modelo;

import java.io.*;
import java.util.*;

public class ItinerarioDAO {
    private String Ruta_BDItinerario;

    // Constructor vacío por defecto (BD real)
    public ItinerarioDAO() {
        this.Ruta_BDItinerario = "data/BDItinerario.txt";
    }
        
    // Constructor para Pruebas Unitarias (Archivo temporal)
    public ItinerarioDAO(String rutaTemp) {
        this.Ruta_BDItinerario = rutaTemp;
    }
        
    public void RegistrarRutaEnBD(ItinerarioModelo itinerario) {

        File f= new File(Ruta_BDItinerario);

            try(BufferedWriter bw= new BufferedWriter(new FileWriter(f, true))) { //lo anade al final junto a lo demas
                bw.write(itinerario.EscribirFormatoTXT());
                bw.newLine();
            }catch(IOException e){
                System.out.println("error al guardar en la base de datos: "+e.getMessage());
        }
    }

    public List<ItinerarioModelo> obtenerRutas(){

        List<ItinerarioModelo> lista = new ArrayList<>();
        File archivo = new File(Ruta_BDItinerario);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length >= 10) {
                    lista.add(new ItinerarioModelo(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5], partes[6], partes[7],partes[8],partes[9]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean itinerarioSolapado(ItinerarioModelo itinerario) {
        List<ItinerarioModelo> lista= obtenerRutas();

        if(lista==null || itinerario==null){
            return false;
        }

        for(ItinerarioModelo i: lista){
            //para mejor entendimiento del codigo
            boolean mismoDiaYHora= i.getDia().equalsIgnoreCase(itinerario.getDia()) && i.getHora().equalsIgnoreCase(itinerario.getHora());

            if(mismoDiaYHora){ 
                boolean mismoConductor=i.getConductor().equalsIgnoreCase(itinerario.getConductor());
                boolean mismaPlaca=i.getPlacaAsignada().equalsIgnoreCase(itinerario.getPlacaAsignada());

                if(mismaPlaca || mismoConductor){ //si se cumple que un mismo dia y hora se comparte placa o se comparte conductor se solapa
                    return true;
                }
            }
        }
        
        return false;
    }

    //eliminar ruta de la base de datos
    public boolean eliminarRuta(ItinerarioModelo itinerario){
        List<ItinerarioModelo> listActual= obtenerRutas();
        boolean eliminado= false;

        if(listActual==null || listActual.isEmpty() || itinerario==null){
            return false;
        }

        for(ItinerarioModelo i: listActual){
            
            //poniendo los resultados en variables seradas para comparacion mas legible
            boolean placa= i.getPlacaAsignada().trim().equals(itinerario.getPlacaAsignada().trim());
            boolean tipoRuta= i.getTipoRuta().trim().equalsIgnoreCase(itinerario.getTipoRuta().trim());
            boolean destino= i.getDestino().trim().equalsIgnoreCase(itinerario.getDestino().trim());
            boolean puntoPartida= i.getPuntoPartida().trim().equalsIgnoreCase(itinerario.getPuntoPartida().trim());
            boolean puntoLLegada= i.getPuntoLLegada().trim().equalsIgnoreCase(itinerario.getPuntoLLegada().trim());
            boolean dia= i.getDia().trim().equalsIgnoreCase(itinerario.getDia().trim());
            boolean hora= i.getHora().trim().equalsIgnoreCase(itinerario.getHora().trim());
            boolean conductor= i.getConductor().trim().equalsIgnoreCase(itinerario.getConductor().trim());

            if(placa && tipoRuta && destino && puntoPartida && puntoLLegada && dia && hora && conductor){
                listActual.remove(i); //se quita de la lista si coincide
                eliminado=true;
                break; //como ya se encontro, se sale del bucle
            }
        }

        if(eliminado){
            actualizarDB(listActual);
        }
        return eliminado;
    }
    
    //actualizar la BD de itinerario
    public boolean actualizarDB(List<ItinerarioModelo> lista){
            File f= new File(Ruta_BDItinerario);

            try (BufferedWriter bw =new BufferedWriter(new FileWriter(f,false))){

                for (ItinerarioModelo i: lista ) {
                    bw.write(i.EscribirFormatoTXT()); //escribir en el txt lo de la lista
                    bw.newLine();
                }
                return true;
                
            }catch(IOException e){
                System.out.println("Error al actualizar la base de datos: "+e.getMessage());
                return false;
        }
    }

    //Descontar los cupos disponibles al reservar
    public boolean restarCupos(ItinerarioModelo itinerario){
        List<ItinerarioModelo> listActual= obtenerRutas();
        boolean modificado= false;

        if(listActual==null || listActual.isEmpty() || itinerario==null){
            return false;
        }

        for(ItinerarioModelo i: listActual){

            boolean placa= i.getPlacaAsignada().trim().equals(itinerario.getPlacaAsignada().trim());
            boolean tipoRuta= i.getTipoRuta().trim().equalsIgnoreCase(itinerario.getTipoRuta().trim());
            boolean destino= i.getDestino().trim().equalsIgnoreCase(itinerario.getDestino().trim());
            boolean puntoPartida= i.getPuntoPartida().trim().equalsIgnoreCase(itinerario.getPuntoPartida().trim());
            boolean puntoLLegada= i.getPuntoLLegada().trim().equalsIgnoreCase(itinerario.getPuntoLLegada().trim());
            boolean dia= i.getDia().trim().equalsIgnoreCase(itinerario.getDia().trim());
            boolean hora= i.getHora().trim().equalsIgnoreCase(itinerario.getHora().trim());
            boolean conductor= i.getConductor().trim().equalsIgnoreCase(itinerario.getConductor().trim());

            if(placa && tipoRuta && destino && puntoPartida && puntoLLegada && dia && hora && conductor){
                try{
                    int cuposActuales = Integer.parseInt(i.getCupos().trim());
                    if(cuposActuales>0){
                        cuposActuales--;
                        i.setCupos(String.valueOf(cuposActuales)); // Lo volvemos a guardar como String
                        modificado = true;
                    }else{
                        return false;
                    }


                }catch (NumberFormatException e){
                    System.err.println("Error al parsear el cupo: " + e.getMessage());
                    return false;
                }
                break; //como ya se encontro, se sale del bucle
            }
        }

        if(modificado){
            actualizarDB(listActual);
        }
        return modificado;
    }

    //verifica si hay cupos dispinibles
    public boolean hayCupos(ItinerarioModelo itinerario) {
        if (itinerario == null) return false;

        List<ItinerarioModelo> lista = obtenerRutas();

        if(lista==null || lista.isEmpty() || itinerario==null){
            return false;
        }

        for (ItinerarioModelo i : lista) {

            boolean placa= i.getPlacaAsignada().trim().equals(itinerario.getPlacaAsignada().trim());
            boolean tipoRuta= i.getTipoRuta().trim().equalsIgnoreCase(itinerario.getTipoRuta().trim());
            boolean destino= i.getDestino().trim().equalsIgnoreCase(itinerario.getDestino().trim());
            boolean puntoPartida= i.getPuntoPartida().trim().equalsIgnoreCase(itinerario.getPuntoPartida().trim());
            boolean puntoLLegada= i.getPuntoLLegada().trim().equalsIgnoreCase(itinerario.getPuntoLLegada().trim());
            boolean dia= i.getDia().trim().equalsIgnoreCase(itinerario.getDia().trim());
            boolean hora= i.getHora().trim().equalsIgnoreCase(itinerario.getHora().trim());
            boolean conductor= i.getConductor().trim().equalsIgnoreCase(itinerario.getConductor().trim());


            if (placa && tipoRuta && destino && puntoPartida && puntoLLegada && dia && hora && conductor) {
                try {
                    int cupos = Integer.parseInt(i.getCupos().trim());
                    return cupos > 0; // Retorna true si hay quedan cupos
                } catch (NumberFormatException e) {
                    System.err.println("Error al buscar el cupo: " + e.getMessage());
                    return false;
                }
        }
    }
    return false; // No se encontró la ruta o no tiene cupos
}
}
