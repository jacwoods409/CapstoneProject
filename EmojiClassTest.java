package com.emojiapp;
import android.graphics.drawable.Drawable;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class EmojiClassTest {

    @Test
    public void newEmoji()
    {
        Emoji em = new Emoji(R.drawable.madem,"mad");

        assertEquals(em.getEmoji(),R.drawable.madem);
        assertEquals(em.getEmojiName(),"mad");

        assertThrows(NullPointerException.class, ()->new Emoji(R.drawable.coolem,null)); //emoji name can not be null
    }
}
