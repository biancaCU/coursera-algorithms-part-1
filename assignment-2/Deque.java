import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Item[] queue;
    private int pHead;
    private int pTail;

    // construct an empty deque
    public Deque(){
        queue = (Item[]) new Object[2];
        pHead = 1;
        pTail = 0;
    }
    // is the deque empty?
    public boolean isEmpty(){
        return pHead > pTail;
    }
    // return the number of items on the deque
    public int size()  {
        return pTail - pHead + 1;
    }
    // add the item to the front
    public void addFirst(Item item){
        if(item == null) throw new NullPointerException();
        if(resizable()) resize(size());
        if(pHead > 0){
            pHead--;
            queue[pHead] = item;
        }
    }
    // add the item to the end
    public void addLast(Item item){
        if(item == null) throw new NullPointerException();
        if(resizable()) resize(size());
        if(pTail < queue.length-1){
            pTail++;
            queue[pTail] = item;
        }
    }
    // remove and return the item from the front
    public Item removeFirst() {
        if(isEmpty()) throw new NoSuchElementException();
        Item i = queue[pHead];
        queue[pHead] = null;
        pHead++;
        if(resizable()) resize(size());
        return i;
    }
    // remove and return the item from the end
    public Item removeLast()  {
        if(isEmpty()) throw new NoSuchElementException();
        Item i = queue[pTail];
        queue[pTail] = null;
        pTail--;
        if(resizable()) resize(size());
        return i;
    }

    private boolean resizable(){
        if(size() > 0 && size() <= queue.length/4){
            return true;
        } else if((pTail+1) == queue.length || pHead == 0) {
            return true;
        }
        return false;
    }

    private void resize(int numElem){
        int newSize = -1;
        if(size() > 0 && size() <= queue.length/4){
            newSize = queue.length/2;
        } else if((pTail+1) == queue.length || pHead == 0) {
            newSize = queue.length*2;
        }
        Item[] newQueue = (Item[]) new Object[newSize];
        int p1 = ((newSize-1)/2) - ((numElem-1)/2);
        int p2 = pHead;
        int pElem = 0;
        while(pElem < numElem){
            newQueue[p1+pElem] = queue[p2+pElem];
            pElem++;
        }
        pHead = p1;
        pTail = pHead + numElem-1;
        queue = newQueue;
    }

    private void print(){
        System.out.print("[");
        for(int i=0; i<queue.length; i++){
            System.out.print(queue[i]+ ", ");
        }
        System.out.print("]");
        System.out.println("  / pHead: " + pHead + " pTail: " + pTail);
    }


    // return an iterator over items in order from front to end
    public Iterator<Item> iterator(){
        return new DequeIterator<Item>(queue, pHead);
    }
    // unit testing (optional)
    public static void main(String[] args){
        Deque<Integer> d = new Deque<Integer>();
        d.addFirst(1);
        //d.print();
        d.addLast(2);
        //d.print();
        d.addLast(4);
        //d.print();
        d.addLast(5);
        //d.print();
        d.addFirst(0);
        //d.print();
        d.addLast(6);
        //d.print();
        d.addFirst(20);
        //d.print();
        d.addFirst(10);
        //d.print();
        d.addFirst(15);
        //d.print();
        d.removeLast();
        //d.print();
        d.removeLast();
        //d.print();
        d.removeLast();
        //d.print();
        d.removeLast();
        //d.print();
        d.removeLast();
        //d.print();

        Iterator<Integer> iter = d.iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }
    }

    private class DequeIterator<Item> implements Iterator<Item>{
        Item[] items;
        int p;
        public DequeIterator(Item[] items, int pHead){
            this.items = items;
            this.p = pHead-1;
        }
        @Override
        public boolean hasNext(){
            return p < items.length-1 && items[p+1] != null;
        }
        @Override
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            return items[++p];
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}