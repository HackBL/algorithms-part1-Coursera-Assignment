import edu.princeton.cs.algs4.*;

public class Permutation {
    public static void main(String[] args)
    {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        int input = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }

        for (int i = 0; i < input; i++)
            StdOut.println(queue.dequeue());
    }
}
