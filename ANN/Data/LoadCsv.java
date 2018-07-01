package Neuroshop.ANN.Data;

import java.io.*;
import java.util.Scanner;

/** 
 * LoadCsv This class deals with CSV files 
 * @author Alan de Souza, Fábio Soares
 * @version 0.1
 */
public class LoadCsv {
	
	/**
	 * CSV file path 
	 */
	private File FILE;
	/**
	 * SavedData matrix to store values from CSV file
	 */
	private double[][] dataMatrix;
	
        
        private boolean columnsInFirstRow=false;
        
        private String separator = ",";
        
        private String fullFilePath;
        
        private String[] columnNames;
        
        final double missingValue=Double.NaN;
        
        public LoadCsv(){
            
        }
        
        public LoadCsv(File file){
            this.FILE=file;
        }
        
        public LoadCsv(String fileName,boolean _columnsInFirstRow,String _separator){
            this.fullFilePath=fileName;
            this.columnsInFirstRow=_columnsInFirstRow;
            this.separator=_separator;
        }
        
	/**
	 * Gets SavedData matrix
	 * @param file
	 * @return Java Matrix
	 */
	public double[][] getData(File file){
            return getData(file, false);
	}
        
        public double[][] getData(File file, boolean _columnsInFirstRow){
            return getData(file, _columnsInFirstRow,",");
        }

        public double[][] getData(File file, boolean _columnsInFirstRow,String _separator){
            this.FILE = file;
            this.columnsInFirstRow=_columnsInFirstRow;
            this.separator=_separator;
            try {
		dataMatrix = csvData2Matrix();
		System.out.println("File loaded!");
            } catch (Exception e) {
		System.err.println("Error while loading CSV file. Details: " + e.getMessage());
            }
            return dataMatrix;
        }
        
        public static double[][] getData(String fullPath, boolean _columnsInFirstRow,String _separator){
            LoadCsv lcsv = new LoadCsv(fullPath,_columnsInFirstRow,_separator);
            lcsv.columnsInFirstRow=_columnsInFirstRow;
            lcsv.separator=_separator;
            try{
                lcsv.dataMatrix=lcsv.csvData2Matrix(fullPath);
                System.out.println("File "+fullPath+" loaded!");
            }
            catch(IOException ioe){
                System.err.println("Error while loading CSV file. Details: " + ioe.getMessage());
            }
            return lcsv.dataMatrix;
        }
        
        public double[][] getDataMatrix(String fullPath, boolean _columnsInFirstRow,String _separator){
            this.columnsInFirstRow=_columnsInFirstRow;
            this.separator=_separator;
            try{
                this.dataMatrix=csvData2Matrix(fullPath);
                System.out.println("File "+fullPath+" loaded!");
            }
            catch(IOException ioe){
                System.err.println("Error while loading CSV file. Details: "+ioe.getMessage());
            }
            return this.dataMatrix;
        }

        public static DataSet getDataSet(File file, boolean _columnsInFirstRow,String _separator){
            LoadCsv lcsv = new LoadCsv(file);
            return LoadCsv.getDataSet(file, _columnsInFirstRow, _separator);
        }
        
        public static DataSet getDataSet(String fullPath,boolean _columnsInFirstRow,String _separator){
            LoadCsv lcsv = new LoadCsv(fullPath,_columnsInFirstRow,_separator);
            lcsv.columnsInFirstRow=_columnsInFirstRow;
            lcsv.separator=_separator;
            try{
                lcsv.dataMatrix=lcsv.csvData2Matrix(fullPath);
                System.out.println("File "+fullPath+" loaded!");
            }
            catch(IOException ioe){
                System.err.println("Error while loading CSV file. Details: " + ioe.getMessage());
            }
            return new DataSet(lcsv.dataMatrix, lcsv.columnNames);
            
        }
        
