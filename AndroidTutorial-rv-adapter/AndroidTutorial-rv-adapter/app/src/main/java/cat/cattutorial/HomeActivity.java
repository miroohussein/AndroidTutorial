package cat.cattutorial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    RecyclerView rv;
    final int ADD_REQUEST_CODE = 1;
    final int EDIT_REQUEST_CODE = 2;
    ArrayList<Contact> contactList = new ArrayList<>();

    ContactAdapterNew.OnContactClicked o = new ContactAdapterNew.OnContactClicked() {
        @Override
        public void c(Contact contact, int position) {
            Intent intent = new Intent(HomeActivity.this, AddContactActivity.class);
            intent.putExtra("contact", contact);
            intent.putExtra("pos", position);
            startActivityForResult(intent, EDIT_REQUEST_CODE);
        }
    };

    ContactAdapterNew adapter = new ContactAdapterNew(new DiffUtil.ItemCallback<Contact>() {
        @Override
        public boolean areItemsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getNumber().equals(newItem.getNumber());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getName().equals(oldItem.getName());
        }
    }, o);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
    }


    void initViews() {
        rv = findViewById(R.id.rv);
        rv.setAdapter(adapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddContactActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            contactList.add((Contact) data.getSerializableExtra("contact"));
            ArrayList<Contact> list =new ArrayList<>();
            adapter.submitList(list);
        } else if (requestCode == EDIT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            contactList.set(data.getIntExtra("pos", 0), (Contact) data.getSerializableExtra("contact"));
            ArrayList<Contact> list =new ArrayList<>();
            adapter.submitList(list);
        }
    }
}
