package my.headbana.fyp.drong3;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayProfile extends AppCompatActivity implements View.OnClickListener {

    DatabaseManager mDatabase;
    ArrayList<Profile> profiles;
    ImageView icon_edit, icon_delete;
    private int user_id;
    private String user_name, user_address, user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDatabase = new DatabaseManager(this);
        profiles = new ArrayList<>();

        int user_profile_id = 0;

        if (getIntent().hasExtra("profile_id")) {
            user_profile_id = getIntent().getIntExtra("profile_id",0);
            if(user_profile_id == 0){
                returnMainActivity();
            }
        } else {
            returnMainActivity();
        }

        this.user_id = user_profile_id;

        loadProfileDataFromDatabase(user_profile_id);

        user_name = profiles.get(0).getName();
        user_address = profiles.get(0).getAddress();
        user_password = profiles.get(0).getPassword();

        TextView profile_name = findViewById(R.id.profile_name);
        TextView profile_address = findViewById(R.id.profile_address);
        TextView profile_id = findViewById(R.id.profile_id);
        icon_edit = findViewById(R.id.icon_edit);
        icon_delete = findViewById(R.id.icon_delete);

        icon_edit.setOnClickListener(this);
        icon_delete.setOnClickListener(this);

        profile_name.setText(user_name);
        profile_address.setText(user_address);
        profile_id.setText("User ID #"+profiles.get(0).getId());

    }

    private void returnMainActivity(){
        Toast.makeText(this, "No profile_id passed !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void loadProfileDataFromDatabase(int id){
        Cursor cursor = mDatabase.getProfile(id);

        if (cursor.moveToFirst()) {
            do {
                profiles.add(new Profile(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                ));
            } while (cursor.moveToNext());

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.icon_edit:
                editProfile();
                break;
            case R.id.icon_delete:
                deleteProfile();
                break;
        }
    }

    private void deleteProfile(){
        if(mDatabase.deleteProfile(user_id)){
            Toast.makeText(this, "Profile successfully deleted !", Toast.LENGTH_SHORT).show();
            returnMainActivity();
        }else{
            Toast.makeText(this, "Something wrong !", Toast.LENGTH_SHORT).show();
        }
    }

    private void editProfile(){
        Intent intent = new Intent(this, InsertData.class);
        intent.putExtra("profile_id",user_id);
        intent.putExtra("profile_name",user_name);
        intent.putExtra("profile_address",user_address);
        intent.putExtra("profile_password",user_password);
        startActivity(intent);
    }
}
