package com.example.ukutagamesv1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.Volley;
import com.example.ukutagamesv1.R;
import com.example.ukutagamesv1.adapters.RecyclerViewAdapter;
import com.example.ukutagamesv1.model.Game;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final String JSON_URL = "https://api.myjson.com/bins/15wf1a";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Game> lstGame;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter myadapter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton = findViewById(R.id.main_info_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Ukuta Games provides a list of the most popular games in 2019! Tap the games for more info.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0 , 0);
                toast.show();
            }
        });

        new LongRunningTask().execute();

        lstGame = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclervieeid);
        //jsonrequest();
    }

    private class LongRunningTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "doInBackground: JSON URL CALL");
            //Toast.makeText(getApplicationContext(), "Checking for Updates", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Void doInBackground(final Void... voids) {

            //private void jsonrequest() {
            Log.d(TAG, "doInBackground: JSON URL CALL");

            request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    JSONObject jsonObject = null;

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            Game game = new Game();

                            game.setName(jsonObject.getString("name"));
                            game.setGenres(jsonObject.getString("genres"));
                            game.setRating(jsonObject.getString("rating"));
                            game.setRelease(jsonObject.getString("release"));
                            game.setPlatforms(jsonObject.getString("platforms"));
                            game.setId(jsonObject.getInt("id"));
                            game.setImage_url(jsonObject.getString("image"));

                            lstGame.add(game);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    setuprecylerview(lstGame);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {}
            });

            requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(request);

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private void setuprecylerview(List<Game> lstGame) {

        myadapter = new RecyclerViewAdapter(this, lstGame);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(myadapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

            SearchView searchView = (SearchView) searchItem.getActionView();
            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    myadapter.getFilter().filter(newText);
                    return false;
                }
            });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_platform:

                Intent intent = new Intent(this,PlatformsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}



