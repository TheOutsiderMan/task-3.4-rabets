package by.tr.web.task_3_4.node;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class NodeListImpl<E> implements List<E> {
	int size = 0;
	Node<E> firstElement;
	Node<E> lastElement;

	public NodeListImpl() {
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		if (size() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean contains(Object o) {
		Iterator<E> itr = iterator();
		if (o == null) {
			while (itr.hasNext()) {
				if (itr.next() == null) {
					return true;
				}
			}
		} else {
			while (itr.hasNext()) {
				if (o.equals(itr.next())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		return new IteratorImpl();
	}

	@Override
	public Object[] toArray() {
		Object[] result = new Object[size];
		int i = 0;
		for (Node<E> node = firstElement; node != null; node = node.next) {
			result[i] = node.data;
			i++;
		}
		return result;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		if (a.length < size) {
			a = (T[]) new Object[size];
		}
		a = (T[]) Arrays.copyOf(this.toArray(), size);
		if (a.length > size) {
			a[size] = null;
		}
		return a;
	}

	@Override
	public boolean add(E e) {
		final Node<E> l = lastElement;
		final Node<E> newNode = new Node<>(e, l, null);
		lastElement = newNode;
		if (l == null) {
			firstElement = newNode;
		} else {
			l.next = newNode;
		}
		size++;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		if (o == null) {
			for (Node<E> elem = firstElement; elem != null; elem = elem.next) {
				if (elem.data == null) {
					deleteLink(elem);
					return true;
				}
			}
		} else {
			for (Node<E> elem = firstElement; elem != null; elem = elem.next) {
				if (o.equals(elem.data)) {
					deleteLink(elem);
					return true;
				}
			}
		}
		return false;
	}

	E deleteLink(Node<E> element) {
		final E data = element.data;
		final Node<E> next = element.next;
		final Node<E> previous = element.prev;

		if (previous == null) {
			firstElement = next;
		} else {
			previous.next = next;
			element.prev = null;
		}

		if (next == null) {
			lastElement = previous;
		} else {
			next.prev = previous;
			element.next = null;
		}

		element.data = null;
		size--;
		return data;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object e : c)
			if (!contains(e)) {
				return false;
			}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return addAll(size, c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		checkBounds(index);

		Object[] temp = c.toArray();
		int tempLength = temp.length;
		if (tempLength == 0)
			return false;

		Node<E> preceding, following;
		if (index == size) {
			following = null;
			preceding = lastElement;
		} else {
			following = getNode(index);
			preceding = following.prev;
		}

		for (Object o : temp) {
			E elem = (E) o;
			Node<E> newNode = new Node<>(elem, preceding, null);
			if (preceding == null)
				firstElement = newNode;
			else
				preceding.next = newNode;
			preceding = newNode;
		}

		if (following == null) {
			lastElement = preceding;
		} else {
			preceding.next = following;
			following.prev = preceding;
		}

		size += tempLength;
		return true;
	}

	Node<E> getNode(int index) {
		if (index < size / 2) {
			Node<E> elem = firstElement;
			for (int i = 0; i < index; i++) {
				elem = elem.next;
			}
			return elem;
		} else {
			Node<E> elem = lastElement;
			for (int i = size - 1; i > index; i--) {
				elem = elem.prev;
			}
			return elem;
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if (c != null) {
			boolean modified = false;
			Iterator<?> iterator = iterator();
			while (iterator.hasNext()) {
				if (c.contains(iterator.next())) {
					iterator.remove();
					modified = true;
				}
			}
			return modified;
		}
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		if (c != null) {
			boolean modified = false;
			Iterator<E> iteraror = iterator();
			while (iteraror.hasNext()) {
				if (!c.contains(iteraror.next())) {
					iteraror.remove();
					modified = true;
				}
			}
			return modified;
		}
		return false;
	}

	@Override
	public void clear() {
		for (Node<E> elem = firstElement; elem != null;) {
			Node<E> next = elem.next;
			elem.data = null;
			elem.next = null;
			elem.prev = null;
			elem = next;
		}
		firstElement = null;
		lastElement = null;
		size = 0;
	}

	@Override
	public void add(int index, E element) {
		checkBounds(index);
		if (index == size) {
			final Node<E> l = lastElement;
			final Node<E> newNode = new Node<>(element, l, null);
			lastElement = newNode;
			if (l == null)
				firstElement = newNode;
			else
				l.next = newNode;
			size++;
		} else {
			final Node<E> following = getNode(index);
			final Node<E> preceding = following.prev;
			final Node<E> newNode = new Node<>(element, preceding, following);
			following.prev = newNode;
			if (preceding == null)
				firstElement = newNode;
			else
				preceding.next = newNode;
			size++;
		}
	}

	@Override
	public int indexOf(Object o) {
		int index = 0;
		if (o == null) {
			for (Node<E> node = firstElement; node != null; node = node.next) {
				if (node.data == null)
					return index;
				index++;
			}
		} else {
			for (Node<E> node = firstElement; node != null; node = node.next) {
				if (o.equals(node.data))
					return index;
				index++;
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		int index = size;
		if (o == null) {
			for (Node<E> node = lastElement; node != null; node = node.prev) {
				index--;
				if (node.data == null)
					return index;
			}
		} else {
			for (Node<E> node = lastElement; node != null; node = node.prev) {
				index--;
				if (o.equals(node.data))
					return index;
			}
		}
		return -1;
	}

	@Override
	public ListIterator<E> listIterator() {
		return new ListItrImpl();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new ListItrImpl(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex < 0) {
			throw new IndexOutOfBoundsException();
		}
		if (fromIndex > toIndex) {
			throw new IllegalArgumentException("fromIndex must be < toIndex");
		}
		if (toIndex > size) {
			throw new IndexOutOfBoundsException();
		}
		NodeListImpl<E> subList = new NodeListImpl<>();
		Node<E> firstNode = getNode(fromIndex);
		Node<E> lastNode = getNode(toIndex - 1);
		subList.firstElement = firstNode;
		subList.lastElement = lastNode;
		subList.size = toIndex - fromIndex;
		return subList;
	}

	@Override
	public E get(int index) {
		checkBounds(index);
		return getNode(index).data;
	}

	private void checkBounds(int index) {
		if (!(index >= 0 && index <= size)) {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public E set(int index, E element) {
		Node<E> node = getNode(index);
		E previous = node.data;
		node.data = element;
		return previous;
	}

	@Override
	public E remove(int index) {
		Node<E> node = getNode(index);
		Node<E> preciding = node.prev;
		Node<E> following = node.next;
		preciding.next = node.next;
		following.prev = node.prev;
		E element = node.data;
		node.data = null;
		return element;
	}

	private static class Node<E> {
		E data;
		Node<E> prev;
		Node<E> next;

		Node(E element, Node<E> prev, Node<E> next) {
			this.data = element;
			this.prev = prev;
			this.next = next;
		}

	}

	private class IteratorImpl implements Iterator<E> {
		Node<E> lastReturned;
		Node<E> next;
		int nextIndex;

		IteratorImpl() {
		}

		@Override
		public boolean hasNext() {
			if (nextIndex < size) {
				return true;
			}
			return false;
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			lastReturned = next;
			next = next.next;
			nextIndex++;
			return lastReturned.data;
		}

	}

	private class ListItrImpl extends IteratorImpl implements ListIterator<E> {

		ListItrImpl() {
		}

		ListItrImpl(int index) {
			if (index == size) {
				next = null;
			} else {
				next = getNode(index);
			}
			nextIndex = index;
		}

		@Override
		public boolean hasPrevious() {
			if (nextIndex > 0) {
				return true;
			}
			return false;
		}

		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}

			lastReturned = next;
			if (next == null) {
				next = lastElement;
			} else {
				next = next.prev;
			}
			nextIndex--;
			return lastReturned.data;
		}

		@Override
		public int nextIndex() {
			return nextIndex;
		}

		@Override
		public int previousIndex() {
			return nextIndex - 1;
		}

		@Override
		public void remove() {
			if (lastReturned == null) {
                throw new IllegalStateException();
			}
            Node<E> lastNext = lastReturned.next;
            deleteLink(lastReturned);
            if (next == lastReturned) {
                next = lastNext;
            } else {
                nextIndex--;
            }
            lastReturned = null;
		}

		@Override
		public void set(E e) {
			if (lastReturned == null) {
                throw new IllegalStateException();
			}
            lastReturned.data = e;
		}

		@Override
		public void add(E e) {
			lastReturned = null;
            if (next == null) {
            	final Node<E> l = lastElement;
                final Node<E> newNode = new Node<>(e, l, null);
                lastElement = newNode;
                if (l == null) {
                    firstElement = newNode;
                } else {
                    l.next = newNode;
                }
                size++;
            } else {
            	final Node<E> preciding = next.prev;
                final Node<E> newNode = new Node<>(e, preciding, next);
                next.prev = newNode;
                if (preciding == null)
                    firstElement = newNode;
                else
                    preciding.next = newNode;
                size++;
            }
            nextIndex++;
		}

	}
}
