package levato.view.main;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;

/**
 * The type Table panel.
 */
public class TablePanel extends JPanel {

    /**
     * The Debug.
     */
    private boolean DEBUG = false;
    /**
     * The Table data.
     */
    private TableData tableData = new TableData();
    /**
     * The Table.
     */
    private JTable table;
    /**
     * The Scroll pane.
     */
    private JScrollPane scrollPane;
    /**
     * The Data.
     */
    private String[][] data;

    /**
     * Instantiates a new Table panel.
     */
    public TablePanel() {

        //SPECIFIC column names for the data retrieved.
        String[] columnNames = {"Anno", "SOx (in T)",
                "NOx (in T)", "CH4 (in T)", "CO (in T)",
                "CO2 (in T)", "NH3 (in T)"};

        //Reads from file and saves in multi-dimensional array
        retrieveData();

        //Setting the layout chosen
        setLayout(new BorderLayout());

        //Initializing the components, declared previously
        TableModel model = new DefaultTableModel(data, columnNames) {
        @Override
        public boolean isCellEditable(int row, int column) {
            //set the table to uneditable
            return false;
          }
        };
        table = new JTable(model);
        for (int i = 1; i < 7; i++ )
            table.getColumnModel().getColumn(i).setCellRenderer(new NumberTableCellRenderer());

        // get a reference to the header, set the font
        JTableHeader header = table.getTableHeader();

        //Table settings
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        //Adding the components to the layout
        add(new JScrollPane(table), BorderLayout.CENTER);

    }


    /**
     * Retrieve data.
     */
    public void retrieveData() {

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

        /*
        for(int i=0; i<row-1; i++) {
            for (int j = 0; j<col; j++){
                System.out.println(data[i][j]);
            }
        }

        System.out.println(row-1);
        System.out.println(col);

         */

    }

    public class NumberTableCellRenderer extends DefaultTableCellRenderer {
        public NumberTableCellRenderer() {}

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column){
            double newValue;
            String newString;
            if (value instanceof String) {
                newString = value.toString();
                newValue = Double.parseDouble(newString);
                value = newValue;
                value = NumberFormat.getNumberInstance().format(value);
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
        }
    }

