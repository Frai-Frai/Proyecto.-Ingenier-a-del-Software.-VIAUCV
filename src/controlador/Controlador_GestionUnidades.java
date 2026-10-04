package controlador;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;

import modelo.*;
import vista.*;


public class Controlador_GestionUnidades {
    private RegistroUnidades vista;
    UnidadesDAO unidadDao= new UnidadesDAO();

    public Controlador_GestionUnidades(RegistroUnidades vista){
        this.vista = vista;
        this.vista.setControlador(this);

        
        if (this.vista != null) {
            cargarBusesEnVista();
        }

        /*if (this.vista.getOpRegistrarP() != null) {
                this.vista.getOpRegistrarP().addActionListener(e -> manejarRegistroUnidad());
        }*/

        this.vista.getOpPlanificar().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                IrAItinerario();
            }
        });

        this.vista.getOpCerrarS().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                irInicio();
            }
        });
    }

    //validar
    public void ValidarRegistroUnidad() {
        UnidadBus unidad;

        Validador_GestionUnidades validador = new Validador_GestionUnidades(unidadDao);

        unidad= new UnidadBus(
            vista.getTxtPlaca().getText().trim(),
            vista.getTxtModelo().getText().trim(),
            vista.getTxtCapacidad().getText().trim(),
            " "  //estado se maneja d eotra forma, irrelvante
        );

        String resultado = validador.validar_RegistroUnidad(unidad);

        if (resultado.equals("Registro exitoso")) {
            unidadDao.registrarUnidad(unidad);
            JOptionPane.showMessageDialog(vista, "Ruta registrada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarBusesEnVista();
            limpiarCamposFormulario();
        } else {
            JOptionPane.showMessageDialog(vista, resultado, "Error de validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarBusesEnVista() {
        List<UnidadBus> listaUnidades = unidadDao.obtenerUnidades();
        if (vista != null) {
            vista.actualizarPanelBuses(listaUnidades);
        }
    }

    private void limpiarCamposFormulario() {
        vista.getTxtPlaca().setText("");
        vista.getTxtCapacidad().setText("");
        vista.getTxtModelo().setText("");
    }

    public void cambiarEstadoBus(String placa, String nuevoEstado) {
        unidadDao.actualizarEstado(placa, nuevoEstado);
        cargarBusesEnVista();
    }

    public List<UnidadBus> obtenerBusesActivosDesdeBD() {
        return unidadDao.obtenerBusesPorEstado("Activo");
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
         // se toma al usuario de la ventana de unidades
        this.vista.setVisible(false);
        this.vista.dispose(); //se cierra la vista de g de unidades
        
        Usuario admin = vista.getAdminActual();

        Itinerario vistaItinerario = new Itinerario(admin); //se crea la vista de itinerario
        new Controlador_Itinerario(vistaItinerario);
        vistaItinerario.setVisible(true);
    }

    private void irInicio(){
        this.vista.setVisible(false);
        this.vista.dispose();
        Inicio vistaI = new Inicio();
        new Controlador_Inicio(vistaI);
        vistaI.setVisible(true);
    }
}
