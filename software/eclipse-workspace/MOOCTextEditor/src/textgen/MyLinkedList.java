package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		head = new LLNode<E>();
		tail = new LLNode<E>();
		head.next = tail;
		tail.prev = head;
		this.size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
//		return false;
		
	    if (element == null) {
	        throw new NullPointerException("element Type cannot be null");
	    }
	    
	    LLNode<E> t1 = new LLNode<E>(element);
	    t1.next = tail;
	    t1.prev = tail.prev;
	    tail.prev.next = t1;
	    tail.prev = t1;
	    
	    this.size += 1;
	    
	    return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
//		return null;
		
		if(index >= 0 && index < this.size) {
			LLNode<E> res= head;
			for (int i = 0; i < index+1; i++) {
				res = res.next;
			}
			return res.data;
		}else {
			throw new IndexOutOfBoundsException("error in get(int index)");
		}

	}
	
	public LLNode<E> getNode(int index) 
	{		
		if(index >= 0 && index < this.size) {
			LLNode<E> res= head;
			for (int i = 0; i < index+1; i++) {
				res = res.next;
			}
			return res;
		}else {
			throw new IndexOutOfBoundsException("error in get(int index)");
		}
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
	    if (element == null) {
	        throw new NullPointerException("element Type cannot be null");
	    }
	    if (!(index >= 0 && index <= this.size)) {
	        throw new IndexOutOfBoundsException("error in add(int index, E element)");
	    }	    
	    
	    LLNode<E> newNode = new LLNode<E>(element);
	    if (index == this.size) {
	    	newNode.next = tail;
	    	newNode.prev = tail.prev;
		    tail.prev.next = newNode;
		    tail.prev = newNode;
		    this.size += 1;
	    } else {
		    LLNode<E> oldNode = getNode(index); 
		    newNode.next = oldNode;
		    newNode.prev = oldNode.prev;
		    oldNode.prev.next = newNode;
		    oldNode.prev = newNode;
		    this.size += 1;	    	
	    }
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
//		return null;
	    if (!(index >= 0 && index < this.size)) {
	        throw new IndexOutOfBoundsException("error in remove(int index)");
	    }	 
	    
	    LLNode<E> oldNode = getNode(index);
	    
//	    newNode.next = oldNode;
//	    newNode.prev = oldNode.prev;
	    oldNode.prev.next = oldNode.next;
	    oldNode.next.prev = oldNode.prev;
	    
	    this.size -= 1;	  
	    
	    return oldNode.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
//		return null;
		
	    if (element == null) {
	        throw new NullPointerException("element Type cannot be null");
	    }
	    
	    if (!(index >= 0 && index < this.size)) {
	        throw new IndexOutOfBoundsException("error in set(int index, E element)");
	    }	    
	    
	    LLNode<E> oldNode = getNode(index);
	    E res = oldNode.data;
	    oldNode.data = element;
	    
//	    newNode.next = oldNode;
//	    newNode.prev = oldNode.prev;
//	    oldNode.prev.next = oldNode.next;
//	    oldNode.next.prev = oldNode.prev;
	    
//	    this.size -= 1;	  
	    
	    return res;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	public LLNode() 
	{
		this(null);
//		this.data = null;
//		this.prev = null;
//		this.next = null;
	}

}
