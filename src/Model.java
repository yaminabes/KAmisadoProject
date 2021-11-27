import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
//todo : set le i du boutton dans la grille, le j aussi et trouver un moyen de désactiver les boutons en comparant les j
public class Model {

    final int NOPLAYER =0;
    final int BLACKPLAYER =2;
    final int WHITEPLAYER =1;

    public void  removePropositions(Button[][] plateau){

        for (int i=0; i<plateau.length; i++){
            for (int j=0; j<plateau[i].length; j++){
                if (plateau[i][j].getState()==Button.PROPOSITION || plateau[i][j].getState()==Button.OBSTACLE){
                    plateau[i][j].setState(NOPLAYER);
                    plateau[i][j].setText("");
                }
                if(plateau[i][j].getState()!=BLACKPLAYER && plateau[i][j].getState()!=WHITEPLAYER && plateau[i][j].getTour()==null){
                    plateau[i][j].setState(NOPLAYER);
                    plateau[i][j].setText("");
                }
                if (plateau[i][j].getTour()!=null&&plateau[i][j].getTour().getPlayer()==WHITEPLAYER){
                    plateau[i][j].setState(WHITEPLAYER);
                }
                if (plateau[i][j].getTour()!=null&&plateau[i][j].getTour().getPlayer()==BLACKPLAYER){
                    plateau[i][j].setState(BLACKPLAYER);
                }
                if (plateau[i][j].getTour()==null&&plateau[i][j].getState()==Button.OBSTACLE){
                    plateau[i][j].setState(NOPLAYER);
                }
            }
        }
    }
    public void noirGagnant(Button[][] plateau) throws WinnerBlack{
        for (int i=0; i<plateau.length; i++) {
            if (plateau[0][i].getState() == BLACKPLAYER) {
                throw new WinnerBlack();
            }
        }
    }
    public void blancGagnant(Button[][] plateau) throws WinnerWhite{
        for (int i=0; i<plateau.length; i++) {
            if (plateau[7][i].getState() == WHITEPLAYER) {
                throw new WinnerWhite();
            }
        }
    }


}



class WinnerBlack extends Exception{
    WinnerBlack() {
        super(
                "Le Joueur Noir a gagné !"
        );

    }
}
class WinnerWhite extends Exception{
    WinnerWhite() {
        super(
                "Le Joueur Blanc a gagné !"
        );

    }
}
