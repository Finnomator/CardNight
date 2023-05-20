package cardnight.games.witch;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    @Override
    public boolean istAmZug() {
        throw new NotImplementedException();
    }
}
