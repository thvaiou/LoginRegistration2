package gr.ntua.eestec.thvaiou.loginregistration2;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import gr.ntua.eestec.thvaiou.loginregistration2.R;

/**
 * Created by thomas on 3/10/14.
 */
public class Register extends FragmentActivity {

    EditText firstname;
    EditText lastname;
    EditText username;
    EditText email;
    EditText password;
    EditText city;
    EditText idCard;
    EditText trn;
    EditText ssn;
    Button submit;
    boolean checked;
    String gender;
    public static final String TAG = "myLogs";
    EditText error;
    RadioGroup group;

    public final static String EXTRA_FIRSTNAME = "gr.ntua.eestec.thvaiou.loginregistration2.FIRSTNAME";
    public final static String EXTRA_LASTNAME = "gr.ntua.eestec.thvaiou.loginregistration2.LASTNAME";
    public final static String EXTRA_USERNAME = "gr.ntua.eestec.thvaiou.loginregistration2.USERNAME";
    public final static String EXTRA_EMAIL = "gr.ntua.eestec.thvaiou.loginregistration2.EMAIL";
    public final static String EXTRA_PASSWORD = "gr.ntua.eestec.thvaiou.loginregistration2.PASSWORD";
    public final static String EXTRA_CITY = "gr.ntua.eestec.thvaiou.loginregistration2.CITY";
    public final static String EXTRA_IDCARD = "gr.ntua.eestec.thvaiou.loginregistration2.IDCARD";
    public final static String EXTRA_TRN = "gr.ntua.eestec.thvaiou.loginregistration2.TRN";
    public final static String EXTRA_SSN = "gr.ntua.eestec.thvaiou.loginregistration2.SSN";
    public final static String EXTRA_GENDER = "gr.ntua.eestec.thavaiou.lgoinregistration2.GENDER";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        firstname = (EditText) findViewById(R.id.registerFirstname);
        lastname = (EditText) findViewById(R.id.registerLastname);
        username = (EditText) findViewById(R.id.registerUsername);
        password = (EditText) findViewById(R.id.registerPassword);
        email = (EditText) findViewById(R.id.registerEmail);
        city = (EditText) findViewById(R.id.registerCity);
        idCard = (EditText) findViewById(R.id.registerIDCard);
        trn = (EditText) findViewById(R.id.registerTRN);
        ssn = (EditText) findViewById(R.id.registerSSN);
        submit = (Button) findViewById(R.id.registerBtnSubmit);
        error = (EditText) findViewById(R.id.registerError);

