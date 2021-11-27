import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;

public class Vue extends JFrame{
    ArrayList<Integer> posNoirs;
    Scanner input;
    Button[][] buttons;
    JPanel plateau;
    File couleurs;
    String couleur;
    Color color;
    Color[][] colors;
    Tour[][] tour;
    int currentButton;

    // controller
    ControlButton controlButton;
    ControlMenu controlMenu ;
    //model
    private Model model;
    //Menun
    JMenuItem itemNouvellePartie;
    ImageIcon replayIcon;
    JMenuItem itemApropos;
    JMenuBar barMenu;
    JMenu menu;



    public Vue(){
        initAttributs();
        plateau();
        mainBlacheCommence();
        creerMenu();
        pack();

        setTitle("Kamisado");
        setVisible(true);
        setResizable(false);
        setSize(700,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void replay(){
        initAttributs();
        plateau();
        mainBlacheCommence();
        creerMenu();
        pack();

        setTitle("Kamisado");
        setVisible(true);
        setResizable(false);
        setSize(700,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initAttributs(){
        // création des buttons et leurs couleurs
        colors = new Color[8][8];
        buttons = new Button[8][8];
        couleurs = new File("src/couleurs.txt");
        this.model = new Model();
        controlButton = new ControlButton(this, model);
        try {
            input = new Scanner( couleurs);
        } catch (FileNotFoundException e) {
            System.out.println(couleurs.exists());
            System.out.println(new File(".").getAbsolutePath());
            System.out.println("File colors not found");
        }

        for(int i=0; i<buttons.length ; i++){
            for (int j=0; j<buttons[i].length; j++)
            {
                couleur = input.next();
                color = stringToColor(couleur);
                colors[i][j]=color;
                buttons[i][j] = new Button();
                buttons[i][j].setBackground(color);
                buttons[i][j].setNumero(i*8+j+2);
                //buttons[i][j].getTour().setOpaque(false);
                buttons[i][j].setText(""+buttons[i][j].getState());
                buttons[i][j].setButtonI(i);
                buttons[i][j].setButtonJ(j);
                buttons[i][j].setBorderPainted(false);
                buttons[i][j].setMargin(new Insets(0, 0, 0, 0));

                buttons[i][j].addActionListener(controlButton);
            }
        }
        tour = new Tour[2][8];

        // création des tours
        //tours blanches
        for(int i=0; i<1;i++){
            for (int j=0; j<tour[i].length; j++){
                tour[i][j] = new Tour(0.5, 0.5, buttons[i][j].getBackground(), Color.WHITE);
            }
        }
        // tours noires

        for (int i=buttons.length-1;i<buttons.length;i++){
            for (int j=0; j<buttons[i].length; j++)
            {
                tour[1][j] = new Tour(0.5, 0.5, buttons[i][j].getBackground(), Color.BLACK);
            }
        }

        //initialisation des items
        menu = new JMenu("Options");
        itemNouvellePartie = new JMenuItem("Rejouer");

        itemNouvellePartie.setIcon(replayIcon);
        itemApropos = new JMenuItem("À Propos");
        barMenu = new JMenuBar();

        // ajout des evenements de la bar
        controlMenu =  new ControlMenu(this);
        itemNouvellePartie.addActionListener(controlMenu);
        itemApropos.addActionListener(controlMenu);

    }
    public void plateau(){

        ButtonGroup groupe = new ButtonGroup();
        plateau = new JPanel(new GridLayout(8,8));
        for (int i=0;i<buttons.length;i++){
            for (int j=0; j<buttons[i].length; j++)
            {
                plateau.add(buttons[i][j]);
            }
        }
        //ajout des tours blanches
        for (int i=0;i<1;i++){
            for (int j=0; j<buttons[i].length; j++)
            {
                buttons[i][j].setTour(tour[0][j]);

            }
        }
        //ajout des tours noires
        for (int i=buttons.length-1;i<buttons.length;i++){
            for (int j=0; j<buttons[i].length; j++)
            {
                buttons[i][j].setTour(tour[1][j]);
            }
        }
        //posNoirs = model.getPosNoir(buttons);

        setContentPane(plateau);
    }
    public void resetPlateau(){
        for (int i=0; i<buttons.length; i++){
            for (int j=0; j<buttons[i].length; j++){
                buttons[i][j].setBackground(colors[i][j]);
            }
        }
    }
    public void resetBorders(){
        for (int i=0; i<buttons.length; i++){
            for (int j=0; j<buttons[i].length; j++){
                buttons[i][j].setBorderPainted(false);

            }
        }
    }
    public static Color stringToColor(final String value) {
        if (value == null) {
            return Color.black;
        }
        try {
            // get color by hex or octal value
            return Color.decode(value);
        } catch (NumberFormatException nfe) {
            // if we can't decode lets try to get it by name
            try {
                // try to get a color by name using reflection
                final Field f = Color.class.getField(value);

                return (Color) f.get(null);
            } catch (Exception ce) {
                // if we can't get any color return black
                return Color.black;
            }
        }
    }
    public void creerMenu(){

        menu.add(itemNouvellePartie);
        menu.add(itemApropos);
        barMenu.add(menu);
        setJMenuBar(barMenu);


    }

    public void setMenuController(ActionListener listener){
        itemNouvellePartie.addActionListener(listener);
        itemApropos.addActionListener(listener);
    }
    public void displayAPropos(){
        JOptionPane.showMessageDialog( this, "Ce jeu a été réalisé par Yamina Ouadah, " +
                        "Furkan Sertdemir et Ridvan Basbunar", "À Propos",
                JOptionPane.INFORMATION_MESSAGE );
    }
    public void mainBlacheCommence(){
        for (int i=0;i<buttons.length;i++){
            for (int j=0;j<buttons[i].length;j++){
                buttons[i][j].setEnabled(false);
                if (i==0){
                    buttons[i][j].setEnabled(true);
                    buttons[i][j].setBorder(controlButton.whiteBorder);
                    buttons[i][j].setBorderPainted(true);
                }
            }
        }
    }
}