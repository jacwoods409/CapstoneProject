package com.emojiapp;

import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.view.View;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class EmojiAppTest {
  @Rule
  public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class);
  public  MainActivity mainActivity = null;
  @Before
  public void setUp()
  {

    mainActivity= rule.getActivity();
  }



    @Test
    public void ValidateMainLayout() throws Exception {
    View view = mainActivity.findViewById(R.id.emoji);
    assertNotNull(view);

    ImageButton b = mainActivity.findViewById(R.id.button);
    assertNotNull(b);

    TextView tv = mainActivity.findViewById(R.id.name);
    assertNotNull(tv);

    FrameLayout backgrnd = mainActivity.findViewById(R.id.view2);
    }


    @Test
    public void TestLoadEmoji()throws Exception
    {
      LinkedList<Emoji> emos =mainActivity.loadEmojis();
      assertEquals(emos.size(),10);
    }

  @Test
  @UiThreadTest
  public void TestChangeEmoji()throws Exception  // change emojis returns changes the current emoji on the screen and returns it
  {
    LinkedList<Emoji> emos =mainActivity.loadEmojis();
     Emoji emo = mainActivity.changeEmojis(); // emojis were changed
    Emoji emo2 = mainActivity.changeEmojis();
    //test to see if emojis were changed
    assertNotEquals(emo.getEmojiName(),emo2.getEmojiName()); // if change emoji returns a different emoji then it was changed within the app

  }

/*    @Test  // can only work when tested on real device
  public void SensorTest() throws  InterruptedException {
      SensorListener sl=null;
      MainActivity m = new MainActivity();
     sl.onSensorChanged(SensorManager.SENSOR_ACCELEROMETER, new float[] {100, 100, 100} );  // Tests to make sure that the sensor has registered
      //Required because method only allows one shake per 100ms
      Thread.sleep(500);
      sl.onSensorChanged(SensorManager.SENSOR_ACCELEROMETER, new float[] {300, 300, 300});

    }*/
   // @Test
 /* public void  testDataBase() throws Exception {
      database db = new database(mainActivity);
      db.clearTable();

      //Test insert values

      db.insertItemDetails("Happy", String.valueOf(mainActivity.getDrawable(R.drawable.loveem)));
      Emoji em = db.GetItemByID("Happy");
      db.insertItemDetails("Sad", String.valueOf(mainActivity.getDrawable(R.drawable.sadem)));
      LinkedList<Emoji> list = db.GetItems();
      assertEquals(list.size(), 2); // 11 plus two added emojis

        //Test deleting items
      db.DeleteItem("Sad"); // removes both original and added sad emoji
      db.DeleteItem("Sad");
      list = db.GetItems();
      try{
      for(Emoji e : list)
      {
        if(e.getEmojiName().equalsIgnoreCase("sad"))
        {
          throw new Exception("DB Failed"); // throws error if not deleted

        }
      }}
      catch (Exception e)
      {
        System.out.println(e.getMessage());
      }
      //tests updating items
      db.UpdateItemName("updated", "Happy");
      assertNotNull(db.GetItemByID("updated"));

    }*/
    }


