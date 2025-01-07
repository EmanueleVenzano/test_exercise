package cern.test.code.exercise.third.utils;

import java.io.File;

/**
 * Utility class that create a File from its filePath. As it is injected, it can be mocked during tests
 * 
 * @author Emanuele Venzano
 * @version 1.0
 * @since 2025-01-07
 */
public class FileReader {
    public File readFileFromFileSystem (String filePath) {
        return new File(filePath);
    }
}
