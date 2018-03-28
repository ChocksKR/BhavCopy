package com.bhav;
/**
 * @author Chocks
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class BhavCopy {
	/**
	 * @param args
	 * @throws ParseException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException, ParseException, ClassNotFoundException {
		// TODO Auto-generated method stub
		//https://www.nseindia.com/content/historical/EQUITIES/2017/JAN/cm01JAN2017bhav.csv.zip
        //String url = "https://www.nseindia.com/content/historical/EQUITIES/2017/JAN/cm01JAN2017bhav.csv.zip";
		try {
            URL url = new URL("https://www.nseindia.com/content/historical/EQUITIES/2017/JAN/cm04JAN2017bhav.csv.zip");
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream("test.zip");
            byte[] b = new byte[1024];
            int count;
            while ((count = in.read(b)) >= 0) {
                out.write(b, 0, count);
            }
            out.flush(); 
            out.close(); 
            in.close();                   
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    	unZipIt("test.zip", "test");
    	
//    	try{
//    		Class.forName("com.mysql.jdbc.Driver");
//    		Connection con=DriverManager.getConnection(
//    		"jdbc:mysql://localhost:3306/test","root","mysql");
//    		//here sonoo is database name, root is username and password
//    		Statement stmt=con.createStatement();
//    		ResultSet rs=stmt.executeQuery("select * from tabletest");
//    		while(rs.next())
//    			System.out.println(rs.getString(1)+"  "+rs.getString(2));
//    		con.close();
//    	}catch(Exception e){
//    		System.out.println(e);
//    	}
    	
		File folder = new File("test");
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        System.out.println(file.getAbsolutePath());
		        readFiles(file.getAbsolutePath());
		    }
		}
	}
	
	public static void readFiles(String file) throws SQLException, ParseException, ClassNotFoundException {
		BufferedReader br = null;
        try {
        	String line = "";
            br = new BufferedReader(new FileReader(file));
            final DateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
            br.readLine(); //To skip the header
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] data = line.split(",");
                System.out.println("->" + data[0] + ", " + data[1] + "," + data[2] + ","
                		+ data[3] + "," + data[4] + "," + data[5] + "," + data[6] + ","
                		+ data[7] + "," + data[8] + "," + data[9] + "," + data[10] + ","
                		+ data[11] + "," + data[12]);

//				Class.forName("com.mysql.jdbc.Driver");
//                Connection con=DriverManager.getConnection(
//        	    		"jdbc:mysql://localhost:3306/bhavcopy","root","mysql");
//
//	    		Statement stmt=con.createStatement();
//	    		String query = " insert into bhavcopy (SYMBOL,SERIES,OPEN,HIGH,LOW,CLOSE,LAST,PREVCLOSE,TOTTRDQTY,TOTTRDVAL,TIMESTAMP,TOTALTRADES,ISIN)"
//	    		        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//
//	    		// create the mysql insert preparedstatement
//			    PreparedStatement preparedStmt = con.prepareStatement(query);
//			    preparedStmt.setString (1, data[0]);
//			    preparedStmt.setString (2, data[1]);
//			    preparedStmt.setDouble (3, Double.parseDouble(data[2]));
//			    preparedStmt.setDouble (4, Double.parseDouble(data[3]));
//			    preparedStmt.setDouble (5, Double.parseDouble(data[4]));
//			    preparedStmt.setDouble (6, Double.parseDouble(data[5]));
//			    preparedStmt.setDouble (7, Double.parseDouble(data[6]));
//			    preparedStmt.setDouble (8, Double.parseDouble(data[7]));
//			    preparedStmt.setInt    (9, Integer.parseInt(data[8]));
//			    preparedStmt.setDouble (10, Double.parseDouble(data[9]));
//			    preparedStmt.setDate   (11, new java.sql.Date(fmt.parse(data[10]).getTime()));
//			    preparedStmt.setInt    (12, Integer.parseInt(data[11]));
//			    preparedStmt.setString (13, data[12]);
//
//			    preparedStmt.execute();
//
//			    con.close();
   	    		break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
    /**
     * Unzip it
     * @param zipFile input zip file
     * @param outputFolder zip file output folder
     */
    public static void unZipIt(String zipFile, String outputFolder){
    	byte[] buffer = new byte[1024];
    	try{
	    	//create output directory is not exists
	    	File folder = new File(outputFolder);
	    	if(!folder.exists()){
	    		folder.mkdir();
	    	}
	    	//get the zip file content
	    	ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
	    	//get the zipped file list entry
	    	ZipEntry ze = zis.getNextEntry();

	    	while(ze!=null){
	    		int len;
	    		String fileName = ze.getName();
	
	    		File newFile = new File(outputFolder + File.separator + fileName);
	
	    		System.out.println("file unzip : "+ newFile.getAbsoluteFile());
	
	    		//create all non exists folders
	    		//else you will hit FileNotFoundException for compressed folder
	    		new File(newFile.getParent()).mkdirs();
	    		
	    		FileOutputStream fos = new FileOutputStream(newFile);
	    		
	    		while ((len = zis.read(buffer)) > 0) {
	       			fos.write(buffer, 0, len);
	    		}
	            fos.close();
	            ze = zis.getNextEntry();
	    	}
	        zis.closeEntry();
	    	zis.close();
	    	System.out.println("Done");
	    }catch(IOException ex){
	       ex.printStackTrace();
	    }
    }
}