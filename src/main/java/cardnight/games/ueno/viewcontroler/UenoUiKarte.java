package cardnight.games.ueno.viewcontroler;

import cardnight.games.ueno.UenoFarbe;
import cardnight.games.ueno.UenoKarte;

public abstract class UenoUiKarte {

    protected UenoKarte karte;

    public abstract void uiErstellen(UenoKarte karte);

    public static String farbeZuString(UenoFarbe farbe) {
        if (farbe == null)
            return "#000000";
        switch (farbe) {
            case ROT:
                return "#ff0000";
            case GRUEN:
                return "#00ff00";
            case BLAU:
                return "#0000ff";
            case GELB:
                return "#ffff00";
        }
        throw new RuntimeException();
    }

    public static String uenoKartenArtZuString(UenoKarte karte) {
        String txt = "";
        switch (karte.art) {
            case ZAHL:
                txt = String.valueOf(karte.wert);
                break;
            case PLUS_ZWEI:
                txt = "+2";
                break;
            case PLUS_VIER:
                txt = "+4";
                break;
            case RICHTUNGSWECHSEL:
                txt = "-><-";
                break;
            case FARBWAHL:
                txt = "O";
                break;
            case AUSSETZEN:
                txt = "%";
                break;
        }
        return txt;
    }
}
