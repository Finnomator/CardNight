package cardnight.games.witch;

import java.util.ArrayList;
import java.util.Scanner;

public class WitchMensch extends WitchSpieler{
    public WitchMensch(String n, Witch s) {
        super(n, s);
    }

    @Override
    public void schaetzen() {
        schaetzung = spiel.gibObserverView().frageNachSchaetzung();
    }

    @Override
    public WitchKarte spielen() {
        ArrayList<WitchKarte> spielbar = getSpielbar();

        //TODO: UI abfrage
        //nur temporär:
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter int: " + spielbar);

        // int cardint = myObj.nextInt();  // Read user input
        int cardint = 0;

        WitchKarte k = spielbar.get(cardint);
        hand.remove(k);
        return k;
    }

    @Override
    public boolean istAmZug() {
        throw new UnsupportedOperationException();
    }
}
