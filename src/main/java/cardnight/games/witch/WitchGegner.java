package cardnight.games.witch;

import java.util.ArrayList;

public class WitchGegner extends WitchSpieler {
    public WitchGegner(String n, Witch s) {
        super(n, s);
        hand = new ArrayList<>();
    }

    @Override
    public void schaetzen() {

    }

    @Override
    public WitchKarte spielen() {
        return null;
    }

}
