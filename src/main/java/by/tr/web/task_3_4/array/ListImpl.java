package by.tr.web.task_3_4.array;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ListImpl<E> implements List<E> {
	private static final Object[] EMPTY_ARRAY = new Object[0];

	private E[] dataArray;

	public ListImpl() {
		dataArray = (E[]) EMPTY_ARRAY;
	}
	
	public ListImpl(int capacity) {
		if (capacity > 0) {
			dataArray = (E[]) new Object[capacity];
		} else if (capacity == 0) {
			dataArray = (E[]) EMPTY_ARRAY;
		} else if (capacity < 0) {
			throw new IllegalArgumentException("capacity can't be < 0");
		}
	}
	@Override
	public int size() {
		int size = dataArray.length;
		return size;
	}

	@Override
	public boolean isEmpty() {
		if (this.size() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean contains(Object o) {
		for (E e : dataArray) {
			if (e.equals(o)) {
				return true;
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
		Object[] resultArray = dataArray;
		return resultArray;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		if (a.length < size()) {
			T[] resultArray = (T[]) Arrays.copyOf(dataArray, dataArray.length, a.getClass());
			return resultArray;
		}
		System.arraycopy(dataArray, 0, a, 0, size());
		if(a.length > size()) {
			a[size()] = null;
		}
		return a;
	}

	@Override
	public boolean add(E e) {
		dataArray = Arrays.copyOf(dataArray, dataArray.length + 1);
		int last = dataArray.length - 1;
		dataArray[last] = e;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		if (o == null) {
			for (int i = 0; i < dataArray.length; i++) {
				if (dataArray == null) {
					int newLength = dataArray.length - 1;
					E[] tempArray = Arrays.copyOf(dataArray, newLength);
					if (i != newLength) {
						System.arraycopy(dataArray, i + 1, tempArray, i, newLength - i);
					}
					dataArray = tempArray;
					return true;
				}
			}
		} else {
			for (int i = 0; i < dataArray.length; i++) {
				if (o.equals(dataArray[i])) {
					int newLength = dataArray.length - 1;
					E[] tempArray = Arrays.copyOf(dataArray, newLength);
					if (i != newLength) {
						System.arraycopy(dataArray, i + 1, tempArray, i, newLength - i);
					}
					dataArray = tempArray;
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		Object[] cArray = c.toArray();
		int oldLength = dataArray.length;
		int newLength = cArray.length + oldLength;
		dataArray = Arrays.copyOf(dataArray, newLength);
		System.arraycopy(cArray, 0, dataArray, oldLength, cArray.length);
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		Object[] cArray = c.toArray();
		int oldLength = dataArray.length;
		int newLength = cArray.length + oldLength;
		E[] tempArray = Arrays.copyOf(dataArray, newLength);
		System.arraycopy(cArray, 0, tempArray, index, cArray.length);
		System.arraycopy(dataArray, index, cArray, index + cArray.length, oldLength - index);
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for (Object object : c) {
			if (contains(object)) {
				remove(object);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		for (Object object : c) {
			if (!contains(object)) {
				remove(object);
				return true;
			}
		}
		return false;
	}

	@Override
	public void clear() {
		dataArray = (E[]) EMPTY_ARRAY;
	}

	@Override
	public E get(int index) {
		if (index >= dataArray.length) {
			throw new IndexOutOfBoundsException();
		}
		return dataArray[index];
	}

	@Override
	public E set(int index, E element) {
		if (index >= dataArray.length) {
			throw new IndexOutOfBoundsException();
		}
		E previous = dataArray[index];
		dataArray[index] = element;
		return previous;
	}

	@Override
	public void add(int index, E element) {
		int oldLength = dataArray.length;
		int newLength = oldLength + 1;
		E[] tempArray = Arrays.copyOf(dataArray, newLength);
		tempArray[index] = element;
		System.arraycopy(dataArray, index, tempArray, index + 1, oldLength - index);
	}

	@Override
	public E remove(int index) {
		E previous = dataArray[index];
		int oldLength = dataArray.length;
		int newLength = oldLength - 1;
		E[] tempArray = Arrays.copyOf(dataArray, newLength);
		System.arraycopy(dataArray, index + 1, tempArray, index, oldLength - index - 1);
		return previous;
	}

	@Override
	public int indexOf(Object o) {
		if (o == null) {
			for (int i = 0; i < dataArray.length; i++) {
				if (dataArray[i] == null) {
					return i;
				}
			}
		} else {
			for (int i = 0; i < dataArray.length; i++) {
				if (o.equals(dataArray[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		int lastIndex = -1;
		if (o == null) {
			for (int i = 0; i < dataArray.length; i++) {
				if (dataArray[i] == null) {
					lastIndex = i;
				}
			}
		} else {
			for (int i = 0; i < dataArray.length; i++) {
				if (o.equals(dataArray[i])) {
					lastIndex = i;
				}
			}
		}
		return lastIndex;
	}

	@Override
	public ListIterator<E> listIterator() {
		return new ListIteratorImpl();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new ListIteratorImpl(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex < 0) {
			throw new IndexOutOfBoundsException();
		}
		if (fromIndex > toIndex) {
			throw new IllegalArgumentException("fromIndex must be < toIndex");
		}
		if (toIndex > dataArray.length) {
			throw new IndexOutOfBoundsException();
		}
		int length = toIndex - fromIndex;
		ListImpl<E> subList = new ListImpl<>(length);
		for (int i = fromIndex, j = 0; i < toIndex || j < subList.dataArray.length; i++, j++) {
			subList.dataArray[j] = dataArray[i];
		}
		return subList;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object object : c) {
			if (!contains(object)) {
				return false;
			}
		}
		return true;
	}
	
	private class IteratorImpl implements Iterator<E>{
		
		int cursor;
		int lastReturned = -1;
		
		IteratorImpl() {}

		@Override
		public boolean hasNext() {
			if (cursor != dataArray.length) {
				return true;
			}
			return false;
		}

		@Override
		public E next() {
			int index = cursor;
			if (index >= dataArray.length) {
				throw new NoSuchElementException();
			}
			Object[] dataItr = ListImpl.this.dataArray;
			if (index >= dataItr.length) {
				throw new NoSuchElementException();
			}
			cursor = index + 1;
			lastReturned = index;
			return (E) dataItr[index];
		}
		
	}
	
	private class ListIteratorImpl extends IteratorImpl implements ListIterator<E> {
		
		ListIteratorImpl() {}
		
		ListIteratorImpl(int index) {
			cursor = index;
		}
		
		@Override
		public boolean hasPrevious() {
			return cursor != 0;
		}

		@Override
		public E previous() {
			int index = cursor - 1;
			if (index < 0) {
				throw new NoSuchElementException();
			}
			Object[] dataItr = ListImpl.this.dataArray;
			if (index >= dataItr.length) {
				throw new NoSuchElementException();
			}
			cursor = index;
			lastReturned = index;
			return (E) dataItr[index];
		}

		@Override
		public int nextIndex() {
			return cursor;
		}

		@Override
		public int previousIndex() {
			return cursor - 1;
		}

		@Override
		public void remove() {
			if (lastReturned < 0) {
				throw new IllegalStateException();
			}
			ListImpl.this.remove(lastReturned);
			cursor = lastReturned;
			lastReturned = -1;
		}

		@Override
		public void set(E e) {
			if (lastReturned < 0) {
				throw new IllegalStateException();
			}
			ListImpl.this.set(lastReturned, e);
		}

		@Override
		public void add(E e) {
			int index = cursor;
			ListImpl.this.add(index, e);
			cursor = index + 1;
			lastReturned = -1;
		}
		
	}
}
