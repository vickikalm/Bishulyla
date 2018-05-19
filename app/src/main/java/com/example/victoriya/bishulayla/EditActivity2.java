package com.example.victoriya.bishulayla;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class EditActivity2 extends AppCompatActivity implements View.OnClickListener {
    TextView etNameEdit, etPhoneEdit,etMeatMilkEdit,etTitleEdit;
    ImageButton btnEdit ;
    ArrayList<Prod> prodList;
    CheckBox checkBox;
    ArrayList<Post> posts;

    TextView tv,tv2;
    FirebaseDatabase database;

    DatabaseReference postRef;

    String key,uid,name,phone,meatmilk,title,bought;
    Post p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit2);

        prodList = new ArrayList<Prod>();
        posts = new ArrayList<Post>();

        database = FirebaseDatabase.getInstance();
        tv = (TextView) findViewById(R.id.tv);
        tv2 = (TextView) findViewById(R.id.tv2);
        etNameEdit = (TextView) findViewById(R.id.etNameEdit);
        etPhoneEdit = (TextView) findViewById(R.id.etPhoneEdit);
        etMeatMilkEdit = (TextView) findViewById(R.id.etMeatMilkEdit);
        etTitleEdit = (TextView) findViewById(R.id.etTitleEdit);

        checkBox = (CheckBox) findViewById(R.id.checkBox);

        btnEdit = (ImageButton) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this);


        Intent intent = getIntent();


        key = intent.getExtras().getString("key");
        uid = intent.getExtras().getString("uid");
        name = intent.getExtras().getString("name");
        phone = intent.getExtras().getString("phone");
        meatmilk = intent.getExtras().getString("meatmilk");
        title = intent.getExtras().getString("title");
        bought = intent.getExtras().getString("bought");
        if (bought.equals("true")){
            checkBox.setChecked(true);
            //checkBox.setEnabled(false);
            checkBox.setVisibility(View.INVISIBLE);
            btnEdit.setVisibility(View.INVISIBLE);
        }
        else
            checkBox.setChecked(false);
        Bundle bundle=intent.getExtras();
        String s="";

        prodList= (ArrayList<Prod>)bundle.getSerializable("lst");
        p=new Post(key,uid,name,phone,meatmilk,title,bought,prodList);
        if(prodList!=null)
        for(Object pp:prodList) {
            String x=pp.toString();
            x=x.replace("{amount=","");
            x=x.replace("prod=","");
            x=x.replace("}","");
            s += x+"\n";
        }
       /* synchronized(prodList)  {
            Iterator<Prod> iterator = prodList.iterator();
            while (iterator.hasNext()) {
               //Prod pp= iterator.next();
                Prod pp=new Prod();
                pp.setProd(iterator.next());
                pp.setAmount(iterator.next().getAmount());
                s += pp.toString();
              // s+=iterator.next();
            }
        }
        */
        etPhoneEdit.setText(phone);
        etNameEdit.setText(name);
        etMeatMilkEdit.setText(meatmilk);
        etTitleEdit.setText(title);
        s=tv2.getText()+"\n"+s;


        tv2.setText(s);//+  prodList.toString());

        //ProdAdapter prodAdapter= new ProdAdapter(EditPostActivity.this, 0, 0,lst);
        // lv.setAdapter(prodAdapter);

        postRef=database.getReference("Posts/" + key);
        // postRef = FirebaseDatabase.getInstance().getReference("Posts/" + key);
        // this.retrieveData();


    }

    @Override
    public void onClick(View v) {
        if(v==btnEdit) {
            postRef = database.getReference("Posts/" + p.key);


            p.bought = checkBox.isChecked()+"";
            postRef.setValue(p);
        }

        finish();
    }
}
