/*
*	Name: Mental Math (ca.michael.evan.monu_mentalmath / MentalMath.java)
*	Authors: Michael Burnie, Evan Pugh
*	Date (due): 2012/12/14
*	Description: The Mental Math game is part of the Monu-Mental Math application. In this game, the user is given 30 seconds
*	to answer as many questions as he/she can. The questions are created from two randomly-generated numbers and a random
*	pick between the four basic math operators.
*/
package ca.michael.evan.monu_mentalmath;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;

public class MentalMath extends Activity implements OnClickListener
{	
	//Declare each textview, edittext and button
	public TextView timerTextView;//timer
	public TextView infoTextView;//information messages
	public TextView scoreTextView;//score
	public TextView numberOneTextView;//first number
	public TextView numberTwoTextView;//second number
	public TextView operatorTextView;//operator
	
	public EditText answerEditText;//user input 
	
	public Button answerButton;//calculate
	public Button clearButton;//clear
	
	//numbers 0-9 buttons
	public Button oneButton;
	public Button twoButton;
	public Button threeButton;
	public Button fourButton;
	public Button fiveButton;
	public Button sixButton;
	public Button sevenButton;
	public Button eightButton;
	public Button nineButton;
	public Button zeroButton;
	
	//declare a checkbox to allow user to create negative values
	public CheckBox makeNegativeCheckBox;
	
	//create an alertDialog
	AlertDialog alertDialog;
	
	//declare class variables
	int min, max, answer, score, incorrect, difficulty;
	String difficultyString;
	
	private GameCountDownTimer countDownTimer; //CountDownTimer for the game's timer
	
	//Create constant integers for the start, end and delay times
	private final int startTime = 30000; //default is 30 seconds
	private final int interval = 100;
	private int seconds;

