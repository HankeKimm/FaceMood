package com.socialtracking.ubiss;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aware.ESM;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_PAM;

import org.json.JSONException;

public class PAMActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pam);

        ESMFactory factory = new ESMFactory();

        try {
            ESM_PAM q1 = new ESM_PAM();
            q1.setTitle("PAM")
                    .setInstructions("Pick the closest to how you feel right now.")
                    .setSubmitButton("OK")
                    .setNotificationTimeout(10)
                    .setTrigger("AWARE Test");

            factory.addESM(q1);

            ESM.queueESM(this, factory.build());

        } catch (JSONException e) {
            e.printStackTrace();
        }    }

    private void createPAM(){

    }
}
