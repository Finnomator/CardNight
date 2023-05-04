package cardnight.games.ueno;

import cardnight.ScreenController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UenoView {

    public Button nachziehstapelButton;
    public Button ablageStapelButton;
    public HBox gegner3kartenBox;
    public HBox gegner2kartenBox;
    public HBox gegner1kartenBox;
    public HBox handkartenBox;

    private Ueno ueno;
    private HashMap<UenoSpieler, HBox> spielerKartenHboxs;
    private HashMap<UenoSpieler, ArrayList<Node>> spielerHandkartenNodes;
    private boolean spielerDarfKarteZiehen = true;


    public void initialize() {

        int gegner = 1;

        spielerHandkartenNodes = new HashMap<>(gegner + 1);
        spielerKartenHboxs = new HashMap<>(gegner + 1);

        ueno = new Ueno(gegner + 1, 7);

        for (UenoSpieler spieler : ueno.spieler)
            spielerHandkartenNodes.put(spieler, new ArrayList<>());

        spielerKartenHboxs.put(ueno.spieler[0], handkartenBox);
        spielerKartenHboxs.put(ueno.spieler[1], gegner1kartenBox);

        if (gegner > 1) {
            spielerKartenHboxs.put(ueno.spieler[2], gegner2kartenBox);
            if (gegner > 2)
                spielerKartenHboxs.put(ueno.spieler[3], gegner3kartenBox);
        }

        zeigeObersteAbgelegteKarte();
        updateHandkarten();
        updateGegnerKarten();
    }

    private void addHandkarte(UenoSpieler spieler, UenoKarte karte) {
        Node karteNode;

        if (spieler == ueno.spieler[0]) {
            karteNode = karteAlsNode(karte);
        } else {
            karteNode = gegnerKarte();
        }

        spielerHandkartenNodes.get(spieler).add(karteNode);
        spielerKartenHboxs.get(spieler).getChildren().add(karteNode);
    }

    private void clearHandkarten(UenoSpieler spieler) {
        spielerHandkartenNodes.get(spieler).clear();
        spielerKartenHboxs.get(spieler).getChildren().clear();
    }

    private Rectangle gegnerKarte() {
        Rectangle rect = new Rectangle();
        rect.setWidth(30);
        rect.setHeight(40);
        rect.setStroke(Color.BLACK);
        rect.setFill(Color.RED);
        return rect;
    }

    private Node karteAlsNode(UenoKarte karte) {

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

        if ((karte.art == UenoKartenArt.PLUS_VIER ||karte.art == UenoKartenArt.FARBWAHL) && karte.farbe == null) {
            GridPane pane = new GridPane();
            pane.setPrefSize(60, 80);
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(50);
            pane.getColumnConstraints().add(cc);
            pane.getColumnConstraints().add(cc);
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(50);
            pane.getRowConstraints().add(rc);
            pane.getRowConstraints().add(rc);

            for (UenoFarbe f : UenoFarbe.values()) {
                Button btn = new Button();
                btn.setStyle("-fx-background-color: "+farbeZuString(f)+";");
                btn.setPrefSize(30, 40);

                EventHandler<MouseEvent> handler = mouseEvent -> {
                    karte.setzeFarbe(f);
                    ueno.spieler[0].ersetzeKarte(karteVonNode(ueno.spieler[0], pane), karte);
                    updateHandkarten();
                    mouseEvent.consume();
                };

                btn.addEventFilter(MouseEvent.MOUSE_CLICKED, handler);
                btn.setOnMouseClicked(handler);

                if (f == UenoFarbe.GELB)
                    pane.add(btn, 0, 0);
                else if (f == UenoFarbe.GRUEN)
                    pane.add(btn, 1, 0);
                else if (f == UenoFarbe.BLAU)
                    pane.add(btn, 0, 1);
                else if (f == UenoFarbe.ROT)
                    pane.add(btn, 1, 1);
            }

            Text textArea = new Text(txt);
            pane.add(textArea, 0, 0, 2, 2);

            return pane;
        } else {
            Button btn = new Button(txt);
            btn.setPrefSize(60, 80);
            btn.setStyle("-fx-background-color: "+farbeZuString(karte.farbe)+";");

            EventHandler<MouseEvent> handler = mouseEvent -> {
                UenoKarte k = karteVonNode(ueno.spieler[0], btn);
                legeKarte(ueno.spieler[0], k);

                for (int i = 1; i < ueno.spieler.length; ++i) {
                    gegnerZug(ueno.spieler[i]);
                }

                spielerDarfKarteZiehen = true;

                mouseEvent.consume();
            };

            btn.addEventFilter(MouseEvent.MOUSE_CLICKED, handler);
            btn.setOnMouseClicked(handler);

            return btn;
        }
    }

    private UenoKarte karteVonNode(UenoSpieler spieler, Node node) {
        return spieler.handkarten.get(spielerHandkartenNodes.get(spieler).indexOf(node));
    }

    private String farbeZuString(UenoFarbe farbe) {
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

    public void nachziehstapelClick() {
        zieheKarte(ueno.spieler[0]);
        spielerDarfKarteZiehen = false;
    }

    private UenoKarte zieheKarte(UenoSpieler spieler) {
        ueno.spielerZug(new UenoZug(spieler, ueno.obersteNachziehkarte(), ZugStart.NACHZIEHSTAPEL, ZugZiel.HAND));
        UenoKarte karte = spieler.handkarten.get(spieler.handkarten.size() - 1);
        System.out.println(spieler.name + " zog " +karte.art + " " + karte.farbe + " " + karte.wert);
        updateGegnerKarten();
        updateHandkarten();
        return karte;
    }


    private void legeKarte(UenoSpieler spieler, UenoKarte karte) {
        ueno.spielerZug(new UenoZug(spieler, karte, ZugStart.HAND, ZugZiel.ABLAGESTAPEL));
        System.out.println(spieler.name + " legte " +karte.art + " " + karte.farbe + " " + karte.wert);
        zeigeObersteAbgelegteKarte();
        updateHandkarten();
        updateGegnerKarten();
    }

    private void zeigeObersteAbgelegteKarte() {
        Button newBtn = (Button) karteAlsNode(ueno.zuletztAbgelegteKarte());
        ablageStapelButton.setStyle(newBtn.getStyle());
        ablageStapelButton.setText(newBtn.getText());
    }

    private void gegnerZug(UenoSpieler spieler) {
        for (UenoKarte karte : spieler.handkarten) {
            if (ueno.istKarteAblegbar(karte)) {

                if (karte.art == UenoKartenArt.FARBWAHL || karte.art == UenoKartenArt.PLUS_VIER) {
                    karte.setzeFarbe(ueno.obersteNachziehkarte().farbe);
                }

                legeKarte(spieler, karte);
                return;
            }
        }

        UenoKarte neueKarte = zieheKarte(spieler);
        if (ueno.istKarteAblegbar(neueKarte)) {
            legeKarte(spieler, neueKarte);
        }
    }

    private void updateGegnerKarten() {
        for (int i = 1; i < ueno.spieler.length; ++i) {
            UenoSpieler spieler = ueno.spieler[i];
            clearHandkarten(spieler);
            for (int j = 0; j < spieler.handkarten.size(); ++j) {
                spielerKartenHboxs.get(ueno.spieler[i]).getChildren().add(gegnerKarte());
            }
        }
    }

    private void updateHandkarten() {

        UenoSpieler spieler = ueno.spieler[0];

        clearHandkarten(spieler);

        int aktiveKarten = 0;

        for (int i = 0; i < spieler.handkarten.size(); ++i) {
            addHandkarte(spieler, spieler.handkarten.get(i));

            Node node = spielerHandkartenNodes.get(spieler).get(i);
            boolean istAblegbar = ueno.istKarteAblegbar(ueno.spieler[0].handkarten.get(i));
            node.setDisable(!istAblegbar);
            if (istAblegbar)
                aktiveKarten++;
        }

        nachziehstapelButton.setDisable(aktiveKarten != 0 || !spielerDarfKarteZiehen);
    }

    public void zurueckClick() {
        ScreenController.activate("main-menu-view");
    }

    public static Pane loadScene() throws IOException {
        return new FXMLLoader(UenoView.class.getResource("/cardnight/game-views/ueno-view.fxml")).load();
    }
}
