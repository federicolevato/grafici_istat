package levato.view.chart;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import levato.view.main.TableData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The type Chart window.
 */
public class ChartWindow extends JPanel {

    /**
     * The Co.
     */
    final int CO = 4; //ID Diossido di carbonio
    /**
     * The Co 2.
     */
    final int CO2 = 5; //ID Monossido di carbonio
    /**
     * The Ch 4.
     */
    final int CH4 = 3; //ID Metano
    /**
     * The P 0.
     */
    final int P0 = 0; //ID Periodo 1980-2010
    /**
     * The P 1.
     */
    final int P1 = 1; //ID Periodo 2000-2010
    /**
     * The P 2.
     */
    final int P2 = 2; //ID Periodo 2005-2010
    /**
     * The Error.
     */
    final int ERROR = 3;
    /**
     * The Row.
     */
    final int row = 31;
    /**
     * The Col.
     */
    final int col = 8;

    /**
     * The Chart.
     */
    private JFreeChart chart;
    /**
     * The Chart panel.
     */
    private org.jfree.chart.ChartPanel chartPanel;
    /**
     * The Chart type.
     */
    private String chartType;
    /**
     * The Data id.
     */
    private int dataID;
    /**
     * The Period id.
     */
    private int periodID;
    /**
     * The Data.
     */
    private String data[][] = new String[row][col];


    /**
     * Instantiates a new Chart window.
     *
     * @param drawingData the drawing data
     */
    public ChartWindow(TableData drawingData) {


        setLayout(new BorderLayout());

        dataID = drawingData.getDataID();
        periodID = drawingData.getPeriodID();
        chartType = drawingData.getChartType();
        data = drawingData.getDataArray();

        CategoryDataset dataset = createDataset(dataID, periodID, data);
        chart = createChart(dataset, chartType, dataID);
        chartPanel = new org.jfree.chart.ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setOutlineVisible(false);

        add(chartPanel, BorderLayout.CENTER);

    }


    /**
     * Create dataset category dataset.
     *
     * @param dataID   the data id
     * @param periodID the period id
     * @param data     the data
     * @return the category dataset
     */
    private CategoryDataset createDataset(int dataID, int periodID, String[][] data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
       String dataIDString = "";
       if (dataID == CO){
           dataIDString = "CO";
       }else if(dataID == CO2){
           dataIDString = "CO2";
       }else if(dataID == CH4){
           dataIDString = "CH4";
       }


        if (periodID == P0) {
            for (int i = 0; i < 31; i++) {
                dataset.setValue(Double.parseDouble(data[i][dataID]), dataIDString, data[i][0]);
            }

        }

        if (periodID == P1) {
            for (int i = 20; i < 31; i++)
                dataset.setValue(Double.parseDouble(data[i][dataID]), dataIDString, data[i][0]);
        }

        if (periodID == P2) {
            for (int i = 25; i < 31; i++)
                dataset.setValue(Double.parseDouble(data[i][dataID]), dataIDString, data[i][0]);
        }

        return dataset;
    }

    /**
     * Create chart j free chart.
     *
     * @param dataset   the dataset
     * @param chartType the chart type
     * @param dataID    the data id
     * @return the j free chart
     */
    private JFreeChart createChart(CategoryDataset dataset, String chartType, int dataID) {

        String dataIDString = "";
        if (dataID == CO){
            dataIDString = "CO";
        }else if(dataID == CO2){
            dataIDString = "CO2";
        }else if(dataID == CH4){
            dataIDString = "CH4";
        }
        String DataIDString = "in Tonnellate";

        if (chartType.equals("Istogramma")) {
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Grafico a Barre",
                    "Anno",
                    dataIDString + " (in Tonnellate)",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false, true, false);
            return barChart;
        } else {
            JFreeChart linearChart = ChartFactory.createLineChart(
                    "Grafico Lineare",
                    "Anno",
                    dataIDString + " (in Tonnellate)",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false, true, false);
            return linearChart;
        }
    }

    /**
     * Gets chart.
     *
     * @return the chart
     */
    public JFreeChart getChart() {
        return chart;
    }

}