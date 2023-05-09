package cardnight.games.ueno.viewcontroler;

import cardnight.games.ueno.UenoKarte;
import javafx.event.Event;
import javafx.event.EventType;

public class UenoKarteKlickEvent extends Event {

    public static final EventType<UenoKarteKlickEvent> KLICK = new EventType<>(Event.ANY, "ANY");
    public static final EventType<UenoKarteKlickEvent> ANY = KLICK;
    public final UenoKarte geklickteKarte;
    public UenoKarteKlickEvent(EventType<? extends Event> eventType, UenoKarte geklickteKarte) {
        super(eventType);
        this.geklickteKarte = geklickteKarte;
    }
}
