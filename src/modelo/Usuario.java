package modelo;

public class Usuario {
    String cedula;
    String nombreUsuario;
    String correo;
    String rol;
    String clave;
    double saldo;

    //De esta forma se ingresan los datos
    public Usuario(String cedula, String nombre,String correo,String rol,String clave,double saldo){
        this.cedula=cedula;
        this.nombreUsuario=nombre;
        this.correo=correo;
        this.rol=rol;
        this.clave=clave;
        this.saldo=saldo;
    }

    //Getters
    public String getCedula(){
        return cedula;
    }
    public String getNombreUsuario(){
        return nombreUsuario;
    }
    public String getCorreo(){
        return correo;
    }
    public String getClave(){
        return clave;
    }
    public double getSaldo(){
        return saldo;
    }
    public String getRol(){
        return rol;
    }

    //Setters (Para cambiar la clave y el saldo)
    public void setClave(String cNueva){
        this.clave=cNueva;
    }
    public void setSaldo(double sNuevo){
        this.saldo= sNuevo;
    }

    //Escribirlo con el formato de una
    public String EscribirFormatoTXT(){
        return cedula +"|"+ nombreUsuario +"|"+ correo +"|"+ rol +"|"+ clave +"|"+ saldo;
    }

}
