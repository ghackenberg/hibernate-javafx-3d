package maven.demo.gui;

import org.hibernate.Session;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import maven.demo.core.Database;
import maven.demo.gui.parts.CenterPart;
import maven.demo.gui.parts.LeftPart;
import maven.demo.gui.parts.RightPart;
import maven.demo.gui.utils.Layouts;

public final class Program extends Application {

	public static void main(String[] args) {
		// Applikation starten
		launch(args);
	}
	
	/**
	 * Die geöffnete Hibernate Session.
	 */
	private Session session;
	
	/**
	 * Die linke Ansicht.
	 */
	private final LeftPart left = new LeftPart();
	/**
	 * Die mittlere Ansicht.
	 */
	private final CenterPart center = new CenterPart();
	/**
	 * Die rechte Ansicht.
	 */
	private final RightPart right = new RightPart();
	/**
	 * Die Hauptansicht bestehend aus oberer, linker, mittlerer und rechter Ansicht.
	 */
	private final GridPane main = new GridPane();
	/**
	 * Die Szene.
	 */
	private final Scene scene = new Scene(main);

	/**
	 * Füllt die primäre Anzeigefläche mit Inhalt und zeigt die Fläche an.
	 * 
	 * @param primaryStage Die primäre Anzeigefläche.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Initialisiere Hibernate
		System.out.println("Booting Hibernate");
		Database.bootHibernate();
		
		// Öffne eine Hibernate Session
		System.out.println("Opening Hibernate session");
		session = Database.openHibernateSession();
		
		// Stelle den GUI-Teilen das Session-Objekt zur Verfügung
		left.setSession(session);
		center.setSession(session);
		right.setSession(session);

		// Leite die Selektion an die mittlere Ansicht weiter
		center.selectedObjectProperty().bind(left.selectedObjectProperty());
		center.selectedSceneProperty().bind(left.selectedSceneProperty());
		center.selectedElementProperty().bind(left.selectedElementProperty());

		// Leite die Selektion an die recht Ansicht weiter
		right.selectedObjectProperty().bind(left.selectedObjectProperty());
		right.selectedSceneProperty().bind(left.selectedSceneProperty());
		right.selectedElementProperty().bind(left.selectedElementProperty());
		
		// Definiere die Breite der Spalten
		main.getColumnConstraints().add(Layouts.createColumnContraints(20));
		main.getColumnConstraints().add(Layouts.createColumnContraints(50));
		main.getColumnConstraints().add(Layouts.createColumnContraints(30));
		// Definiere die Höhe der Zeilen
		main.getRowConstraints().add(Layouts.createRowContraints(100));
		// Füge die Ansichten in die Zellen hinzu
		main.add(left, 0, 0, 1, 1);
		main.add(center, 1, 0, 1, 1);
		main.add(right, 2, 0, 1, 1);
		
		primaryStage.setOnCloseRequest(event -> {
			// Setze die Session in den GUI-Teilen zurück
			left.setSession(null);
			center.setSession(null);
			right.setSession(null);
			
			// Prüfe ob Hibernate Session existiert
			if (session != null) {
				// Schließe die Hibernate Session
				System.out.println("Closing Hibernate session");
				session.close();	
			}
			
			// Stoppe Hibernate
			System.out.println("Stopping Hibernate");
			Database.stopHibernate();
		});
		primaryStage.setScene(scene);
		primaryStage.setTitle("JAV1IL");
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

}
