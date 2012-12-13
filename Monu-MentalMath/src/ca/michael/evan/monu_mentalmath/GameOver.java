/*
*	Name: GameOver (ca.michael.evan.monu_mentalmath / GameOver.java)
*	Authors: Michael Burnie, Evan Pugh
*	Date (due): 2012/12/14
*	Description: The GameOver activity is shown after each game has ended. It takes the name of the
*	game the user last played, the difficulty they played on, and the score they attained. It also
*	generates a message depending on their performance. It has buttons to let the user play again, 
*	check their scores or return to the main menu.
*/
package ca.michael.evan.monu_mentalmath;


import java.io.DataOutputStream;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class GameOver extends Activity implements OnClickListener
{
	private final Context context = this;//set the context to 'this' activity
	
	//declare the TextViews
	public TextView gameTextView;
	public TextView difficultyTextView;
	public TextView scoreTextView;
	public TextView descriptionTextView;
	
	//declare the Buttons
	public Button playAgainButton;
	public Button returnToMainMenuButton;
	public Button checkScoresButton;

	//declare the class variables
	String gameString, difficultyString, scoreString;
	int score;
	
	/**
	 * This method is called when the activity is first created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_over);
		
		//initialize and link the textView and Button variables to their GUI counterparts
		gameTextView = (TextView) findViewById(R.id.gameTextView);
		difficultyTextView = (TextView) findViewById(R.id.difficultyTextView);
		scoreTextView = (TextView) findViewById(R.id.scoreTextView);
		descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
		
		playAgainButton = (Button) findViewById(R.id.playAgainButton);
		returnToMainMenuButton = (Button) findViewById(R.id.returnToMainMenuButton);
		checkScoresButton = (Button) findViewById(R.id.checkScoresButton);
		//set the button onClickListener to this activity
		playAgainButton.setOnClickListener(this);
		returnToMainMenuButton.setOnClickListener(this);
		checkScoresButton.setOnClickListener(this);
		
		//get the intents passed from the other activities: game, difficulty and score
		gameString = getIntent().getExtras().getString("game");
		difficultyString = getIntent().getExtras().getString("difficulty");
		scoreString = getIntent().getExtras().getString("score");
		
		//set the textViews to the intent strings
		gameTextView.setText(gameString);
		difficultyTextView.setText(difficultyString);
		scoreTextView.setText(scoreString);
		
		String writeString = gameString + "  |  " + difficultyString + "  |  " + scoreString;
		
		showDescription();
		
		// try to write the binary file
		try
		{
			DataOutputStream out = // append to the file if possible
					new DataOutputStream(openFileOutput("scores", Context.MODE_APPEND));

			out.writeUTF(writeString); // write the new string
			out.close(); // close the output stream

		} catch (Exception e)
		{				// catch any exceptions and log them for debugging.
			Log.i("Data Input Sample", e.getMessage());
		}
	}
	
	/**
	 * This method generates a message for the user depending on their performance
	 */
	public void showDescription()
	{
		//Declare a message string and initialize it
		String messageString = "";
		score = Integer.parseInt(scoreString);//parse the score intent into an integer variable
		
		//The following code creates a message depending on the user's performance.
		if(score == 0)messageString = "Did you even try? ";
		else if(score < 3)messageString = "A score of " + score + " is... terrible. ";
		else if(score < 6)messageString = "A score of " + score + " isn't exactly amazing, but good try. ";
		else if(score < 10)messageString = score + "? Not bad. ";
		else if(score < 15)messageString = "Nice job. At " + score + " points, you can be proud. ";
		else messageString = "Wow! You got " + score + " points?! Amazing! Now go solve more important problems. ";
		
		if(difficultyString.equals("Easy") && score < 5)messageString = messageString + "I wouldn't try any higher difficulties if I were you.";
		else if(difficultyString.equals("Easy") && score < 6)messageString = messageString +"Keep practicing on Easy.";
		else if(difficultyString.equals("Easy") && score < 15)messageString = messageString +"Keep at it! Maybe try Medium.";
		else if(difficultyString.equals("Easy"))messageString = messageString +"Okay it's time to move on to something more difficult. Try Medium.";
		else if(difficultyString.equals("Medium") && score < 5)messageString = messageString +"Maybe go back to Easy?";	
		else if(difficultyString.equals("Medium") && score < 10)messageString = messageString +"More practice!";
		else if(difficultyString.equals("Medium") && score < 15)messageString = messageString +"Keep at it! Maybe try Hard.";
		else if(difficultyString.equals("Medium"))messageString = messageString +"Okay it's time to move on to something more difficult. Try Hard.";
		else if(difficultyString.equals("Hard") && score < 5)messageString = messageString +"Try Easy or Medium more before attemping Hard.";
		else if(difficultyString.equals("Hard") && score < 10)messageString = messageString +"You're certainly improving!";
		else if(difficultyString.equals("Hard") && score < 15)messageString = messageString +"Not much is holding you back huh.";
		else if(difficultyString.equals("Hard"))messageString = messageString +"It doesn't get any harder than this!";
		
		descriptionTextView.setText(messageString);//set the descriptionTextView's text to the messageString
	}
	
	/**
	 * This method listens for clicks and determines what action to take depending on the button clicked
	 */
	@Override
	public void onClick(View v)
	{
		if(v.getId()==R.id.returnToMainMenuButton)//if the returnToMainMenuButton was clicked
		{
			startActivity(new Intent(context, MainMenu.class));//return to the main menu by calling the main menu activity
		}
		else if(v.getId()==R.id.playAgainButton)//if the playAgain button was clicked
		{
			//determine which game was played by the player last, and restart the respective activity
			if(gameString.equals("Mental Math"))
			{
				startActivity(new Intent(context, MentalMath.class));
			}
			else if(gameString.equals("Fleeting Figures"))
			{
				startActivity(new Intent(context, FleetingFigures.class));
			}
			
		}
		else if(v.getId()==R.id.checkScoresButton)
		{
			startActivity(new Intent(context, ScoreScreen.class));//start the ScoreScreen activity
		}
		
	}

}
