package cardnight;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;

public class Tools {

    public static final SecureRandom random = new SecureRandom();

    public static String readFile(Path resourcePath) {
        try {
            return String.join("\n", Files.readAllLines(resourcePath, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
