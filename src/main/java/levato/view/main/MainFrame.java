package levato.view.main;


import com.alee.utils.IOUtils;
import levato.view.chart.ChartFrame;
import levato.view.chart.ChartWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * The type Main frame.
 */
public class MainFrame extends JFrame {

    /**
     * The Table panel.
     */
    private TablePanel tablePanel;
    /**
     * The Form panel.
     */
    private FormPanel formPanel;
    /**
     * The Table data.
     */
    private TableData tableData;

    /**
     * Instantiates a new Main frame.
     */
    public MainFrame() {

        //Calling the JFrame constructor
        super("Esercizio 20");

        //Setting the layout chosen
        setLayout(new BorderLayout());

        /*--------COMPONENTS------------*/

        //Initializing the components, declared previously
        tablePanel = new TablePanel();
        formPanel = new FormPanel();
        setJMenuBar(createMenuBar());


        //Adding the components to the layout
        add(tablePanel, BorderLayout.CENTER);
        add(formPanel, BorderLayout.WEST);

        /*--------FRAME SETTINGS---------*/

        //Make the program stop when we close the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Setting the window size
        setSize(800, 600);

        //Making the window visible
        setVisible(true);

        //Centering the window when it appears
        setLocationRelativeTo(null);

    }


    /**
     * Create menu bar j menu bar.
     *
     * @return the j menu bar
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem quit = new JMenuItem("Quit");
        JMenu info = new JMenu("Info");
        JMenuItem infoGuida = new JMenuItem("Apri Guida (.pdf)");
        JMenuItem infoDoc = new JMenuItem("Apri Javadoc (.html)");
        file.add(quit);
        info.add(infoGuida);
        info.add(infoDoc);
        menuBar.add(file);
        menuBar.add(info);

        //Setting up accelerators ("CTRL +" shortcuts)
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));

        //Setting up mnemonics ("ALT +" shortcuts)
        info.setMnemonic(KeyEvent.VK_I);

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to quit the application?",
                        "Exit Confirmation", JOptionPane.OK_CANCEL_OPTION);
                if(action == JOptionPane.OK_OPTION)
                    System.exit(0);
            }
        });

        infoGuida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(MainFrame.this, "An external PDF editor will be launched to open the file...",
                        "Open PDF Confirmation", JOptionPane.OK_CANCEL_OPTION);
                if(action == JOptionPane.OK_OPTION){
                    if(Desktop.isDesktopSupported()){
                        try{
                            InputStream inputStream = getClass().getResourceAsStream("/guide.pdf");
                            File f = stream2file(inputStream, "guide", ".pdf");
                            Desktop.getDesktop().open(f);
                        }catch (Exception ex){
                            JOptionPane warningDialog = new JOptionPane();
                            warningDialog.showMessageDialog(null, "ERROR: no PDF editors have been found.", "PDF Opening Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        infoDoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(MainFrame.this, "An external HTML viewer will be launched to open the file...",
                        "Open HTML Confirmation", JOptionPane.OK_CANCEL_OPTION);
                if(action == JOptionPane.OK_OPTION){
                    if(Desktop.isDesktopSupported()){
                        try{
                            InputStream myDoc = getClass().getResourceAsStream("/docs/index.html");
                            File f = stream2file(myDoc, "doc", ".html");
                            Desktop.getDesktop().open(f);
                        }catch (Exception ex){
                            ex.printStackTrace();
                            JOptionPane warningDialog = new JOptionPane();
                            warningDialog.showMessageDialog(null, "ERROR: no HTML editors have been found.", "HTML Opening Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

            }
        });

        return menuBar;
    }

    private static File stream2file (InputStream in, String prefix, String suffix) throws IOException {
        final File tempFile = File.createTempFile(prefix, suffix);
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }

}
