package pl.krusiec.snapchatjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class ChooseUserActivity extends AppCompatActivity {
    ListView chooseUserListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);

        chooseUserListView = findViewById(R.id.chooseUserListView);
    }
}
