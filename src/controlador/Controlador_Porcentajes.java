package controlador;
import vista.*;
import modelo.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador_Porcentajes {
    
    private Porcentajes miniVentana;
    private Validador_Porcentajes valida;
    private PorcentajeTarifasDAO Pdao;

    public Controlador_Porcentajes(Porcentajes miniVentana){
        this.miniVentana = miniVentana;
        this.Pdao = new PorcentajeTarifasDAO();
        this.valida = new Validador_Porcentajes(Pdao);
        
        //para que al abrir otra vez la mini ventana le salga los descuentos ingresados antes por si no quiere cambiar alguno
        cargarPrevio();

        this.miniVentana.getBotonAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarPorcentajes();
            }
        });
    }

    private void cargarPrevio(){
        double porcentajeEstudiante = Pdao.obtenerPorcentajePorRol("Estudiante");
        double porcentajeEmpyDocen = Pdao.obtenerPorcentajePorRol("Empleado" );

        miniVentana.getcuadroUser().setText(String.valueOf(porcentajeEstudiante));
        miniVentana.getCuadroEmpyDocente().setText(String.valueOf(porcentajeEmpyDocen));
    }

    private void procesarPorcentajes(){

        //toma lo que ingresó el admim
        String PEstudiante = miniVentana.getcuadroUser().getText().trim();
        String PEyD = miniVentana.getcuadroUser().getText().trim();
        
        String result = valida.validarPorcentaje(PEstudiante, PEyD);

        if(result.equals("Porcentaje válido")){
            try{
                Pdao.actualizarTarifas(PEstudiante, PEyD);

                JOptionPane.showMessageDialog(miniVentana, "Descuentos actualizados con éxito","Éxito",JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception e){
                JOptionPane.showMessageDialog(miniVentana, "Error al guardar","Error",JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(miniVentana, result, "Error de validación", JOptionPane.WARNING_MESSAGE);
        }  
    }   
}
