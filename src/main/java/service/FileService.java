package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class FileService {

    public File readFile(String path) {
        try {
            return new File(path);
        } catch (NullPointerException npe) {
            System.out.println("Path is null");
        }
        return null;
    }

    public void processFile(File file, LineProcessor lineProcessor){
        if (file.exists()) {
            if (file.isFile()) {
                if (file.getPath().endsWith(".txt")) {
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new FileReader(file));
                        String line;
                        while ((line = br.readLine()) != null) {
                            lineProcessor.processLine(line);
                        }
                    } catch (Exception e) {
                        System.out.println("Something went wrong: " + e.getMessage());
                    } finally {
                        if (br != null) {
                            try {
                                br.close();
                            } catch (IOException e) {
                                System.out.println("Something went wrong: " + e.getMessage());
                            }
                        }
                    }
                }
            } else {
                Arrays.stream(Objects.requireNonNull(file.listFiles())).forEach(f -> processFile(f, lineProcessor));
            }
        }
    }
}
