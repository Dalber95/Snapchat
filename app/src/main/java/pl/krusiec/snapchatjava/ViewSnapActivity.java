package pl.krusiec.snapchatjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewSnapActivity extends AppCompatActivity {

    TextView messageTextView;
    ImageView snapImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_snap);

        messageTextView = findViewById(R.id.messageTextView);
        snapImageView = findViewById(R.id.snapImageView);

        messageTextView.setText(getIntent().getStringExtra("message"));
    }
}
