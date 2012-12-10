/*
*	Name: Fleeting Figures (ca.michael.evan.monu_mentalmath / FleetingFigures.java)
*	Authors: Michael Burnie, Evan Pugh
*	Date (due): 2012/12/14
*	Description: The Fleeting Figures game is part of the Monu-Mental Math application. In this game, the user is shown
*	4 numbers in 4 different coloured ovals (red, blue, yellow, green). The user has to memorize the number and colour
*	pairs. After a few seconds, the ovals disappear and a question is shown. The question asks which colour was associated
*	with a given number. The user has to choose between the four colours within 5 seconds. If not, or if the user chooses incorrectly,
*	only one more mistake is allowed before they lose.
*/
package ca.michael.evan.monu_mentalmath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FleetingFigures extends Activity implements OnClickListener
{
	//Declare each textview and button
	//oval buttons	
	public Button buttonOne;
	public Button buttonTwo;
	public Button buttonThree;
	public Button buttonFour;
	//answer buttons
	public Button answerButton1;
	public Button answerButton2;
	public Button answerButton3;
	public Button answerButton4;
	
	public TextView infoTextView;//displays if the user is correct or not
	public TextView questionTextView;//displays the question
	public TextView questionNumberTextView;//displays the required number for the question
	public TextView timerTextView;//displays the current time remaining
	public TextView scoreTextView;//displays the user's score
	
	//declare class variables
	int max, min, answerInteger, chosenNumber, currentQuestionNumber, score, difficulty, seconds, incorrect;
	public String answerColor, difficultyString;
	
	private final int maxIncorrect = 3;
	
	//create an alertDialog
	AlertDialog alertDialog;
	
	private GameCountDownTimer countDownTimer; //CountDownTimer for the game's timer
	
	//Create constant integers for the start, end and delay times
	private final int startTime = 5000; //default is 5 seconds
	private final int interval = 100;
		
	//Declare the Handler and delay
	Handler handler;
	long delay;
	
	/**
	 * This method is called when the activity is first created. It initializes the project's views
	 * and many of its variables. It also sets the onClickListeners for each button, and calls an alertDialog.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fleeting_figures);
		
		//initialize the buttons and textviews by connecting them to their XML counterpart
		buttonOne = (Button) findViewById(R.id.buttonOne);
		buttonTwo = (Button) findViewById(R.id.buttonTwo);
		buttonThree = (Button) findViewById(R.id.buttonThree);
		buttonFour = (Button) findViewById(R.id.buttonFour);
		answerButton1 = (Button) findViewById(R.id.answerButton1);
		answerButton2 = (Button) findViewById(R.id.answerButton2);
		answerButton3 = (Button) findViewById(R.id.answerButton3);
		answerButton4 = (Button) findViewById(R.id.answerButton4);
		//set the onClickListener for each button
		answerButton1.setOnClickListener(this);
		answerButton2.setOnClickListener(this);
		answerButton3.setOnClickListener(this);
		answerButton4.setOnClickListener(this);
		
		infoTextView = (TextView) findViewById(R.id.infoTextView);
		questionTextView = (TextView) findViewById(R.id.questionTextView);
		questionNumberTextView = (TextView) findViewById(R.id.questionNumberTextView);
		timerTextView = (TextView) findViewById(R.id.timerTextView);
		scoreTextView = (TextView) findViewById(R.id.scoreTextView);
		
		//initialize class variables
		max = 0;
		min = 0;
		score = 0;
		difficulty = 0;
		currentQuestionNumber = 0;
		incorrect = 0;
		
		//initialize the countDownTimer and give it a start/tick time
        countDownTimer = new GameCountDownTimer(startTime, interval);
        seconds = 5;
		
		handler = new Handler();
		delay = 6000;//6 seconds
		
		displayAlertDialog("Select Difficulty");
	}
	
	/**
	 * buildQuestion() manages other methods to generate random numbers and the answer.
	 */
	public void buildQuestion()
	{
		//declare variables
		int num1, num2, num3, num4;

		//generate four random numbers by calling the generateRandomNums() method four times,
		//and ensure the numbers aren't duplicated
		num1 = generateRandomNums();
		
		num2 = generateRandomNums();
		while(num1 == num2)num2 = generateRandomNums();
		
		num3 = generateRandomNums();
		while(num1 == num3 || num2 == num3)num3 = generateRandomNums();
		
		num4 = generateRandomNums();
		while(num1 == num4 || num2 == num4 || num3 == num4)num4 = generateRandomNums();
		
		//display the numbers on the oval buttons
		buttonOne.setText(String.valueOf(num1));
		buttonTwo.setText(String.valueOf(num2));
		buttonThree.setText(String.valueOf(num3));
		buttonFour.setText(String.valueOf(num4));
		
		//ArrayList to get the passed randomized colors from chooseColors()
		ArrayList<Integer> chosenColors = new ArrayList<Integer>();
		chosenColors = chooseColors();
		
		//Create a HashMap for integer key/value pairs, using num1 to num4 as keys and the randomized colors as values
		HashMap<Integer, Integer> numberColorPair = new HashMap<Integer, Integer>();
		numberColorPair.put(num1, chosenColors.get(0));
		numberColorPair.put(num2, chosenColors.get(1));
		numberColorPair.put(num3, chosenColors.get(2));
		numberColorPair.put(num4, chosenColors.get(3));
		
		chosenNumber = (int)(Math.random() * 4);//random number 0-3 to randomly choose one of the numbers for the question
		int answerID = 0;//answer ID used to compare the resourceID of the colours
		
		//determine which number was chosen from 0-3 and build the answer around it
		if(chosenNumber == 0)
		{
			currentQuestionNumber = num1;//number displayed in answerNumberTextView for the user to find the paired colour
			answerID = numberColorPair.get(num1);//used to compare colour_button resourceIDs later
		}
		else if(chosenNumber == 1)
		{
			currentQuestionNumber = num2;//number displayed in answerNumberTextView for the user to find the paired colour
			answerID = numberColorPair.get(num2);//used to compare colour_button resourceIDs later
		}
		else if(chosenNumber == 2)
		{
			currentQuestionNumber = num3;//number displayed in answerNumberTextView for the user to find the paired colour
			answerID = numberColorPair.get(num3);//used to compare colour_button resourceIDs later
		}
		else if(chosenNumber == 3)
		{
			currentQuestionNumber = num4;//number displayed in answerNumberTextView for the user to find the paired colour
			answerID = numberColorPair.get(num4);//used to compare colour_button resourceIDs later
		}
		
		//the following if statements are used to determine the answer through comparison of the colour_button resource IDs
		if(answerID==R.drawable.red_button)answerColor = "Red";
		else if(answerID==R.drawable.blue_button)answerColor = "Blue";
		else if(answerID==R.drawable.yellow_button)answerColor = "Yellow";
		else if(answerID==R.drawable.green_button)answerColor = "Green";
		
		removeButtons();//remove the oval buttons from sight by calling the removeButtons() method
		
	}//end buildQuestion
	
	/**
	 * nextQuestion() calls buildQuestion(), manages the button functions like their visibility,
	 * and resets two textviews for the next question.
	 */
	public void nextQuestion()
	{
		//set the oval buttons to visible
		buttonOne.setVisibility(View.VISIBLE);
		buttonTwo.setVisibility(View.VISIBLE);
		buttonThree.setVisibility(View.VISIBLE);
		buttonFour.setVisibility(View.VISIBLE);
		
		//make the answer buttons invisible
		answerButton1.setVisibility(View.INVISIBLE);
		answerButton2.setVisibility(View.INVISIBLE);
		answerButton3.setVisibility(View.INVISIBLE);
		answerButton4.setVisibility(View.INVISIBLE);
		
		//disable the answer buttons
		answerButton1.setEnabled(false);
		answerButton2.setEnabled(false);
		answerButton3.setEnabled(false);
		answerButton4.setEnabled(false);
		
		//clear the textviews displaying the previous question information
		questionTextView.setText("");
		questionNumberTextView.setText("");
		
		buildQuestion();//build the next question
		
	}//end nextQuestion()
	
	/**
	 * This method generates one random number in the given min and max range.
	 */
	private int generateRandomNums(){return min + (int)(Math.random() * ((max - min) + 1));}
	
	/**
	 * This method randomizes the colors for the oval buttons, and returns the randomized
	 * list of coloured button drawables
	 */
	private ArrayList<Integer> chooseColors()
	{
		//create an Integer ArrayList to store the 4 colours' button drawables
		ArrayList<Integer> chosenColors = new ArrayList<Integer>();
		chosenColors.add(R.drawable.red_button);
		chosenColors.add(R.drawable.blue_button);
		chosenColors.add(R.drawable.yellow_button);
		chosenColors.add(R.drawable.green_button);
		
		Collections.shuffle(chosenColors);//randomize the chosen colors
		
		//set the background colours of the ovals now that they're randomized
		buttonOne.setBackgroundResource(chosenColors.get(0));
		buttonTwo.setBackgroundResource(chosenColors.get(1));
		buttonThree.setBackgroundResource(chosenColors.get(2));
		buttonFour.setBackgroundResource(chosenColors.get(3));
		
		return chosenColors;//return the ArrayList
	}//end chooseColors()

	/**
	 * This method removes the ovals from the user's screen so they cannot see the answers.
	 * A handler with delay is used to postpone the user input and question
	 */
	public void removeButtons()
	{	   
		handler.postDelayed(new Runnable()
		{
			public void run()//after the delay, do the following:
			{
				infoTextView.setText("");//erase the text in the infoTextView
				
				//set the ovals invisible
				buttonOne.setVisibility(View.INVISIBLE);
				buttonTwo.setVisibility(View.INVISIBLE);
				buttonThree.setVisibility(View.INVISIBLE);
				buttonFour.setVisibility(View.INVISIBLE);
				
				//show the answer buttons
				answerButton1.setVisibility(View.VISIBLE);
				answerButton2.setVisibility(View.VISIBLE);
				answerButton3.setVisibility(View.VISIBLE);
				answerButton4.setVisibility(View.VISIBLE);
				
				//enable the answer buttons
				answerButton1.setEnabled(true);
				answerButton2.setEnabled(true);
				answerButton3.setEnabled(true);
				answerButton4.setEnabled(true);
				
				displayQuestion();//show the question
				countDownTimer.start(); //start the game's timer
			}
		}, delay);//delay value, default 6secs
	}//end removeButtons
	
	/**
	 * This method shows the current question, called when the user can begin to select and answer
	 */
	public void displayQuestion()
	{
		questionTextView.setText("What colour of circle contained the number:");
		questionNumberTextView.setText(String.valueOf(currentQuestionNumber));//display the random number used for the answer
	}//end displayQuestion

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
	 * (i.e.: select difficulty, start), it will generate an alertDialog for that state.
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
					min = 10;
					max = 99;
					delay = 7000;//7 seconds
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
					min = 100;
					max = 999;
					delay = 6000;//6 seconds
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
					min = 1000;
					max = 9999;
					delay = 5000;//5 seconds
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
			dialogMessage = "Find the correct colour for the matching number. After three mistakes you lose! Difficulty: " + difficultyString;
			
			//button for starting the game
			alertDialog.setButton("I'm ready!", new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int whichButton) 
				{
					nextQuestion();//create the first question
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
		
		//set the variables to the title and message
		alertDialog.setTitle(dialogTitle);
		alertDialog.setMessage(dialogMessage);
		alertDialog.show();//show the AlertDialog
	}//end displayAlertDialog()
	
	/**
	 * This method keeps track of, and updates, the user's score and mistakes
	 */
	public void determineScore(boolean isCorrect)
	{
		countDownTimer.cancel();//cancel the timer
		
		if(isCorrect)
		{
			infoTextView.setText("Correct!");//notify the user they are correct
			
			//reduce the delay depending on the current delay value, increasing difficulty over time
			if(delay > 4000)delay = delay - 200;
			else if(delay > 3000)delay = delay - 100;
			else delay = delay - 50;
			
			score++;//increase the user's score
			scoreTextView.setText(String.valueOf(score));//update the user's score textview
			
			nextQuestion();//display the next question
		}
		else
		{
			infoTextView.setText("Incorrect. It was " + answerColor + ".");
			incorrect++;//add to their incorrect score
			if(incorrect == maxIncorrect)endGame();//end the game
			else nextQuestion();//display the next question
		}
	}
	
	/**
	 * This method determines what will happen when the state of the game is at end.
	 * The activity is switched to the GameOver activity
	 */
	public void endGame()
	{
		//create a new intent
		Intent intent = new Intent(this, GameOver.class);
		// send Key/Value pairs to the GameOver activity
		intent.putExtra("game", "Fleeting Figures");
		intent.putExtra("difficulty", difficultyString);
		intent.putExtra("score", String.valueOf(score));
		startActivity(intent);//start the activity
	}

	/**
	 * This method listens for clicks and determines what action to take depending on the button clicked
	 */
	@Override
	public void onClick(View v)
	{
		if(v.getId() == R.id.answerButton1)//if the user clicked the first button
		{
			//if the answerColor is red, the user is correct
			if(answerColor == "Red")determineScore(true);
			else determineScore(false);

		}//end answerButton1
		else if(v.getId() == R.id.answerButton2)//if the user clicked the second button
		{
			//if the answerColor is blue, the user is correct
			if(answerColor == "Blue")determineScore(true);
			else determineScore(false);
			
		}//end answerButton2
		else if(v.getId() == R.id.answerButton3)//if the user clicked the third button
		{
			//if the answerColor is yellow, the user is correct
			if(answerColor == "Yellow")determineScore(true);
			else determineScore(false);
		}//end answerButton3
		else if(v.getId() == R.id.answerButton4)//if the user clicked the fourth button
		{
			//if the answerColor is green, the user is correct
			if(answerColor == "Green")determineScore(true);
			else determineScore(false);
		}//end answerButton4
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
			infoTextView.setText("Time's up! It was " + answerColor + ".");
			timerTextView.setText(":00");//set the timerTextView to 0	
			
			incorrect++;
			if(incorrect == maxIncorrect)
			{
				infoTextView.setText("Game over!");//notify the user the timer has run out
				endGame();//end the game
			}
			else nextQuestion();//display the next question
				
		}//end onFinish()

		//This method determines what happens for each tick of the countDownTimer
		@Override
		public void onTick(long msUntilFinished) 
		{			
			//calculate the seconds by dividing the milliseconds
			seconds = (int) (msUntilFinished / 1000);
			
			//set the timer TextView to the current time
			timerTextView.setText(":0" + seconds);	
		}//end onTick()
	}
}
