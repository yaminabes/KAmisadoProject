import javax.security.auth.callback.TextOutputCallback;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        /*for(int i = 0; i<8; i++){
            for (int j=0; j<8; j++){
                if(vue.buttons[i][j].getState() == Button.ORDINATEUR ){
                    System.out.println(i+" ici "+j);*//*
                    coordonnees[0] = i;
                    coordonnees[1] = j;*//*
                    vue.buttons[i][j].setTour(tour);
                    propositions.add(coordonnees);
                }
            }
        }*/
        Tour tour = b.getTour();
        System.out.println(b.getButtonI()+" bouton "+b.getButtonJ());
        Integer[] coordonnees = new Integer[2];
        List<Integer[]> propositions = new ArrayList<>();
        controlButton.setCliquedButtonBlackChose(b);
        controlButton.mainNoireEntiere();
        //controlButton.mainNoire(controlButton.PLAY,b);
        for(int i = 0; i<8; i++){
            for (int j=0; j<8; j++){
                if(vue.buttons[i][j].getState() == Button.ORDINATEUR ){
                    System.out.println(i+" ici "+j);/*
                    coordonnees[0] = i;
                    coordonnees[1] = j;*/
                    vue.buttons[i][j].setTour(tour);
                    propositions.add(coordonnees);
                }
            }
        }
       // System.out.println("size : "+propositions.size());
        //int rnd = new Random().nextInt(propositions.size());
       //coordonnees = propositions.get(rnd);

    }
}
