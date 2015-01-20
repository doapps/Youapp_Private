package me.doapps.youapps;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by mili on 08/01/2015.
 */
public class Push extends ActionBarActivity {
    private TextView txtPush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push);

        txtPush = (TextView) findViewById(R.id.txtPush);
        txtPush.setText(getIntent().getExtras().getString("data"));
    }
}
