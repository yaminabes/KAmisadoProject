import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlMenu implements ActionListener {
    Vue vue;


    public ControlMenu(Vue vue){
        this.vue = vue;
        vue.setMenuController(this);
    }
    public ControlMenu(Vue vue, Model model){}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vue.itemApropos){
            vue.displayAPropos();
        }
        if (e.getSource() == vue.itemNouvellePartie){
            vue.replay();
        }
    }

}