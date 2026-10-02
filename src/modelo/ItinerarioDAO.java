package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ItinerarioDAO {
    private static final String Ruta_BDItinerario = "data/BDItinerario.txt";

    public ItinerarioDAO() {}
        
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
                if (partes.length >= 8) {
                    lista.add(new ItinerarioModelo(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5], partes[6], partes[7]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean itinerarioSolapado(ItinerarioModelo itinerario) {
        List<ItinerarioModelo> lista= obtenerRutas();

        for(ItinerarioModelo i: lista){
            if(i.getPlacaAsignada().equals(itinerario.getPlacaAsignada()) && i.getDia().equals(itinerario.getDia()) &&
            i.getHora().equals(itinerario.getHora())){ //si ambos tienen la misma placa, mismo dia y hora entonces se solapan
                return true;
            }
        }
        
        return false;
    }
}
