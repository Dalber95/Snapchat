package pl.krusiec.snapchatjava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SnapsActivity extends AppCompatActivity {
    ListView snapsListView;

    ArrayList<String> emails = new ArrayList<>();
    ArrayList<DataSnapshot> snaps = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snaps);

        snapsListView = findViewById(R.id.snapsListView);

        mAuth = FirebaseAuth.getInstance();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, emails);
        snapsListView.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid())
                .child("snaps").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                emails.add(String.valueOf(dataSnapshot.child("from").getValue()));
                snaps.add(dataSnapshot);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        snapsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataSnapshot snapshot = snaps.get(position);

                Intent intent = new Intent(getApplicationContext(), ViewSnapActivity.class);
                intent.putExtra("imageName", String.valueOf(snapshot.child("imageName").getValue()));
                intent.putExtra("imageURL", String.valueOf(snapshot.child("imageURL").getValue()));
                intent.putExtra("message", String.valueOf(snapshot.child("message").getValue()));
                intent.putExtra("snapKey", snapshot.getKey());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.snaps_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.createSnap) {
            Intent intent = new Intent(getApplicationContext(), CreateSnapActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.logout) {
            mAuth.signOut();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mAuth.signOut();
    }
}
