package vista;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import util.*;

public class SeleccionarHorario extends JDialog {
    private BotonUtil botonAceptar;
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

    public SeleccionarHorario(JFrame pantallaPadre, List<String[]> filasDatos){
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

        JLabel titulo = new JLabel("Seleccione una opción:");
        titulo.setFont(fuenteT);
        titulo.setForeground(azulCuadros);
        titulo.setBounds(35, 15, 500, 40);
        panelFondo.add(titulo);

        // Columnas 
        String[] columnas = {"", "Placa", "Cupos disponibles", "Tipo de ruta", "Destino", "Punto de partida", "Punto de llegada", "Día", "Horario"};
        
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class; // Casilla de verificación
                return String.class;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; 
            }
            
            // Lógica para permitir solo una selección a la vez 
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
                    datos[1], // Cupos
                    datos[2], // Tipo ruta
                    datos[3], // Destino
                    datos[4], // Partida
                    datos[5], // Llegada
                    datos[6], // Día
                    datos[7]  // Horario
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
        
        // Ajustar ancho de la columna de selección
        tablaHorarios.getColumnModel().getColumn(0).setMaxWidth(40);
        tablaHorarios.getColumnModel().getColumn(0).setPreferredWidth(30);

        JScrollPane scroll = new JScrollPane(tablaHorarios);
        scroll.setBounds(35, 65, 1035, 320);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(150, 180, 210), 1));
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        panelFondo.add(scroll);

        // Boton de aceptar
        botonAceptar = new BotonUtil("Aceptar", azulCuadros, Color.WHITE, 14, fuenteSubT, 150, 40);
        botonAceptar.setBounds(480, 410, 150, 40);

        panelFondo.add(botonAceptar);
    }

    public BotonUtil getBotonAceptar(){ 
        return botonAceptar; 
    }

    // Método main para probar la interfaz
    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<String[]> prueba = new ArrayList<>();
            prueba.add(new String[]{" ", " ", " ", " ", " ", " ", " ", " "});
            prueba.add(new String[]{" ", " ", " ", " ", " ", " ", " ", " "});

            SeleccionarHorario ventana = new SeleccionarHorario(null, prueba);
            ventana.setVisible(true);
        });
    }*/
}