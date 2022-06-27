package com.emojiapp;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emojiapp.Emoji;
import com.emojiapp.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.LinkedList;

import static com.emojiapp.R.drawable.*;

public class MainActivity extends Activity implements SensorEventListener, Serializable {
    private SensorManager sensorManager;
    private View view;
    private FloatingActionButton add;
    private long lastUpdate;
    private ImageButton emojiButton;
    private ImageView currentEmoji;
    private TextView emojiNameView;
    private LinkedList<Emoji> emojiList = new LinkedList();
    public int index = 0;
    private int code =0;


    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(1);
        this.getWindow().setFlags(1024, 1024);
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.view = this.findViewById(R.id.view2);
        this.emojiNameView = (TextView)this.findViewById(R.id.name);
        this.sensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        this.lastUpdate = System.currentTimeMillis();
        this.currentEmoji = (ImageView) this.findViewById(R.id.emoji);
        try {
            currentEmoji.setImageResource(coolem);
            currentEmoji.setVisibility(View.VISIBLE);
            emojiList.addAll(loadEmojis());
                Bundle b= getIntent().getExtras();
                Uri uri = getIntent().getData();
                if(b!=null&& uri!=null) {

                    Emoji emo = new Emoji(0,b.getString("EmojiName"));
                    emo.setUri(uri);
                    System.out.println(emo.getEmojiName());
                    emojiList.add(emo);
                }
        } catch (Exception e) {
            Toast.makeText(this,"Load Error"+e.getMessage(),Toast.LENGTH_SHORT);
        }






        this.add = (FloatingActionButton)this.findViewById(R.id.floatingActionButton2);
        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, addEmoji.class);

                startActivity(i);
            }
        });
        this.emojiButton = (ImageButton)this.findViewById(R.id.button);
        this.emojiButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               changeEmojis();
            }
        });
    }
    public LinkedList<Emoji> loadEmojis()throws Exception {
        LinkedList<Emoji> emos = new LinkedList();


        emos.add(new Emoji(happyem, "HAPPY!"));
        emos.add(new Emoji(sadem, "Sad!"));
        emos.add(new Emoji(coolem, "Cool Dude!"));
        emos.add(new Emoji(greeedyem, "Greedy!"));
        emos.add(new Emoji(shocked, "Shocked!"));
        emos.add(new Emoji(hugem, "AWE!"));
        emos.add(new Emoji(cowboyem, "Cowboy Emoji!"));
        emos.add(new Emoji(loveem, "Feel The Love!"));
        emos.add(new Emoji(madem, "Grrrr!"));
        emos.add(new Emoji(thanksem, "Thanks for using our app!"));
        for(Emoji em:emos) // for testing
        {
            System.out.println(em.getEmojiName());
            System.out.println();
        }
        return emos;


    }





    public Emoji changeEmojis() {
            if(emojiList.size()>0) {  //to avoid null pointer
                if(emojiList.get(index).emoji==0) // indicates uploaded emoji
                {
                    this.currentEmoji.setImageURI(emojiList.get(index).getUri());
                    this.emojiNameView.setText(((Emoji) this.emojiList.get(this.index)).getEmojiName());
                    this.emojiNameView.setVisibility(View.VISIBLE);
                    System.out.println(this.emojiList.get(this.index).getEmojiName());
                    ++this.index;
                    if (this.index >= this.emojiList.size()) {
                        this.index = 0;
                    }
                }
                Uri imgUri=Uri.parse("android.resource://com.emojiapp/"+emojiList.get(index).getEmoji());

                this.currentEmoji.setImageURI(imgUri);
                this.emojiNameView.setText(((Emoji) this.emojiList.get(this.index)).getEmojiName());
                this.emojiNameView.setVisibility(View.VISIBLE);
                ++this.index;
                if (this.index >= this.emojiList.size()) {
                    this.index = 0;
                }
                return emojiList.get(this.index);
            }
            else {
                Emoji test = new Emoji(madem, "mad");
               return test;
        }}

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == 1) {
            this.getAccelerometer(event);
        }

    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];
        float accelationSquareRoot = (x * x + y * y + z * z) / 96.17039F;
        long actualTime = System.currentTimeMillis();
        int testValue = 50;
        int normalValue = 10;
        if (accelationSquareRoot >= (float)testValue) {
            if (actualTime - this.lastUpdate < 200L) {
                return;
            }

            this.lastUpdate = actualTime;
            this.changeEmojis();
        }

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    protected void onResume() {
        super.onResume();
        this.sensorManager.registerListener(this, this.sensorManager.getDefaultSensor(1), 3);
    }

    protected void onPause() {
        super.onPause();
        this.sensorManager.unregisterListener(this);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.sensorManager.unregisterListener(this);
    }
}