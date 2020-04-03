package levato.view.chart;

import levato.view.main.MainFrame;
import levato.view.main.TableData;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.entity.StandardEntityCollection;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * The type Chart frame.
 */
public class ChartFrame extends JFrame {

    /**
     * The Chart window.
     */
    private ChartWindow chartWindow;

    /**
     * Instantiates a new Chart frame.
     *
     * @param drawingData the drawing data
     */
    public ChartFrame(TableData drawingData) {

        //Calling the JFrame constructor
        super("Visualizza Grafico");

        //Setting the layout chosen
        setLayout(new BorderLayout());

        /*--------COMPONENTS------------*/

        //Initializing the components, declared previously
        chartWindow = new ChartWindow(drawingData);
        setJMenuBar(createMenuBar());

        add(chartWindow, BorderLayout.CENTER);

        /*--------FRAME SETTINGS---------*/

        //Make the program stop when we close the window
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //Setting the window size
        setSize(1200, 720);

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
        JMenuItem save = new JMenuItem("Save as PNG");
        JMenuItem quit = new JMenuItem("Quit");
        JMenu info = new JMenu("Info");
        JMenuItem infoDati = new JMenuItem("Dati");
        JMenuItem infoAbout = new JMenuItem("About");
        file.add(save);
        file.add(quit);
        info.add(infoDati);
        info.add(infoAbout);
        menuBar.add(file);

        //Setting up accelerators ("CTRL +" shortcuts)
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));

        //Setting up mnemonics ("ALT +" shortcuts)
        info.setMnemonic(KeyEvent.VK_I);

        ////// Save File Chooser Extensions Setup
        JFileChooser saveFileChooser = new JFileChooser();
        saveFileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filterPNG = new FileNameExtensionFilter("Portable Network Graphics (*.png)", "png");
        saveFileChooser.addChoosableFileFilter(filterPNG);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(saveFileChooser.showSaveDialog(ChartFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try{
                        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
                        String path = saveFileChooser.getSelectedFile().getAbsolutePath();
                        if(path.endsWith(".png")){
                            File file = new File(saveFileChooser.getSelectedFile().getAbsolutePath());
                            ChartUtils.saveChartAsPNG(file, chartWindow.getChart(), 1280, 720 );
                            JOptionPane successDialog = new JOptionPane();
                            successDialog.showMessageDialog(null, "SUCCESS: the file has been correctly saved in " +
                                    "the specified location.", "Succesful Operatrion", JOptionPane.INFORMATION_MESSAGE);
                        }else{
                            JOptionPane warningDialog = new JOptionPane();
                            warningDialog.showMessageDialog(null, "ERROR: You've tried to save a file with the wrong extension.\nMake " +
                                    "sure to specify .png at the end of the name.", "Warning", JOptionPane.ERROR_MESSAGE);
                        }

                    }catch (Exception ex) {
                        System.out.println(ex.getStackTrace());
                    }
                }
                    //System.out.println(saveFileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(ChartFrame.this, "Do you really want to exit this window?",
                        "Exit Confirmation", JOptionPane.OK_CANCEL_OPTION);
                if(action == JOptionPane.OK_OPTION){
                    dispose();
                }
            }
        });

        return menuBar;
    }




}
