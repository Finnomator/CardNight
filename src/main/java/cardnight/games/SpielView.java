package cardnight.games;

import java.io.IOException;

public abstract class SpielView {
    public abstract void initialize() throws IOException;

    public abstract void beendeSpiel();

    public abstract void pauseClick() throws IOException;
}
