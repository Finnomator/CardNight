package cardnight.games.witch.viewcontroller;

import cardnight.games.witch.WitchKarte;
import javafx.event.Event;
import javafx.event.EventType;

public class WitchKartenKlickEvent extends Event {

    public static final EventType<WitchKartenKlickEvent> KLICK = new EventType<>(Event.ANY, "ANY-WITCH");
    public static final EventType<WitchKartenKlickEvent> ANY = KLICK;
    public final WitchKarte geklickteKarte;
    public WitchKartenKlickEvent(EventType<? extends Event> eventType, WitchKarte geklickteKarte) {
        super(eventType);
        this.geklickteKarte = geklickteKarte;
    }
}
