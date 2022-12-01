package maven.demo.gui.utils;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import maven.demo.core.model.CustomElement;
import maven.demo.core.model.CustomScene;
import maven.demo.core.model.elements.CustomCube;
import maven.demo.core.model.elements.CustomSphere;

/**
 * Hilfsfunktionen für das Überwachen von Änderungen an Datenobjekten.
 * 
 * @author Georg Hackenberg <georg.hackenberg@fh-wels.at>
 */
public final class Changes {
	
	/**
	 * Callback-Funktionen für die Speicherung von Änderungen in der Datenbank.
	 */
	private static final Map<Object, Map<Property<?>, InvalidationListener>> listeners = new HashMap<>();
	
	/**
	 * Änderungen an Szenenobjekt überwachen und speichern.
	 * 
	 * @param session Die Hibernate Session für die Speicherung von Änderungen.
	 * @param scene Das Szenenobjekt.
	 */
	public static void track(Session session, CustomScene scene) {
		track(session, scene, scene.nameProperty());
	}
	
	/**
	 * Änderungen an Szenenelementen überwachen und speichern.
	 * 
	 * @param session Die Hibernate Session für die Speicherung von Änderungen.
	 * @param element Das Szenenelement.
	 */
	public static void track(Session session, CustomElement element) {
		track(session, element, element.nameProperty());
		track(session, element, element.sceneProperty());
		track(session, element, element.xProperty());
		track(session, element, element.yProperty());
		track(session, element, element.zProperty());
		track(session, element, element.alphaProperty());
		track(session, element, element.betaProperty());
		track(session, element, element.gammaProperty());
		track(session, element, element.redProperty());
		track(session, element, element.greenProperty());
		track(session, element, element.blueProperty());
		if (element instanceof CustomCube) {
			track(session, (CustomCube) element);
		} else if (element instanceof CustomSphere) {
			track(session, (CustomSphere) element);
		} else {
			// TODO Unterstütze auch Zylinder
			throw new IllegalStateException("Element type not supported!");
		}
	}
	
	/**
	 * Änderungen an Szenenwürfeln überwachen und speichern.
	 * 
	 * @param session Die Hibernate Session für die Speicherung von Änderungen.
	 * @param element Der Szenenwürfel.
	 */
	private static void track(Session session, CustomCube cube) {
		track(session, cube, cube.widthProperty());
		track(session, cube, cube.heightProperty());
		track(session, cube, cube.lengthProperty());
	}
	
	/**
	 * Änderungen an Szenenkugeln überwachen und speichern.
	 * 
	 * @param session Die Hibernate Session für die Speicherung von Änderungen.
	 * @param element Die Szenenkugel.
	 */
	private static void track(Session session, CustomSphere sphere) {
		track(session, sphere, sphere.radiusProperty());
	}

	
	/**
	 * Änderungen an Objekteigenschaften überwachen und speichern.
	 * 
	 * @param <T> Der Typ der Objekteigenschaft.
	 * @param session Die Hibernate Session für die Speicherung von Änderungen.
	 * @param object Das Objekt, das gespeichert werden soll.
	 * @param property Die Eigenschaft, die auf Änderungen überwacht werden soll.
	 */
	private static <T> void track(Session session, Object object, Property<T> property) {
		// Prüfe, ob für das Objekt bereits Callback-Funktionen registriert wurden
		if (!listeners.containsKey(object)) {
			listeners.put(object, new HashMap<>());
		}
		// Prüfe, ob für die Eigenschaft des Objekts bereits eine Callback-Funktion registriert wurde
		if (!listeners.get(object).containsKey(property)) {
			// Definiere die Callback-Funktion
			InvalidationListener listener = event -> {
				session.beginTransaction();
				session.merge(object);
				session.getTransaction().commit();
			};
			// Registrierte die Callback-Funktion auf der Eigenschaft
			property.addListener(listener);
			// Speichere die Callback-Funktion im Cache
			listeners.get(object).put(property, listener);
		}
	}
	
	/**
	 * Änderungen an Szene nicht mehr überwachen.
	 * 
	 * @param scene das Szenenobjekt.
	 */
	public static void untrack(CustomScene scene) {
		untrack(scene, scene.nameProperty());
	}
	
	/**
	 * Änderungen an Szenenelement nicht mehr überwachen.
	 * 
	 * @param element Das Szenenelement.
	 */
	public static void untrack(CustomElement element) {
		untrack(element, element.nameProperty());
		untrack(element, element.sceneProperty());
		untrack(element, element.xProperty());
		untrack(element, element.yProperty());
		untrack(element, element.zProperty());
		untrack(element, element.alphaProperty());
		untrack(element, element.betaProperty());
		untrack(element, element.gammaProperty());
		untrack(element, element.redProperty());
		untrack(element, element.greenProperty());
		untrack(element, element.blueProperty());
		if (element instanceof CustomCube) {
			untrack((CustomCube) element);
		} else if (element instanceof CustomSphere) {
			untrack((CustomSphere) element);
		} else {
			// TODO Unterstütze auch Zylinder
			throw new IllegalStateException("Element type not supported!");
		}
	}
	
	/**
	 * Änderungen an Szenenwürfel nicht mehr überwachen.
	 * 
	 * @param cube Der Szenenwürfel.
	 */
	private static void untrack(CustomCube cube) {
		untrack(cube, cube.widthProperty());
		untrack(cube, cube.heightProperty());
		untrack(cube, cube.lengthProperty());
	}
	
	/**
	 * Änderungen an Szenenkugel nicht mehr überwachen.
	 * 
	 * @param sphere Die Szenenkugel.
	 */
	private static void untrack(CustomSphere sphere) {
		untrack(sphere, sphere.radiusProperty());
	}
	
	/**
	 * Änderungen an Objekteigenschaften nicht mehr überwachen.
	 * 
	 * @param <T> Der Typ der Objekteigenschaft, die nicht mehr überwacht werden soll.
	 * @param object Das Objekt, deren Eigenschaft nicht mehr überwacht werden soll.
	 * @param property Die Objekteigenschaft, die nicht mehr überwacht werden soll.
	 */
	private static <T> void untrack(Object object, Property<T> property) {
		if (listeners.containsKey(object)) {
			if (listeners.get(object).containsKey(property)) {
				property.removeListener(listeners.get(object).remove(property));
			}
			if (listeners.get(object).size() == 0) {
				listeners.remove(object);
			}
		}
	}

}
