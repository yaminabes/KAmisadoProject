import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlButton implements ActionListener {
    //constante définissant les tours
    private static final int TOURBLANC = 1 ;
    private static final int TOURNOIR = 2 ;

    //case à jouer par l'ordinateur
    private  static final int CASE_NOIR = 99;

    //orientation des obstacles
    private static final int LEFT = 1 ;
    private static final int RIGHT = 2 ;
    private static final int STRAIGHT = 3 ;

    //model
    private Model model;
    //vue
    Vue vue;
    //bordures qui définissent les possibilités de jeu du joureur en cours
    final Border whiteBorder =BorderFactory.createLineBorder(Color.WHITE,5);
    final Border blackBorder =BorderFactory.createLineBorder(Color.BLACK,5);
    final Border testBorder =BorderFactory.createLineBorder(Color.CYAN,5);
    //TODO
    final int WHITEPLAYER=1;
    final int BLACKPLAYER=2;
    //clic définit le moment où le joueur choisi le jeton avec le quel il veut jouer
    //play définit le moment où le joueur choisi la case sur la quelle il veut poser son jeton
    final int CLIC=1;
    final int PLAY=2;

    //TODO
    private Tour blackTower;
    private Tour whiteTower;

    public int getTourDeJouer() {
        return tourDeJouer;
    }

    int tourDeJouer;
    //boutons avec des tours sur le chemin des boutons blancs
    private Button boutonObstacleLeftWhite;
    private Button boutonObstacleRightWhite;
    private Button boutonObstacleStraightWhite;

    //boutons avec des tours sur le chemin des boutons noirs
    private Button boutonObstacleLeftBlack;
    private Button boutonObstacleRightBlack;
    private Button boutonObstacleStraightBlack;

    //bouton cliqué pour choisir une position
    private  Button cliquedButtonBlackChose;
    private  Button cliquedButtonWhiteChose;

    //bouton cliqué pour jouer
    private  Button cliquedButtonBlackPlay;
    private  Button cliquedButtonWhitePlay;

    public Button getBlackButton() {
        return blackButton;
    }

    public void setBlackButton(Button blackButton) {
        blackButton.setState(CASE_NOIR);
        System.out.println(blackButton.getButtonI()+" "+blackButton.getButtonJ());
        Ordinateur ordinateur = new Ordinateur(vue, this);
        ordinateur.play(blackButton);
    }

    //bouton à jouer par l'ordinateur
    public Button blackButton;


    public void setBlackTower(Tour blackTower) {
        this.blackTower = blackTower;
    }

    public void setTourDeJouer(int tourDeJouer) {
        this.tourDeJouer = tourDeJouer;
    }

    public Button getCliquedButtonBlackChose() {
        return cliquedButtonBlackChose;
    }

    public void setCliquedButtonBlackChose(Button cliquedButtonBlackChose) {
        this.cliquedButtonBlackChose = cliquedButtonBlackChose;
    }




    public Button getCliquedButtonWhiteChose() {
        return cliquedButtonWhiteChose;
    }





    public void setWhiteTower(Tour whiteTower) {
        this.whiteTower = whiteTower;
    }

    public void setCliquedButtonBlackPlay(Button cliquedButtonBlackPlay) {
        this.cliquedButtonBlackPlay = cliquedButtonBlackPlay;
    }

    public void setCliquedButtonWhitePlay(Button cliquedButtonWhitePlay) {
        this.cliquedButtonWhitePlay = cliquedButtonWhitePlay;
    }



    public ControlButton(Vue vue, Model model) {
        this.vue=vue;
        this.model = model;
    }
    public void mainBlanche(int play, Button cliquedButton){
        vue.resetBorders();
        for (int i = 0; i< vue.buttons.length; i++){
            for (int j = 0; j< vue.buttons[i].length; j++){

                if (vue.buttons[i][j].getState()!=WHITEPLAYER){
                    vue.buttons[i][j].setEnabled(false);
                }
            }
        }
        if(play==CLIC){
            this.cliquedButtonWhiteChose = cliquedButton;
        }
        setBoutonObstacle(TOURBLANC, LEFT);
        setBoutonObstacle(TOURBLANC, RIGHT);
        setBoutonObstacle(TOURBLANC, STRAIGHT);
        if (play == CLIC) {
            enableButtons(boutonObstacleLeftWhite,TOURBLANC, LEFT);
            enableButtons(boutonObstacleRightWhite,TOURBLANC, RIGHT);
            enableButtons(boutonObstacleStraightWhite,TOURBLANC, STRAIGHT);

        }
        if (play == PLAY) {enableObstacles();
            moveWhiteTower(cliquedButton);
            setCliquedButtonWhitePlay(cliquedButton);resetObstacles();
            playableButtonBlack(cliquedButton,vue.buttons);
            resetObstacles();
            model.removePropositions(vue.buttons);
            enableObstacles();
            resetObstacles();
            model.removePropositions(vue.buttons);

        }
    }
    public void mainNoire(int play, Button cliquedButton){
        vue.resetBorders();
        for (int i = 0; i< vue.buttons.length; i++){
            for (int j = 0; j< vue.buttons[i].length; j++){
                if (vue.buttons[i][j].getState()!=BLACKPLAYER){
                    vue.buttons[i][j].setEnabled(false);
                }
            }
        }
        if(play==CLIC){
            setCliquedButtonBlackChose(cliquedButton);
        }
        setBoutonObstacle(TOURNOIR, LEFT);
        setBoutonObstacle(TOURNOIR, RIGHT);
        setBoutonObstacle(TOURNOIR, STRAIGHT);
        if (play == CLIC && cliquedButtonBlackChose==cliquedButton  ) {
            enableButtons(boutonObstacleLeftBlack,TOURNOIR, LEFT);
            enableButtons(boutonObstacleRightBlack,TOURNOIR, RIGHT);
            enableButtons(boutonObstacleStraightBlack,TOURNOIR, STRAIGHT);

        }
        if (play == PLAY) {
            enableObstacles();
            moveBlackTower(cliquedButton);
            setCliquedButtonBlackPlay(cliquedButton);
            playableButtonWhite(cliquedButton,vue.buttons);
            resetObstacles();
            model.removePropositions(vue.buttons);
            enableObstacles();
            resetObstacles();
            model.removePropositions(vue.buttons);

        }
    }
    private void setBoutonObstacle(int tourPlayer, int orientation) {
        Button cliquedButton = null;
        if(tourPlayer == TOURBLANC){
            cliquedButton = cliquedButtonWhiteChose;
        }
        if(tourPlayer == TOURNOIR){
            cliquedButton = cliquedButtonBlackChose;
        }
        //récupération des coordonnées du bouton cliqué
        assert cliquedButton != null;
        int modifiedI=cliquedButton.getButtonI();
        int modifiedJ =cliquedButton.getButtonJ();
        for (int i = 0; i< 8; i++){
            //tour blanc
            if(tourPlayer == TOURBLANC && orientation == LEFT){
                modifiedI++;
                modifiedJ--;
            }
            else if(tourPlayer == TOURBLANC && orientation == RIGHT){
                modifiedI++;
                modifiedJ++;
            }
            else if(tourPlayer == TOURBLANC && orientation == STRAIGHT){
                modifiedI++;
            }
            //tour noir
            else if(tourPlayer == TOURNOIR && orientation == LEFT){
                modifiedI--;
                modifiedJ--;
            }
            else if(tourPlayer == TOURNOIR && orientation == RIGHT){
                modifiedI--;
                modifiedJ++;
            }
            else if(tourPlayer == TOURNOIR && orientation == STRAIGHT){
                modifiedI--;
            }

            if (modifiedI>=8||modifiedJ>=8||modifiedI<=-1||modifiedJ<=0){
                break;
            }


            if (vue.buttons[modifiedI][modifiedJ].getTour() != null) {
                //definir les obstacles blancs
                if(tourPlayer == TOURBLANC){
                    if(orientation ==LEFT){
                        this.boutonObstacleLeftWhite = vue.buttons[modifiedI][modifiedJ];
                    }
                    if(orientation ==RIGHT){
                        this.boutonObstacleRightWhite = vue.buttons[modifiedI][modifiedJ];
                    }
                    if(orientation ==STRAIGHT){
                        this.boutonObstacleStraightWhite = vue.buttons[modifiedI][modifiedJ];
                    }

                }

                //definir les obstacles noirs
                if(tourPlayer == TOURNOIR){
                    if(orientation ==LEFT){
                        this.boutonObstacleLeftBlack = vue.buttons[modifiedI][modifiedJ];
                    }
                    if(orientation ==RIGHT){
                        this.boutonObstacleRightBlack = vue.buttons[modifiedI][modifiedJ];
                    }
                    if(orientation ==STRAIGHT){
                        this.boutonObstacleStraightBlack = vue.buttons[modifiedI][modifiedJ];
                    }

                }

                vue.buttons[modifiedI][modifiedJ].setState(Button.OBSTACLE);
                break;
            }
        }
    }

    private void enableButtons(Button buttonObstacle, int tour, int orientation){
        int iObstacle = 0;
        boolean condition = false;
        try{
            iObstacle = buttonObstacle.getButtonI();
        }
        catch(NullPointerException e){
            if(tour == TOURBLANC){
                iObstacle=8;
            }
            if(tour == TOURNOIR){
                iObstacle=-1;
            }
        }
        if(tour == TOURBLANC){
            int iCliquedButton = cliquedButtonWhiteChose.getButtonI();
            int jCliquedButton = cliquedButtonWhiteChose.getButtonJ();
            for (int i = iCliquedButton + 1; i < iObstacle; i++) {
                if(orientation == LEFT){
                    jCliquedButton--;
                    condition = jCliquedButton>=0;
                }
                if(orientation == RIGHT){
                    jCliquedButton++;
                    condition = jCliquedButton<8;
                }
                if(condition || orientation == STRAIGHT ){
                    vue.buttons[i][jCliquedButton].setEnabled(true);
                    vue.buttons[i][jCliquedButton].setBorderPainted(true);
                    vue.buttons[i][jCliquedButton].setBorder(whiteBorder);
                    vue.buttons[i][jCliquedButton].setState(Button.PROPOSITION);

                }
            }

        }
        if(tour == TOURNOIR){
            int iCliquedButton = cliquedButtonBlackChose.getButtonI();
            int jCliquedButton = cliquedButtonBlackChose.getButtonJ();

            for (int i = iCliquedButton - 1; i > iObstacle; i--) {
                if(orientation == LEFT){
                    jCliquedButton--;
                    condition = jCliquedButton>=0;
                }
                if(orientation == RIGHT){
                    jCliquedButton++;
                    condition = jCliquedButton<8;
                }
                if(condition || orientation == STRAIGHT){
                    vue.buttons[i][jCliquedButton].setEnabled(true);
                    vue.buttons[i][jCliquedButton].setBorderPainted(true);
                    vue.buttons[i][jCliquedButton].setBorder(testBorder);
                    vue.buttons[i][jCliquedButton].setState(Button.ORDINATEUR);

                }
            }
        }
    }
    private void moveWhiteTower(Button button){
        cliquedButtonWhiteChose.removeTour();
        cliquedButtonWhiteChose.setState(Button.NOPLAYER);

        button.setTour(whiteTower);
        button.setState(Button.WHITEPLAYER);
        model.removePropositions(vue.buttons);
        resetObstacles();
    }
    private void moveBlackTower(Button button){
        cliquedButtonBlackChose.removeTour();
        cliquedButtonBlackChose.setState(Button.NOPLAYER);
        button.setTour(blackTower);
        button.setState(Button.BLACKPLAYER);
        model.removePropositions(vue.buttons);
        resetObstacles();
    }
    private void resetObstacles(){
        this.boutonObstacleStraightWhite=null;
        this.boutonObstacleLeftWhite=null;
        this.boutonObstacleRightWhite=null;
        this.boutonObstacleStraightBlack=null;
        this.boutonObstacleLeftBlack=null;
        this.boutonObstacleRightBlack=null;
    }
    private void enableObstacles(){
        try{
            this.boutonObstacleStraightWhite.setEnabled(true);
        }catch(NullPointerException e){
            System.out.println("");
        }
        try{
            this.boutonObstacleLeftWhite.setEnabled(true);
        }
        catch(NullPointerException e){
            System.out.println("");}
        try{
            this.boutonObstacleRightWhite.setEnabled(true);
        }
        catch(NullPointerException e){
            System.out.println("");}
        try{
            this.boutonObstacleStraightBlack.setEnabled(true);
        }
        catch(NullPointerException e){
            System.out.println("");}
        try{
            this.boutonObstacleLeftBlack.setEnabled(true);
        }
        catch(NullPointerException e){
            System.out.println("");}
        try{
            this.boutonObstacleRightBlack.setEnabled(true);
        }
        catch(NullPointerException e){
            System.out.println("");}
    }
    private void playableButtonWhite(Button cliquedButtonBlackPlay, Button[][] buttons){

        for (int i=0; i<8;i++){
            for (int j=0; j<8; j++){
                buttons[i][j].setEnabled(false);
                if (buttons[i][j].getState()==WHITEPLAYER){
                    if (buttons[i][j].getTour().getRectColor()==cliquedButtonBlackPlay.getBackground() ) {
                        setBlackButton(buttons[i][j]);
                        buttons[i][j].setEnabled(true);
                        buttons[i][j].setBorderPainted(true);
                        buttons[i][j].setBorder(whiteBorder);
                        if (buttons[i][j].getState()==Button.OBSTACLE){
                            buttons[i][j].setEnabled(true);
                            buttons[i][j].setBorderPainted(true);
                            buttons[i][j].setBorder(whiteBorder);
                        }
                        if (buttons[i+1][j].getState()==Button.OBSTACLE && buttons[i+1][j+1].getState()==Button.OBSTACLE&& buttons[i+1][j-1].getState()==Button.OBSTACLE ){
                            mainNoireEntiere();
                            buttons[i][j].setEnabled(false);
                            buttons[i][j].setBorderPainted(false);
                        }

                    }
                }
            }
        }
    }

    public void mainBlancheEntiere(Button clic){

        for (int i=0; i<8;i++) {
            for (int j = 0; j < 8; j++) {
                vue.buttons[i][j].setEnabled(false);
                if (vue.buttons[i][j].getState()==WHITEPLAYER){
                    if (vue.buttons[i][j].getState() == WHITEPLAYER || vue.buttons[i][j].getState() == Button.OBSTACLE && vue.buttons[i][j].getTour().getPlayer() == WHITEPLAYER) {
                        if(vue.buttons[i][j].getTour().getRectColor()==cliquedButtonBlackChose.getBackground()) {

                            vue.buttons[i][j].setEnabled(true);
                            setWhiteTower(vue.buttons[i][j].getTour());
                            mainBlanche(CLIC,vue.buttons[i][j]);

                            vue.buttons[i][j].setBorder(whiteBorder);
                            vue.buttons[i][j].setBorderPainted(true);
                        }
                    }
                }
            }
        }
    }

    public void mainNoireEntiere(){
        for (int i=0; i<8;i++) {
            for (int j = 0; j < 8; j++) {
                vue.buttons[i][j].setEnabled(false);
                if (vue.buttons[i][j].getState() == BLACKPLAYER || vue.buttons[i][j].getState() == Button.OBSTACLE && vue.buttons[i][j].getTour().getPlayer() == BLACKPLAYER) {
                    if(vue.buttons[i][j].getTour().getRectColor()==cliquedButtonWhitePlay.getBackground()) {

                        vue.buttons[i][j].setEnabled(true);
                        vue.buttons[i][j].setState(Button.ORDINATEUR);
                        vue.buttons[i][j].setBorder(blackBorder);
                        vue.buttons[i][j].setBorderPainted(true);
                        setBlackTower(vue.buttons[i][j].getTour());
                        mainNoire(CLIC,vue.buttons[i][j]);
                    }
                }

            }
        }
    }
    private void playableButtonBlack(Button cliquedButtonWhitePlay, Button[][] buttons){
        for (int i=0; i<8;i++){
            for (int j=0; j<8; j++){
                buttons[i][j].setEnabled(false);
                if (buttons[i][j].getState()==BLACKPLAYER){
                    if (buttons[i][j].getTour().getRectColor()==cliquedButtonWhitePlay.getBackground() ) {
                        setBlackButton(buttons[i][j]);
                        buttons[i][j].setEnabled(true);
                        buttons[i][j].setBorderPainted(true);
                        buttons[i][j].setBorder(blackBorder);
                        if (buttons[i][j].getState()==Button.OBSTACLE){
                            buttons[i][j].setEnabled(true);
                            buttons[i][j].setBorderPainted(true);
                            buttons[i][j].setBorder(blackBorder);
                        }

                    }
                }
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Button button=null;

        for (int i=0; i<vue.buttons.length;i++){
            for (int j=0; j<vue.buttons[i].length; j++){
                if (e.getSource().equals(vue.buttons[i][j]) ) {
                    vue.resetPlateau();
                    if (vue.buttons[i][j].getState()==Button.WHITEPLAYER){
                        model.removePropositions(vue.buttons);
                        mainBlanche(CLIC,vue.buttons[i][j]);
                        cliquedButtonWhiteChose = vue.buttons[i][j];
                        setWhiteTower(vue.buttons[i][j].getTour());
                        setTourDeJouer(TOURBLANC);

                        try{
                            if (vue.buttons[i + 1][j].getState() == Button.OBSTACLE && vue.buttons[i + 1][j + 1].getState() == Button.OBSTACLE && vue.buttons[i + 1][j - 1].getState() == Button.OBSTACLE) {
                                mainNoireEntiere();
                                //vue.buttons[i][j].setEnabled(false);
                                //vue.buttons[i][j].setBorderPainted(false);
                            }
                        }
                        catch(ArrayIndexOutOfBoundsException ae){
                            mainNoireEntiere();
                        }

                    }
                    if (vue.buttons[i][j].getState()== Button.PROPOSITION && tourDeJouer==TOURBLANC){
                        mainBlanche(PLAY,vue.buttons[i][j]);
                        model.removePropositions(vue.buttons);
                        setTourDeJouer(TOURNOIR);
                        enableObstacles();
                        resetObstacles();
                        model.removePropositions(vue.buttons);

                        Ordinateur ordinateur = new Ordinateur(vue, this);
                        ordinateur.play(vue.buttons[i][j]);

                    }

                    try {
                        model.noirGagnant(vue.buttons);
                    } catch (WinnerBlack winnerBlack) {
                        vue.replay();
                        displayWinner(winnerBlack.getMessage());
                    }


                    try {
                        model.blancGagnant(vue.buttons);
                    } catch (WinnerWhite winnerWhite) {
                        vue.replay();
                        displayWinner(winnerWhite.getMessage());
                    }

                }
            }
        }
    }
    public void displayWinner(String msg){
        JOptionPane message = new JOptionPane();
        message.showMessageDialog(vue , msg,"Gagné",
                JOptionPane.ERROR_MESSAGE );
    }
}
