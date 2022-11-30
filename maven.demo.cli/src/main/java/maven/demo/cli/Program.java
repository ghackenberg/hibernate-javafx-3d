package maven.demo.cli;

import org.hibernate.Session;

import maven.demo.core.Database;
import maven.demo.core.model.CustomScene;
import maven.demo.core.model.elements.CustomCube;
import maven.demo.core.model.elements.CustomSphere;

public class Program {
	
	public static void main(String[] args) {		
		// Szene erstellen
		CustomScene scene = new CustomScene();
		scene.setName("Erste Szene");
		
		// Würfel erstellen
		CustomCube cube = new CustomCube();
		cube.setName("Erster Würfel");
		
		cube.setRed(255);
		cube.setGreen(0);
		cube.setBlue(0);
		
		cube.setX(0);
		cube.setY(0);
		cube.setZ(0);
		
		cube.setAlpha(0);
		cube.setBeta(0);
		cube.setGamma(0);
		
		cube.setWidth(1);
		cube.setHeight(1);
		cube.setLength(1);
		
		// Kugel erstellen
		CustomSphere sphere = new CustomSphere();
		sphere.setName("Erste Kugel");
		
		sphere.setRed(255);
		sphere.setGreen(0);
		sphere.setBlue(0);
		
		sphere.setX(0);
		sphere.setY(0);
		sphere.setZ(0);
		
		sphere.setAlpha(0);
		sphere.setBeta(0);
		sphere.setGamma(0);
		
		sphere.setRadius(1);
		
		// Würfel und Szene verknüpfen
		scene.getElements().add(cube);
		cube.setScene(scene);
		
		// Kugel und Szene verknüpfen
		scene.getElements().add(sphere);
		sphere.setScene(scene);
		
		System.out.println("Booting Hibernate");
		Database.bootHibernate();
		
		System.out.println("Using Hibernate");
		Session session = Database.openHibernateSession();
		
		// Szene, Würfel und Kugel in der Datenbank speichern
		session.beginTransaction();
		session.persist(scene);
		session.persist(cube);
		session.persist(sphere);
		session.getTransaction().commit();
		
		// Bestehende Szenen auslesen
		for (CustomScene temp : session.createQuery("from CustomScene", CustomScene.class).getResultList()) {
			// ID, Name und Elemente der Szene ausgeben
			System.out.println(temp.getId() + " - " + temp.getName() + " : " + temp.getElements());
		}
		
		// Hibernate Session schließen
		session.close();

		// Hibernate stoppen
		System.out.println("Stopping Hibernate");
		Database.stopHibernate();
	}

}
