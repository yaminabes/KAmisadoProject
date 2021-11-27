import javax.security.auth.callback.TextOutputCallback;

public class Ordinateur {
    Vue vue;
    ControlButton controlButton;
    private static final int TOURBLANC = 1 ;
    private static final int TOURNOIR = 2 ;
    private  static final int CASE_NOIR = 99;

    public Ordinateur( Vue v, ControlButton c){
        vue = v;
        controlButton = c;
    }

    public  void  play(Button b){
        Tour tour = b.getTour();
        vue.buttons[6][6].setTour(tour);
    }
}
