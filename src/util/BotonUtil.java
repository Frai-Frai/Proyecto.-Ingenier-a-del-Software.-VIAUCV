package util;
import javax.swing.*;
import java.awt.*;

public class BotonUtil extends JButton {
    private int radioBorde;

    public BotonUtil(String texto, Color colorFondo, Color colorTexto, int radioBorde, Font fuente, int ancho, int alto ){
        super(texto);
        this.radioBorde = radioBorde;

        setPreferredSize(new Dimension(ancho, alto));

        setContentAreaFilled(false); //quita el relleno
        setFocusPainted(false); //quita el borde al hacer click
        setBorderPainted(false); //quita el borde al hacer click

        setBackground(colorFondo);
        setForeground(colorTexto);
        setFont(fuente);
        
        setCursor (new Cursor(Cursor.HAND_CURSOR)); //la manito al pasar por encima

        //Para el efecto interactivo
        addMouseListener(new java.awt.event.MouseAdapter (){
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event){
                setBackground(colorFondo.brighter());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event){
                setBackground(colorFondo);
            }

            @Override 
            public void mousePressed(java.awt.event.MouseEvent event){
                setBackground(colorFondo.darker());
            }

            @Override 
            public void mouseReleased(java.awt.event.MouseEvent event){
                setBackground(colorFondo);
            }
        });

    }


    //Bordes y fondo
    @Override 
    protected void paintComponent(Graphics g){
        Graphics2D g2= (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radioBorde, radioBorde);

        g2.dispose();
        super.paintComponent(g);
    }

}
