/*
*	Name: Instructions (ca.michael.evan.monu_mentalmath / Instructions.java)
*	Authors: Michael Burnie, Evan Pugh
*	Date (due): 2012/12/14
*	Description: Displays information about each game.
*/
package ca.michael.evan.monu_mentalmath;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Instructions extends Activity
{
	//declare textviews and buttons
	private TextView instructionsTextView1, instructionsTextView2, gameTextView;
	private Button InstructionNextButton, instructions_activity_menu_button;
	
	//declare String constants containing the names of the activities
	private final String MONU_MENTAL_MATH = "Monu-Mental Math";
	private final String MENTAL_MATH = "Mental Math";
	private final String FLEETING_FIGURES = "Fleeting Figures";
	private final String GAME_OVER_AND_SCORES = "Game Over & Scores";
	
	/**
	 * This method is called when the activity is first created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instructions);
		
		//link the TextViews and Buttons to their GUI counterpart
		instructionsTextView1 = (TextView) findViewById(R.id.instructionsTextView1);
		instructionsTextView2 = (TextView) findViewById(R.id.instructionsTextView2);
		gameTextView = (TextView) findViewById(R.id.gameTextView);
		InstructionNextButton = (Button) findViewById(R.id.instruction_activity_next_button);
		instructions_activity_menu_button = (Button) findViewById(R.id.instruction_activity_menu_button);
		
		//Set the default instruction to describing the game
		gameTextView.setText(MONU_MENTAL_MATH);
		instructionsTextView1.setText("Monu-Mental Math is a set of games that deal with numbers. ");
		instructionsTextView2.setText("They are generally set against a clock for fast-paced thinking. It’s a fun tool to build your mental math skills!");
		
		InstructionNextButton.setOnClickListener(new OnClickListener() //When the user clicks next
        {
			public void onClick(View v) 
			{
				//Depending on what current instruction is being shown, the following piece of information is shown.
				if(gameTextView.getText().equals(MONU_MENTAL_MATH))
				{
					gameTextView.setText(MENTAL_MATH);
					instructionsTextView1.setText("Mental Math is a game where you solve simple math equations as quickly as possible. Start by solving the equations near the top of the screen with the given numpad. Some answers will require negative values, ");
					instructionsTextView2.setText("so click the “Make Negative” checkbox when required. You have 30 seconds to solve as many as you can. Don’t worry about getting one wrong, just move on to the next and power through them!");
				}
				else if(gameTextView.getText().equals(MENTAL_MATH))
				{
					gameTextView.setText(FLEETING_FIGURES);
					instructionsTextView1.setText("In this game, the user is shown 4 numbers in 4 different coloured ovals (red, blue, yellow, green). The user has to memorize the number and colour pairs. After a few seconds, the ovals disappear and a question is shown. ");
					instructionsTextView2.setText("The question asks which colour was associated with a given number. The user has to choose between the four colours within 5 seconds. Three mistakes are allowed before the game ends.");
				}
				else if(gameTextView.getText().equals(FLEETING_FIGURES))
				{
					gameTextView.setText(GAME_OVER_AND_SCORES);
					instructionsTextView1.setText("In Game Over, the player's score is displayed and evaluated. You can see the results of your performance and you will see a small description regarding your performance. ");
					instructionsTextView2.setText("In the Scores activity, you can see your latest scores in each game, along with the difficulty you played.");
					InstructionNextButton.setVisibility(View.INVISIBLE);
				}
			} // end onClick inner class
		}); // end onClickListener
		
		instructions_activity_menu_button.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View v) 
			{
				finish();//end the current activity
			} // end onClick inner class
		}); // end onClickListener
		
		
	}
}
