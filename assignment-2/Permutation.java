import edu.princeton.cs.algs4.StdIn;

public class Permutation{
    public static void main(String[] args){
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while(!StdIn.isEmpty()){
            rq.enqueue(StdIn.readString());
        }
        int p1 = 0;
        while(p1 < k){
            System.out.println(rq.dequeue());
            p1++;
        }
    }
}