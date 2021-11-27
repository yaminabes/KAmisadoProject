import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    final static int NOPLAYER =0;
    final static int BLACKPLAYER =2;
    final static int WHITEPLAYER =1;
    final static int PROPOSITION =3;
    final static int OBSTACLE =4;
    final static int ORDINATEUR=5;
    int state;

    int numero;

    int buttonI;

    int buttonJ;

    public boolean isTour() {
        return isTour;
    }

    public void setIsTour(boolean tour) {
        isTour = tour;
    }

    boolean isTour;

    Tour tour;


    //
    public Button(){
        tour = null;
    }

    public Button(Tour tour){
        //state =tour.getPlayer();
        this.tour = tour;
    }
    public int getState(){
        return this.state;
    }

    public void setState(int state){
        this.state = state;
    }
    public void setTour(Tour tour){
        //this.state = tour.getPlayer();
        this.tour = tour;
        this.add(tour);
        setState(tour.getPlayer());
    }
    public void removeTour(){
        //this.state = tour.getPlayer();
        this.tour = null;
    }


    public Tour getTour(){
        return this.tour;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getButtonI() {
        return buttonI;
    }

    public void setButtonI(int buttonI) {
        this.buttonI = buttonI;
    }


    public int getButtonJ() {
        return buttonJ;
    }

    public void setButtonJ(int buttonJ) {
        this.buttonJ = buttonJ;
    }

    public String toString(){
        return "state : "+this.state;
    }
}
