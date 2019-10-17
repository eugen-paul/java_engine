package org.eugenpaul.javaengine.core.data;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * "add" function add sorted element, other add-Function should not be used.
 * 
 * @author Eugen Paul
 *
 * @param <E>
 */
public class SimpleSortedList<E extends Comparable<E>> extends LinkedList<E> {

  private static final long serialVersionUID = 3675022487203959009L;

  @Override
  public boolean add(E e) {
    return sortedInsert(e);
  }

  @Override
  public void add(int index, E element) {
    sortedInsert(element);
  }

  @Override
  public void addFirst(E e) {
    sortedInsert(e);
  }

  @Override
  public void addLast(E e) {
    sortedInsert(e);
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    for (E elem : c) {
      sortedInsert(elem);
    }
    return true;
  }

  @Override
  public boolean addAll(int index, Collection<? extends E> c) {
    int i = 0;
    for (E elem : c) {
      if (i >= index) {
        sortedInsert(elem);
      }
      i++;
    }
    return true;
  }

  @Override
  public E set(int index, E element) {
    sortedInsert(element);
    return null;
  }

  private boolean sortedInsert(E element) {
    boolean added = false;
    ListIterator<E> iterator = this.listIterator();

    while (iterator.hasNext()) {
      E testElement = iterator.next();
      if (element.compareTo(testElement) < 0) {
        if (iterator.hasPrevious()) {
          iterator.previous();
        }
        iterator.add(element);
        return true;
      }
    }

    if (!added) {
      super.addLast(element);
    }
    return true;
  }

}
