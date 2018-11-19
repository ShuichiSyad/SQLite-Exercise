package my.headbana.fyp.drong3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setClickListener();

    }

    private void setClickListener(){
        Button displayData = findViewById(R.id.display_data);

        displayData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent display = new Intent(getApplicationContext(), DisplayData.class);
                startActivity(display);
            }
        });

        Button insertData = findViewById(R.id.insert_data);

        insertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent insert = new Intent(getApplicationContext(), InsertData.class);
                startActivity(insert);
            }
        });

        // For debug purpose
        Log.d("Flow","Listener configured successfully");
    }
}
