package vista;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import util.*;
import modelo.*;

public class EliminarRuta extends JDialog {
    private BotonUtil botonElim, botonCancelar;
    private JTable tablaRutas;
    private DefaultTableModel modeloTabla;
    private List<ItinerarioModelo> listaRutas;

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

    public EliminarRuta(JFrame pantallaIT, List <ItinerarioModelo> rutasRegistradas){
        super(pantallaIT, "Seleccionar rutas - Vista de Tabla", true);

        this.listaRutas = rutasRegistradas;

        setSize(920, 560);
        setResizable(true);
        setLocationRelativeTo(pantallaIT); 
        setLayout(new BorderLayout());

        Font fuenteT = League("res/LeagueSpartan-Bold.otf", 20, Font.BOLD); 
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

        JLabel titulo = new JLabel("Seleccione rutas a eliminar:");
        titulo.setFont(fuenteT);
        titulo.setForeground(azulCuadros);
        titulo.setBounds(35, 15, 500, 40);
        panelFondo.add(titulo);

        // Definición de columnas al estilo Excel
        String[] columnas = {"", "Placa", "Tipo de Ruta", "Destino", "Partida", "Llegada", "Día", "Hora", "Conductor"};
        
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class; // Casilla de verificación interactiva
                return String.class;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Solo la columna del checkbox se marca directamente
            }
        };

        if(rutasRegistradas != null) {
            for(ItinerarioModelo r : rutasRegistradas) {
                Object[] fila = {
                    false,
                    r.getPlacaAsignada(),
                    r.getTipoRuta(),
                    r.getDestino(),
                    r.getPuntoPartida(),
                    r.getPuntoLLegada(),
                    r.getDia(),
                    r.getHora(),
                    r.getConductor()
                };
                modeloTabla.addRow(fila);
            }
        }

        tablaRutas = new JTable(modeloTabla);
        tablaRutas.setFont(fuenteLetras);
        tablaRutas.getTableHeader().setFont(fuenteHeaders);
        tablaRutas.getTableHeader().setBackground(new Color(214, 233, 245));
        tablaRutas.getTableHeader().setForeground(azulCuadros);
        tablaRutas.setRowHeight(30);
        tablaRutas.setShowGrid(true);
        tablaRutas.setGridColor(new Color(180, 210, 235));
        
        // Ajustar ancho de la columna de selección
        tablaRutas.getColumnModel().getColumn(0).setMaxWidth(40);
        tablaRutas.getColumnModel().getColumn(0).setPreferredWidth(30);

        JScrollPane scroll = new JScrollPane(tablaRutas);
        scroll.setBounds(35, 65, 835, 360);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(150, 180, 210), 1));
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        panelFondo.add(scroll);

        // Botones de acción inferiores
        botonElim = new BotonUtil("Eliminar", azulCuadros, Color.WHITE, 14, fuenteSubT, 150, 40);
        botonElim.setBounds(275, 445, 150, 40);

        botonCancelar = new BotonUtil("Cancelar", azulCuadros, Color.WHITE, 14, fuenteSubT, 150, 40);
        botonCancelar.setBounds(495, 445, 150, 40);
        botonCancelar.addActionListener(e -> dispose()); 

        panelFondo.add(botonElim);
        panelFondo.add(botonCancelar);
    }

    public List<ItinerarioModelo> getRutasSeleccionadas(){
        List<ItinerarioModelo> seleccionadas = new ArrayList<>();
        for(int i = 0; i < modeloTabla.getRowCount(); i++){
            Boolean seleccionado = (Boolean) modeloTabla.getValueAt(i, 0);
            if(seleccionado != null && seleccionado){
                seleccionadas.add(listaRutas.get(i));
            }
        }
        return seleccionadas;
    }

    public BotonUtil getbotonElim(){ 
        return botonElim; 
    }

    public BotonUtil getbotonCancelar(){ 
        return botonCancelar; 
    }
}
