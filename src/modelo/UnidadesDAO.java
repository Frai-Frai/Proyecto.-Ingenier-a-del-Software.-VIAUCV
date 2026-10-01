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
}