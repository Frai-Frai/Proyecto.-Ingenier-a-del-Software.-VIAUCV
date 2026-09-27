package util;
import java.awt.*;
import java.awt.event.MouseAdapter;
import javax.swing.JLabel;
import java.awt.event.MouseEvent;

public class TextosInteractivosUtil{

    //le paso la variable jlabel, el texto de la misma, color normal de letra y si me pongo encima a que color cambia
     public TextosInteractivosUtil(JLabel jlabel, String texto, Color colorNormal, Color colorIluminado, Font fuente){
        jlabel.setText(texto);
        jlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jlabel.setForeground(colorNormal);
        jlabel.setFont(fuente);

        jlabel.addMouseListener(new MouseAdapter() {
            @Override 
            public void mouseEntered (MouseEvent ev){
                jlabel.setForeground(colorIluminado);
            }

            @Override
            public void mouseExited (MouseEvent ev){
                jlabel.setForeground(colorNormal);
            }

            @Override 
            public void mousePressed(java.awt.event.MouseEvent event){
                jlabel.setForeground(colorIluminado);
            }

            @Override 
            public void mouseReleased(java.awt.event.MouseEvent event){
                jlabel.setForeground(colorNormal);
            }

            
        });
    }
}
