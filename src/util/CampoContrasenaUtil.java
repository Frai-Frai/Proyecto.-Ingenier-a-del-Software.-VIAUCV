package util;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;

public class CampoContrasenaUtil extends JPasswordField{
    private int radioBorde;
    private Color colorBorde;
    private JPasswordField campoContrasena;
    private boolean mostrar=false;
    private JLabel IconoOjo;

    public CampoContrasenaUtil(int radioBorde, Color colorBorde, int ancho, int alto, Color colorFondo, Color colorTexto, Font fuente){
        super();
        this.radioBorde= radioBorde;
        this.colorBorde= colorBorde;

        setPreferredSize(new Dimension(ancho, alto));
        setFont(fuente);
        setBackground(colorFondo);
        setForeground(colorTexto);
        setOpaque(false);
        setBorder(new EmptyBorder(5, 10, 5, 10));

        /*//Para agregarle el icono de mostrar contrasena (work in progress....)
        campoContrasena= new JPasswordField();
        campoContrasena.setFont(fuente);
        campoContrasena.setForeground(colorTexto);
        campoContrasena.setOpaque(false);
        campoContrasena.setBorder(new EmptyBorder(5,12,5,5));

        //EL ojo como tal
        IconoOjo= new JLabel("👁");
        IconoOjo.setFont(new Font("Open Sans", Font.PLAIN, 12));
        IconoOjo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        IconoOjo.setBorder(new EmptyBorder(getInsets()));

        add(campoContrasena, BorderLayout.CENTER);
        add(IconoOjo, BorderLayout.EAST);

        //Que sirva la caja de texto y el ojo
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                campoContrasena.requestFocusInWindow();
            }
        });*/
    }

    //Ignorar esta abierto a modificaciones
    public void Ver(){
        if(mostrar){
            campoContrasena.setEchoChar('.');
            IconoOjo.setText("👁");
            mostrar=false;
        }else{
            campoContrasena.setEchoChar((char) 0);
            mostrar=true;
        }
    }

    //Ignorar esta abierto a modificaciones
    public JLabel getIconoOjo(){
        return IconoOjo;
    }

    //Ignorar esta abierto a modificaciones
    public char[] getContrasena(){
        return campoContrasena.getPassword();
    }

    //definitivo
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