	/**
	 * Loads CSV file into Java matrix
	 * @return Java matrix
	 * @throws IOException
	 */
	private double[][] csvData2Matrix() throws IOException {

		String fullPath = FILE.getAbsolutePath();

		return csvData2Matrix(fullPath);

	}

	private double[][] csvData2Matrix(String fullPath) throws IOException {

            BufferedReader buffer = new BufferedReader(new FileReader(fullPath));
		
            try {
                StringBuilder builder = new StringBuilder();
			
                String line = buffer.readLine();
			
                int columns = line.split(this.separator).length;
                columnNames=new String[columns];
                int rows = 0; 
                while (line != null) {
                    builder.append(line);
                    builder.append(System.lineSeparator());
                    line = buffer.readLine();
                    rows++;
                }
		boolean cr = this.columnsInFirstRow;
                double[][] matrix = new double[cr?rows-1:rows][columns];
                String everything = builder.toString();
			
                Scanner scan = new Scanner( everything );
                rows = 0;
                
                while(scan.hasNextLine()){
                    String[] strVector = scan.nextLine().split(this.separator,-1);
                    if(cr && rows==0){
                        System.out.println(strVector.length);
                        System.arraycopy(strVector, 0, columnNames, 0, strVector.length);
                    }
                    else{
                        for (int i = 0; i < strVector.length; i++) {
                            try{
                                matrix[cr?rows-1:rows][i] = Double.parseDouble(strVector[i]);
                            }
                            catch(Exception e){
                                matrix[cr?rows-1:rows][i] = this.missingValue;
                            }
                        }
                    }
                    rows++;
                }
		scan.close();
			
		return matrix;

            } finally {
		buffer.close();
            }

	}
        
        public void save() throws IOException{
            if(fullFilePath==null)
                fullFilePath=FILE.getAbsolutePath();
            try(FileWriter w = new FileWriter(fullFilePath)) {
                StringBuilder sb = new StringBuilder();
                if(columnsInFirstRow){
                    for(int i=0;i<columnNames.length;i++){
                        if(i>0)
                            sb.append(separator);
                        sb.append(columnNames[i]);
                    }
                    sb.append(System.lineSeparator());
                }
                for(int i=0;i<dataMatrix.length;i++){
                    for(int j=0;j<dataMatrix[i].length;j++){
                        if(j>0)
                            sb.append(separator);
                        try{
                            if(!Double.isNaN(dataMatrix[i][j]))
                                sb.append(String.valueOf(dataMatrix[i][j]));
                            else
                                sb.append("");
                        }
                        catch(Exception e){
                            sb.append("");
                        }
                    }
                    if(i<dataMatrix.length-1)
                        sb.append(System.lineSeparator());
                }
                w.append(sb.toString());
                w.flush();
                w.close();
            }
            
        }
        
        public static void dataMatrix2csv(double[][] data,String[] columnNames,File file,String filename,String separator){
            LoadCsv lcsv = new LoadCsv(file);
            try{
                lcsv.fullFilePath=file.getAbsolutePath();
                lcsv.dataMatrix=data;
                lcsv.columnsInFirstRow=true;
                lcsv.columnNames=columnNames;
                lcsv.separator=separator;
                lcsv.save();
                System.out.println("File "+filename+" saved succesfully!");
            }
            catch(IOException ioe){
                System.err.println("Error while saving "+filename+":"+ioe.getMessage());
            }
        }
        
        public static void dataMatrix2csv(double[][] data,String[] columnNames,String filename,String separator){
            LoadCsv lcsv = new LoadCsv(filename, true, separator);
            lcsv.dataMatrix=data;
            lcsv.columnNames=columnNames;
            try{
                lcsv.save();
                System.out.println("File "+filename+" saved succesfully!");
            }
            catch(IOException ioe){
                System.err.println("Error while saving "+filename+":"+ioe.getMessage());
            }
        }
        
        public String[] getColumnNames(){
            return columnNames;
        }

}
