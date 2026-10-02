package controlador;

import modelo.UnidadBus;
import modelo.UnidadesDAO;
import vista.RegistroUnidades;

import javax.swing.*;
import java.util.List;

public class Controlador_RegistroUnidades {
    
    private RegistroUnidades vista;
    private UnidadesDAO dao;

    public Controlador_RegistroUnidades(RegistroUnidades vista) {
        this.vista = vista;
        this.dao = new UnidadesDAO();
        
        if (this.vista != null) {
            cargarBusesEnVista();
        }
    }

    public void cargarBusesEnVista() {
        List<UnidadBus> listaUnidades = dao.obtenerUnidades();
        if (vista != null) {
            vista.actualizarPanelBuses(listaUnidades);
        }
    }

    public String registrarUnidad(String placa, String modelo, String capacidad) {
        if (placa == null || placa.trim().isEmpty() || 
            modelo == null || modelo.trim().isEmpty() || 
            capacidad == null || capacidad.trim().isEmpty()) {
            if (vista != null) {
                JOptionPane.showMessageDialog(vista, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return "Error: Campos incompletos";
        }

        if (!placa.matches("^[a-zA-Z0-9]+$")) {
            if (vista != null) {
                JOptionPane.showMessageDialog(vista, "Placa con caracteres no permitidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return "Error: Placa con caracteres no permitidos";
        }

        if (placa.length() < 3 || placa.length() > 8) {
            if (vista != null) {
                JOptionPane.showMessageDialog(vista, "Longitud de placa inválida", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return "Error: Longitud de placa inválida";
        }

        List<UnidadBus> listaUnidades = dao.obtenerUnidades();
        for (UnidadBus unidad : listaUnidades) {
            if (unidad.getPlaca().equalsIgnoreCase(placa)) {
                if (vista != null) {
                    JOptionPane.showMessageDialog(vista, "Placa duplicada", "Error", JOptionPane.ERROR_MESSAGE);
                }
                return "Error: Placa duplicada";
            }
        }

        if (capacidad.contains(".") || capacityCheck(capacidad)) { // Validación segura de enteros
            // ...
        }

        int capVal;
        try {
            capVal = Integer.parseInt(capacidad);
        } catch (NumberFormatException e) {
            if (vista != null) {
                JOptionPane.showMessageDialog(vista, "Capacidad con caracteres no numéricos", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return "Error: Capacidad con caracteres no numéricos";
        }

        if (capVal <= 0) {
            if (vista != null) {
                JOptionPane.showMessageDialog(vista, "Capacidad menor o igual a cero", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return "Error: Capacidad menor o igual a cero";
        }

        if (capVal < 20 || capVal > 50) {
            if (vista != null) {
                JOptionPane.showMessageDialog(vista, "Capacidad fuera de rango", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return "Error: Capacidad fuera de rango";
        }

        UnidadBus nuevaUnidad = new UnidadBus(placa, modelo, capacidad, "Activo");
        dao.registrarUnidad(nuevaUnidad);
        
        if (vista != null) {
            JOptionPane.showMessageDialog(vista, "¡Unidad registrada con éxito!");
            cargarBusesEnVista();
        }
        
        return "Registro exitoso";
    }

    private boolean capacityCheck(String cap) {
        return cap.contains(".") || cap.contains(",");
    }

    public void cambiarEstadoBus(String placa, String nuevoEstado) {
        dao.actualizarEstado(placa, nuevoEstado);
        cargarBusesEnVista();
    }

    public List<UnidadBus> obtenerBusesActivosDesdeBD() {
        return dao.obtenerBusesPorEstado("Activo");
    }
}