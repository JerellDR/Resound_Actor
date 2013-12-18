package com.example.Resound_Actor_Studio.pdfutilities;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.*;

/**
 * User: mrgadgetz
 * Date: 12/17/13
 */
public class PdfToString {
    static SpannableString mPDFText;
    static TextView mText;
    static Context mContext;

    public PdfToString(SpannableString pdfString, TextView text, Context context) {
        this.mPDFText = pdfString;
        this.mText = text;
        this.mContext = context;

    }

    public static SpannableString fillTextView() {
        String path = "/mnt/sdcard/scriptsample.pdf";
        PdfReader reader = null;
        try {
            reader = new PdfReader(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int numberOfPages = reader.getNumberOfPages();
        final Context context = mContext;
        ClickableSpan clickablespan = new ClickableSpan(){

            @Override
            public void onClick(View view) {
                Toast.makeText(context, "dolor",
                        Toast.LENGTH_LONG).show();
            }
        };
        try {
            mPDFText = new SpannableString(PdfTextExtractor.getTextFromPage(reader, numberOfPages));
            mPDFText.setSpan(clickablespan, 50,100, 0);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return mPDFText;

    }

    public static void writeFileToSDCard(AssetManager mgr) {
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


            MediaScannerConnection.scanFile(mContext,
                    new String[]{file.toString()}, null,
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
