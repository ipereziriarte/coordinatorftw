package com.letgo.coordinatorftw;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);

        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().add(R.id.fragment_container, MainFragment.newInstance()).commit();

        button = (Button) findViewById(R.id.coordinated_btn);

    }

    public Button getButton() {
        return button;
    }

}
