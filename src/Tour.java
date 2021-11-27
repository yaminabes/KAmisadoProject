import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Tour extends JComponent {
    double x;
    double y;

    public Color getRectColor() {
        return rectColor;
    }

    public void setRectColor(Color rectColor) {
        this.rectColor = rectColor;
    }

    Color rectColor;
    Color circlColor;
    double size;
    double rectSize;

    public Tour(double x,double y, Color rectColor, Color circlColor){
        this.circlColor = circlColor;
        this.rectColor = rectColor;
        this.x = x;
        this.y = y;
        size = 50;
        rectSize = 30;
    }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2d =(Graphics2D)g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(circlColor); //sélectionner la couleur
            g2d.fillOval(0, 0, 70, 70);//dessiner un rectangle
            g2d.setColor(rectColor);
            g2d.fillRect(17, 17, 40, 40);//dessiner un rectangle

        }


    public int getPlayer(){
        if (this.circlColor.equals(Color.BLACK)){
            return Button.BLACKPLAYER;
        }
        else if (this.circlColor.equals(Color.WHITE)){
            return Button.WHITEPLAYER;
        }
        return 0;
    }
}

