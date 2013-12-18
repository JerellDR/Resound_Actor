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
import com.example.Resound_Actor_Studio.pdfutilities.PdfToString;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.sun.org.apache.xpath.internal.compiler.Keywords;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyActivity extends Activity {

    TextView mText;
    SpannableString mPDFText;
    AssetManager mMgr;
    private static Matcher mMatcher;
    PdfToString converter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        mText = (TextView)findViewById(R.id.text);
        mMgr = getResources().getAssets();
        converter = new PdfToString(mPDFText, mText, this);
        PdfToString.writeFileToSDCard(mMgr);
        mText.setMovementMethod(new ScrollingMovementMethod());
        mText.setText(PdfToString.fillTextView(), TextView.BufferType.SPANNABLE);

    }


}
