package gr.ntua.eestec.thvaiou.loginregistration2;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by thomas on 3/10/14.
 */
public class Login extends Activity {

    private static final String TAG = "myLogs";
    EditText email;
    EditText password;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Log.d(TAG, "login activity");

        email = (EditText) findViewById(R.id.loginEmaileT);
        password = (EditText) findViewById(R.id.loginPasswordeT);
        btnlogin = (Button) findViewById(R.id.loginButton);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((!email.getText().toString().equals("")) && (!password.getText().toString().equals(""))) {
                    new DatabaseCheck().execute();
                } else if ((!email.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(), "Password field empty", Toast.LENGTH_SHORT).show();
                } else if ((!password.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(), "E-mail field empty", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "E-mail and password field empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private class DatabaseCheck extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:8080/login", "root", "");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select email from userss;");
                rs.next();
                while (rs.next()) {
                    if (rs.getString(1) == email.getText().toString()) {
                        return null;
                    }
                }
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
