package cardnight;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Tools {
    public static String readFile(URL path) {
        byte[] encoded;
        try {
            encoded = Files.readAllBytes(Paths.get(path.toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
