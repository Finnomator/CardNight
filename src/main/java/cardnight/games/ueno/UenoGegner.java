package cardnight.games.ueno;

import java.util.ArrayList;

public class UenoGegner extends UenoSpieler {

    public UenoGegner(String name, Ueno spiel) {
        super(name, spiel);
    }

    public UenoKarte whaeleKarteZumAblegen() {
        UenoKarte wahl = vorAuswahl();
        if (wahl != null && wahl.istSchwarz())
            wahl.setzeFarbe(spiel.gibZuletztAbgelegteKarte().farbe);
        return wahl;
    }

    private UenoKarte vorAuswahl() {
        ArrayList<UenoKarte> ablegbare = ablegbareKarten();

        // Wenn nur eine Karte ablegbar ist, die direkt ablegen
        if (ablegbare.size() == 1)
            return ablegbare.get(0);

        if (ablegbare.size() == 0)
            return null;

        UenoKarte letzteKarte = spiel.gibZuletztAbgelegteKarte();
        ArrayList<UenoKarte> zahlenKarten = filterNachArt(UenoKartenArt.ZAHL, ablegbare, false);
        ArrayList<UenoKarte> sonderkarten = filterNachArt(UenoKartenArt.ZAHL, ablegbare, true);

        boolean kannZahlAblegen = zahlenKarten.size() > 0;
        boolean kannSonderAblegen = sonderkarten.size() > 0;

        boolean sollteZahlAblegen = kannZahlAblegen && (letzteKarte.art == UenoKartenArt.FARBWAHL || letzteKarte.art == UenoKartenArt.PLUS_VIER || letzteKarte.art == UenoKartenArt.PLUS_ZWEI);
        boolean sollteSonderAblegen = kannSonderAblegen && (ablegbare.size() - sonderkarten.size() <= 2 || (double) sonderkarten.size() / ablegbare.size() >= 0.25);

        if (sollteZahlAblegen && ablegbare.size() - sonderkarten.size() > 2) {
            // Versuche eine Zahlenkarte abzulegen, um nicht noch einen Sound zu spielen.
            return zahlenKarten.get(0);
        }

        if (sollteSonderAblegen) {
            // Versuche Sonderkarten abzulegen, damit die sich nicht anstauen
            return sonderkarten.get(0);
        }

        return ablegbare.get(0);
    }

    public static ArrayList<UenoKarte> filterNachArt(UenoKartenArt art, ArrayList<UenoKarte> karten, boolean invertiert) {
        ArrayList<UenoKarte> resultat = new ArrayList<>();
        for (UenoKarte karte : karten) {
            if (invertiert) {
                if (karte.art != art)
                    resultat.add(karte);
            } else {
                if (karte.art == art)
                    resultat.add(karte);
            }
        }
        return resultat;
    }
}
