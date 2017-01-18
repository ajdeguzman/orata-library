/**
 * Created by wogaljohn on 1/13/2017.
 * Email: aljohndeguzman@gmail.com
 */


package edu.ucuccs.oratalibrary;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.ucuccs.oratalibrary.app.AppConfig;
import edu.ucuccs.oratalibrary.app.Utils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SearchResultActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Bundle mExtras;
    private String mSearchKeyword;
    private RecyclerView mRecyViewSearchResult;
    private LinearLayout mLinearEmptyState, mLinearProgressState;
    private static final String TAG = SearchResultActivity.class.getSimpleName();
    private ArrayList<String> mBookTitle = new ArrayList<>();
    private ArrayList<String> mBookAuthor = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        mToolbar                = (Toolbar) findViewById(R.id.toolbar);
        mRecyViewSearchResult   = (RecyclerView) findViewById(R.id.recyview_search_result);
        mLinearEmptyState       = (LinearLayout) findViewById(R.id.layout_empty_state);
        mLinearProgressState    = (LinearLayout) findViewById(R.id.layout_progress_state);

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
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle("Search " + "\"" + mSearchKeyword + "\"");
        }

        mRecyViewSearchResult.setHasFixedSize(true);
        loadSearchResult();
    }
    private void loadSearchResult() {
        mLinearEmptyState.setVisibility(View.VISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,  AppConfig.BASE_URL + "books/title/" + mSearchKeyword, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                mLinearEmptyState.setVisibility(View.GONE);
                try {
                    JSONArray mArrBooks = response.getJSONArray("books");
                    Log.d(TAG, "onResponse: " + mArrBooks.length());
                    Log.d(TAG, "response: " + response.toString());
                    clearArray();
                    for (int i = 0; i < mArrBooks.length(); i++) {
                        JSONObject mObjBook = mArrBooks.getJSONObject(i);
                        mBookTitle.add(mObjBook.getString("book_list_title"));
                        mBookAuthor.add(mObjBook.getString("book_list_author"));
                    }

                    BooksAdapter adapter;
                    if (mRecyViewSearchResult.getAdapter() == null) {
                        adapter = new BooksAdapter(getApplicationContext(), feedListContent(mBookTitle), mRecyViewSearchResult);
                        mRecyViewSearchResult.setAdapter(adapter);
                    } else {
                        adapter = ((BooksAdapter) mRecyViewSearchResult.getAdapter());
                        adapter.resetData(feedListContent(mBookTitle));
                    }
                }
                catch (JSONException ignored) {
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("Volley", "Error " + error);
                    }
                }
        );
        requestQueue.add(obreq);
    }

    private class BookClass implements Serializable {
        String book_title;
        String book_author;
    }

    private List<BookClass> feedListContent(ArrayList mArrayID) {
        List<BookClass> result = new ArrayList<>();
        for (int i = 0; i < mArrayID.size(); i++) {
            BookClass ci = new BookClass();
            ci.book_title = mBookTitle.get(i).toString();
            ci.book_author = mBookAuthor.get(i);
            result.add(ci);
        }
        return result;
    }
    private class BooksAdapter extends RecyclerView.Adapter {
        private final Context applicationContext;
        private final List<BookClass> foodList;
        private final RecyclerView mRecySearchResult;

        BooksAdapter(Context applicationContext, List<BookClass> foodList, RecyclerView mRecySearchResult) {
            this.applicationContext = applicationContext;
            this.foodList = foodList;
            this.mRecySearchResult = mRecySearchResult;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_book_list, parent, false);
            return new FoodListHolder(rowView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder hold, int position) {
            final BookClass ci = foodList.get(position);
            FoodListHolder holder = ((FoodListHolder) hold);
            holder.mBookTitle.setText(Utils.capsFirst(ci.book_title));
            holder.mBookAuthor.setText(ci.book_author);
        }


        @Override
        public int getItemCount() {
            return foodList.size();
        }

        class FoodListHolder extends RecyclerView.ViewHolder {
            final TextView mBookTitle, mBookAuthor;

            FoodListHolder(View itemView) {
                super(itemView);
                mBookTitle        = (TextView) itemView.findViewById(R.id.text_book_title);
                mBookAuthor       = (TextView) itemView.findViewById(R.id.text_book_author);
            }
        }

        void resetData(List<BookClass> listSong) {
            this.foodList.clear();
            this.foodList.addAll(listSong);
            notifyDataSetChanged();
        }
    }
    private void clearArray() {
        mBookTitle.clear();
        mBookAuthor.clear();
    }
    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