	/**
	 * This method is called when the activity is first created. It initializes the project's views
	 * and many of its variables. It also sets the onClickListeners for each button, and calls an alertDialog.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mental_math);
		
		//initialize the textViews, edittexts and buttons by connecting them to their XML counterpart
		timerTextView = (TextView) findViewById(R.id.timerTextView);
		infoTextView = (TextView) findViewById(R.id.infoTextView);
		scoreTextView = (TextView) findViewById(R.id.scoreTextView);		
		numberOneTextView = (TextView) findViewById(R.id.numberOneTextView);
		numberTwoTextView = (TextView) findViewById(R.id.numberTwoTextView);
		operatorTextView = (TextView) findViewById(R.id.operatorTextView);
		
		answerEditText = (EditText) findViewById(R.id.answerEditText);
		
		answerButton = (Button) findViewById(R.id.answerButton);
		clearButton = (Button) findViewById(R.id.clearButton);
		oneButton = (Button) findViewById(R.id.oneButton);
		twoButton = (Button) findViewById(R.id.twoButton);
		threeButton = (Button) findViewById(R.id.threeButton);
		fourButton = (Button) findViewById(R.id.fourButton);
		fiveButton = (Button) findViewById(R.id.fiveButton);
		sixButton = (Button) findViewById(R.id.sixButton);
		sevenButton = (Button) findViewById(R.id.sevenButton);
		eightButton = (Button) findViewById(R.id.eightButton);
		nineButton = (Button) findViewById(R.id.nineButton);
		zeroButton = (Button) findViewById(R.id.zeroButton);
		
		//initialize the makeNegativeCheckBox
		makeNegativeCheckBox = (CheckBox) findViewById(R.id.makeNegativeCheckBox);
		
		//initialize the class variables
		score = 0;
		incorrect = 0;
		difficulty = 0;
		
		//instantiate the countDownTimer and give it a start/tick time
        countDownTimer = new GameCountDownTimer(startTime, interval);
        seconds = 30;

        //set the onClickListener for each button
		answerButton.setOnClickListener(this);
		clearButton.setOnClickListener(this);
		oneButton.setOnClickListener(this);
		twoButton.setOnClickListener(this);
		threeButton.setOnClickListener(this);
		fourButton.setOnClickListener(this);
		fiveButton.setOnClickListener(this);
		sixButton.setOnClickListener(this);
		sevenButton.setOnClickListener(this);
		eightButton.setOnClickListener(this);
		nineButton.setOnClickListener(this);
		zeroButton.setOnClickListener(this);
		
		//set the onCheckedChangeListener to listen for changes in the makeNegativeCheckBox
		makeNegativeCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{	
			boolean isNeg = false;//variable for determining if the checkbox is negative
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{				
				if(!isNeg)//if it isn't negative
				{
					answerEditText.setText("-" + answerEditText.getText());//concatenate a negative symbol
					isNeg = true;//set the value to negative
				}
				else//if it is checked
				{
					answerEditText.setText(answerEditText.getText().toString().replace("-", ""));//get rid of the negative symbol
					isNeg = false;//set the value to positive
				}		
			}
		});
		
		//display an alertDialog for "select difficulty" if user is not in a practice session
		displayAlertDialog("Select Difficulty");
		
	}
	
	/**
	 * buildQuestion() manages other methods to generate random numbers, the operation, and the answer.
	 */
	public void buildQuestion()
	{
		//declare variables
		int num1, num2, operation;
		String operator = "";
		
		//generate two random numbers by calling the generateRandomNums() method twice
		num1 = generateRandomNums();
		num2 = generateRandomNums();
		
		//generate the operation by calling the generatoeOperator() method
		operation = generateOperator();
		
		//set the string variables to the proper operators
		if(operation==1)operator = "+";
		else if(operation==2)operator = "-";
		else if(operation==3)operator = "*";
		else if(operation==4)
		{
			//To ensure that the answer is a full number from the division, get the result of
			//num1 and num2, then divide num2 by the new result (assigned to num1 again)
			num1 = num1 * num2;
			
			//to ensure division by zero is impossible, reset the num2 variable:
			while(num2 == 0)generateRandomNums();
			
			operator = "/";
		}//end division
		
		generateAnswer(operation, num1, num2);//generate the answer by calling the generateAnswer() method
		
		//if the numbers are negative values, surround them with brackets
		if(num1 < 0)numberOneTextView.setText("(" + num1 + ")");
		else numberOneTextView.setText(String.valueOf(num1));
		
		if(num2 < 0)numberTwoTextView.setText("(" + num2 + ")");
		else numberTwoTextView.setText(String.valueOf(num2));
		
		operatorTextView.setText(operator);//set the operator textview to the right operation
		
	}//end buildQuestion
	
	/**
	 * This method generates one random number in the given min and max range .
	 */
	private int generateRandomNums(){return min + (int)(Math.random() * ((max - min) + 1));}
	
	/**
	 * This method generates the operator by randomizing numbers 1-4.
	 */
	private int generateOperator()
	{
		Random rnd = new Random();	
		return 1 + rnd.nextInt(4);
	}//end generateOperator
	
	/**
	 * This method takes the proper operator and does the math to set the answer.
	 */
	private void generateAnswer(int operation, int num1, int num2)
	{
		if(operation==1)answer = num1 + num2;//addition
		else if(operation==2)answer = num1 - num2;//subtraction		
		else if(operation==3)answer = num1 * num2;//multiplication
		else if(operation==4)answer = num1 / num2;//division
	}//end generateAnswer
	
	/**
	 * This method clears the user's input.
	 */
	private void clearInput()
	{
		answerEditText.setText("");//clear the edittext
		makeNegativeCheckBox.setChecked(false);//uncheck the makeNegativeCheckBox
	}//end clearInput

