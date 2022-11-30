package maven.demo.core;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import maven.demo.core.model.CustomElement;
import maven.demo.core.model.CustomScene;
import maven.demo.core.model.elements.CustomCube;
import maven.demo.core.model.elements.CustomCylinder;
import maven.demo.core.model.elements.CustomSphere;

/**
 * Grundfunktionen für die Anbindung der SQL-Datenbank.
 * 
 * @author Georg Hackenberg <georg.hackenberg@fh-wels.at>
 */
public final class Database {
	
	private static StandardServiceRegistryBuilder builder;
	
	private static StandardServiceRegistry registry;
	
	private static MetadataSources sources;
	
	private static Metadata metadata;
	
	private static SessionFactory factory;
	
	/**
	 * Initialisiere Hibernate.
	 */
	public static void bootHibernate() {
		builder = new StandardServiceRegistryBuilder();
		builder.configure();
		
		registry = builder.build();
		
		sources = new MetadataSources(registry);
		sources.addAnnotatedClass(CustomScene.class);
		sources.addAnnotatedClass(CustomElement.class);
		sources.addAnnotatedClass(CustomCube.class);
		sources.addAnnotatedClass(CustomCylinder.class);
		sources.addAnnotatedClass(CustomSphere.class);
		
		metadata = sources.buildMetadata();
		
		factory = metadata.buildSessionFactory();
	}
	
	/** 
	 * Stelle eine Verbindung mit der Datenbank her.
	 * 
	 * @return Die Datenbank-Verbindung.
	 */
	public static Session openHibernateSession() {
		if (factory != null) {
			return factory.openSession();
		} else {
			throw new IllegalStateException("Factory not defined!");
		}
	}
	
	/**
	 * Stoppe Hibernate.
	 */
	public static void stopHibernate() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
			
			factory = null;
			
			metadata = null;
			
			sources = null;
			
			registry = null;
			
			builder = null;
		} else {
			throw new IllegalStateException("Registry not defined!");
		}
	}
	
}
