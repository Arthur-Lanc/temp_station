import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int N = 0;
    
    // construct an empty randomized queue
    public RandomizedQueue(){
        s = (Item[]) new Object[1];
    }                 

    // is the randomized queue empty?
    public boolean isEmpty(){
        return N == 0;
    }                 

    // return the number of items on the randomized queue
    public int size(){
        return N;
    }                        

    // add the item
    public void enqueue(Item item){
       if (item == null) {
           throw new IllegalArgumentException();
       }

        if (N == s.length) resize(2 * s.length);
        s[N++] = item;
    }           

    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++)
        copy[i] = s[i];
        s = copy;
    }

    // remove and return a random item
    public Item dequeue(){
       if (N == 0) {
           throw new NoSuchElementException();
       }

        int randomIndex = StdRandom.uniform(0, N);
        Item item = s[randomIndex];
        s[randomIndex] = s[N-1];
        s[N-1] = null;
        N -= 1;
        if (N > 0 && N == s.length/4) resize(s.length/2);
        return item;
    }               

    // return a random item (but do not remove it)     
    public Item sample(){
       if (N == 0) {
           throw new NoSuchElementException();
       }

        return s[StdRandom.uniform(N)];
    }                     

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomArrayIterator();
    }         

    private class RandomArrayIterator implements Iterator<Item>{
    	private int i = 0;
        Item[] copy;
        
        public RandomArrayIterator() {
        	copy = (Item[]) new Object[N];
        	System.arraycopy( s, 0, copy, 0, N);
        	StdRandom.shuffle(copy, 0, N);
        }
        
        public boolean hasNext() { return i < N; }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() { 
           if (i >= N) {
               throw new NoSuchElementException();
           }

            return copy[i++]; 
        }
    }

    // unit testing (optional)
    public static void main(String[] args){
    	System.out.println("start");
    	RandomizedQueue<String> randque = new RandomizedQueue<String>();
    	randque.enqueue("a");
    	randque.enqueue("b");
    	randque.enqueue("c");
    	randque.enqueue("e");
    	randque.enqueue("f");
    	
    	System.out.println(randque.size());
    	
//        for(String str : randque) {
//        	System.out.println(str);
//        }
        
//        System.out.println(randque.sample());
//        System.out.println(randque.sample());
//        System.out.println(randque.sample());
    	System.out.println(randque.dequeue());
//    	System.out.println(randque.size());
    	System.out.println(randque.dequeue());
//    	System.out.println(randque.size());
    	System.out.println(randque.dequeue());
//    	System.out.println(randque.size());
//    	System.out.println(randque.dequeue());
//    	System.out.println(randque.dequeue());
//    	System.out.println(randque.dequeue());
    	
      for(String str : randque) {
    	System.out.println(str);
    }
    }
}
