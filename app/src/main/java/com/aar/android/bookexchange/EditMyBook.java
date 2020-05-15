package com.aar.android.bookexchange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aar.android.bookexchange.models.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class EditMyBook extends AppCompatActivity {

    private Book EBook;

    private EditText bname;
    private EditText bauthor;
    private EditText bprice;
    private EditText blocation;
    private EditText bcondition;
private EditText bexchange;
    private Button bsave;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_mybook);
        EBook = (Book)getIntent().getParcelableExtra("ABook");



        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("Books").child(BuySell.finalUid);

        bname = findViewById(R.id.edit_mybook_bookname);
        bauthor = findViewById(R.id.edit_mybook_author);
        bprice = findViewById(R.id.edit_mybook_price);
        blocation=findViewById(R.id.edit_mybook_location);
        bcondition=findViewById(R.id.edit_mybook_condition);
        bexchange=findViewById(R.id.edit_mybook_exchange);
        bsave = findViewById(R.id.edit_mybook_save_button);

        bname.setText(EBook.getBookname());
        bauthor.setText(EBook.getAuthorname());
        bprice.setText(EBook.getPrice());
        blocation.setText(EBook.getLocation());
        bcondition.setText(EBook.getCondition());
        bexchange.setText(EBook.getExchange());
        bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ename =bname.getText().toString();
                String eauthor = bauthor.getText().toString();
                String eprice = bprice.getText().toString();
                String elocation=blocation.getText().toString();
                String econdition=bcondition.getText().toString();
                String eexchange=bexchange.getText().toString();

                if(ename.equals("") || eauthor.equals("") || eprice.equals("")|| elocation.equals("") || econdition.equals("") || eexchange.equals(""))
                {
                    Toast.makeText(EditMyBook.this,"All Fields are Mandatory",Toast.LENGTH_LONG).show();
                }
                else
                {
                    final Book edited = new Book();
                    edited.setBookname(ename);
                    edited.setAuthorname(eauthor);
                    edited.setPrice(eprice);
                    edited.setLocation(elocation);
                    edited.setCondition(econdition);
                    edited.setExchange(eexchange);
                    edited.setCount(EBook.getCount());
                    edited.setUidd(EBook.getUidd());
                    edited.setEmail(EBook.getEmail());
                    edited.setPhotoUrl(EBook.getPhotoUrl());
                    edited.setBooktype(EBook.getBooktype());

                    Query applesQuery1 = mDatabaseReference.orderByChild("bookname").equalTo(EBook.getBookname());
                    applesQuery1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().setValue(edited);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });
                    Intent numbersIntent = new Intent(EditMyBook.this, Advertise.class);
                    numbersIntent.putExtra("Book",edited);
                    startActivity(numbersIntent);

                }



            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                //Toast.makeText(EditMyBook.this,"back clicked",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("Book", EBook);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(EditMyBook.this,"back pressed",Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.putExtra("Book", EBook);
        setResult(Activity.RESULT_OK, intent);
        finish();
        startActivity(intent);
    }
}
