package edu.ucuccs.oratalibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SearchResultActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Bundle mExtras;
    private String mSearchKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setupToolbar();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mExtras = getIntent().getExtras();

        if (mExtras != null) {
            mSearchKeyword = mExtras.getString("str_keyword");
        }
    }
    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            if(mSearchKeyword != null)
                getSupportActionBar().setTitle(mSearchKeyword);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