	/**
	 * This method creates an options menu for the user, so they can exit the game
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.mental_math, menu);
		super.onCreateOptionsMenu(menu);
		
		menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "Exit Game");//exit
		menu.add(Menu.NONE, Menu.FIRST+1, Menu.NONE, "Cancel");//cancel and return
		return true;
	}//end onCreateOptionsMenu
	
	public boolean onOptionsItemSelected(MenuItem item)
    {
    	//switch for the menu ID of the user-selected option
    	switch(item.getItemId())
    	{
			case Menu.FIRST: //if the user wishes to exit the game
			//exits the program
			System.exit(0);
			return true;//commit the above changes
			
			case Menu.FIRST+1: //if the user wishes to return to game, do nothing
			return true;
    	}

    	return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected
	
	/**
	 * This method builds each alertDialog in the game. Depending on what gameState is given to it
	 * (i.e.: select difficulty, start, end), it will generate an alertDialog for that state.
	 */
	public void displayAlertDialog(String gameState)
	{
		//instantiate and create the AlertDialog
		alertDialog = new AlertDialog.Builder(this).create();
		
		//initialize the title and message
		String dialogTitle = "";
		String dialogMessage = "";
		
		//If the alertDialog is being created for the 'select difficulty' interface
		if(gameState.equals("Select Difficulty"))
		{
			//set a proper title and message
			dialogTitle = "Select the Difficulty";
			dialogMessage = "Select the difficulty of the questions.";
			
			//if the user selects 'easy'
			alertDialog.setButton("Easy", new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int whichButton) 
				{
					//Create the game based on easy settings
					min = 0;
					max = 10;
					difficulty = 0;
					difficultyString = "Easy";
					displayAlertDialog("Start");//begin the 'start' alert dialog
				}
			});//end easy
			//if the user selects 'medium'
			alertDialog.setButton3("Medium", new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int whichButton) 
				{
					//Create the game based on 'medium' settings
					min = -10;
					max = 10;
					difficulty = 1;
					difficultyString = "Medium";
					displayAlertDialog("Start");//begin the 'start' alert dialog
				}
			});//end medium
			//if the user selects 'hard'
			alertDialog.setButton2("Hard", new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int whichButton) 
				{
					//Create the game based on 'hard' settings
					min = -25;
					max = 25;
					difficulty = 2;
					difficultyString = "Hard";
					displayAlertDialog("Start");//begin the 'start' alert dialog
				}
			});//end hard
		}//end 'select difficulty'
		//if the alertDialog is set the create a 'start' alertDialog for the beginning of the game
		else if(gameState.equals("Start"))
		{
			//set a proper title and message
			dialogTitle = "Get Ready!";
			dialogMessage = "Answer as many questions as you can in " + seconds + " seconds! Difficulty: " + difficultyString;
			
			//button for starting the game
			alertDialog.setButton("I'm ready!", new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int whichButton) 
				{
					countDownTimer.start(); //start the game's timer
					clearInput();//clear the inputs
					buildQuestion();//create the first question
				}
			});
			//button for exiting the game
			alertDialog.setButton2("Exit", new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int whichButton) 
				{
					//exits the program
					System.exit(0);
				}
			});
		}//end 'start'
		//if the game is at its end
		else if(gameState.equals("End"))
		{
			//set a proper title
			dialogTitle = "Game Over! Difficulty: " + difficultyString;
			
			//depending on the user's performance, set different messages
			if(score == 0 && incorrect == 0)dialogMessage = "You didn't even attempt it. Maybe read the instructions first.";
			else if(score == 0)dialogMessage = "Did you even try? Not even one answer was correct. At least you managed to hit the calculate button " + incorrect + " times. Try the practice mode.";
			else if(score == 1 && incorrect > 1)dialogMessage = "Congratulations on getting 1 correct and failing " + incorrect + " times.";
			else if(score == 1)dialogMessage = "Wow, 1 correct? Consider me truly impressed... *cough*";
			else if(score < 5 && incorrect == 0)dialogMessage = score + " correct answers isn't exactly amazing, but you at least had no mistakes.";
			else if(score < 5)dialogMessage = "Good effort. At " + score + " correct answers and " + incorrect + " incorrect attempts you probably could have done better.";
			else if(score < 10)dialogMessage = score + " correct answers? Not bad at all! You had " + incorrect + " incorrect attempts.";
			else if(difficulty < 2)dialogMessage = score + " correct answers! Great job! You had " + incorrect + " incorrect attempts. Perhaps try a harder difficulty.";
			else dialogMessage = score + " correct answers on the hardest difficulty? Why aren't you saving the world instead of playing Android games?!";
			
			//Give an option to play again
			alertDialog.setButton("Play again", new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int whichButton) 
				{
					//play again by restarting the activity
					Intent intent = getIntent();
					finish();
					startActivity(intent);
				}
			});
			//or give an option to exit the game
			alertDialog.setButton2("Exit", new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int whichButton) 
				{
					//exits the program
					System.exit(0);
				}
			});
		}//end 'end'
		
		//set the variables to the title and message
		alertDialog.setTitle(dialogTitle);
		alertDialog.setMessage(dialogMessage);
		alertDialog.show();//show the AlertDialog
	}//end displayAlertDialog()

	/**
	 * This method listens for clicks and determines what action to take depending on the button clicked
	 */
	@Override
	public void onClick(View v)
	{
		//check the id of the button clicked
		//if it is answerButton
		if(v.getId() == R.id.answerButton)
		{
			//declare variables
			String answerString = "";
			int userAnswer;
			//set the answer string to the input of the text
			answerString = answerEditText.getText().toString();
			
			//try to parse the input into an integer. If it fails, default the user answer to zero
			try {userAnswer = Integer.parseInt(answerString);}
			catch(Exception e){userAnswer = 0;}
			
			//if the user is correct
			if(userAnswer == answer)
			{
				infoTextView.setText("Correct!");//notify the user they are correct
				score++;//increase the correct score
				scoreTextView.setText("Score: " + score);//update the score textview
				
				clearInput();//clear the input
			}
			else//if the user is incorrect
			{
				infoTextView.setText("Incorrect. Correct answer: " + answer);//notify the user they are incorrect and provide the correct answer
				incorrect++;//increase the incorrect score
				clearInput();//clear the input
			}
			
			buildQuestion();//create the next question			
		}//end answerButton
		else if(v.getId() == R.id.clearButton)clearInput();//clear the input if the clearButton was pressed
		//if any of the numbers were pressed, append the number in the answerEditText
		else if(v.getId() == R.id.oneButton)answerEditText.append("1");
		else if(v.getId() == R.id.twoButton)answerEditText.append("2");
		else if(v.getId() == R.id.threeButton)answerEditText.append("3");
		else if(v.getId() == R.id.fourButton)answerEditText.append("4");
		else if(v.getId() == R.id.fiveButton)answerEditText.append("5");
		else if(v.getId() == R.id.sixButton)answerEditText.append("6");
		else if(v.getId() == R.id.sevenButton)answerEditText.append("7");
		else if(v.getId() == R.id.eightButton)answerEditText.append("8");
		else if(v.getId() == R.id.nineButton)answerEditText.append("9");
		else if(v.getId() == R.id.zeroButton)answerEditText.append("0");
		
	}//end onClick
	
	/**
	 * This class builds the timer for the application.
	 */
	public class GameCountDownTimer extends CountDownTimer
	{
		//constructor for the countDownTimer
		public GameCountDownTimer(int startTime, int interval)
		{
			super(startTime, interval);
		}//end GameCountDownTimer constructor
		
		//This method determines what happens when the timer runs out
		@Override
		public void onFinish() 
		{
			infoTextView.setText("Game over!");//notify the user the timer has run out
			timerTextView.setText(":00");//set the timerTextView to 0	
			
			displayAlertDialog("End");//display the 'end' alertDialog
			
		}//end onFinish()

		//This method determines what happens for each tick of the countDownTimer
		@Override
		public void onTick(long msUntilFinished) 
		{			
			//calculate the seconds by dividing the milliseconds
			seconds = (int) (msUntilFinished / 1000);
			
			//set the timer TextView to the current time
			if(seconds > 9)timerTextView.setText(":" + seconds);
			else timerTextView.setText(":0" + seconds);
		}//end onTick()
	}

}
