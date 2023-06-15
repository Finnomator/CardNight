package cardnight.games.ueno;

import cardnight.Logger;
import cardnight.Tools;
import cardnight.games.GegnerNamen;
import cardnight.games.Spiel;
import cardnight.games.Spieler;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Ueno extends Spiel {

    /*
    Aufbau und Bedeutung der Spielkarten:
    Insgesamt befinden sich im UNO Spiel 108 Spielkarten, welche wie folgt aufgeteilt sind:
        19 blaue Karten mit den Ziffern 0 bis 9 (0 nur einmal, alles andere zweimal)
        19 grüne
        19 rote
        19 gelbe
    Macht zusammen 76 Nummernkarten.
    Dazu kommen die 32 Aktionskarten: je
        8 Zieh-Zwei-, Retour- und Aussetzkarten
        und je 4 schwarze Farbwahl- und Zieh-Vier Farbenwahlkarten (beide werden auch als Joker bezeichnet).
    */

    private final Stack<UenoKarte> ablagestapel;
    private Stack<UenoKarte> nachziehstapel;
    private final UenoSpieler[] spieler;
    private final ArrayList<UenoSpieler> fertigeSpieler;
    private int aktiverSpieler;
    private boolean invertierteRichtung;

    public Ueno(int spielerAnzahl, int kartenProSpieler) {

        ablagestapel = new Stack<>();
        erstelleNeuenNachziehstapel();
        spieler = new UenoSpieler[spielerAnzahl];
        fertigeSpieler = new ArrayList<>();

        spieler[0] = new UenoSpieler("Du", this);
        for (int i = 0; i < kartenProSpieler; ++i)
            spieler[0].fuegeKarteHinzu(karteNachziehen());

        ArrayList<String> gegnerNamen = GegnerNamen.gibZufaelligeNamen(spielerAnzahl - 1);

        for (int i = 1; i < spielerAnzahl; ++i) {
            spieler[i] = new UenoGegner(gegnerNamen.get(i - 1), this);

            for (int j = 0; j < kartenProSpieler; ++j)
                spieler[i].fuegeKarteHinzu(karteNachziehen());
        }

        karteAblegen(karteNachziehen());
        while (gibZuletztAbgelegteKarte().art != UenoKartenArt.ZAHL)
            karteAblegen(karteNachziehen());
    }

    public UenoSpieler gibHauptSpieler() {
        return spieler[0];
    }

    public UenoSpieler[] gibSpieler() {
        return spieler;
    }

    public UenoSpieler gibSpieler(int idx) {
        return spieler[idx];
    }

    public UenoKarte gibZuletztAbgelegteKarte() {
        return ablagestapel.peek();
    }

    public void fuegeFertigenSpielerHinzu(UenoSpieler spieler) {
        assert spieler.istFertig();
        fertigeSpieler.add(spieler);
    }

    public UenoSpieler gibAktivenSpieler() {
        return spieler[aktiverSpieler];
    }

    public UenoSpielrichtung gibSpielrichtung() {
        return invertierteRichtung ? UenoSpielrichtung.GEGEN_UHRZEIGERSINN : UenoSpielrichtung.IM_UHRZEIGERSINN;
    }

    public boolean mussZweiZiehen() {
        UenoKarte obersteKarte = gibZuletztAbgelegteKarte();
        boolean zweiZiehen = obersteKarte.art == UenoKartenArt.PLUS_ZWEI && !obersteKarte.wurdeEffektAktiviert();
        if (zweiZiehen)
            obersteKarte.aktiviereEffekt();
        return zweiZiehen;
    }

    public boolean mussVierZiehen() {
        UenoKarte obersteKarte = gibZuletztAbgelegteKarte();
        boolean vierZiehen = obersteKarte.art == UenoKartenArt.PLUS_VIER && !obersteKarte.wurdeEffektAktiviert();
        if (vierZiehen)
            obersteKarte.aktiviereEffekt();
        return vierZiehen;
    }

    public boolean mussAussetzen() {
        UenoKarte obersteKarte = gibZuletztAbgelegteKarte();
        boolean aussetzen = obersteKarte.art == UenoKartenArt.AUSSETZEN && !obersteKarte.wurdeEffektAktiviert();
        if (aussetzen)
            obersteKarte.aktiviereEffekt();
        return aussetzen;
    }

    private boolean richtungswechsel() {
        UenoKarte obersteKarte = gibZuletztAbgelegteKarte();
        boolean wechsel = obersteKarte.art == UenoKartenArt.RICHTUNGSWECHSEL && !obersteKarte.wurdeEffektAktiviert();
        if (wechsel)
            obersteKarte.aktiviereEffekt();
        return wechsel;
    }

    public UenoSpieler nachsterSpieler(Spieler momentanAktiverSpieler) {

        if (richtungswechsel())
            invertierteRichtung = !invertierteRichtung;

        for (int i = 0; i < spieler.length; ++i)
            if (spieler[i] == momentanAktiverSpieler)
                aktiverSpieler = i;

        if (aktiverSpieler == 0) {
            if (invertierteRichtung)
                aktiverSpieler = spieler.length - 1;
            else
                aktiverSpieler++;
        } else if (aktiverSpieler == spieler.length - 1) {
            if (invertierteRichtung)
                aktiverSpieler--;
            else
                aktiverSpieler = 0;
        } else {
            if (invertierteRichtung)
                aktiverSpieler--;
            else
                aktiverSpieler++;
        }

        UenoSpieler naechster = spieler[aktiverSpieler];

        if (naechster.istFertig()) {
            Logger.log(naechster.name + " wurde ausgelassen, da er fertig ist");
            return nachsterSpieler(naechster);
        }
        return naechster;
    }

    private UenoKarte karteNachziehen() {
        if (nachziehstapel.isEmpty())
            erstelleNeuenNachziehstapel();
        return nachziehstapel.pop();
    }

    private void erstelleNeuenNachziehstapel() {
        Logger.log("Ein neuer Nachziehstapel wurde erstellt");
        nachziehstapel = new Stack<>();
        nachziehstapel.addAll(Arrays.asList(UenoKartenset.erstelleGemischtesSet()));
    }

    public void nKartenNachziehen(UenoSpieler spieler, int n) {
        Logger.log(spieler.name + " zieht " + n + " Karte(n) nach:");
        for (int i = 0; i < n; ++i) {
            UenoKarte karte = karteNachziehen();
            spieler.fuegeKarteHinzu(karte);
            Logger.log("\t" + karte.datenAlsString());
        }
    }

    public ArrayList<UenoSpieler> gibGewinner() {
        return fertigeSpieler;
    }

    public boolean istSpielBeendet() {

        if (gibHauptSpieler().istFertig() && !fertigeSpieler.contains(gibHauptSpieler()))
            fertigeSpieler.add(gibHauptSpieler());

        return gibHauptSpieler().istFertig() || fertigeSpieler.size() >= spieler.length - 1;
    }

    @Override
    public String gibAnleitung() {
        try {
            return Tools.readFile(Paths.get(getClass().getResource("/cardnight/anleitungen/UenoAnleitung").toURI()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void karteAblegen(UenoKarte karte) {
        ablagestapel.add(karte);
    }

    public void karteAblegen(UenoSpieler spieler, UenoKarte karte) {
        karteAblegen(karte);
        spieler.entferneKarte(karte);
        Logger.log(spieler.name + " legte " + karte.datenAlsString());
    }

    public boolean istKarteAblegbar(UenoKarte karte) {
        UenoKarte obersteKarte = gibZuletztAbgelegteKarte();

        if (karte.art == UenoKartenArt.ZAHL)
            return karte.wert == obersteKarte.wert || karte.farbe == obersteKarte.farbe;
        if (karte.art == UenoKartenArt.FARBWAHL || karte.art == UenoKartenArt.PLUS_VIER)
            return true;
        return karte.art == obersteKarte.art || karte.farbe == obersteKarte.farbe;
    }
}
