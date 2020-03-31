package writeFileException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteExceptionToFile {
    public static void getFileException(String messageException) {
        try {
            File file = new File("FileException.txt").getAbsoluteFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(messageException);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

}
