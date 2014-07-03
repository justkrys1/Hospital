package com.example.helloAndroid;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.Switch;
import android.widget.TextView;

import java.util.regex.Pattern;

public class About extends MyActivity{
    private TextView mTextViewSupport, mTextViewCompany, mTextViewTeam,textView, textView2, textView3, textView4, textViewSupport, textView6, textView7, textViewCompany, textView9;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        initializeApp();
    }

    private void initializeApp() {
        mTextViewSupport = (TextView) findViewById(R.id.textViewsupport);
        String support = "Contact me phpproduction.justkrys1.com/contactus.php";
        mTextViewSupport.setText(support);
        Pattern supportPattern = Pattern.compile("phpproduction.justkrys1.com/contactus.php");
        Linkify.addLinks(mTextViewSupport, supportPattern, "http://");
        mTextViewSupport.setMovementMethod(LinkMovementMethod.getInstance());
        mTextViewSupport.setText(Html.fromHtml(support));


        mTextViewCompany = (TextView) findViewById(R.id.textViewCompany);
        String company = "View samples of my work mobiledev.justkrys1.com";
        mTextViewCompany.setText(company);
        Pattern companyPattern = Pattern.compile("mobiledev.justkrys1.com");
        Linkify.addLinks(mTextViewCompany, companyPattern, "http://");
        mTextViewCompany.setMovementMethod(LinkMovementMethod.getInstance());
        mTextViewCompany.setText(Html.fromHtml(company));

        mTextViewTeam = (TextView) findViewById(R.id.textViewTeam);
        String team = "Test out my team's work codespork.com";
        mTextViewTeam.setText(team);
        Pattern teamPattern = Pattern.compile("codespork.com");
        Linkify.addLinks(mTextViewTeam, teamPattern, "http://");
        mTextViewTeam.setMovementMethod(LinkMovementMethod.getInstance());
        mTextViewTeam.setText(Html.fromHtml(team));
    }
}
