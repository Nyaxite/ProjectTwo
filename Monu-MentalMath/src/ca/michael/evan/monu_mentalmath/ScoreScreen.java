/*
*	Name: ScoreScreen (ca.michael.evan.monu_mentalmath / ScoreScreen.java)
*	Authors: Michael Burnie, Evan Pugh
*	Date (due): 2012/12/14
*	Description: The score screen shows the user's latest scores from each game played.
*/
package ca.michael.evan.monu_mentalmath;

import java.io.DataInputStream;
import java.io.EOFException;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ScoreScreen extends Activity
{
	//declare TextViews
	private TextView scoresTextView0;
	private TextView scoresTextView1;
	private TextView scoresTextView2;
	private TextView scoresTextView3;
	private TextView scoresTextView4;
	private TextView scoresTextView5;
	private TextView scoresTextView6;
	private TextView scoresTextView7;
	private TextView scoresTextView8;
	private TextView scoresTextView9;
	
	//declare Button
	private Button returnToMenuButton;
	
	/**
	 * This method is called when the activity is first created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_screen);
		
		//link the TextViews to their GUI counterpart
		scoresTextView0 = (TextView) findViewById(R.id.scoresTextView0);
		scoresTextView1 = (TextView) findViewById(R.id.scoresTextView1);
		scoresTextView2 = (TextView) findViewById(R.id.scoresTextView2);
		scoresTextView3 = (TextView) findViewById(R.id.scoresTextView3);
		scoresTextView4 = (TextView) findViewById(R.id.scoresTextView4);
		scoresTextView5 = (TextView) findViewById(R.id.scoresTextView5);
		scoresTextView6 = (TextView) findViewById(R.id.scoresTextView6);
		scoresTextView7 = (TextView) findViewById(R.id.scoresTextView7);
		scoresTextView8 = (TextView) findViewById(R.id.scoresTextView8);
		scoresTextView9 = (TextView) findViewById(R.id.scoresTextView9);
		
		returnToMenuButton = (Button) findViewById(R.id.returnToMenuButton);
		returnToMenuButton.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View v) 
			{
				finish();//finish this activity
			} // end onClick inner class
		}); // end onClickListener

		displayScores();//display the scores on the textViews by calling the displayScores() method
	}
	
	/**
	 * This method handles displaying the latest score values
	 */
	private void displayScores()
	{
		try // try to read data from the scores file
		{
			DataInputStream in = new DataInputStream(openFileInput("scores"));//open the scores file
			ArrayList<String> inputList = new ArrayList<String>();//create an ArrayList to hold the text lines
			
			try
			{	
				for(;;)
				{
					inputList.add(in.readUTF());//read in each line of the text
				}
			}
			catch (EOFException e) // catch end of file exception
			{// show in the log when the end of the file is reached
				Log.i("Data Input Sample", "End of file reached");
			}
			in.close(); // close the input stream
			
			// for all the text views read in the latest 10 scores
			scoresTextView0.setText(inputList.get(inputList.size()-10));
			scoresTextView1.setText(inputList.get(inputList.size()-9));
			scoresTextView2.setText(inputList.get(inputList.size()-8));
			scoresTextView3.setText(inputList.get(inputList.size()-7));
			scoresTextView4.setText(inputList.get(inputList.size()-6));
			scoresTextView5.setText(inputList.get(inputList.size()-5));
			scoresTextView6.setText(inputList.get(inputList.size()-4));
			scoresTextView7.setText(inputList.get(inputList.size()-3));
			scoresTextView8.setText(inputList.get(inputList.size()-2));
			scoresTextView9.setText(inputList.get(inputList.size()-1));
		}
		catch (Exception e) 
		{// catch all exceptions and log the error message
			Log.i("Data Input Sample", e.getMessage());
		}
	}

}
