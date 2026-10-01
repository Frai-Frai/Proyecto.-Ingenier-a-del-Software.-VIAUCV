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
        // Validación de campos vacíos
        if (placa == null || placa.trim().isEmpty() || 
            modelo == null || modelo.trim().isEmpty() || 
            capacidad == null || capacidad.trim().isEmpty()) {
            if (vista != null) {
                JOptionPane.showMessageDialog(vista, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return "Error: Campos incompletos";
        }

        // Validación de placa con símbolos no permitidos
        if (!placa.matches("^[a-zA-Z0-9]+$")) {
            if (vista != null) {
                JOptionPane.showMessageDialog(vista, "Placa con caracteres no permitidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return "Error: Placa con caracteres no permitidos";
        }

        // Validación de longitud de placa inválida
        if (placa.length() < 3 || placa.length() > 8) {
            if (vista != null) {
                JOptionPane.showMessageDialog(vista, "Longitud de placa inválida", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return "Error: Longitud de placa inválida";
        }

        // Validación de placa duplicada
        List<UnidadBus> listaUnidades = dao.obtenerUnidades();
        for (UnidadBus unidad : listaUnidades) {
            if (unidad.getPlaca().equalsIgnoreCase(placa)) {
                if (vista != null) {
                    JOptionPane.showMessageDialog(vista, "Placa duplicada", "Error", JOptionPane.ERROR_MESSAGE);
                }
                return "Error: Placa duplicada";
            }
        }

        // Validación de números no enteros en capacidad
        if (capacidad.contains(".") || capacidad.contains(",")) {
            if (vista != null) {
                JOptionPane.showMessageDialog(vista, "Capacidad con números no enteros", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return "Error: Capacidad con números no enteros";
        }

        // Validación de caracteres no numéricos en capacidad
        int capVal;
        try {
            capVal = Integer.parseInt(capacidad);
        } catch (NumberFormatException e) {
            if (vista != null) {
                JOptionPane.showMessageDialog(vista, "Capacidad con caracteres no numéricos", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return "Error: Capacidad con caracteres no numéricos";
        }

        // Validación de capacidad menor o igual a cero
        if (capVal <= 0) {
            if (vista != null) {
                JOptionPane.showMessageDialog(vista, "Capacidad menor o igual a cero", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return "Error: Capacidad menor o igual a cero";
        }

        // Validación de rango permitido (Entre 20 y 50)
        if (capVal < 20 || capVal > 50) {
            if (vista != null) {
                JOptionPane.showMessageDialog(vista, "Capacidad fuera de rango", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return "Error: Capacidad fuera de rango";
        }

        // Caso de éxito
        UnidadBus nuevaUnidad = new UnidadBus(placa, modelo, capacidad, "Activo");
        dao.registrarUnidad(nuevaUnidad);
        
        if (vista != null) {
            JOptionPane.showMessageDialog(vista, "¡Unidad registrada con éxito!");
            cargarBusesEnVista();
        }
        
        return "Registro exitoso";
    }

    public void cambiarEstadoBus(String placa, String nuevoEstado) {
        dao.actualizarEstado(placa, nuevoEstado);
        cargarBusesEnVista();
    }
}