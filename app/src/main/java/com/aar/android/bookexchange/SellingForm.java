package com.aar.android.bookexchange;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.aar.android.bookexchange.models.Book;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class SellingForm extends AppCompatActivity {

    private EditText mbookName;
    private EditText mbookAuthor;
    private ImageView mbookimage;
    private EditText mbookprice;
    private EditText mbooklocation;
    private EditText mbookcondition;
    private EditText mbookexchange;
    private String mbookTag;
    private Book mbookObject;


    private static final int RC_PHOTO_PICKER =  2;



    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    private FirebaseStorage mFirebaseStorage;
    private StorageReference mPhotoStrorageReference;

    private FirebaseAuth.AuthStateListener mFirebaseAuthListener;

    //private BookDbHelper mDbHelper;

    private static int photoupload = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_for_selling);

        mFirebaseDatabase = FirebaseDatabase.getInstance();


        mDatabaseReference = mFirebaseDatabase.getReference().child("Books");

        mFirebaseStorage = FirebaseStorage.getInstance();
        mPhotoStrorageReference = mFirebaseStorage.getReference().child("mybook_photos");

        Spinner spinner = (Spinner) findViewById(R.id.form_for_selling_book_type_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mbookTag = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button continueBuying = findViewById(R.id.form_for_selling_add_button);
        mbookName = findViewById(R.id.form_for_selling_book_name);
        mbookAuthor = findViewById(R.id.form_for_selling_author);
        mbookimage = findViewById(R.id.form_for_selling_add_image);
        mbookprice = findViewById(R.id.form_for_selling_price);
        mbooklocation=findViewById(R.id.form_for_location);
        mbookcondition=findViewById(R.id.form_for_selling_condition);
        mbookexchange=findViewById(R.id.form_for_selling_exchange);

        mbookObject = new Book();

        mbookimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });


        continueBuying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mbookName.getText().toString().equals("") || mbookAuthor.getText().toString().equals("") || mbookprice.getText().toString().equals("") || mbooklocation.getText().toString().equals("") || mbookcondition.getText().toString().equals("") || mbookexchange.getText().toString().equals(""))
                {
                    mbookName.setHintTextColor(Color.parseColor("#E57373"));
                    mbookAuthor.setHintTextColor(Color.parseColor("#E57373"));
                    mbookprice.setHintTextColor(Color.parseColor("#E57373"));
                    mbooklocation.setHintTextColor(Color.parseColor("#E57373"));
                    mbookcondition.setHintTextColor(Color.parseColor("#E57373"));
                    mbookexchange.setHintTextColor(Color.parseColor("#E57373"));
                    Toast.makeText(SellingForm.this,"Red Fields are Mandatory",Toast.LENGTH_LONG).show();
                }
                else
                {
                    mbookObject.setBookname(mbookName.getText().toString());
                    mbookObject.setAuthorname(mbookAuthor.getText().toString());
                    mbookObject.setPrice(mbookprice.getText().toString());
                    mbookObject.setCondition(mbookcondition.getText().toString());
                    mbookObject.setExchange(mbookexchange.getText().toString());
                    mbookObject.setBooktype(mbookTag);
                    mbookObject.setLocation(mbooklocation.getText().toString());
                     mbookObject.setEmail(BuySell.finalemail);
                    mbookObject.setCount(0);
                    mbookObject.setUidd(BuySell.finalUid);
                    if(photoupload==0)
                    {
                        mbookObject.setPhotoUrl("https://firebasestorage.googleapis.com/v0/b/book-exchange-49.appspot.com/o/boi.jpeg?alt=media&token=8c13755f-251e-4dca-bffe-064981fb0be4");
                    }

                    mDatabaseReference.child(BuySell.finalUid).push().setValue(mbookObject);
                    //mDatabaseReference.child(mbookTag.getText().toString()).push().setValue(mbookObject);


                    Intent familyIntent = new Intent(SellingForm.this, ShowBookList.class);
                    startActivity(familyIntent);

                }

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_PHOTO_PICKER && resultCode==RESULT_OK)
        {
            Uri SelectedImageUri = data.getData();
            StorageReference photoRef =
                    mPhotoStrorageReference.child(SelectedImageUri.getLastPathSegment());

            photoRef.putFile(SelectedImageUri).addOnSuccessListener(
                    this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            mbookObject.setPhotoUrl(taskSnapshot.getDownloadUrl().toString());
                            photoupload = 1;
                            Glide.with(mbookimage.getContext())
                                    .load(taskSnapshot.getDownloadUrl().toString())
                                    .into(mbookimage);
                        }
                    });
        }
    }

}
