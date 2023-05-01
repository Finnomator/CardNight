package com.example.informatiksoftwareprojekt.games.ueno;

public class UenoZug {
    public final UenoSpieler spieler;
    public final ZugStart start;
    public final ZugZiel ziel;
    public final UenoKarte karte;

    public UenoZug(UenoSpieler spieler, UenoKarte karte, ZugStart start, ZugZiel ziel) {
        this.spieler = spieler;
        this.start = start;
        this.ziel = ziel;
        this.karte = karte;
    }
}
