package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UniversidadDAO {
    //DATA ACCESS
    private String Ruta_BDUCV;

    public UniversidadDAO(){this.Ruta_BDUCV= "data/BDUCV.txt";} //Constructor vacio por defecto
    public UniversidadDAO(String rutaBDUCV){this.Ruta_BDUCV= rutaBDUCV;} //Constructor con string para la creacion de temp en pruebas unitarias

    //Para tener toda la comunidad universitaria
    public List<Usuario> obtenerUsuarios(){
        List<Usuario> lista= new ArrayList<>();
        File f= new File(Ruta_BDUCV);

        if(!f.exists()) return lista;

        try(BufferedReader b=new BufferedReader(new FileReader(f))){
            String linea;

            while((linea = b.readLine()) !=null){ //recorre toda la lista
                if(linea.trim().isEmpty()) continue;

                String[] datos= linea.split("\\|",-1);

                if (datos.length==6){
                    Usuario u=new Usuario(datos[0], "", datos[2], datos[3], "", 0.0);
                    lista.add(u); //anadir a la lista 
                    
                } 
            }

        }catch(IOException e){
            System.out.println("Error al leer la base de datos "+e.getMessage());
        }
        return lista;
    }

    //Retorna los que coincidan con el rol de la pagina
    public List<Usuario> buscarPorRol (String rolBuscado){
        List<Usuario> listaRol=new ArrayList<>();
        File f= new File(Ruta_BDUCV);

        if(!f.exists()){
            return listaRol; //si el archivo no existe 
        }

        try(BufferedReader b=new BufferedReader(new FileReader(f))){
            String linea;

            while((linea = b.readLine()) !=null){ //recorre toda la lista
                if(linea.trim().isEmpty()) continue;

                String[] datos= linea.split("\\|",-1);//que no se pierdan los ultimos campos vacios

                if (datos.length==6){
                    String rolUsuario= datos[3].trim(); //donde esta el rol

                    //comparar el rol buscado
                    if(rolUsuario.equalsIgnoreCase(rolBuscado.trim())){
                        Usuario u=new Usuario(datos[0].trim(), "", datos[2].trim(), datos[3].trim(), "", 0.0);
                        listaRol.add(u); //anadir a la lista si es el rol que se busca
                    }
                } 

            }

        }catch(IOException e){
            System.out.println("Error al filtrar por rol: "+e.getMessage());
        }
        return listaRol; //Retorno lista de los usuarios con ese rol
    }

    //Retorna el que coincide con la cedula de la pagina
        public Usuario buscarPorCedula (String cedulaBuscada){
            File f=new File(Ruta_BDUCV);

            if(!f.exists()) return null;

            try(BufferedReader b=new BufferedReader(new FileReader(f))){
                String linea;

            while((linea = b.readLine()) !=null){ //recorre toda la lista
                if(linea.trim().isEmpty()) continue;

                String[] datos= linea.split("\\|",-1);//que no se pierdan los ultimos campos vacios

                if (datos.length>=3){
                    String cedulaUsuario= datos[0].trim(); //donde esta la cedula
                    

                    //comparar la cedula buscado
                    if(cedulaUsuario.equalsIgnoreCase(cedulaBuscada.trim())){
                        return new Usuario(datos[0].trim(), "", datos[1].trim(), datos[2].trim(), "", 0.0);  
                    }
                } 

            }
            }catch(IOException e){
                System.out.println("Error al buscar la cedula: "+e.getMessage());;
            }

            return null;
        }

        //Existe la cedula o no en la pagina
        public boolean cedulaExistente(String cedulaBuscada){
            File f= new File(Ruta_BDUCV);
            if(!f.exists()) return false;

            try(BufferedReader b=new BufferedReader(new FileReader(f))){
                String linea;

                while((linea = b.readLine()) !=null){ //recorre toda la lista
                if(linea.trim().isEmpty()) continue;

                    String[] datos= linea.split("\\|",-1); //que no se pierdan los ultimos campos vacios

                    if (datos.length>=3){
                    String cedulaUsuario= datos[0].trim(); //donde esta la cedula

                        //comparar la cedula buscado
                        if(cedulaUsuario.equalsIgnoreCase(cedulaBuscada.trim())){
                            return true;     
                        }
                    } 
                }   

            }catch(IOException e){
                System.out.println("Error al buscar la cedula: "+e.getMessage());;
            }
            return false;
        }

        //Actualizar cosas (lo que le des como parametro, saldo o clave)
        public boolean actualizarDatos(Usuario uModificado){
            File f= new File(Ruta_BDUCV);
            if(!f.exists())return false;

            List<Usuario> todos= obtenerUsuarios();
            boolean encontrado=false;
                 
            try (BufferedWriter bw =new BufferedWriter(new FileWriter(f,false))){

                for (int i = 0; i < todos.size(); i++) {
                    Usuario u = todos.get(i);

                    if(u.getCedula().equals(uModificado.getCedula())){
                        bw.write(uModificado.EscribirFormatoTXT());
                        encontrado=true;
                    }else{// si no es el que buscamos no cmabiamos nada
                        bw.write(u.EscribirFormatoTXT());
                    }
                    bw.newLine(); //salto de linea

                }
                
            }catch(IOException e){
                System.out.println("Error al actualizar la base de datos: "+e.getMessage());
                return false;
            }

            return encontrado;
        }
}
