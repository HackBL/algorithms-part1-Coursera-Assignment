import java.util.Iterator;
import edu.princeton.cs.algs4.*;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue()
    {
        queue = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty()
    {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size()
    {
        return size;
    }

    // add the item
    public void enqueue(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException("Illegal Item");

        if (size < queue.length)
            queue[size++] = item;

        else if (size == queue.length) {  // Doubling array size
            resize(2*size);
            queue[size++] = item;
        }
    }

    // remove and return a random item
    public Item dequeue()
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Dequeue Is Empty");

        int randomIndex = StdRandom.uniform(size);
        Item item = queue[randomIndex];
        queue[randomIndex] = queue[--size];
        queue[size] = null;

        if (size > 0 && size == queue.length/4) // Shrink array size
            resize(queue.length/2);

        return item;
    }

    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            copy[i] = queue[i];
        queue = copy;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Sample Is Empty");

        int randomIndex = StdRandom.uniform(size) ;
        return queue[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>
    {
        private int i = size-1;

        @Override
        public boolean hasNext() { return i >= 0; }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Do not call remove()");
        }

        @Override
        public Item next()
        {
            if (!hasNext())
                throw new java.util.NoSuchElementException("No More Items");

            return queue[i--];
        }
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);


        StdOut.println("Iterator: ");

        Iterator<Integer> i = q.iterator();
        while (i.hasNext())
        {
            int val = i.next();
            StdOut.print(val + " ");
        }

        StdOut.println("\n\nDequeue: \n"+q.dequeue());
        StdOut.println(q.dequeue());
        StdOut.println(q.dequeue());
        StdOut.println(q.dequeue());
    }
}
