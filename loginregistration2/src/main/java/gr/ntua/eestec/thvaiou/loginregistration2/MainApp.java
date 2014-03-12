package gr.ntua.eestec.thvaiou.loginregistration2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by thomas on 12/3/2014.
 */
public class MainApp extends Activity {

    TextView user;
    Button btn;
    String username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_app);

        user = (TextView) findViewById(R.id.mainappUser);
        btn = (Button) findViewById(R.id.mainappButton);

        Intent intent = getIntent();
        username = intent.getStringExtra(RegisterCompleted.EXTRA_MESSAGE);
        user.setText(username);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainApp.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

    }
}
