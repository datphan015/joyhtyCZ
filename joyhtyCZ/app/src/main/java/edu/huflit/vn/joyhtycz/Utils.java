package edu.huflit.vn.joyhtycz;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class Utils {
    public static android.graphics.Bitmap convertToBitmapFromAssets(Context context, String imageName){
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("images/"+imageName);
            android.graphics.Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
