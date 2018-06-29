package com.socialtracking.ubiss;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aware.ESM;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_PAM;
import com.aware.ui.esms.ESM_Radio;
import com.aware.ui.esms.ESM_Web;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SurveysFragment extends android.support.v4.app.Fragment {

    public SurveysFragment() {
        // Required empty public constructor
    }


//    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_surveys, container, false);

        setUpPHQ8Button(rootview, R.id.phq8);
        setUpPHQ4Button(rootview, R.id.phq4);
        setUpPanasButton(rootview, R.id.panas);
        setUpBig5Button(rootview, R.id.big5);
//        setUpPamButton(rootview, R.id.pam);

        return rootview;
    }

    private void setUpPHQ8Button(View rootview, int buttonId) {
        Button pam = rootview.findViewById(buttonId);
        pam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPHQ8();

            }
        });

    }

    private void createPHQ8(){

        ESMFactory factory_panas = new ESMFactory();

        try {
            List<String> questions = Arrays.asList("Little interest or pleasure in doing things.", "Feeling down, depressed, or hopeless.",
                    "Trouble falling or staying asleep, or sleeping too much.", "Feeling tired or having little energy.",
                    "Poor appetite or overeating.", "Feeling bad about yourself, or that you are a failure, or have let yourself or your family down.",
                    "Trouble concentrating on things, such as reading the newspaper or watching television.",
                    "Moving or speaking so slowly that other people could have noticed.");

            for (String s: questions) {
                ESM_Radio q = new ESM_Radio();
                q.setInstructions(s);
                q.setTitle("Over the last week, how often have you been bothered by the following problems?");
                q.addRadio("Nearly every day")
                        .addRadio("More than half the days")
                        .addRadio("Several days")
                        .addRadio("Not at all")
                        .setSubmitButton("Next")
                        .setExpirationThreshold(60 * 60);
                factory_panas.addESM(q);
            }

            ESM.queueESM(getActivity().getApplicationContext(), factory_panas.build());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setUpPHQ4Button(View rootview, int buttonId) {
        Button pam = rootview.findViewById(buttonId);
        pam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPHQ4();

            }
        });

    }

    private void createPHQ4(){

        ESMFactory factory_panas = new ESMFactory();

        try {
            List<String> questions = Arrays.asList("Feeling nervous, anxious or on edge", "Not being able to stop or control worrying",
                    "Little interest or pleasure in doing things", "Feeling down, depressed, or hopeless");

            for (String s: questions) {
                ESM_Radio q = new ESM_Radio();
                q.setInstructions(s);
                q.setTitle("Over the last week, how often have you been bothered by the following problems?");
                q.addRadio("Nearly every day")
                    .addRadio("More than half the days")
                    .addRadio("Several days")
                    .addRadio("Not at all")
                    .setSubmitButton("Next")
                    .setExpirationThreshold(60 * 60);
                factory_panas.addESM(q);
            }

            ESM.queueESM(getActivity().getApplicationContext(), factory_panas.build());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setUpBig5Button(View rootview, int buttonId) {
        Button pam = rootview.findViewById(buttonId);
        pam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBig5();

            }
        });

    }


    private void createBig5(){

    }
    private void setUpPamButton(View rootview, int buttonId) {
        Button pam = rootview.findViewById(buttonId);
        pam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPAM();

            }
        });

    }

    private void createPAM() {
        ESMFactory factory_pam = new ESMFactory();

        try {
            ESM_PAM q1 = new ESM_PAM();
            q1.setTitle("Photographic Affect Meter")
                .setInstructions("Pick the closest to how you feel right now.")
                .setSubmitButton("OK");

            factory_pam.addESM(q1);

            ESM.queueESM(getActivity().getApplicationContext(), factory_pam.build());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void setUpPanasButton(View rootview, int buttonId) {
        Button panas = rootview.findViewById(buttonId);
        panas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPANAS();

            }
        });

    }

    private void createPANAS(){
        ESMFactory factory_panas = new ESMFactory();

        try {

            List<String> questions = Arrays.asList("Interested", "Distressed", "Excited", "Upset", "Strong", "Guilty", "Scared", "Hostile",
                    "Enthusiastic", "Proud","Irritable", "Alert","Ashamed", "Inspired","Nervous", "Determined", "Attentive", "Jittery", "Active", "Afraid");

            for (String s: questions) {
                ESM_Radio q = new ESM_Radio();
                q.setInstructions(s);
                q.setTitle("Positive and Negative Affect Schedule");
                q.addRadio("Extremely")
                        .addRadio("Quite a bit")
                        .addRadio("Moderately")
                        .addRadio("A little")
                        .addRadio("Very slightly or not at all")
                        .setSubmitButton("Next")
                        .setExpirationThreshold(60 * 60);
                factory_panas.addESM(q);
            }

            ESM.queueESM(getActivity().getApplicationContext(), factory_panas.build());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



}
