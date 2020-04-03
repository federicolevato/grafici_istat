package levato.view.main;

public class TableData {

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
    private  String[][]data;
    /**
     * The Drawing data.
     */
    private TableData drawingData;


    /**
     * Instantiates a new Table data.
     */
    public TableData() {}

    /**
     * Instantiates a new Table data.
     *
     * @param chartType the chart type
     * @param dataID    the data id
     * @param periodID  the period id
     * @param data      the data
     */
    public TableData(String chartType, int dataID, int periodID, String[][]data) {
        this.chartType = chartType;
        this.dataID = dataID;
        this.periodID = periodID;
        this.data = data;
    }

    /**
     * Gets chart type.
     *
     * @return the chart type
     */
    public String getChartType() {
        return chartType;
    }

    /**
     * Sets chart type.
     *
     * @param chartType the chart type
     */
    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    /**
     * Gets data id.
     *
     * @return the data id
     */
    public int getDataID() {
        return dataID;
    }

    /**
     * String to data id int.
     *
     * @param dataID the data id
     * @return the int
     */
    public int stringToDataID(String dataID) {
        if(dataID.equals("Diossido di carbonio")){
            return CO2;
        }else if(dataID.equals("Monossido di carbonio")){
            return CO;
        }else if(dataID.equals("Metano")){
            return CH4;
        }else{
            return ERROR;
        }
    }

    /**
     * String to period id int.
     *
     * @param periodID the period id
     * @return the int
     */
    public int stringToPeriodID(String periodID) {
        if(periodID.equals("1980 - 2010")){
            return P0;
        }else if(periodID.equals("2000 - 2010")){
            return P1;
        }else if(periodID.equals("2005 - 2010")){
            return P2;
        }else{
            return ERROR;
        }
    }

    /**
     * Sets data id.
     *
     * @param dataID the data id
     */
    public void setDataID(int dataID) {
        this.dataID = dataID;
    }

    /**
     * Gets period id.
     *
     * @return the period id
     */
    public int getPeriodID() {
        return periodID;
    }

    /**
     * Sets period id.
     *
     * @param periodID the period id
     */
    public void setPeriodID(int periodID) {
        this.periodID = periodID;
    }

    /**
     * Set drawing data.
     *
     * @param tableData the table data
     */
    public void setDrawingData(TableData tableData){
        this.drawingData = tableData;
    }

    /**
     * Get drawing data table data.
     *
     * @return the table data
     */
    public TableData getDrawingData(){
        return this.drawingData;
    }

    /**
     * Get data array string [ ] [ ].
     *
     * @return the string [ ] [ ]
     */
    public String[][] getDataArray(){
        return data;
    }
}