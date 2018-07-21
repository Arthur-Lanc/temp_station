import edu.princeton.cs.algs4.StdIn;
//import edu.princeton.cs.algs4.StdOut;

public class Permutation {
	public static void main(String[] args) {
		String arg1 = args[0];
		int times = Integer.parseInt(arg1);
		
		RandomizedQueue<String> randque = new RandomizedQueue<String>();		
		while (!StdIn.isEmpty()) {
			String tmp = StdIn.readString();
			randque.enqueue(tmp);
//			StdOut.println(tmp);
		}
		
		int loopNum = 0;
		for(String str : randque) {
			
			if (loopNum >= times) {
				break;
			}
			
			System.out.println(str);
			loopNum += 1;
		}
		
	}
}
