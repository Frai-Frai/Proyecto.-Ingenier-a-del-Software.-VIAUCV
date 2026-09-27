package util;
import javax.swing.*;
import java.awt.*;

public class MiniVentanaUtil extends JPanel {
    private int radioBorde;
    private Color colorBorde;
    private boolean hayBorde;

    public MiniVentanaUtil(int radioBorde, Color colorFondo, int ancho, int alto, boolean hayBorde, Color colorBorde) {
        this.radioBorde = radioBorde;
        this.colorBorde = colorBorde;
        this.hayBorde = hayBorde;
        

        setPreferredSize(new Dimension(ancho, alto));
        setSize(ancho, alto);
        setBackground(colorFondo);
        setOpaque(false); // Para que el fondo transparente
        setLayout(null); //poner botones y texto donde quiera

        //SI NO TIENE BORDES SE PONE ASI AL LLMARLO: (radioBorde, colorFondo, ancho, alto, false, null);

    }

        @Override 
        protected void paintComponent(Graphics g) {

        Graphics2D g2= (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, radioBorde, radioBorde);

        if(hayBorde && colorBorde!=null){
            g2.setColor(colorBorde);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radioBorde, radioBorde);
        }

        g2.dispose();
        super.paintComponent(g);
        }


}

