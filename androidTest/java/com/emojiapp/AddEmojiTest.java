package com.emojiapp;

import android.app.Instrumentation;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class AddEmojiTest {
    @Rule
    public ActivityTestRule<addEmoji> rule = new ActivityTestRule<addEmoji>(addEmoji.class);
    public  addEmoji mainActivity = null;
    @Before
    public void setUp()
    {

        mainActivity= rule.getActivity();
    }


    @Test  // validates layout
    public void ValidateAddLayout() throws Exception {
       ImageView view = mainActivity.findViewById(R.id.preview);
        assertNotNull(view);

        FloatingActionButton fb = mainActivity.findViewById(R.id.floatingActionButton);
        assertNotNull(fb);

       EditText et = (EditText)mainActivity.findViewById(R.id.EnterEmojiName);
        assertNotNull(et);

        Button b = mainActivity.findViewById(R.id.button2);
        assertNotNull(b);


        FrameLayout backgrnd = mainActivity.findViewById(R.id.view2);
    }

    @Test  //makes sure the text box displays the correct message
            public void testTextBox()
    {
        onView(withId(R.id.EnterEmojiName))
                .perform(typeText("Happy"), closeSoftKeyboard());
        onView(withId(R.id.EnterEmojiName))
                .check(matches(withText("Happy")));
    }
   /* @UiThreadTest
    @Test
    public void testImageButton()
    {
        // this test can only work when file path is prearranged and testing on a real device
        // delete comment when ready to test on real device
        onView(withId(R.id.EnterEmojiName))
                .perform(typeText("Happy"), closeSoftKeyboard());
        String s = "C:\\Users\\xoxki\\OneDrive\\Pictures\\Camera Roll\\12322373_446263872226862_6725310269428101885_o.jpg";
        Drawable d = Drawable.createFromPath(s);
        database db = new database(mainActivity);
        ImageView prev = mainActivity.findViewById(R.id.preview);
        db.insertItemDetails("Happy",s);
        prev.setImageURI(Uri.parse(s));

    }*/
    @Test //makes sure that the add emoji button us
    public void addEmojiButtonTest()
    {
        mainActivity.findViewById(R.id.button2).isEnabled();
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
                .addMonitor(MainActivity.class.getName(), null, false);
       onView(withId(R.id.button2)).perform(click());
        MainActivity targetActivity = (MainActivity) activityMonitor.waitForActivity();
        assertNotNull("Target Activity is not launched", targetActivity);
    }
    @After
    public void onClose()
    {
        rule.finishActivity();
    }
    }


