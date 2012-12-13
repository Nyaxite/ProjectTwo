/*
*	Name: GameOver (ca.michael.evan.monu_mentalmath / GameOver.java)
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

	private TextView InstructionsTextView;
	private Button InstructionNextButton, instructions_activity_menu_button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instructions);
		
		InstructionsTextView = (TextView) findViewById(R.id.InstructionsTextView);
		InstructionNextButton = (Button) findViewById(R.id.instruction_activity_next_button);
		instructions_activity_menu_button = (Button) findViewById(R.id.instruction_activity_menu_button);
		
		InstructionsTextView.setText("Mental Math: In this game, the user is given " +
				"30 seconds to answer as many questions as he/she can. The questions are created from two " +
				"randomly-generated numbers and a random pick between the four basic math operators.");
		
		InstructionNextButton.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View v) 
			{
				InstructionsTextView.setText("Fleeting Figures: In this game, the user is shown" +
						" 4 numbers in 4 different coloured ovals (red, blue, yellow, green). The user has to memorize the number and colour" +
						" pairs. After a few seconds, the ovals disappear and a question is shown. The question asks which colour was associated" +
						" with a given number. The user has to choose between the four colours within 5 seconds. Three mistakes are allowed before the game ends.");
				
				InstructionNextButton.setVisibility(View.INVISIBLE);
			} // end onClick inner class
		}); // end onClickListener
		
		instructions_activity_menu_button.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View v) 
			{
				finish();
			} // end onClick inner class
		}); // end onClickListener
		
		
	}
}
