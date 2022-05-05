import java.util.Scanner;

//CS3110 project1
public class driver {
	public static void main(String[] args) {
		boolean exit = false;
		
		//create Scanner
		Scanner sc = new Scanner(System.in);
		while(!exit) {
			System.out.println("Please Input a java floating point expression to evaluate");
			String input = sc.nextLine();
			if (input.equals("q") || input.equals("Q")) {
				exit = true;
			} else {
				linkedPDA convert = new linkedPDA(input);
				double result = convert.analyzeString();
				if (result == Double.POSITIVE_INFINITY) {
					System.out.println("Rejected: Invalid input");
				} else {
					System.out.println(result);
				}
			}
		}
		
	}
}