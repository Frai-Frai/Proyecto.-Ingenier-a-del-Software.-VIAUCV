package modelo;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

//DATA ACCESS
public class UsuarioDAO {
    private static final String Ruta_BDViaUCV= "data/BDViaUCV.txt";
    private static final String Ruta_BDUCV= "data/BDUCV.txt";

    //PAra tener todo los usuarios de la pagina
    public List<Usuario> obtenerUsuarios(){
        List<Usuario> lista= new ArrayList<>();
        File f= new File(Ruta_BDViaUCV);

        if(!f.exists()) return lista;

        try(BufferedReader b=new BufferedReader(new FileReader(f))){
            String linea;

            while((linea = b.readLine()) !=null){ //recorre toda la lista
                if(linea.trim().isEmpty()) continue;

                String[] datos= linea.split("\\|");
                double saldo= Double.parseDouble(datos[5].trim()); //Pasar de string a double

                if (datos.length==6){
                        
                    Usuario u=new Usuario(datos[0], datos[1], datos[2], datos[3], datos[4], saldo);
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
        File f= new File(Ruta_BDViaUCV);

        if(!f.exists()){
            return listaRol; //si el archivo no existe 
        }

        try(BufferedReader b=new BufferedReader(new FileReader(f))){
            String linea;

            while((linea = b.readLine()) !=null){ //recorre toda la lista
                if(linea.trim().isEmpty()) continue;

                String[] datos= linea.split("\\|");

                if (datos.length==6){
                    String rolUsuario= datos[3].trim(); //donde esta el rol

                    //comparar el rol buscado
                    if(rolUsuario.equalsIgnoreCase(rolBuscado.trim())){
                        double saldo= Double.parseDouble(datos[5].trim()); //Pasar de string a double
                        Usuario u=new Usuario(datos[0], datos[1], datos[2], datos[3], datos[4], saldo);
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
            File f=new File(Ruta_BDViaUCV);

            if(!f.exists()) return null;

            try(BufferedReader b=new BufferedReader(new FileReader(f))){
                String linea;

            while((linea = b.readLine()) !=null){ //recorre toda la lista
                if(linea.trim().isEmpty()) continue;

                String[] datos= linea.split("\\|");

                if (datos.length==6){
                    String cedulaUsuario= datos[0].trim(); //donde esta la cedula

                    //comparar la cedula buscado
                    if(cedulaUsuario.equalsIgnoreCase(cedulaBuscada.trim())){
                        double saldo= Double.parseDouble(datos[5].trim()); //Pasar de string a double
                        return new Usuario(datos[0], datos[1], datos[2], datos[3], datos[4], saldo);     
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
            File f= new File(Ruta_BDViaUCV);
            if(!f.exists()) return false;

            try(BufferedReader b=new BufferedReader(new FileReader(f))){
                String linea;

                while((linea = b.readLine()) !=null){ //recorre toda la lista
                if(linea.trim().isEmpty()) continue;

                    String[] datos= linea.split("\\|");

                    if (datos.length>=1){
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
            File f= new File(Ruta_BDViaUCV);
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

        //anadir nuevo usuario
        public boolean anadirUsuario(Usuario uNuevo){
            File f= new File(Ruta_BDViaUCV);

            try(BufferedWriter bw= new BufferedWriter(new FileWriter(f, true))) { //lo anade al final junto a lo demas
                bw.write(uNuevo.EscribirFormatoTXT());
                bw.newLine();
                return true;

            }catch(IOException e){
                System.out.println("error al guardar en la base de datos: "+e.getMessage());
                return false;
            }
        }

        //BD de la UCV (resgistro)
        //retorna true si la cedula es de la BD de la universidad, false lo contrario
        public boolean cedulaRegistradaEnLaUCV (String cedulaBuscada){
            File f= new File(Ruta_BDUCV);
            if(!f.exists()) return false;

            try(BufferedReader b=new BufferedReader(new FileReader(f))){
                String linea;

                while((linea = b.readLine()) !=null){ //recorre toda la lista
                if(linea.trim().isEmpty()) continue;

                    String[] datos= linea.split("\\|");

                    if (datos.length>=1){
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

        //retorna true si el rol coincide con la de BD de la ucv
        
        public boolean rolCorresponde(String cedulaBuscada, String rolEsperado ){
            File f= new File(Ruta_BDUCV);
            if(!f.exists()) return false;

            try(BufferedReader b=new BufferedReader(new FileReader(f))){
                String linea;

                while((linea = b.readLine()) !=null){ //recorre toda la lista
                if(linea.trim().isEmpty()) continue;

                    String[] datos= linea.split("\\|");

                    if (datos.length>=2){
                    String cedulaf= datos[0].trim(); //cedula en el file
                    String rolf= datos[2].trim(); //donde esta el rol del file

                        //comparar la cedula buscado y el rol seleccionado
                        if(cedulaf.equals(cedulaBuscada.trim()) && rolf.equalsIgnoreCase(rolEsperado)){
                            return true;     
                        }
                    } 
                }   

            }catch(IOException e){
                System.out.println("Error al validar el rol: "+e.getMessage());;
            }
            return false;
        }
}
