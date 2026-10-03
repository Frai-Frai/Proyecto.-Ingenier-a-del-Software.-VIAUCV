package controlador;

import modelo.*;
import vista.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import java.awt.*;
import java.util.List;


public class Controlador_RegistroUnidades {
    
    private RegistroUnidades vista;
    private UnidadesDAO dao;

    public Controlador_RegistroUnidades(RegistroUnidades vista) {
        this.vista = vista;
        this.vista.setControlador(this);
        this.dao = new UnidadesDAO();
        
        if (this.vista != null) {
            cargarBusesEnVista();
        }

        this.vista.getOpPlanificar().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                IrAItinerario();
            }
        });

        this.vista.getOpCerrarS().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                irInicio();
            }
        });
    }

    public void cargarBusesEnVista() {
        List<UnidadBus> listaUnidades = dao.obtenerUnidades();
        if (vista != null) {
            vista.actualizarPanelBuses(listaUnidades);
        }
    }

    public void manejarRegistroUnidad() {
        String placa = vista.getTxtPlaca().getText().equals("Placa") ? "" : vista.getTxtPlaca().getText().trim();
        String modelo = vista.getTxtModelo().getText().equals("Modelo") ? "" : vista.getTxtModelo().getText().trim();
        String capacidad = vista.getTxtCapacidad().getText().equals("Capacidad de pasajeros") ? "" : vista.getTxtCapacidad().getText().trim();

        String resultado = registrarUnidad(placa, modelo, capacidad);

        if (resultado.equals("Registro exitoso")) {
            // Limpiar campos y restablecer placeholders en la vista
            vista.getTxtPlaca().setText("Placa"); vista.getTxtPlaca().setForeground(Color.GRAY);
            vista.getTxtModelo().setText("Modelo"); vista.getTxtModelo().setForeground(Color.GRAY);
            vista.getTxtCapacidad().setText("Capacidad de pasajeros"); vista.getTxtCapacidad().setForeground(Color.GRAY);
        }
    }

    public String registrarUnidad(String placa, String modelo, String capacidad) {
        if (placa == null || placa.trim().isEmpty() || 
            modelo == null || modelo.trim().isEmpty() || 
            capacidad == null || capacidad.trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return "Error: Campos incompletos";
        }

        if (!placa.matches("^[a-zA-Z0-9]+$")) {
            JOptionPane.showMessageDialog(vista, "Placa con caracteres no permitidos", "Error", JOptionPane.ERROR_MESSAGE);
            return "Error: Placa con caracteres no permitidos";
        }

        if (placa.length() < 3 || placa.length() > 8) {
            JOptionPane.showMessageDialog(vista, "Longitud de placa inválida", "Error", JOptionPane.ERROR_MESSAGE);
            return "Error: Longitud de placa inválida";
        }

        List<UnidadBus> listaUnidades = dao.obtenerUnidades();
        for (UnidadBus unidad : listaUnidades) {
            if (unidad.getPlaca().equalsIgnoreCase(placa)) {
                JOptionPane.showMessageDialog(vista, "Placa duplicada", "Error", JOptionPane.ERROR_MESSAGE);
                return "Error: Placa duplicada";
            }
        }

        int capVal;
        try {
            capVal = Integer.parseInt(capacidad);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Capacidad con caracteres no numéricos", "Error", JOptionPane.ERROR_MESSAGE);
            return "Error: Capacidad con caracteres no numéricos";
        }

        if (capVal <= 0) {
            JOptionPane.showMessageDialog(vista, "Capacidad menor o igual a cero", "Error", JOptionPane.ERROR_MESSAGE);
            return "Error: Capacidad menor o igual a cero";
        }

        if (capVal < 20 || capVal > 50) {
            JOptionPane.showMessageDialog(vista, "Capacidad fuera de rango", "Error", JOptionPane.ERROR_MESSAGE);
            return "Error: Capacidad fuera de rango";
        }

        UnidadBus nuevaUnidad = new UnidadBus(placa, modelo, capacidad, "Activo");
        dao.registrarUnidad(nuevaUnidad);
        
        JOptionPane.showMessageDialog(vista, "¡Unidad registrada con éxito!");
        cargarBusesEnVista();
        
        return "Registro exitoso";
    }

    public void cambiarEstadoBus(String placa, String nuevoEstado) {
        dao.actualizarEstado(placa, nuevoEstado);
        cargarBusesEnVista();
    }

    public List<UnidadBus> obtenerBusesActivosDesdeBD() {
        return dao.obtenerBusesPorEstado("Activo");
    }

    public void mostrarVentanaBusesActivos() {
        Font fuenteBold = Font.decode("GlacialIndifference-Bold-16"); // O usa un método de carga si prefieres
        Font fuenteRegular = new Font("SansSerif", Font.PLAIN, 14);

        JDialog dialogoActivos = new JDialog(vista, "Autobuses Activos", true);
        dialogoActivos.setSize(400, 450);
        dialogoActivos.setLocationRelativeTo(vista);
        dialogoActivos.setLayout(new BorderLayout());

        JPanel panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelContenido.setBackground(new Color(240, 243, 246));

        JLabel lblTitulo = new JLabel("Unidades en Estado Activo", JLabel.CENTER);
        lblTitulo.setFont(fuenteBold);
        lblTitulo.setForeground(new Color(30, 80, 135));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panelContenido.add(lblTitulo, BorderLayout.NORTH);

        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        
        try {
            List<UnidadBus> activosBD = obtenerBusesActivosDesdeBD();

            for (UnidadBus bus : activosBD) {
                modeloLista.addElement("Placa: " + bus.getPlaca() + " - Modelo: " + bus.getModelo());
            }

            if (modeloLista.isEmpty()) {
                modeloLista.addElement("No hay unidades activas registradas en la base de datos.");
            }
        } catch (Exception e) {
            modeloLista.addElement("Error al consultar la base de datos.");
            System.out.println("Error: " + e.getMessage());
        }

        JList<String> listaBusesActivos = new JList<>(modeloLista);
        listaBusesActivos.setFont(fuenteRegular);
        listaBusesActivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(listaBusesActivos);
        panelContenido.add(scrollPane, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("SansSerif", Font.BOLD, 13));
        JPanel panelSur = new JPanel();
        panelSur.setOpaque(false);
        panelSur.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panelSur.add(btnCerrar);
        
        btnCerrar.addActionListener(ev -> dialogoActivos.dispose());
        panelContenido.add(panelSur, BorderLayout.SOUTH);

        dialogoActivos.add(panelContenido);
        dialogoActivos.setVisible(true);
    }

    public void IrAItinerario(){
         // 1. Rescatas al usuario de la ventana de unidades
        this.vista.setVisible(false);
        this.vista.dispose(); //se cierra la pantalla de g de unidades
        
        Usuario admin = vista.getAdminActual();
 
        Itinerario pantallaItinerario = new Itinerario(admin); //se crea la pantalla de itinerario
        new Controlador_Itinerario(pantallaItinerario);
        pantallaItinerario.setVisible(true);
    }


    private void irInicio(){
        
        this.vista.setVisible(false);
        this.vista.dispose();
        Inicio pantallaI = new Inicio();
        new Controlador_Inicio(pantallaI);
        pantallaI.setVisible(true);
        
    }
}