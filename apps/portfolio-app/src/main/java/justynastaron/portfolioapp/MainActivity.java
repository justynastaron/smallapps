package justynastaron.portfolioapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private int[][] refTable = new int[][]{{R.id.first_button, R.string.first_app},
            {R.id.second_button, R.string.second_app}, {R.id.third_button, R.string.third_app},
            {R.id.forth_button, R.string.forth_app}, {R.id.fifth_button, R.string.fifth_app},
            {R.id.sixth_button, R.string.sixth_app}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button;
        for (int i = 0; i < refTable.length; i++) {
            button = (Button) findViewById(refTable[i][0]);
            button.setText(refTable[i][1]);
        }
    }

    /**
     * Showing awesome app.
     *
     * @param view gate to awesome app
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.first_button:
                try {
                    Intent projectIntent = new Intent("popularmovies.intent.action.Launch");
                    startActivity(projectIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.not_installed), Toast.LENGTH_SHORT).show();
                    Log.e(LOG_TAG, "This application is not yet installed", e);
                }
                break;
            default:
                Toast.makeText(MainActivity.this, String.format(getResources().getString(R.string.to_be_defined),
                        ((Button) view).getText()).toLowerCase(), Toast.LENGTH_SHORT).show();
        }
    }
}
