package cardnight.games.ueno;

public class UenoZug {
    public final UenoSpieler spieler;
    public final ZugStart start;
    public final ZugZiel ziel;
    public final UenoKarte karte;

    public UenoZug(UenoSpieler spieler, UenoKarte karte, ZugZiel ziel) {
        this.spieler = spieler;
        this.ziel = ziel;
        this.karte = karte;

        switch (ziel) {

            case ABLAGESTAPEL:
                start = ZugStart.HAND;
                break;
            case HAND:
                start = ZugStart.NACHZIEHSTAPEL;
                break;
            default:
                throw new RuntimeException();
        }
    }

    public UenoZug(UenoSpieler spieler, UenoKarte karte, ZugStart start) {
        this.spieler = spieler;
        this.start = start;
        this.karte = karte;

        switch (start) {
            case NACHZIEHSTAPEL:
                ziel = ZugZiel.HAND;
                break;
            case HAND:
                ziel = ZugZiel.ABLAGESTAPEL;
                break;
            default:
                throw new RuntimeException();
        }
    }
}
