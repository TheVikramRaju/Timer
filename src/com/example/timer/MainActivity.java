package com.example.timer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button startButton;
	private Button pauseButton;
	private Button resetButton;

	private TextView timerValue;
	private Context mContext;
	private long startTime = 0L;

	private Handler customHandler = new Handler();

	int timeInMilliseconds = 0;
	int timeSwapBuff = 0;
	int updatedTime = 0;
	boolean isrunning = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;

		timerValue = (TextView) findViewById(R.id.timerValue);

		startButton = (Button) findViewById(R.id.startButton);

		startButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				isrunning = true;
				startTime = SystemClock.uptimeMillis();
				customHandler.postDelayed(updateTimerThread, 0);

			}
		});

		pauseButton = (Button) findViewById(R.id.pauseButton);

		pauseButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				if (isrunning) {
					isrunning = false;
					startButton.setText("Resume");
					timeSwapBuff += timeInMilliseconds;
					customHandler.removeCallbacks(updateTimerThread);
				}
				else{
					Toast.makeText(mContext, "Click Start/Resume before Pause..!",Toast.LENGTH_LONG).show();
				}
			}
		});

		resetButton = (Button) findViewById(R.id.resetButton);
		resetButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if(!isrunning){	
					startButton.setText("Start");
				timeInMilliseconds = 0;
				timeSwapBuff = 0;
				updatedTime = 0;
				timerValue.setText("00:00:00");
				}
				else{
					Toast.makeText(mContext, "Please Pause and Reset..!",Toast.LENGTH_LONG).show();
				}
			}
		});

	}

	private Runnable updateTimerThread = new Runnable() {

		public void run() {

			timeInMilliseconds = (int) (SystemClock.uptimeMillis() - startTime);

			updatedTime = timeSwapBuff + timeInMilliseconds;

			int secs = (int) (updatedTime / 1000);
			int mins = secs / 60;
			secs = secs % 60;
			int milliseconds = (int) (updatedTime % 1000);
			timerValue.setText("" + mins + ":" + String.format("%d", secs)
					+ ":" + String.format("%d", milliseconds));
			customHandler.postDelayed(this, 0);
		}

	};

}