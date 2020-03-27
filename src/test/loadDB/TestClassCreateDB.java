package loadDB;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class TestClassCreateDB {
    private static final String NAME_FILE = "scriptDB.sql";
    private static final String FILE_PROPERTIES_PATH = "src/main/resources/database.properties";


    public static void main(String[] args) {
        Properties prop = new Properties();
        try{
        FileInputStream fis = new FileInputStream(FILE_PROPERTIES_PATH);
        prop.load(fis);
        String url = prop.getProperty("jdbc.url");
        String user = prop.getProperty("jdbc.user");
        String password = prop.getProperty("jdbc.password");
            try  (Connection connection = DriverManager.getConnection(url,user,password)) {
                String str = "";
                StringBuilder stringBuilder = new StringBuilder("");
                PreparedStatement pst = null;
                FileReader fileReader = new FileReader(NAME_FILE);
                System.out.println("File found...");
                Scanner scanner = new Scanner(fileReader);
                System.out.println("File read...");
                while (scanner.hasNext()) {
                    str = scanner.nextLine();
                    if (!str.equals("")) {
                        stringBuilder.append(str).append("\n");
                    }
                    if (!stringBuilder.toString().equals("")) {
                        if (stringBuilder.charAt(stringBuilder.length() - 2) == ';') {
                            System.out.println(stringBuilder.toString());
                            pst = connection.prepareStatement(stringBuilder.toString());
                            pst.execute();
                            stringBuilder.delete(0, stringBuilder.length());
                        }
                    }
                    str = "";
                }
                fileReader.close();
                scanner.close();
                System.out.println("Database successfully created...");
            }
        } catch (SQLException | IOException e) {
            System.out.println("\nException:\n" + e.getMessage());
        }
    }
}
