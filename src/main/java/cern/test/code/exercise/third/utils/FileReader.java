package cern.test.code.exercise.third.utils;

import java.io.File;

public class FileReader {
    public File readFileFromFileSystem (String filePath) {
        return new File(filePath);
    }
}
