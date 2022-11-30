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

public final class Changes {
	
	private static final Map<Object, Map<Property<?>, InvalidationListener>> listeners = new HashMap<>();
	
	public static void track(Session session, CustomScene scene) {
		track(session, scene, scene.nameProperty());
	}
	
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
			throw new IllegalStateException("Element type not supported!");
		}
	}
	
	private static void track(Session session, CustomCube cube) {
		track(session, cube, cube.widthProperty());
		track(session, cube, cube.heightProperty());
		track(session, cube, cube.lengthProperty());
	}
	
	private static void track(Session session, CustomSphere sphere) {
		track(session, sphere, sphere.radiusProperty());
	}
	
	private static <T> void track(Session session, Object object, Property<T> property) {
		if (!listeners.containsKey(object)) {
			listeners.put(object, new HashMap<>());
		}
		if (!listeners.get(object).containsKey(property)) {
			InvalidationListener listener = event -> {
				session.beginTransaction();
				session.merge(object);
				session.getTransaction().commit();
			};
			property.addListener(listener);
			listeners.get(object).put(property, listener);
		}
	}
	
	public static void untrack(CustomScene scene) {
		untrack(scene, scene.nameProperty());
	}
	
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
			throw new IllegalStateException("Element type not supported!");
		}
	}
	
	private static void untrack(CustomCube cube) {
		untrack(cube, cube.widthProperty());
		untrack(cube, cube.heightProperty());
		untrack(cube, cube.lengthProperty());
	}
	
	private static void untrack(CustomSphere sphere) {
		untrack(sphere, sphere.radiusProperty());
	}
	
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
