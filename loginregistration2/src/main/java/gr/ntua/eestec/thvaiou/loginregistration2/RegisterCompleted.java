package gr.ntua.eestec.thvaiou.loginregistration2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by thomas on 12/3/2014.
 */
public class RegisterCompleted extends Activity {

    TextView firstname;
    TextView lastname;
    TextView username;
    TextView password;
    TextView email;
    TextView gender;
    TextView city;
    TextView IDCard;
    TextView trn;
    TextView ssn;
    Button btn;

    String fn;
    String ln;
    String un;
    String ps;
    String em;
    String gn;
    String cy;
    String id;
    String tn;
    String sn;

    public final static String EXTRA_MESSAGE = "gr.ntua.eestec.thvaiou.loginregistration2.MESSAGE";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registercompleted);

        firstname = (TextView) findViewById(R.id.registerCFirstname);
        lastname = (TextView) findViewById(R.id.registerCLastname);
        username = (TextView) findViewById(R.id.registerCUsername);
        password = (TextView) findViewById(R.id.registerCPassword);
        email = (TextView) findViewById(R.id.registerCEmail);
        gender = (TextView) findViewById(R.id.registerCGender);
        city = (TextView) findViewById(R.id.registerCCity);
        IDCard = (TextView) findViewById(R.id.registerCIDCard);
        trn = (TextView) findViewById(R.id.registerCTRN);
        ssn = (TextView) findViewById(R.id.registerCSSN);
        btn = (Button) findViewById(R.id.registerCButton);

        Intent intent = getIntent();
        fn = intent.getStringExtra(Register.EXTRA_FIRSTNAME);
        ln = intent.getStringExtra(Register.EXTRA_LASTNAME);
        un = intent.getStringExtra(Register.EXTRA_USERNAME);
        ps = intent.getStringExtra(Register.EXTRA_PASSWORD);
        em = intent.getStringExtra(Register.EXTRA_EMAIL);
        gn = intent.getStringExtra(Register.EXTRA_GENDER);
        cy = intent.getStringExtra(Register.EXTRA_CITY);
        id = intent.getStringExtra(Register.EXTRA_IDCARD);
        tn = intent.getStringExtra(Register.EXTRA_TRN);
        sn = intent.getStringExtra(Register.EXTRA_SSN);

        firstname.setText(fn);
        lastname.setText(ln);
        username.setText(un);
        password.setText(ps);
        email.setText(em);
        gender.setText(gn);
        city.setText(cy);
        IDCard.setText(id);
        trn.setText(tn);
        ssn.setText(sn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), MainApp.class);
                myIntent.putExtra(EXTRA_MESSAGE, un);
                startActivity(myIntent);
                finish();
            }
        });
    }
}
