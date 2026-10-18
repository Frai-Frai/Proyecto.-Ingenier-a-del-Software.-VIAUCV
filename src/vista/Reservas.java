package vista;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import modelo.ItinerarioModelo;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import util.*;

public class Reservas extends JDialog {
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

        // Columnas 
        String[] columnas = {"Placa", "Tipo de ruta", "Destino", "Punto de partida", "Punto de llegada", "Día", "Horario"};
        
        // Tabla solo de información (ninguna celda es editable)
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        if(filasDatos != null) {
            for(String[] datos : filasDatos) {
                Object[] fila = {
                    datos[0], // Placa
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

        // Sin efecto de selección al hacer click
        tablaHorarios.setRowSelectionAllowed(false);
        tablaHorarios.setColumnSelectionAllowed(false);
        tablaHorarios.setCellSelectionEnabled(false);
        tablaHorarios.setFocusable(false);

        JScrollPane scroll = new JScrollPane(tablaHorarios);
        scroll.setBounds(35, 65, 1035, 320);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(150, 180, 210), 1));
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        panelFondo.add(scroll);
    }

    public JTable getTablaSeleccionada(){
        return tablaHorarios;
    }

    // Método main para probar la interfaz
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<String[]> prueba = new ArrayList<>();
            prueba.add(new String[]{" ", " ", " ", " ", " ", " ", " ", " "});
            prueba.add(new String[]{" ", " ", " ", " ", " ", " ", " ", " "});

            Reservas ventana = new Reservas(null, prueba);
            ventana.setVisible(true);
        });
    }
}