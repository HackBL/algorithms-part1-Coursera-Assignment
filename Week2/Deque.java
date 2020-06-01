import java.util.Iterator;
import edu.princeton.cs.algs4.*;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    // construct an empty deque
    public Deque()
    {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty()
    {
        return size == 0;
    }

    // return the number of items on the deque
    public int size()
    {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException("IllegalArgumentException");

        Node newItem = new Node();
        newItem.item = item;

        if (isEmpty()) {
            first = newItem;
            last = first;
        }
        else {
            newItem.next = first;
            first.prev = newItem;
            first = newItem;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException("Illegal Item");

        Node newItem = new Node();
        newItem.item = item;

        if (isEmpty()) {
            last = newItem;
            first = last;
        }
        else {
            last.next = newItem;
            newItem.prev = last;
            last = newItem;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Already Empty");

        Item item = first.item;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }
        size--;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast()
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Already Empty");

        Item item = last.item;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }
        size--;

        return item;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator()
    {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;

        @Override
        public boolean hasNext() { return current != null; }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Do not call remove()");
        }

        @Override
        public Item next()
        {
            if (!hasNext())
                throw new java.util.NoSuchElementException("No more item");

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        Deque<Integer> q = new Deque<Integer>();

        q.addFirst(3);
        q.addFirst(2);
        q.addFirst(1);
        q.addLast(4);

        StdOut.println("Iterator: ");
        Iterator<Integer> i = q.iterator();
        while (i.hasNext())
        {
            int val = i.next();
            StdOut.print(val + " ");
        }

        StdOut.println("\n\nDequeue: \n"+q.removeFirst());
        StdOut.println(q.removeLast());
        StdOut.println(q.removeFirst());
        StdOut.println(q.removeLast());

    }
}
