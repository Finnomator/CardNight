package cardnight.games.ueno;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class UenoGegner extends UenoSpieler {

    public UenoGegner(String name, Ueno spiel) {
        super(name, spiel);
    }

    public UenoKarte whaeleKarteZumAblegen() {
        UenoKarte wahl = vorAuswahl();
        if (wahl != null && wahl.istSchwarz())
            wahl.setzeFarbe(meisteFarbeAufDerHand());
        return wahl;
    }

    private UenoKarte vorAuswahl() {
        ArrayList<UenoKarte> ablegbare = ablegbareKarten();

        // Wenn nur eine Karte ablegbar ist, die direkt ablegen
        if (ablegbare.size() == 1)
            return ablegbare.get(0);

        if (ablegbare.isEmpty())
            return null;

        UenoKarte letzteKarte = spiel.gibZuletztAbgelegteKarte();
        ArrayList<UenoKarte> zahlenKarten = filterNachArt(UenoKartenArt.ZAHL, ablegbare, false);
        ArrayList<UenoKarte> sonderkarten = filterNachArt(UenoKartenArt.ZAHL, ablegbare, true);

        boolean kannZahlAblegen = !zahlenKarten.isEmpty();
        boolean kannSonderAblegen = !sonderkarten.isEmpty();

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

    private UenoFarbe meisteFarbeAufDerHand() {
        EnumMap<UenoFarbe, Integer> zaehlung = new EnumMap<>(UenoFarbe.class);

        for (UenoFarbe farbe : UenoFarbe.values())
            zaehlung.put(farbe, 0);

        for (UenoKarte karte : handkarten) {
            if (karte.farbe != null)
                zaehlung.put(karte.farbe, zaehlung.get(karte.farbe) + 1);
        }

        int max = 0;
        UenoFarbe maxFarbe = UenoFarbe.BLAU;

        for (Map.Entry<UenoFarbe, Integer> entry : zaehlung.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                maxFarbe = entry.getKey();
            }
        }

        return maxFarbe;
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
