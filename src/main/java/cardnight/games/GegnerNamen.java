package cardnight.games;

import cardnight.Tools;

import java.util.ArrayList;
import java.util.Arrays;

public class GegnerNamen {
    private static final String[] namen = {
            "Carl",
            "Dieter",
            "Uwe",
            "Hans",
            "Finn",
            "Chris",
            "Marc",
            "David",
            "Henry VIII",
            "Arthur",
            "Alfred",
            "Peter",
            "James",
            "Hugo",
            "Louis XIV",
            "Marty",
            "McFly",
            "Emmett",
            "George",
            "Rick",
            "Morty",
            "Bernard",
            "Laverne",
            "Hoagie",
            "Fred",
            "Edison",
    };

    public static ArrayList<String> gibZufaelligeNamen(int anzahl) {

        assert anzahl <= namen.length;

        ArrayList<String> gegnerNamen = new ArrayList<>(Arrays.asList(namen));
        ArrayList<String> result = new ArrayList<>(anzahl);

        for (int i = 0; i < anzahl; ++i) {
            int wahl = Tools.random.nextInt(gegnerNamen.size());
            result.add(gegnerNamen.get(wahl));
            gegnerNamen.remove(wahl);
        }

        return result;
    }
}
