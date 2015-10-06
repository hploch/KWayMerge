import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;



public class Program {

	public static void main(String[] args) {

		int[][] as = new int[][]{{1,2,5,6,9},{3,4,5,7},{8,10,19},{15,16},{20}};
		
		ForkJoinPool commonPool = ForkJoinPool.commonPool();
		
		int[] result = commonPool.invoke(new MergeTask(as, 0, as.length - 1));
		
		System.err.println(Arrays.toString(result));
	}

}
