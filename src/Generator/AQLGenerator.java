package Generator;

public class AQLGenerator implements Generator {

    private String[][] data;
    private ColumnType[] columnTypes;
    private String collectionTarget;

    public AQLGenerator(String[][] dataMatrix, String collectionTarget){
        this.data = dataMatrix;
        this.collectionTarget = collectionTarget;
        determineColumnTypes();
    }

    @Override
    public String generate() {
        //Generating JsonObjects ARRAY
        String result = "LET data = [\n";
        // Creating each JsonObject
        for (int i = 1; i < data.length - 1; i++)
            result += "\t" + getJsonFromRow(i) + ",\n";
        result += "\t" + getJsonFromRow(data.length - 1)  + "\n]\n";

        //Adding data to collection
        result += "FOR d IN data\n" +
                "\tINSERT d INTO " + collectionTarget + "\n";

        return result;
    }

    private String getJsonFromRow(int row){
        String jsonObject = "{";
        for (int column = 0; column < data[0].length - 1; column++)
            jsonObject += "\"" + data[0][column] + "\":" + parseCell(row,column) + ",";

        jsonObject += "\"" + data[0][data[0].length-1] + "\":" + parseCell(row,data[0].length-1) + "}";
        return jsonObject;
    }

    private String parseCell(int row, int column){
        if (columnTypes[column] == ColumnType.numeric)
            return data[row][column];
        else
            return "\"" + data[row][column] + "\"";
    }

    private void determineColumnTypes(){
        columnTypes = new ColumnType[data[0].length];

        for (int i = 0; i < data[0].length; i++)
            columnTypes[i] = determineColumnType(i);
    }

    private ColumnType determineColumnType(int column){
        ColumnType currentColumnType = ColumnType.numeric;

        for (int i = 1; i < data.length ; i++)
            if (!isNumeric(data[i][column])){
                currentColumnType = ColumnType.literal;
                break;
            }
        return currentColumnType;
    }

    private boolean isNumeric(String string){
        return string.matches("-?[0-9]+(\\.[0-9]+)?");
    }
}
