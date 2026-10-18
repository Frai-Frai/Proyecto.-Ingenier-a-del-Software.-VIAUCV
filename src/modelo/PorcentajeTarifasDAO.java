package modelo;
import java.io.*;

public class PorcentajeTarifasDAO {
    private String tarifasTxt;

    public PorcentajeTarifasDAO(){
        this.tarifasTxt="data/Tarifas.txt";
    }

    // Constructor para las Pruebas Unitarias (Usa el archivo temporal)
    public PorcentajeTarifasDAO(String ruta) {
        this.tarifasTxt = ruta;
    }

    //Obtener porcentaje por rol
    public double obtenerPorcentajePorRol(String rol){
        String buscarRol= rol.trim();

        //roles no incluidos
        if (buscarRol.equalsIgnoreCase("Público General") || buscarRol.equalsIgnoreCase("Administrador") || buscarRol.equalsIgnoreCase("Conductor")) {
            return 1.0;
        }

        if(buscarRol.equalsIgnoreCase("Docente")){  //si es docente, se toma como empleado
            buscarRol="Empleado";
        }
        
        File archivo = new File(tarifasTxt);
        double porcentaje=1.0; // se guarda el porcentaje dependiendo del rol

        if (archivo.exists()){
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split("="); //separo por el =
                    if (partes.length == 2) {
                        if(partes[0].equalsIgnoreCase(buscarRol)){ //si la primera parte del txt coincide
                            porcentaje=Double.parseDouble(partes[1]);
                            break;
                        }
                    }
                } 
            }catch (IOException e) {
                System.err.println("Error al cargar porcentajes del TXT: " + e.getMessage());
            }      
        }
        return porcentaje;
    }

    //actualizar tarifas globales
    public void actualizarTarifas(String porcentajeEstudiante, String porcentajeEmpleado) {   
        double pEstudiante= Double.parseDouble(porcentajeEstudiante);
        double pEmpleado= Double.parseDouble(porcentajeEmpleado);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tarifasTxt,false))) {
            bw.write("Estudiante=" + pEstudiante);
            bw.newLine();
            bw.write("Empleado=" + pEmpleado);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar porcentajes en el archivo: " + e.getMessage());
        }
    }

    //funcion que calcula la tarifa segun el tipo de usuario y el porcentaje establecido (DAO)
    public double calcularCobroSolidario(String rol, String tarifaBase){
        double tarifa= Double.parseDouble(tarifaBase);
        double porcentaje= obtenerPorcentajePorRol(rol)/100.0;  //se usa para ponerlo como 0.40

        return tarifa*porcentaje; //retorna lo que se paga
    }

}
