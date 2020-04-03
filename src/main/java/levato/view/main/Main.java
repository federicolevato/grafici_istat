package levato.view.main;
import com.alee.laf.WebLookAndFeel;
import com.alee.skin.dark.WebDarkSkin;
import levato.view.main.MainFrame;


import javax.swing.*;

/**
 * The type Main.
 */
public class Main {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                WebLookAndFeel.install();
                new MainFrame();
            }
        });
    }
}
