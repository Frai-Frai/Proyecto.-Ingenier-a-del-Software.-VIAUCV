package vista;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.io.File;
import java.util.List;
import util.*;

public class Reservas extends JDialog {
    private BotonUtil botonTicket;
    private JTable tablaHorarios;
    private DefaultTableModel modeloTabla;
    private List<String[]> listaDatos; 

    // Tipografía 
    private Font fuenteGlacial(String ruta, float tamano, int estilo){
        try {
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, new File(ruta));
            return fuente.deriveFont(estilo, tamano);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la fuente: " + e.getMessage());
            return new Font("SansSerif", estilo, (int) tamano);
        }
    }

    private Font League(String ruta, float tamano, int estilo){
        try {
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, new File(ruta));
            return fuente.deriveFont(estilo, tamano);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la fuente: " + e.getMessage());
            return new Font("SansSerif", estilo, (int) tamano);
        }
    }

    public Reservas(JFrame pantallaPadre, List<String []> filasDatos){
        super(pantallaPadre, "Seleccionar opción - Vista de Tabla", true);

        this.listaDatos = filasDatos;

        setSize(1120, 520); 
        setResizable(true);
        setLocationRelativeTo(pantallaPadre); 
        setLayout(new BorderLayout());

        Font fuenteT = League("res/LeagueSpartan-Bold.otf", 20, Font.PLAIN); 
        Font fuenteSubT = fuenteGlacial("res/GlacialIndifference-Bold.otf", 14, Font.BOLD); 
        Font fuenteLetras = fuenteGlacial("res/GlacialIndifference-Regular.otf", 13, Font.PLAIN);
        Font fuenteHeaders = fuenteGlacial("res/GlacialIndifference-Bold.otf", 13, Font.BOLD);
        
        Color azulCuadros = new Color(0x0D47A1); 
        Color azulPastelC = new Color(0xE1F5FE); 
        
        getContentPane().setBackground(azulPastelC);

        JPanel panelFondo = new JPanel();
        panelFondo.setLayout(null);
        panelFondo.setOpaque(false);
        add(panelFondo, BorderLayout.CENTER);

        JLabel titulo = new JLabel("Reservas realizadas:", SwingConstants.CENTER);
        titulo.setFont(fuenteT);
        titulo.setForeground(azulCuadros);
        titulo.setBounds(35, 15, 1035, 40);
        panelFondo.add(titulo);

        // Columnas (la primera es la casilla de selección)
        String[] columnas = {"", "Placa", "Tipo de ruta", "Destino", "Punto de partida", "Punto de llegada", "Día", "Horario"};
        
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class; // cuadrito de verificación
                return String.class;
            }

            // Solo la casilla se puede editar
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; 
            }

            // para que permita solo una seleccion a la vez 
            @Override
            public void setValueAt(Object aValue, int row, int column) {
                if (column == 0 && Boolean.TRUE.equals(aValue)) {
                    for (int i = 0; i < getRowCount(); i++) {
                        super.setValueAt(false, i, 0);
                    }
                }
                super.setValueAt(aValue, row, column);
            }
        };

        if(filasDatos != null) {
            for(String[] datos : filasDatos) {
                Object[] fila = {
                    false,
                    datos[0], // Placa
                    datos[1], // Tipo ruta
                    datos[2], // Destino
                    datos[3], // Partida
                    datos[4], // Llegada
                    datos[5], // Día
                    datos[6]  // Horario
                };
                modeloTabla.addRow(fila);
            }
        }

        tablaHorarios = new JTable(modeloTabla);
        tablaHorarios.setFont(fuenteLetras);
        tablaHorarios.getTableHeader().setFont(fuenteHeaders);
        tablaHorarios.getTableHeader().setBackground(new Color(214, 233, 245));
        tablaHorarios.getTableHeader().setForeground(azulCuadros);
        tablaHorarios.setRowHeight(30);
        tablaHorarios.setShowGrid(true);
        tablaHorarios.setGridColor(new Color(180, 210, 235));

        // ajustar ancho de la columna de seleccion
        tablaHorarios.getColumnModel().getColumn(0).setMaxWidth(40);
        tablaHorarios.getColumnModel().getColumn(0).setPreferredWidth(30);

        JScrollPane scroll = new JScrollPane(tablaHorarios);
        scroll.setBounds(35, 65, 1035, 320);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(150, 180, 210), 1));
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        panelFondo.add(scroll);

        // Boton de visualizar ticket 
        FontMetrics fm = new JLabel().getFontMetrics(fuenteSubT);
        int anchoTicket = fm.stringWidth("Visualizar ticket") + 50;
        int xTicket = 35 + (1035 - anchoTicket) / 2; 

        botonTicket = new BotonUtil("Visualizar ticket", azulCuadros, Color.WHITE, 14, fuenteSubT, anchoTicket, 40);
        botonTicket.setBounds(xTicket, 410, anchoTicket, 40);
        panelFondo.add(botonTicket);
    }

    public JTable getTablaSeleccionada(){
        return tablaHorarios;
    }

    public BotonUtil getBotonTicket(){
        return botonTicket;
    }

    // devuelve el indice de la fila marcada con la casilla, o -1 si no hay ninguna
    public int getFilaSeleccionada(){
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if (Boolean.TRUE.equals(modeloTabla.getValueAt(i, 0))) {
                return i;
            }
        }
        return -1;
    }

    // devuelve los datos de la reserva marcada, o null si no hay ninguna
    public String[] getDatosSeleccionados(){
        int fila = getFilaSeleccionada();
        if (fila == -1 || listaDatos == null) return null;
        return listaDatos.get(fila);
    }  
}