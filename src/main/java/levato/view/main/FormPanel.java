package levato.view.main;

import com.alee.laf.tabbedpane.Tab;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import levato.view.chart.ChartFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * The type Form panel.
 */
public class FormPanel extends JPanel{

    /**
     * The Chart type.
     */
    private JLabel chartType;
    /**
     * The Chart data.
     */
    private JLabel chartData;
    /**
     * The Chart period.
     */
    private JLabel chartPeriod;
    /**
     * The Chart type selection.
     */
    private JComboBox chartTypeSelection;
    /**
     * The Chart data selection.
     */
    private JComboBox chartDataSelection;
    /**
     * The Chart period selection.
     */
    private JComboBox chartPeriodSelection;
    /**
     * The Generate Button.
     */
    private JButton generateChart;
    /**
     * The Table data.
     */
    private TableData tableData;

    /**
     * Instantiates a new Form panel.
     */
    public FormPanel() {

        // Setting up the form panel dimension
        Dimension dim = getPreferredSize();
        dim.width = 250;
        setPreferredSize(dim);

        // Setting up the form panel border
        Border innerBorder = BorderFactory.createTitledBorder("Genera grafico");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        // Declaring Components
        chartType = new JLabel("Tipo:");
        chartData = new JLabel("Dato:");
        chartPeriod = new JLabel("Periodo:");
        chartTypeSelection = new JComboBox();
        chartPeriodSelection = new JComboBox();
        chartDataSelection = new JComboBox();
        generateChart = new JButton("Genera");

        // Setting up ComboBoxes
        DefaultComboBoxModel chartTypeModel = new DefaultComboBoxModel();
        chartTypeModel.addElement("Istogramma");
        chartTypeModel.addElement("Lineare");
        chartTypeSelection.setModel(chartTypeModel);

        DefaultComboBoxModel chartDataModel = new DefaultComboBoxModel();
        chartDataModel.addElement("Diossido di carbonio");
        chartDataModel.addElement("Monossido di carbonio");
        chartDataModel.addElement("Metano");
        chartDataSelection.setModel(chartDataModel);

        DefaultComboBoxModel chartPeriodModel = new DefaultComboBoxModel();
        chartPeriodModel.addElement("1980 - 2010");
        chartPeriodModel.addElement("2000 - 2010");
        chartPeriodModel.addElement("2005 - 2010");
        chartPeriodSelection.setModel(chartPeriodModel);

        // Setting Layout
        layoutComponents();

        generateChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableData tableData = new TableData();
                String chartType = chartTypeSelection.getSelectedItem().toString();
                String dataIDString = chartDataSelection.getSelectedItem().toString();
                String periodIDString = chartPeriodSelection.getSelectedItem().toString();
                int dataID = tableData.stringToDataID(dataIDString);
                int periodID = tableData.stringToPeriodID(periodIDString);
                String[][] data = retrieveData();

                TableData chartData = new TableData(chartType, dataID, periodID, data);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new ChartFrame(chartData);
                    }
                });


            }
        });

    }

    /**
     * Layout components.
     */
    public void layoutComponents() {

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        //Initialize the fundamental fields of gc to default values
        gc.gridx = 0;
        gc.gridy = 0;
        //The weight controls how much space the cell occupy, the value itself is
        //not important; what's important is the correlation between the two weights.
        gc.weightx = 1;
        gc.weighty = 1;
        //Components space occupied
        gc.fill = GridBagConstraints.NONE;

        ////////////////First Row

        gc.gridy = 0;
        gc.gridx = 0;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.insets = new Insets(3, 0 , 0, 0);
        gc.anchor = GridBagConstraints.LINE_END;
        add(chartType, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 10 , 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        add(chartTypeSelection, gc);

        ////////////////Next Row

        gc.gridx = 0;
        gc.gridy ++;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.insets = new Insets(3, 0 , 0, 0);
        gc.anchor = GridBagConstraints.LINE_END;
        add(chartData, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 10 , 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        add(chartDataSelection, gc);

        ////////////////Next Row

        gc.gridx = 0;
        gc.gridy ++;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.insets = new Insets(3, 0 , 0, 0);
        gc.anchor = GridBagConstraints.LINE_END;
        add(chartPeriod, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 10 , 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        add(chartPeriodSelection, gc);


        ////////////////Next row

        gc.weightx = 1;
        gc.weighty = 0.8;
        gc.gridx = 1;
        gc.gridy ++;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(20, 10,0,0);
        add(generateChart, gc);
    }

    /**
     * Retrieve data string [ ] [ ].
     *
     * @return the string [ ] [ ]
     */
    public String[][] retrieveData() {

        String[][] data;

        InputStream is = getClass().getResourceAsStream("/opendata.xls");

        //The workbook represents the XLS table general structure
        Workbook wb = null;
        try {
            wb = Workbook.getWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

        //The sheet represents the XLS table specific structure
        Sheet s = wb.getSheet(0);

        //getting row and columns number value
        int row = s.getRows()-1;
        int col = s.getColumns();

        //declaring the new multidimensional array, skipping the first row
        data = new String[row][col];

        //iterating through the sheet and saving to the object,
        //by skipping the first row (column names)
        for (int i = 1; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Cell c = s.getCell(j, i);
                data[i-1][j] = c.getContents();
            }
        }

        return data;

    }


}
