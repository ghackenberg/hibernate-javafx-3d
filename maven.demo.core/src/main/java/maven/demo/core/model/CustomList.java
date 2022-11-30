package maven.demo.core.model;

import java.util.List;

import javafx.collections.ModifiableObservableListBase;

/**
 * Diese Klasse macht aus einer einfachen Liste eine überwachbare Liste.
 * 
 * @author Georg Hackenberg <georg.hackenberg@fh-wels.at>
 *
 * @param <T> Typ der Listenelemente.
 */
public final class CustomList<T> extends ModifiableObservableListBase<T> {
	
	/**
	 * Die einfache Liste.
	 */
	private List<T> list;
	
	/**
	 * Erstellt eine überwachbare Liste aus einer einfachen Liste.
	 * 
	 * @param list Die einfache Liste.
	 */
	public CustomList(List<T> list) {
		this.list = list;
	}
	
	/**
	 * Gibt ein Listenelement an einer bestimmten Listenposition zurück.
	 * 
	 * @param index Die Listenposition.
	 * 
	 * @return Das Element an der gegebenen Position.
	 */
	@Override
	public T get(int index) {
		return list.get(index);
	}

	/**
	 * Gibt die Länge der Liste zurück.
	 * 
	 * @return Die Länge der Liste.
	 */
	@Override
	public int size() {
		return list.size();
	}
	
	/**
	 * Fügt ein neues Element in die Liste an einer bestimmten Position ein.
	 */
	@Override
	protected void doAdd(int index, T element) {
		list.add(index, element);
	}

	/**
	 * Überschreibt ein Element in der Liste an einer bestimmten Position.
	 * 
	 * @param index Die Listenposition.
	 * @param element Das neue Listenelement.
	 * 
	 * @return Das alte Listenelement an der Position.
	 */
	@Override
	protected T doSet(int index, T element) {
		return list.set(index, element);
	}

	/**
	 * Löscht ein Element aus der Liste an einer bestimmten Position.
	 * 
	 * @param index Die Listenposition.
	 * 
	 * @return Das gelöschte Element.
	 */
	@Override
	protected T doRemove(int index) {
		return list.remove(index);
	}

}
