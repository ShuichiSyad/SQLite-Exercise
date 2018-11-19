package my.headbana.fyp.drong3;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

public class DisplayData extends AppCompatActivity {

    // Arraylist will store each row of data from DB as Object
    // We'll create an Object (Profile.java) so they would refer each other. Tak reti explain sorry.
    // Nanti dalam adapter kena pass object gak
    // Adapter akan handle. Takyah risau kalau tak faham.

    ArrayList<Profile> profiles;
    DatabaseManager mDatabase;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDatabase = new DatabaseManager(this);
        profiles = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recycler_view);

        //Initialize myRecyclerView

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        loadProfileDataFromDatabase();
    }

    private void loadProfileDataFromDatabase(){
        Cursor cursor = mDatabase.getAllProfiles();

        if (cursor.moveToFirst()) {
            do {
                profiles.add(new Profile(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                ));
            } while (cursor.moveToNext());

            ProfileAdapter adapter = new ProfileAdapter(profiles);
            // Attach the adapter to the recyclerview to populate items
            mRecyclerView.setAdapter(adapter);
            // Set layout manager to position the items
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
