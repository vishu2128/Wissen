package com.quiz.mnnit.wissenjson;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int score = 0 ,qid = 0;
    String ur;
    String[] optarr = new String[4];

    List<Question> quesList;
    Question curr;

    TextView tmr,scr,ques;
    ToggleButton opta,optb,optc,optd;
    Button sub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tmr = (TextView)findViewById(R.id.tmr);
        scr = (TextView)findViewById(R.id.scr);
        ques = (TextView)findViewById(R.id.ques);

        opta = (ToggleButton)findViewById(R.id.opta);
        optb = (ToggleButton)findViewById(R.id.optb);
        optc = (ToggleButton)findViewById(R.id.optc);
        optd = (ToggleButton)findViewById(R.id.optd);

        sub = (Button)findViewById(R.id.sub);

        sub.setText("SKIP");
        String ct = getIntent().getStringExtra("ct");
        quesList = new ArrayList<Question>();
        ur="https://www.opentdb.com/api.php?amount=10&category="+ct+"&type=multiple";
        new QuestionAsynTask().execute(ur);

        opta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //Log.d("CHECKING",isChecked+"");

                if(opta.isChecked())
                {
                    optb.setChecked(false);
                    optc.setChecked(false);
                    optd.setChecked(false);
                }
                TogglePress();
            }
        });

        optb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(optb.isChecked())
                {
                    opta.setChecked(false);
                    optc.setChecked(false);
                    optd.setChecked(false);
                }
                TogglePress();

            }
        });

        optc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(optc.isChecked())
                {
                    opta.setChecked(false);
                    optb.setChecked(false);
                    optd.setChecked(false);
                }
                TogglePress();

            }
        });

        optd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(optd.isChecked())
                {
                    opta.setChecked(false);
                    optb.setChecked(false);
                    optc.setChecked(false);
                }
                TogglePress();

            }
        });


        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                opta.setClickable(false);
                optb.setClickable(false);
                optc.setClickable(false);
                optd.setClickable(false);

                if(sub.getText().equals("SUBMIT"))
                {
                    String selected = "";
                    if(opta.isChecked())
                        selected = (String)opta.getText();
                    if(optb.isChecked())
                        selected = (String)optb.getText();
                    if(optc.isChecked())
                        selected = (String)optc.getText();
                    if(optd.isChecked())
                        selected = (String)optd.getText();

                    if(selected.equals(curr.getANSWER()))
                        score+=10;
                    else
                        score-=5;
                }

                qid++;
                setView(qid);

            }
        });


    }

    public void TogglePress()
    {
        if(opta.isChecked() || optb.isChecked() || optc.isChecked() || optd.isChecked())
            sub.setText("SUBMIT");
        else
            sub.setText("SKIP");
    }

    public void setView(int q_id)
    {
        if(q_id == 10)
        {
            Intent i = new Intent(MainActivity.this, Result.class);
            //i.putExtra("scr",score);
            startActivity(i);
        }

        else
        {

           // Log.d("HI",q_id+"");
            curr = quesList.get(q_id);
           // Log.d("HI","TESTING MAIN ACTIVITY2");
            ques.setText(curr.getQUESTION());
            scr.setText(score+"");

            Random rand = new Random();
            int i = rand.nextInt(10000) % 4;

            optarr[i%4] = curr.getOPTA();
            optarr[(i+1)%4] = curr.getOPTB();
            optarr[(i+2)%4] = curr.getOPTC();
            optarr[(i+3)%4] = curr.getOPTD();

            opta.setTextOn(optarr[0]);
            opta.setTextOff(optarr[0]);

            optb.setTextOn(optarr[1]);
            optb.setTextOff(optarr[1]);

            optc.setTextOn(optarr[2]);
            optc.setTextOff(optarr[2]);

            optd.setTextOn(optarr[3]);
            optd.setTextOff(optarr[3]);

            opta.setClickable(true);
            optb.setClickable(true);
            optc.setClickable(true);
            optd.setClickable(true);

            opta.setChecked(false);
            optb.setChecked(false);
            optc.setChecked(false);
            optd.setChecked(false);

        }
    }


    public class QuestionAsynTask extends AsyncTask<String, Void,Boolean>
    {

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                //Log.d("HI","TESTING1");
                URL url=new URL(params[0]);
                //Log.d("HI","TESTING2");
                HttpURLConnection con=(HttpURLConnection) url.openConnection();
                //Log.d("HI","TESTING3");
                int status=con.getResponseCode();


                if(status == HttpURLConnection.HTTP_OK){
                    InputStream is = con.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String data = "", line;

                    while((line = br.readLine())!=null)
                    {
                        data+=line;
                    }

                    //Log.d("abc",data+"");

                    JSONObject jobj = new JSONObject(data);
                    JSONArray jarray = jobj.getJSONArray("results");

                    for(int i=0; i < jarray.length(); i++)
                    {
                        JSONObject obj = jarray.getJSONObject(i);

                        Question q = new Question();

                        q.setQUESTION(obj.getString("question"));
                        q.setANSWER(obj.getString("correct_answer"));
                        q.setOPTD(q.getANSWER());

                        JSONArray jarray1 = obj.getJSONArray("incorrect_answers");
                        q.setOPTA(jarray1.getString(0));
                        q.setOPTB(jarray1.getString(1));
                        q.setOPTC(jarray1.getString(2));

                        quesList.add(q);
                    }

                    return true;
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                //Log.d("HI","EXCEPTION THROWN");
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result==false)
            {
                Toast.makeText(MainActivity.this,"False",Toast.LENGTH_SHORT).show();
            }
            else
            {
                new CountDownTimer(300000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        long sec,min;
                        min = (millisUntilFinished/1000)/60;
                        sec = (millisUntilFinished/1000)%60;
                        tmr.setText(min+":"+sec);
                    }

                    public void onFinish() {
                        Intent i = new Intent(MainActivity.this, Result.class);
                        //i.putExtra("scr",score);
                        startActivity(i);
                    }
                }.start();
                setView(0);
            }
        }
    }
}