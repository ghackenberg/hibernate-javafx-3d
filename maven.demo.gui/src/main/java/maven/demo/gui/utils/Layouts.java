package maven.demo.gui.utils;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public final class Layouts {

	public static ColumnConstraints createColumnContraints(double percentage) {
		ColumnConstraints result = new ColumnConstraints();
		result.setPercentWidth(percentage);
		result.setHgrow(Priority.ALWAYS);
		result.setFillWidth(true);
		return result;
	}

	public static RowConstraints createRowContraints(double percentage) {
		RowConstraints result = new RowConstraints();
		result.setPercentHeight(percentage);
		result.setVgrow(Priority.ALWAYS);
		result.setFillHeight(true);
		return result;
	}
	
}
