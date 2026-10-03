package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UnidadesDAO {
    
    private static final String RUTA_TXT = "data/BDUnidadesRegistradas.txt";

    // 1. Método para leer todas las unidades del TXT
    public List<UnidadBus> obtenerUnidades() {
        List<UnidadBus> lista = new ArrayList<>();
        File archivo = new File(RUTA_TXT);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length >= 4) {
                    lista.add(new UnidadBus(partes[0], partes[1], partes[2], partes[3]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 2. Método para agregar una nueva unidad al TXT
    public void registrarUnidad(UnidadBus unidad) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_TXT, true))) {
            bw.write(unidad.getPlaca() + "|" + unidad.getModelo() + "|" + unidad.getCapacidad() + "|" + unidad.getEstado());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 3. Método para actualizar el estado operativo de un bus específico en el TXT
    public void actualizarEstado(String placaBuscada, String nuevoEstado) {
        List<UnidadBus> lista = obtenerUnidades();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_TXT, false))) {
            for (UnidadBus unidad : lista) {
                if (unidad.getPlaca().equalsIgnoreCase(placaBuscada)) {
                    unidad.setEstado(nuevoEstado);
                }
                bw.write(unidad.getPlaca() + "|" + unidad.getModelo() + "|" + unidad.getCapacidad() + "|" + unidad.getEstado());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<UnidadBus> obtenerBusesPorEstado(String estadoDeseado) {
    List<UnidadBus> lista = new ArrayList<>();
    File archivo = new File("data/BDUnidadesRegistradas.txt");
    
    if (!archivo.exists()) {
        return lista;
    }

    try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(archivo))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) continue; // Ignorar líneas vacías

            String[] datos = linea.split("\\|"); 
            
            // Verificamos que tenga las 4 partes exactas
            if (datos.length >= 4) {
                String placa = datos[0].trim();
                String modelo = datos[1].trim();
                String capacidad = datos[2].trim();
                String estado = datos[3].trim();

                // Comparamos el estado (ej. "Activo")
                if (estado.equalsIgnoreCase(estadoDeseado)) {
                    // Pasamos los 4 atributos tal como los tienes en tu TXT
                    lista.add(new UnidadBus(placa, modelo, capacidad, estado));
                }
            }
        }
    } catch (Exception e) {
        System.out.println("Error al leer el archivo de la base de datos: " + e.getMessage());
    }

    return lista;
}

    //Saber si esta dentro de la lista de un tal estado puesto
    public boolean estaActivo(String placa){
        List<UnidadBus> busesActivos= obtenerBusesPorEstado("Activo");

        if(busesActivos==null || busesActivos.isEmpty()){
            return false;
        }

        for(UnidadBus bus: busesActivos){
            if(bus.getPlaca()!=null && bus.getPlaca().trim().equalsIgnoreCase(placa) ){
                return true;
            }
        }

        return false;
    }

    //buscar por placa 
    public UnidadBus buscarPorPlaca (String placaBuscada){
            File f=new File(RUTA_TXT);

            if(!f.exists()) return null;

            try(BufferedReader b=new BufferedReader(new FileReader(f))){
                String linea;

            while((linea = b.readLine()) !=null){ //recorre toda la lista
                if(linea.trim().isEmpty()) continue;

                String[] datos= linea.split("\\|");

                if (datos.length==4){
                    String placa= datos[0].trim(); //donde esta la placa

                    //comparar la placa buscada
                    if(placa.equalsIgnoreCase(placaBuscada.trim())){
                        String modelo= datos[1].trim();
                        String capacidad= datos[2].trim();
                        String estado= datos[3].trim();
                        return new UnidadBus(placa, modelo, capacidad, estado);
                    }
                }

            }
            }catch(IOException e){
                System.out.println("Error al buscar la placa: "+e.getMessage());;
            }

            return null;
        }


}