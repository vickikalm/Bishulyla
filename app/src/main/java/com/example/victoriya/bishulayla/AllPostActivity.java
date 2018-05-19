package com.example.victoriya.bishulayla;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class AllPostActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> postsposts=null;
    ArrayList<Post> posts=null;
    AllPostAdapter allPostAdapter;
String nm="";
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_post);
       postsposts = new ArrayList<String>();
        posts = new ArrayList<Post>();

        database = FirebaseDatabase.getInstance().getReference("Posts");
        lv = (ListView) findViewById(R.id.lv);
        this.retrieveData();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post p = posts.get(position);
                Intent intent = new Intent(AllPostActivity.this, EditActivity2.class);
                intent.putExtra("key", p.key);
                intent.putExtra("uid", p.uid);
                intent.putExtra("name", p.name);
                intent.putExtra("phone", p.phone);
                intent.putExtra("meatmilk", p.meatmilk);
                intent.putExtra("title", p.title);
                intent.putExtra("bought", p.bought);
                //List<String> shoppingList=null;//=(ArrayList<String>)p.shoppingList;
               // List<String> shoppingList=( ArrayList<String>)p.shoppingList;
              Bundle bundle=new Bundle();
            //    bundle.putParcelableArrayList("lst", (ArrayList<? extends Parcelable>) p.shoppingList);
               bundle.putSerializable("lst", (Serializable) p.shoppingList);
                intent.putExtras(bundle);
               //intent.getExtras().putCharSequenceArrayList("lst",shoppingList);
                startActivity(intent);
            }
        });
    }

    public void retrieveData() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                posts = new ArrayList<Post>();
            //  postsposts=( ArrayList<String>)dataSnapshot.getValue();


                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    // public Post(String key, String uid, String name, String phone, String bought, List<Prod> lst)
                  //  Post t=(Post)data.getValue();
                    String key=data.child("key").getValue()+"";
                    String uid=data.child("uid").getValue()+"";
                    String name=data.child("name").getValue()+"";
                    String phone=data.child("phone").getValue()+"";
                    String meatmilk=data.child("meatmilk").getValue()+"";
                    String title=data.child("title").getValue()+"";
                    String bought=data.child("bought").getValue()+"";

                    List<String> shoppingList=( ArrayList<String>)data.child("shoppingList").getValue();
                    List<Prod> shoppingListProd=( ArrayList<Prod>)data.child("shoppingList").getValue();;
                    Post p=new Post( key,  uid,  name,  phone,meatmilk, title, bought,  shoppingListProd);
                    posts.add(p);

                            //data.getValue(Post.class));
                }
                allPostAdapter = new AllPostAdapter(AllPostActivity.this, 0, 0, posts);
                lv.setAdapter(allPostAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}

