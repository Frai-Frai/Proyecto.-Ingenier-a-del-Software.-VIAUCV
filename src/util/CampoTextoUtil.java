package util;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CampoTextoUtil extends JTextField{
    private int radioBorde;
    private Color colorBorde;

    public CampoTextoUtil(int radioBorde, Color colorBorde, int ancho, int alto, Color colorFondo, Color colorTexto, Font fuente){
        super();
        this.radioBorde= radioBorde;
        this.colorBorde= colorBorde;

        setPreferredSize(new Dimension(ancho, alto));
        setFont(fuente);
        setBackground(colorFondo);
        setForeground(colorTexto);

        setOpaque(false);
        setBorder(new EmptyBorder(5, 10, 5, 10)); // Espaciado interno
    }


    @Override 
    protected void paintComponent(Graphics g){
        Graphics2D g2= (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, radioBorde, radioBorde);
        
        g2.setColor(colorBorde);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radioBorde, radioBorde);

        g2.dispose();

        super.paintComponent(g);
    }
}
