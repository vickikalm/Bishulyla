package com.example.victoriya.bishulayla;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddPostActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    EditText etProd,etAm, etName, etPhone,etTitle;
    ImageButton btnSave, btnPlus;
    ArrayList<Prod> lst;
    List<Prod> lstlst;
    ListView lv;
    RadioButton Meat,Milk;
    String meatmilk="";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference postRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        firebaseDatabase = FirebaseDatabase.getInstance();

        etProd = (EditText) findViewById(R.id.etProd);
        etAm = (EditText) findViewById(R.id.etAm);
        etName=(EditText) findViewById(R.id.etName);
        etPhone=(EditText) findViewById(R.id.etPhone);
        etTitle=(EditText) findViewById(R.id.etTitle);
        Meat=(RadioButton) findViewById(R.id.Meat);
        Milk=(RadioButton) findViewById(R.id.Milk);
        lv=(ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(this);

        lst=new ArrayList<Prod>();
        btnSave = (ImageButton) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnSave.setEnabled(false);

        btnPlus = (ImageButton) findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v==btnSave) {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
             lstlst=lst.subList(0,lst.size());
            if(Meat.isChecked()) meatmilk="בשרי";
            else
            if(Milk.isChecked()) meatmilk="חלבי";
            else
                meatmilk="פרווה";
            Post p = new Post("", uid, etName.getText().toString(), etPhone.getText().toString(),meatmilk,etTitle.getText().toString(), "false", lst); //key, uid, name, phone, bought, list of products
            postRef = firebaseDatabase.getReference("Posts").push();
            p.key = postRef.getKey();
            postRef.setValue(p);
            for(Prod item : lstlst) {
                postRef.push().setValue(item);
            }
            finish();
        }
        if(v==btnPlus) {
            String pr=etProd.getText().toString();
            String am=etAm.getText().toString();
            Prod p1=new Prod(pr,am);
            lst.add(p1);
            ProdAdapter prodAdapter= new ProdAdapter(AddPostActivity.this, 0, 0,lst);
            lv.setAdapter(prodAdapter);

            etAm.setText("");
            etProd.setText("");
            btnSave.setEnabled(true);

        }
    }
//        Intent intent=new Intent(AddPostActivity.this, MainActivity.class);
  //      startActivity(intent);

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item=parent.getItemAtPosition(position).toString();
       lst.remove(position);
        etAm.setText(item.substring(item.indexOf('>')+1,item.indexOf("\n")));
        etProd.setText(item.substring(0,item.indexOf(':')));
        ProdAdapter prodAdapter= new ProdAdapter(AddPostActivity.this, 0, 0,lst);
        lv.setAdapter(prodAdapter);

    }
}
