package com.init.team.streams;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Message;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;


public class Streams extends AppCompatActivity implements View.OnClickListener{

    ProgressBar progressBar;
    Handler handler;
    ImageView imageViewOne , imageViewTwo , imageViewThree;
    TextView textView;
    Button buttonHand , buttonAsync;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streams);

        buttonHand = (Button) findViewById(R.id.button);
        buttonAsync = (Button) findViewById(R.id.button2);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imageViewOne = (ImageView) findViewById(R.id.imageView2);
        imageViewTwo = (ImageView) findViewById(R.id.imageView3);
        imageViewThree = (ImageView) findViewById(R.id.imageView4);

        buttonHand.setOnClickListener(this);
        buttonAsync.setOnClickListener(this);

        progressBar.setVisibility(View.GONE);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setMax(3);
                handler = new Handler(){
                    @Override
                    public void handleMessage(Message message){
                        switch(message.what){
                            case 1:
                                imageViewOne.setImageResource(R.drawable.firsim);
                                progressBar.setProgress(1);
                                break;
                            case 2:
                                imageViewTwo.setImageResource(R.drawable.twoimage);
                                progressBar.setProgress(2);
                                break;
                            case 3:
                                imageViewThree.setImageResource(R.drawable.threeimage);
                                progressBar.setProgress(3);
                                break;
                        }
                    }
                };
                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i =1 ; i <= 3 ; i++ ){
                            handler.sendEmptyMessage(i);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
                thread.start();
                break;
            case R.id.button2:
                MyAsynctask myAsynctask = new MyAsynctask(this);
                myAsynctask.execute();
                break;
            }
        }

    private class MyAsynctask extends AsyncTask< Void , Integer ,Void > {
        private Context context;

        public MyAsynctask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context , "Start Asunctask" ,Toast.LENGTH_SHORT).show();
        }



        @Override
        protected Void doInBackground(Void... voids) {
            for(int i = 1 ; i <= 3 ; i++){
                publishProgress(i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Toast.makeText(context , "Start " ,Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(3);
            switch (values[0]){
                case 1:
                    imageViewOne.setImageResource(R.drawable.threeimage);
                    progressBar.setProgress(1);
                    break;
                case 2:
                    progressBar.setProgress(2);
                    imageViewTwo.setImageResource(R.drawable.twoimage);
                    break;
                case 3:
                    progressBar.setProgress(3);
                    imageViewThree.setImageResource(R.drawable.firsim);
                    break;
            }
        }

    }
}



