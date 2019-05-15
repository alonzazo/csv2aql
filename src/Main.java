import Generator.AQLGenerator;
import Generator.Generator;
import SyntaxAnalyzer.CSVParse;
import SyntaxAnalyzer.CSVParser;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        String sourcePath;
        String outputPath;
        String collectionTarget;

        if (args.length > 0){
            sourcePath = args[0];

            if (args.length > 1) {
                outputPath = args[1];
                if (args.length > 2) {
                    collectionTarget = args[2];
                } else {
                    collectionTarget = "defaultCollection";
                }
            }
            else {
                outputPath = args[0] + ".aql";
                collectionTarget = "defaultCollection";
            }

        } else {

            System.out.println("Escriba la dirección del archivo:");

            Scanner scanner = new Scanner(System.in);

            sourcePath = scanner.next();

            System.out.println("Escriba el nombre del archivo de salida:");

            outputPath = scanner.next();

            System.out.println("Escriba el nombre de la colección donde se quieren insertar los datos:");

            collectionTarget = scanner.next();
        }

        try {
            File file = new File(System.getProperty("user.dir") + sourcePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            CSVParse csvParse = new CSVParser(fileInputStream);
            String[][] csvStructure = csvParse.getAllValues();

            Generator generator = new AQLGenerator(csvStructure, collectionTarget);
            String aqlCode = generator.generate();

            FileWriter fileWriter = new FileWriter(outputPath);
            fileWriter.write(aqlCode);
            fileWriter.close();

        } catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


    }
}
