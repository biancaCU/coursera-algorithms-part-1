import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int numItems;
    private boolean needsShuffle;
    // construct an empty randomized queue
    public RandomizedQueue(){
        queue = (Item[]) new Object[2];
    }
    // is the queue empty?
    public boolean isEmpty(){
        return numItems == 0;
    }
    // return the number of items on the queue
    public int size(){
        return numItems;
    }
    // add the item
    public void enqueue(Item item){
        if(item == null) throw new NullPointerException();
        numItems++;
        if(resizable()) resize();
        queue[numItems-1] = item;
        needsShuffle = true;
    }
    // remove and return a random item
    public Item dequeue(){
        if(isEmpty()) throw new NoSuchElementException();
        if(needsShuffle){
            StdRandom.shuffle(queue, 0, numItems);
            needsShuffle=false;
        }
        Item temp = queue[numItems-1];
        queue[numItems-1] = null;
        numItems--;
        if(resizable()) resize();
        return temp;
    }
    // return (but do not remove) a random item
    public Item sample(){
        if(isEmpty()) throw new NoSuchElementException();
        return queue[StdRandom.uniform(0, numItems)];
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomizedQueueIterator<Item>(queue, size());
    }

    private boolean resizable(){
        if(numItems >= queue.length) return true;
        else if(!isEmpty() && numItems == queue.length/4) return true;
        else return false;
    }

    private void resize(){
        if(numItems == queue.length){
            queue = transfer(queue, (Item[]) new Object[queue.length*2], numItems);
        } else if (numItems < queue.length/4){
            queue = transfer(queue, (Item[]) new Object[queue.length/2], numItems);
        }
    }

    private Item[] transfer(Item[] moveThis, Item[] toThis, int items){
        int p1 = 0;
        int p2 = 0;
        while(p1 < items){
            toThis[p2] = moveThis[p1];
            p1++;
            p2++;
        }
        return toThis;
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item>{
        private int pointer;
        private Item[] newItems;
        public RandomizedQueueIterator(Item[] items, int numItems){
            this.pointer = 0;
            this.newItems = (Item[]) new Object[numItems];
            int p1 = 0;
            int p2 = 0;
            while(p1 < numItems){
                this.newItems[p2] = items[p1];
                p2++;
                p1++;
            }
            StdRandom.shuffle(this.newItems);
        }
        @Override
        public boolean hasNext(){
            return pointer < newItems.length;
        }
        @Override
        public Item next(){
            if(!hasNext()) throw new NoSuchElementException();
            return newItems[pointer++];
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void print(){
        System.out.print("[");
        for(int i=0; i<queue.length; i++){
            System.out.print(queue[i]+ ", ");
        }
        System.out.println("]");
    }
    // unit testing (optional)
    public static void main(String[] args){
        /*RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        //rq.print();
        System.out.println("Size: " + rq.size());
        rq.enqueue(4);
        rq.enqueue(5);
        //rq.print();
        System.out.println("Size: " + rq.size());
        System.out.println("Dequeue: " + rq.dequeue());
        //rq.print();
        System.out.println("Size: " + rq.size());
        System.out.println("Dequeue: " + rq.dequeue());
        System.out.println("Dequeue: " + rq.dequeue());
        System.out.println("Dequeue: " + rq.dequeue());
        //rq.print();
        System.out.println("Size: " + rq.size());

        Iterator<Integer> iter = rq.iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }*/
    }
}