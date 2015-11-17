package com.nanoglz.leonardogonzalez_androidcodechallenge;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    PostsAdapter adapter;
    ArrayList<Post> posts;
    SearchView searchView;
    String selectedPost;
    FloatingActionButton fabCopy, fabMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchView = (SearchView) findViewById(R.id.searchView);
        if (searchView.getQuery() != null)
            searchView.setQuery(getString(R.string.default_filter), false);

        Button button = (Button) findViewById(R.id.buttonSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!searchView.getQuery().toString().trim().equals("")) {
                    updateData(searchView.getQuery().toString());
                }
            }
        });

        fabCopy = (FloatingActionButton) findViewById(R.id.fabCopy);
        fabCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "functionality not implemented", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fabMail = (FloatingActionButton) findViewById(R.id.fabMail);
        fabMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPost != null) {
                    Intent intent = new Intent(MainActivity.this, MailActivity.class);
                    intent.putExtra("title", selectedPost);
                    startActivity(intent);
                }
            }
        });

        posts = new ArrayList<Post>();
        adapter = new PostsAdapter(this, posts);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPost = adapter.getItem(position).getTitle();
                fabCopy.setVisibility(View.VISIBLE);
                fabMail.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData(searchView.getQuery().toString());
        searchView.hasFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateData(String filter) {
        Log.i("Test", "filter=" + filter);
        FetchPosts fetchPostsTask = new FetchPosts();
        fetchPostsTask.execute(filter);
    }

    public class FetchPosts extends AsyncTask<String, Void, ArrayList<Post>> {

        @Override
        protected ArrayList<Post> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            String JsonStr = null;

            try {
                // simple REST, no need for parameters
                final String dataURL = "http://www.reddit.com/r/" + params[0] + "/.json";
                URLConnection connection = new URL(dataURL).openConnection();
                InputStream inputStream = connection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {return null;}
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {return null;}
                JsonStr = buffer.toString();
            } catch (IOException e) {
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                return getPostsDataFromJson(JsonStr);
            } catch (JSONException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Post> posts) {
            if (posts != null) {
                adapter.clear();
                adapter.addAll(posts);
            }
        }
    }

    private ArrayList<Post> getPostsDataFromJson(String JsonStr) throws JSONException {
        posts = new ArrayList<Post>();
        posts.clear();
        JSONObject dataJson = new JSONObject(JsonStr).getJSONObject("data") ;
        JSONArray childrenArray = dataJson.getJSONArray("children");

        for(int i = 0; i < childrenArray.length(); i++) {
            // get all children
            JSONObject data = childrenArray.getJSONObject(i).getJSONObject("data") ;
            posts.add(new Post(
                    data.getString("title"),
                    data.getString("author"),
                    data.getString("thumbnail"),
                    data.getInt("num_comments"),
                    data.getInt("ups"),
                    data.getInt("downs")
            ));
        }
        return posts;
    }

}
