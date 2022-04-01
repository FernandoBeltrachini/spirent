package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.util.Arrays.stream;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public class FileService {

    public static final String ALLOWED_EXTENSIONS = ".txt";

    public File readFile(String path) {
        try {
            return new File(path);
        } catch (NullPointerException npe) {
            System.out.println("Path is null");
        }
        return null;
    }

    public void processFile(File file, LineProcessor lineProcessor){
        if (nonNull(file) && file.exists()) {
            if (file.isFile()) {
                if (file.getPath().endsWith(ALLOWED_EXTENSIONS)) {
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
                stream(requireNonNull(file.listFiles())).forEach(f -> processFile(f, lineProcessor));
            }
        }
    }
}
