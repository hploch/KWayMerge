import java.util.Objects;
import java.util.concurrent.RecursiveTask;


public class MergeTask extends RecursiveTask<int[]> {
	private static final long serialVersionUID = 3121582423398734214L;
	
	private final int[][] arraysToMerge;
	private final int from;
	private final int to;

	public MergeTask(int[][] arraysToMerge, int from, int to) {
		this.arraysToMerge = Objects.requireNonNull(arraysToMerge);
		if (from < 0 || to < 0 || from > to) {
			throw new IllegalArgumentException();			
		}
		this.from = from;
		this.to = to;
	}
	
	public int[] mergeArrays(int[] a1, int[] a2) {
		int[] b = new int[a1.length + a2.length];
		
		int i = 0;
		int j = 0;
		int k = 0;
		
		while (i < a1.length && j < a2.length) {
			if (a1[i] < a2[j]) {
				b[k++] = a1[i++];
			} else {
				b[k++] = a2[j++];
			}
		}
		
		while (i < a1.length) {
			b[k++] = a1[i++];
		}
		
		while (j < a2.length) {
			b[k++] = a2[j++];
		}		
		
		return b;
	}
	
	@Override
	protected int[] compute() {
		int length = to - from + 1;
		
		if (length == 1) {
			return arraysToMerge[from];
		} else  if (length == 2) {
			return mergeArrays(arraysToMerge[from], arraysToMerge[to]);
		}
		
		MergeTask mergeTask1 = new MergeTask(arraysToMerge, from, from + length/2 - 1);
		MergeTask mergeTask2 = new MergeTask(arraysToMerge, from + length/2, from + length - 1);
		
		mergeTask2.fork();
		
		return mergeArrays(mergeTask1.compute(), mergeTask2.join()); 
	}

}
