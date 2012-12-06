import java.util.Random;
import java.util.Scanner;


public class MathGameFundamentals 
{
	
	public static void main(String[] args) 
	{
		Random rnd = new Random();
		Scanner in = new Scanner(System.in);
		
		int operation;
		
		operation = 1 + rnd.nextInt(4);
		
		System.out.println("Operation: " + operation);
		
		String input = "";
		int inputInteger;
		int answer = 0;
		
		int min = -20;
		int max = 20;
		
		int num1 = min + (int)(Math.random() * ((max - min) + 1));
		int num2 = min + (int)(Math.random() * ((max - min) + 1));
		
		if(operation==1)
		{
			System.out.println("Add these two numbers: ");
			answer = num1 + num2;
		}
		else if(operation==2)
		{
			System.out.println("Subtract these two numbers: ");
			answer = num1 - num2;		
		}
		else if(operation==3)
		{
			System.out.println("Multiply these two numbers: ");
			answer = num1 * num2;
		}
		else if(operation==4)
		{
			System.out.println("Divide these two numbers: ");
			
			/*To ensure that the answer is a full number from the division, get the result of
			num1 and num2, then divide num2 by the new result (assigned to num1 again)
			*/
			num1 = num1 * num2;
			
			//to ensure division by zero is impossible, reset the num1 variable:
			while(num2 == 0)
			{
				num2 = min + (int)(Math.random() * ((max - min) + 1));
			}
			answer = num1 / num2;
		}
		
		System.out.println(num1);
		System.out.println(num2);
		
		input = in.nextLine();
		
		try
		{
			inputInteger = Integer.parseInt(input);
			
			if(inputInteger == answer)
			{
				System.out.println("Correct!");
			}
			else
			{
				System.out.println("Wrong. The correct answer is: " + answer);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
				
	}

}
