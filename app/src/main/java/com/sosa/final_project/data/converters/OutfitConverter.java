package com.sosa.final_project.data.converters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

// credit to https://medium.com/@uttam.cooch/save-images-in-room-persistence-library-c71b60865b7e
// type converter that is used manually, and not passed to database
// used to change bitmaps to and from strings when interacting with outfits
public class OutfitConverter {
    @TypeConverter
    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    @TypeConverter
    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            //noinspection ResultOfMethodCallIgnored
            e.getMessage();
            return null;
        }
    }
}