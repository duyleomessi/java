import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


public class CalculatorResult {
	private static class Frame {
		private int n;
		Frame() {}
		Frame(int sz) {
			this.n = sz;
		}
		
		int getNFrame(Scanner in) {
			this.n = in.nextInt();
			return n;
		}
	}
	
	public static void main(String[] args) {
		File candidateFile = new File("candidate.txt");
		File standardFile = new File("standardResult.txt");
		try {
			Scanner standardScanner = new Scanner(standardFile);
			Scanner candidateScanner = new Scanner(candidateFile);
			
			while(standardScanner.hasNext()) {
				int value = standardScanner.nextInt();
				System.out.println(value);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 
	
}
