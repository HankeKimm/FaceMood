package com.socialtracking.ubiss.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.socialtracking.ubiss.LocalDataStorage.DatabaseHelper;
import com.socialtracking.ubiss.MainActivity;
import com.socialtracking.ubiss.R;
import com.socialtracking.ubiss.User.User;
import com.socialtracking.ubiss.User.UserData;
import com.socialtracking.ubiss.User.UsersContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    private static final String TAG = "RegisterFragment";

    DatabaseHelper dbHelper;

    private EditText usernameEditText;
    private EditText emailAdress;
    private EditText ageOptions;
    private RadioGroup genderOptions;

    private Button submitRegistrationFormButton;
    private Button cancelRegistrationFormButton;

    public String username;
    public String email;
    public String age;


    private String genderSelection = "";

    private String android_id;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_register, container, false);

        dbHelper = new DatabaseHelper(getContext());
        android_id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        usernameEditText = (EditText) rootview.findViewById(R.id.username_value);
        emailAdress = (EditText) rootview.findViewById(R.id.email);

        ageOptions = (EditText) rootview.findViewById(R.id.age_range);
        genderOptions = (RadioGroup) rootview.findViewById(R.id.genderRadioButtons);

        setUpGenderRadioButtons();


        submitRegistrationFormButton = (Button)rootview.findViewById(R.id.submit_button);
        submitRegistrationFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                email = emailAdress.getText().toString();
                age = ageOptions.getText().toString();


                Log.v("RegistrationActivity", username);

                if(genderSelection.equals("")){
                    Toast.makeText(getContext(), "Please select your Gender!", Toast.LENGTH_LONG).show();
                    return;
                }



                User user = new User();
                user._android_id = android_id;
                user._username = username;
                user._emailAdress = email;
                user._age = age;
                user._gender = genderSelection;

                Log.v(TAG, user._username);
                UserData._username = user._username;

                dbHelper.addUser(user);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("New User Account");
                alertDialog.setMessage("You have successfully created account with: \n \n" + "Username: " + username +  "\nEmail: " + email +"\nAge: " + age +
                        "\nGender: " + genderSelection + "\n \n \n Do you want to proceed?");

                alertDialog.setIcon(R.drawable.account);

                alertDialog.setNegativeButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), "Thank you!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent (getContext(), MainActivity.class);
                                startActivity(i);
                                getActivity().finish();
                            }
                        });

                alertDialog.setPositiveButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();

            }
        });

        cancelRegistrationFormButton = (Button)rootview.findViewById(R.id.cancel_button);
        cancelRegistrationFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return rootview;
    }

    /*
   *   Gender Radio Buttons
    */
    private void setUpGenderRadioButtons(){

        genderOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radiobutton_female:
                        genderSelection = UsersContract.UserEntry.GENDER_FEMALE;
                        break;
                    case R.id.radiobutton_male:
                        genderSelection = UsersContract.UserEntry.GENDER_MALE;
                        break;
                    default:
                        genderSelection = "";
                }
            }
        });
    }


}