        group = (RadioGroup) findViewById(R.id.radiogroup);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((!firstname.getText().toString().equals("")) && (!lastname.getText().toString().equals(""))
                        && (!username.getText().toString().equals("")) && (!password.getText().toString().equals(""))
                        && (!email.getText().toString().equals("")) && (!city.getText().toString().equals(""))
                        && (!idCard.getText().toString().equals("")) && (!trn.getText().toString().equals(""))
                        && (!ssn.getText().toString().equals("")) && (checked)) {
                    new DataCheck().execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Some fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void onRadioButtonClicked(View v) {
        checked = ((RadioButton) v).isChecked();

        switch (v.getId()) {
            case R.id.registerMale:
                if (checked) {
                    gender = "Male";
                }
                break;
            case R.id.registerFemale:
                if (checked) {
                    gender = "Female";
                }
                break;
        }
    }

    private class DataCheck extends AsyncTask {

        boolean exEmail = false;
        boolean exUsername = false;

        private ProgressDialog nDialog;

        protected void onPreExecute() {
            super .onPreExecute();
            nDialog = new ProgressDialog(Register.this);
            nDialog.setTitle("Checking database");
            nDialog.setMessage("Checking...");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
            Log.d(TAG, "Email: " + email.getText().toString());
            Log.d(TAG, "Username: " + username.getText().toString());
            Log.d(TAG, "exEmail: " + Boolean.toString(exEmail));
            Log.d(TAG, "exUsername: " + Boolean.toString(exUsername));
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                Log.d(TAG, "Database");
                Class.forName("com.mysql.jdbc.Driver");
                Log.d(TAG, "Database 1");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:8080/login", "root", "");
                Log.d(TAG, "Database 2");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT username, email FROM userss;");
                Log.d(TAG, "I have the result set");
                while (rs.next()) {
                    Log.d(TAG, "Database email: " + rs.getString("email"));
                    if (rs.getString("email").equals(email.getText().toString())) {
                        exEmail = true;
                        Log.d(TAG, "E-mail exists");
                    }
                    Log.d(TAG, "Database username: " + rs.getString("username"));
                    if (rs.getString("username").equals(username.getText().toString())) {
                        exUsername = true;
                        Log.d(TAG, "Username exists");
                    }
                }
                con.close();
            } catch (Exception e) {
                Log.d(TAG, e.toString());
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Object th) {
            if (exEmail==true && exUsername==true) {
                nDialog.dismiss();
                error.setText("The email and the username already exist. Please choose different ones.");
                clearData();
                exEmail=false;
                exUsername=false;

            } else if (exEmail==true) {
                nDialog.dismiss();
                error.setText("The email already exists. Please choose different one.");
                clearData();
                exEmail = false;

            } else if (exUsername==true) {
                nDialog.dismiss();
                error.setText("The username already exists. Please choose different one.");
                clearData();
                exUsername = false;

            } else {
                nDialog.dismiss();
                new DataEntry().execute();
            }

        }
    }

    private class DataEntry extends AsyncTask {

        private ProgressDialog nDialog;

        protected void onPreExecute() {
            super .onPreExecute();
            nDialog = new ProgressDialog(Register.this);
            nDialog.setTitle("Loading database");
            nDialog.setMessage("Loading...");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                Log.d(TAG, "Database");
                Class.forName("com.mysql.jdbc.Driver");
                Log.d(TAG, "Database 1");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:8080/login", "root", "");
                Log.d(TAG, "Database 2");
                Statement stmt = con.createStatement();
                Log.d(TAG, "Before execute");
                Log.d(TAG, gender);
                stmt.executeUpdate("Insert into userss(firstname, lastname, username, email, password, gender, city, adt, afm, amka)" +
                        " values ('" + firstname.getText().toString() + "','" + lastname.getText().toString() + "','" + username.getText().toString() + "','" + email.getText().toString() + "','" + password.getText().toString() + "','" +
                        gender + "','" + city.getText().toString() + "','" + idCard.getText().toString() + "'," + trn.getText().toString() + ",'" + ssn.getText().toString() + "');");
                Log.d(TAG, "After execute");
                con.close();
                Log.d(TAG, "Contact saved");
                return true;
            } catch (Exception e) {
                Log.d(TAG, e.toString());
                e.printStackTrace();
                return false;
            }
        }

        protected void onPostExecute(Object th) {
            boolean t = (Boolean) th;
            if (t==true) {
                nDialog.dismiss();
                Intent myIntent = new Intent(Register.this, RegisterCompleted.class);
                myIntent.putExtra(EXTRA_FIRSTNAME, firstname.getText().toString());
                myIntent.putExtra(EXTRA_LASTNAME, lastname.getText().toString());
                myIntent.putExtra(EXTRA_USERNAME, username.getText().toString());
                myIntent.putExtra(EXTRA_PASSWORD, password.getText().toString());
                myIntent.putExtra(EXTRA_EMAIL, email.getText().toString());
                myIntent.putExtra(EXTRA_CITY, city.getText().toString());
                myIntent.putExtra(EXTRA_GENDER, gender);
                myIntent.putExtra(EXTRA_SSN, ssn.getText().toString());
                myIntent.putExtra(EXTRA_TRN, trn.getText().toString());
                myIntent.putExtra(EXTRA_IDCARD, idCard.getText().toString());
                startActivity(myIntent);
                finish();

            } else {
                nDialog.dismiss();
                error.setText("Error in database connection");

            }
        }
    }

    public void clearData() {
        error.setText("The email and the username already exist. Please choose different ones.");
        firstname.setHint("firstname");
        lastname.setHint("lastname");
        username.setHint("username");
        password.setHint("password");
        email.setHint("e-mail");
        city.setHint("city");
        idCard.setHint("id card");
        trn.setHint("tax registration number");
        ssn.setHint("social security number");
        group.clearCheck();
        error.setText("");
    }
}

