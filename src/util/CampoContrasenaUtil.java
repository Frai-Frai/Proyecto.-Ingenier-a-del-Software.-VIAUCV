package util;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CampoContrasenaUtil extends JPasswordField{
    private int radioBorde;
    private Color colorBorde;

    public CampoContrasenaUtil(int radioBorde, Color colorBorde, int ancho, int alto, Color colorFondo, Color colorTexto, Font fuente){
        super();
        this.radioBorde= radioBorde;
        this.colorBorde= colorBorde;

        setPreferredSize(new Dimension(ancho, alto)); //Dar el tamano deseado
        setFont(fuente);  //tipo de letra a usar
        setBackground(colorFondo);  //Fondo
        setForeground(colorTexto);  //texto
        setOpaque(false);   //Transparente
        setBorder(new EmptyBorder(5, 10, 5, 10));  //Margen interno
    }

    //Darle diseno bonito
    @Override 
    protected void paintComponent(Graphics g){
        Graphics2D g2= (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //suaviza bordes

        g2.setColor(getBackground()); //Color del fondo
        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, radioBorde, radioBorde); //Para los bordes redondeados
        g2.setColor(colorBorde);    //Color del marco del borde
        g2.setStroke(new BasicStroke(1.5f));   //Grosor del borde
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radioBorde, radioBorde); //Marcos redondeados

        g2.dispose();

        super.paintComponent(g);
    }
    
}
