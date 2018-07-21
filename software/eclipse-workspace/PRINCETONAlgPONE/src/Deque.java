import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int item_num;

    // construct an empty deque
    public Deque(){
        first = null;
        last = null;
        item_num = 0;
    }

    private class Node
    {
        Item item;
        Node next;
        Node prev;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return item_num == 0;
    }

    // return the number of items on the deque
    public int size(){
        return item_num;
    }

    // add the item to the front
    public void addFirst(Item item){
       if (item == null) {
           throw new IllegalArgumentException();
       }

       Node oldfirst = first;
       first = new Node();
       first.item = item;
       first.next = oldfirst;
       if(isEmpty()) {
	    	last = first;
	    	last.prev = null;
	    	last.next = null;
        }
        else {
        	oldfirst.prev = first;
        }
        
        item_num += 1;
    }

    // add the item to the end
    public void addLast(Item item){
       if (item == null) {
           throw new IllegalArgumentException();
       }

       Node oldlast = last;
       last = new Node();
       last.item = item;
       last.prev = oldlast;
       if(isEmpty()) {
        	first = last;
        	first.prev = null;
        	first.next = null;
        }
        else {
        	oldlast.next = last;
        }
        
        item_num += 1;
    }

    // remove and return the item from the front
    public Item removeFirst(){
       if (item_num == 0) {
           throw new NoSuchElementException();
       }

        Item item = first.item;
        if (item_num == 1) {
        	first.next = null;
        	last = first;
        }
        else {
            first.next.prev = null;
            first = first.next;	
        }

        item_num -= 1;
        return item;
    }

    // remove and return the item from the end
    public Item removeLast(){
       if (item_num == 0) {
           throw new NoSuchElementException();
       }

        Item item = last.item;
        if (item_num == 1) {
        	last.prev = null;
        	first = last;
        }
        else {
            last.prev.next = null;
            last = last.prev;
        }

        item_num -= 1;
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator(){
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next(){
            if (current == null) {
               throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
//	private String toStringTest()
//	{
//		String toReturn = "";
//		return toReturn;
//	}

    // unit testing (optional)
    public static void main(String[] args){
//        System.out.println("start");
        Deque<String> deq = new Deque<String>();
        deq.addFirst("a");
        deq.addFirst("b");
        deq.addFirst("c");
        deq.addLast("d");
        
        for(String str : deq) {
        	System.out.println(str);
        }
        
        System.out.println(deq.size());
        System.out.println(deq.removeFirst());
        System.out.println(deq.removeLast());
        System.out.println(deq.removeLast());
        System.out.println(deq.removeFirst());
//        System.out.println(deq.size());
//        System.out.println(deq.removeFirst());
//        System.out.println(deq.size());
//        System.out.println("end");
    }
}