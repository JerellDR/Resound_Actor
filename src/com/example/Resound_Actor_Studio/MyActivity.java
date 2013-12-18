package com.example.Resound_Actor_Studio;

import android.app.Activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.sun.org.apache.xpath.internal.compiler.Keywords;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    TextView mText;
    SpannableString mPDFText;
    private static Matcher mMatcher;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mText = (TextView)findViewById(R.id.text);
        writeFileToSDCard();
        fillTextView();
        mText.setText(mPDFText, TextView.BufferType.SPANNABLE);


    }

    public void fillTextView() {
        String path = "/mnt/sdcard/scriptsample.pdf";
        PdfReader reader = null;
        try {
            reader = new PdfReader(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int numberOfPages = reader.getNumberOfPages();
        final Context context = this;
        ClickableSpan clickablespan = new ClickableSpan(){

            @Override
            public void onClick(View view) {
                Toast.makeText(context,"dolor",
                        Toast.LENGTH_LONG).show();
            }
        };
            try {
                mPDFText = new SpannableString(PdfTextExtractor.getTextFromPage(reader, numberOfPages));
                mPDFText.setSpan(clickablespan, 50,100, 0);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        mText.setMovementMethod(new ScrollingMovementMethod());

        mText.setText(mPDFText);
    }

    public void writeFileToSDCard() {
        AssetManager mgr = getResources().getAssets();
        String[] files = null;
        InputStream is = null;
        OutputStream os = null;

        try {
            files = mgr.list("");
        } catch (IOException e) {
             Log.e("tag", "Failed to get asset file list.", e);
        }

        String out = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(out, "scriptsample.pdf");
        Log.i("output path ", out);

        try {
            is = mgr.open("scriptsample.pdf");
            os = new FileOutputStream(out + file);
            byte[] data = new byte[is.available()];
            is.read(data);
            os.write(data);
            is.close();
            os.close();


        MediaScannerConnection.scanFile(this,
                new String[] {file.toString() }, null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> Uri=" + uri);
                    }
                });
        }  catch (IOException e) {
            Log.w("ExernalStorage", "Error writing " + file, e);
        }
    }


}
