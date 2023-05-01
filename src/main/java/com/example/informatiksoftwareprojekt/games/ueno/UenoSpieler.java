package com.example.informatiksoftwareprojekt.games.ueno;

import com.example.informatiksoftwareprojekt.games.Spieler;

import java.util.ArrayList;

public class UenoSpieler extends Spieler {

    public ArrayList<UenoKarte> handkarten;
    public UenoSpieler(String name) {
        super(name);
        handkarten = new ArrayList<>();
    }

    public void ersetzeKarte(UenoKarte alteKarte, UenoKarte neueKarte) {
        handkarten.set(handkarten.indexOf(alteKarte), neueKarte);
    }
}
