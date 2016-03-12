package com.gdgvitvellore.developers.gdgvitvellore.Volley;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gdgvitvellore.developers.gdgvitvellore.R;

import java.util.ArrayList;

/**
 * Created by shalini on 13-03-2015.
 */
public class FeedbackActivity extends ActionBarActivity implements View.OnClickListener {
    private Spinner actionListSpinner;
    private ArrayList<String> m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_layout);
        actionListSpinner=(Spinner)findViewById(R.id.actionlist);
        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feedback");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.rest)));
        ArrayAdapter<String> dataAdapter;
        m=new ArrayList<String>();
        m.add("Anonymous");
        m.add("eklavyabhar.2008@rediffmail.com");
        dataAdapter=new ArrayAdapter<String>(this,R.layout.simple_spinner_item, m);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dopdown_item);
        actionListSpinner.setAdapter(dataAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.smallmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home:
                finish();
            default:
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.home:
                finish();
                break;
            default:
                break;

        }
    }
}
