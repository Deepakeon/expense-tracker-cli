package helpers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {
    final private Path filePath;

    public FileManager(String filePath){
        this.filePath = Path.of(filePath);
    }

    public boolean doesFileExists(){
        return Files.exists(filePath);
    }

    public void createFile(){
        try{
            Files.createFile(filePath);
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public String readFile(){
        if (!doesFileExists()){
            createFile();
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))){
            StringBuilder fileContents = new StringBuilder();
            String content;
            while((content = reader.readLine())!= null){
                fileContents.append(content);
            }
            return fileContents.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public void writeFile(String fileContents){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))){
            writer.write(fileContents);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}