package maven.demo.gui.utils;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 * Hilfsfunktionen für die Konfiguration des Layouts.
 * 
 * @author Georg Hackenberg <georg.hackenberg@fh-wels.at>
 */
public final class Layouts {

	/**
	 * Spaltenbedingung für ein Grid-Layout erstellen.
	 * 
	 * @param percentage Breite der Spalte in Prozent.
	 * 
	 * @return Das Bedingungs-Objekt.
	 */
	public static ColumnConstraints createColumnContraints(double percentage) {
		ColumnConstraints result = new ColumnConstraints();
		result.setPercentWidth(percentage);
		result.setHgrow(Priority.ALWAYS);
		result.setFillWidth(true);
		return result;
	}

	/**
	 * Zeilenbedingung für ein Grid-Layout erstellen.
	 * 
	 * @param percentage Höhe der Zeile in Prozent.
	 * 
	 * @return Das Bedingungs-Objekt.
	 */
	public static RowConstraints createRowContraints(double percentage) {
		RowConstraints result = new RowConstraints();
		result.setPercentHeight(percentage);
		result.setVgrow(Priority.ALWAYS);
		result.setFillHeight(true);
		return result;
	}
	
}
