/*
*	Name: GameOver (ca.michael.evan.monu_mentalmath / GameOver.java)
*	Authors: Michael Burnie, Evan Pugh
*	Date (due): 2012/12/14
*	Description: The score screen shows the users scores from each game played.
*/
package ca.michael.evan.monu_mentalmath;

import java.io.DataInputStream;
import java.io.EOFException;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class ScoreScreen extends Activity {

	private TextView[] scoreText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_screen);
		
		for(int a = 1; a < 5; a++)
		{
			scoreText[a] =  (TextView) findViewById(R.id.scoresTextView);
		}
		
		try // try to read data from the scores file
		{
			DataInputStream in = new DataInputStream(openFileInput("scores"));
			try
			{
				for (int b = 1; b < 5; b++) // for all the text views read in the file contents
				{
					scoreText[b].setText(in.readUTF());
				}
			}
			catch (EOFException e) // catch end of file exception
			{// show in the log when the end of the file is reached
				Log.i("Data Input Sample", "End of file reached");
			}
			in.close(); // close the input stream
		}
		catch (Exception e) 
		{// catch all exceptions and log the error message
			Log.i("Data Input Sample", e.getMessage());
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_score_screen, menu);
		return true;
	}

}
