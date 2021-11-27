public class Kamisado {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                Vue vue = new Vue();
                Ordinateur ordinateur = new Ordinateur(vue, vue.controlButton);

            }

        });
    }
}
