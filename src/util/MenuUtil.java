package util; // para que sepa que este archivo es de util
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;//para que no se vea el cuadro gris del menu
import java.awt.*;

public class MenuUtil extends JComboBox <String>{
    private int radio;
    private Color colorFondoM;
    private Color ColorBordeM;

    public MenuUtil(String[] opciones, int radio, Color colorFondoM, Color Colotexto, Color ColorBordeM, Font fuenteM){

        super(opciones);
        this.radio = radio;
        this.colorFondoM = colorFondoM;
        this.ColorBordeM = ColorBordeM;
        Color flecha = new Color(0x0D47A1);

        setFont(fuenteM);
        setForeground(Colotexto);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        setUI(new BasicComboBoxUI(){ //para ver las opciones dandole a una flecha
            @Override
            protected JButton createArrowButton() {
                JButton boton = new JButton("▼");
                boton.setContentAreaFilled(false);
                boton.setBorder(BorderFactory.createEmptyBorder()); //sin borde
                boton.setForeground(flecha);
                boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                return boton;
            }

            //para que no se vea el cuadro gris que se pinta de fondo
            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                // se queda vacío para que no pinte nada
            }

            // la lista de opciones con el mismo color de borde que el cuadro para que no se note separacion
            @Override
            protected javax.swing.plaf.basic.ComboPopup createPopup() {
                javax.swing.plaf.basic.BasicComboPopup popup = (javax.swing.plaf.basic.BasicComboPopup) super.createPopup();
                popup.setBorder(BorderFactory.createLineBorder(ColorBordeM, 1)); 
                return popup;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics graficos) {//crea y suaviza las esquinas del cuadro
        Graphics2D grafico = (Graphics2D) graficos;
        grafico.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        grafico.setColor(colorFondoM);
        grafico.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);
        
        super.paintComponent(graficos); 
    }

    @Override
    protected void paintBorder(Graphics graficos) { //se encarga del borde y tambien lo suaviza
        Graphics2D grafico = (Graphics2D) graficos;
        grafico.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        grafico.setColor(ColorBordeM);
      
        grafico.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radio, radio); //el -1 es por la forma de los bordes
    }
}