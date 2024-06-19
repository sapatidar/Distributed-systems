public class LamportClock{
	public static void main(String[]args) {
		int process1[] = new int[7];
		int process2[] = new int[6];
		int arrows[][] = new int[7][6];
		takeInputAndInitialize(arrows, process1, process2);
		calculateClock(process1, process2, arrows);
		
		System.out.println("Input:");
		for(int i=0; i<7; i++) {
			System.out.println("");
			for(int j=0; j<6; j++) {
				if(arrows[i][j]==-1)
					System.out.print(arrows[i][j]+"  ");
				else
				System.out.print(arrows[i][j]+"   ");
			}
		}
		
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("Output:");
		System.out.println("");
		System.out.println("EventNumber    TimeStamp");
		for(int i=0; i<process1.length; i++) {
			System.out.println("      e1" + (i+1) + ":           " + process1[i]);
		}
		
		for(int i=0; i<process2.length; i++) {
			System.out.println("      e2" + (i+1) + ":           " + process2[i]);
		}
		
	}

	public static void takeInputAndInitialize(int[][] arrows, int[] process1, int[] process2) {
		for(int i=0; i<7; i++) {
			for(int j=0; j<6; j++) {
				arrows[i][j]=0;
			}
		}
		arrows[1][2] = 1;
		arrows[4][4] = 1;
		arrows[6][3] = -1;
		
		for(int i=0; i<process1.length; i++) {
			process1[i] = i+1;
		}
		
		for(int i=0; i<process2.length; i++) {
			process2[i] = i+1;
		}
	}

	public static void calculateClock(int[] process1, int[] process2, int[][] arrows) {
		
		for(int i=0; i<process1.length; i++) {
			for(int j=0; j<process2.length; j++) {
				
				if(arrows[i][j]==1) {
					process2[j] = Math.max(process2[j], process1[i]+1);
					for(int repair=j+1; repair<process2.length; repair++) {
						process2[repair] = process2[repair-1] + 1;
					}
				}
				
				if(arrows[i][j]==-1) {
					process1[i] = Math.max(process1[i], process2[j]+1);
					for(int repair=i+1; repair<process1.length; repair++) {
						process1[repair] = process1[repair-1] + 1;
					}
				}
			}
		}
	}
}