/**
 * Created by wogaljohn on 1/13/2017.
 * Email: aljohndeguzman@gmail.com
 */

package edu.ucuccs.oratalibrary;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private List<String> mListCategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton mFabFilterSearch = (FloatingActionButton) findViewById(R.id.fab);
        mFabFilterSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        DrawerLayout mDrawerMain = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerMain, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerMain.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView mNavMain = (NavigationView) findViewById(R.id.nav_view);
        mNavMain.setNavigationItemSelectedListener(this);
        mNavMain.getMenu().getItem(0).setChecked(true);

        mListCategories.add("BUSINESS");
        mListCategories.add("EDUCATION");
        mListCategories.add("TRAVEL");
        mListCategories.add("COMPUTERS & TECHNOLOGY");
        mListCategories.add("SCIENCE & MATH");
        mListCategories.add("RELIGION");
        mListCategories.add("SEE ALL");
        addChipsViewFinal(mListCategories);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                Intent i = new Intent(getApplicationContext(), SearchResultActivity.class);
                i.putExtra("str_keyword", query);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_featured) {
            getSupportActionBar().setTitle(R.string.nav_menu_featured);
        } else if (id == R.id.nav_acquisitions) {
            getSupportActionBar().setTitle(R.string.nav_menu_new_acquisitions);
        } else if (id == R.id.nav_bookmarks) {
            getSupportActionBar().setTitle(R.string.nav_menu_bookmarks);
        } else if (id == R.id.nav_policies) {
            getSupportActionBar().setTitle(R.string.nav_menu_policies);
        } else if (id == R.id.nav_recent) {
            getSupportActionBar().setTitle(R.string.nav_menu_recent);
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        } else if (id == R.id.nav_rate) {
            openAppOnPlaystore();
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void addChipsViewFinal(List<String> tagList) {
        LinearLayout adjustableLayout = (LinearLayout) findViewById(R.id.linear_categories_container);
        adjustableLayout.removeAllViews();
        for (int i = 0; i < tagList.size(); i++) {
            @SuppressLint("InflateParams")
            final View newView = LayoutInflater.from(this).inflate(R.layout.layout_view_chip_text, null);
            LinearLayout mLinearChipTag = (LinearLayout) newView.findViewById(R.id.linear_chip_tag);
            final TextView mTxtChipTags = (TextView) newView.findViewById(R.id.txt_chip_content);

            mLinearChipTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            mTxtChipTags.setText(tagList.get(i));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5, 5, 5, 5);
            newView.setLayoutParams(layoutParams);

            adjustableLayout.addView(newView);
        }
    }
    private void openAppOnPlaystore(){
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
