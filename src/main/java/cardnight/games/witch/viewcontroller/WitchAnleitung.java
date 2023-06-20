package cardnight.games.witch.viewcontroller;

public class WitchAnleitung {
    public static final String anleitung = "Spielziel:\n" +
            "    Ziel des Spiels ist durch korrektes Einschätzen seiner Karten möglichst viele Punkte zu erreichen. Der Spieler,\n" +
            "    welcher am Spielende die meisten Punkte hat, gewinnt.\n" +
            "\n" +
            "Spielablauf:\n" +
            "    Die Rundenanzahl sagt aus, wie viele Karten jeder Spieler diese Runde hat. Somit spielt beispielsweise in der ersten\n" +
            "    Runde jeder Spieler mit genau einer Karte. Anschließend wird eine Karte aufgedeckt, welche den sogenannten Trumpf\n" +
            "    dieser Runde darstellt. Dieser ist die horizontal liegende Karte links auf dem Tisch. Jede Runde beginnt der nächste\n" +
            "    Spieler im Uhrzeigersinn, was mit dem Ladesymbol angezeigt wird. Am Rundenanfang wird die Schätzung in das Textfeld\n" +
            "    eingetragen. Danach legt jeder Spieler, beginnend bei dem Startspieler dieser Runde, im Uhrzeigersinn eine Karte auf\n" +
            "    den Ablagestapel. Nachdem jeder einmal am Zug war, wird dieser sogenannte Stich an den Spieler verteilt, welche die\n" +
            "    beste Karte abgelegt hat. Dieser Spieler beginnt dann mit der Ablegen der nächsten Karte. Dies läuft so lange, bis\n" +
            "    alle Spieler ihre Karten abgelegt haben. Dann werden die Punkte dieser Runde ausgewertet.\n" +
            "\n" +
            "Ablageregeln:\n" +
            "    Narren und Hexen, dürfen immer gelegt werden. Für die restlichen Karten besteht Farbzwang. Das bedeutet, dass immer\n" +
            "    die unterste farbige Karte auf dem Ablagestapel festlegt, welche Farbe gelegt werden darf. Wenn keine farbige Karte\n" +
            "    auf dem Ablagestapel liegt, darf eine beliebige Karte gelegt werden. Dies ist auch der Fall, wenn man keine Karte\n" +
            "    dieser Farbe besitzt, wobei Narren und Hexen bei dem Farbzwang nicht miteinbezogen werden.\n" +
            "\n" +
            "Stichbewertung:\n" +
            "    Die Farbe der Trumpfkarte ist für diese Runde die beste Farbe. Falls die Trumpfkarte ein Narr oder eine Hexe ist,\n" +
            "    gibt es keine universell beste Farbe für diese Runde. Die zweitbeste Farbe ist bei jedem Stich unterschiedlich.\n" +
            "    Hierbei handelt es sich immer um die Farbe, welche auch der Farbzwang war, also die Farbe der untersten farbigen\n" +
            "    Karte. Falls der Trumpf ein Narr oder eine Hexe ist, ist diese Farbe die beste Farbe, nicht die Zweitbeste.\n" +
            "    Innerhalb einer Farbe bestimmt der Kartenwert, welche Karte besser ist. Hexen sind besser als alle anderen Karten,\n" +
            "    wobei bei mehreren abgelegten Hexen die erste Hexe gewinnt. Narren sind schlechter als alle anderen Karten, wobei\n" +
            "    der erste Narr gewinnt, wenn in einem Stich nur Narren sind.\n" +
            "\n" +
            "Punktevergabe:\n" +
            "    Wenn man richtig geschätzt hat, bekommt man 20 Punkte und zusätzlich pro Stich 10 Punkte. Wenn man falsch geschätzt\n" +
            "    hat, bekommt man pro Stich, welchen man daneben liegt, -10 Punkte und erhält sonst keine weiteren Punkte. Wenn man\n" +
            "    beispielsweise 4 Stiche geschätzt hat, aber 6 Stiche erhalten hat, bekommt man -20 Punkte.\n" +
            "    Die Punkte werden rechts in der Tabelle angezeigt, während die Gesamtpunktzahl in der untersten Zeile steht.\n" +
            "    Im kleinen Feld rechts neben den Punkten steht immer die Schätzung.\n" +
            "\n" +
            "Beispiel:\n" +
            "    Der Trumpf der Runde ist eine rote 4. Der Startspieler legt eine blaue 12. Anschließend legt der nächste Spieler\n" +
            "    eine blaue 2. Der dritte Spieler hat keine blauen Karten und darf deshalb eine beliebige Karte legen. Er entscheidet\n" +
            "    sich für die rote 1. Hier gewinnt der dritte Spieler den Stich, weil er als einziges die beste Farbe, die\n" +
            "    Trumpffarbe, gelegt hat. Hätte er statt der roten 1 eine gelbe 13 gelegt, würde der erste Spieler den Stich\n" +
            "    gewinnen, weil blau für diesen Stich die zweitbeste Farbe ist. Es gewinnt nicht der zweiten Spieler, weil der Wert\n" +
            "    der Karte des ersten Spielers höher ist.";
}
