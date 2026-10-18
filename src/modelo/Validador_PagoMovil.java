package modelo;

public class Validador_PagoMovil {
    
    public Validador_PagoMovil(){

    }

    public String ValidarDatos(String numero, String cedula, String monto, Usuario u){

        if(numero.trim().isEmpty() && cedula.trim().isEmpty() && monto.trim().isEmpty()){
            return "Por favor, complete todos lo campos del formulario.";
        }

        StringBuilder errores = new StringBuilder();

        if(numero.trim().isEmpty()){
            errores.append("- El campo de número no puede estar vacío.\n");
        }else if(!numero.trim().matches("^\\d{7}$")){
            errores.append("- El campo de número solo acepta 7 valores numéricos.\n");
        }

        if(cedula.trim().isEmpty()){
            errores.append("- El campo de cédula no puede estar vacío.\n");
        } else if(!cedula.trim().matches("\\d+")){
            errores.append("- La cédula solo debe contener números.\n");
        }else{
            int cedulaNumerica= Integer.parseInt(cedula.trim());
            if(cedulaNumerica < 1000000 || cedulaNumerica> 99999999){
            errores.append("- La cédula debe estar dentro del rango [1.000.000 -99.999.999]\n");
            }
        }

        if(monto.trim().isEmpty()){
            errores.append("- El campo de monto no puede estar vacío.\n");
        }else if(!monto.trim().matches("^\\d+([.,]\\d{1,2})?$")){
            errores.append("- El monto solo debe contener números (enteros o decimales).\n");
        }else{
            Double montoNum = Double.parseDouble(monto);
            if(montoNum < 0 || montoNum > 10000){
                errores.append("- El monto tiene que estar debajo de 10.000\n");
            }
        }

        if(errores.length()>0){
            return errores.toString();
        }

        double montoNum=0.0;
        try {
            montoNum = Double.parseDouble(monto.trim());
        } catch (NumberFormatException e) {
            return "El monto ingresado no tiene un formato numérico válido.";
        }

        //monto insuficiente
        if(u.getSaldo()< montoNum){
            return "Saldo insuficiente para realizar la transacción. Por favor, recargue en su monedero "+montoNum+"bs";
        }

        return "Pago exitoso";
    }

}
