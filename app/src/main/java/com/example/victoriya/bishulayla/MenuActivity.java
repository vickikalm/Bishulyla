package com.example.victoriya.bishulayla;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth firebaseAuth;

    Button btnAddPost;
    Button btnReg,btnLogin;//dialog buttons
    Button btnMainLogin,btnMainRegister;
    EditText etEmail,etPass;
    Dialog d;
    int mode=0; // 0 means register, 1 means login
    ProgressDialog progressDialog;
    Button btnAllPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        firebaseAuth=FirebaseAuth.getInstance();

        btnMainLogin = (Button)findViewById(R.id.btnLogin);
        btnMainLogin.setOnClickListener(this);

        btnMainRegister = (Button)findViewById(R.id.btnMainRegister);
        btnMainRegister.setOnClickListener(this);

        progressDialog=new ProgressDialog(this);
        btnAddPost=(Button)findViewById(R.id.btnAddPost);

        btnAllPost=(Button)findViewById(R.id.btnAllPost);
        btnAllPost.setOnClickListener(this);
        btnAllPost.setVisibility(View.INVISIBLE);
        btnAddPost.setVisibility(View.INVISIBLE);

        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null) {
            btnMainLogin.setText("Logout");
            if(firebaseAuth.getCurrentUser().getEmail().equals("admin1@gmail.com"))
                btnAllPost.setVisibility(View.VISIBLE);
            else
                btnAddPost.setVisibility(View.VISIBLE);
        }
        else {
            btnMainLogin.setText("Login");
            //btnAllPost.setVisibility(View.INVISIBLE);
        }



        btnAddPost.setOnClickListener(this);
    }

    public void createRegisterDialog()
    {
        d= new Dialog(this);
        d.setContentView(R.layout.register_layout);
        d.setTitle("Register");
        d.setCancelable(true);
        etEmail=d.findViewById(R.id.etEmail);
        etPass=d.findViewById(R.id.etPass);
        btnReg=d.findViewById(R.id.btnRegister);
        btnReg.setOnClickListener(this);
        mode=0;
        d.show();

    }

    public void createLoginDialogue()
    {
        d=new Dialog(this);
        d.setContentView(R.layout.login_layout);
        d.setTitle("Login");
        d.setCancelable(true);
        etEmail=d.findViewById(R.id.etEmail);
        etPass=d.findViewById(R.id.etPass);
        btnLogin=d.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        mode=1;
        d.show();
    }

    public void login()
    {
        progressDialog.setMessage("Login Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(etEmail.getText().toString(),etPass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(MenuActivity.this, "auth_success",Toast.LENGTH_SHORT).show();
                            btnMainLogin.setText("Logout");
                            if(firebaseAuth.getCurrentUser().getEmail().equals("admin1@gmail.com")) {
                                btnAllPost.setVisibility(View.VISIBLE);
                                btnAddPost.setVisibility(View.INVISIBLE);
                            }
                            else {
                                btnAllPost.setVisibility(View.INVISIBLE);
                                btnAddPost.setVisibility(View.VISIBLE);
                            }

                        }
                        else
                        {
                            Toast.makeText(MenuActivity.this, "auth_failed",Toast.LENGTH_SHORT).show();

                        }
                        d.dismiss();
                        progressDialog.dismiss();

                    }
                });
    }


    public void register()
    {
        progressDialog.setMessage("Registrating Please Wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(MenuActivity.this, "successfully registered", Toast.LENGTH_LONG).show();

                    btnMainLogin.setText("Logout");

                        if(firebaseAuth.getCurrentUser().getEmail().equals("admin1@gmail.com")) {
                            btnAllPost.setVisibility(View.VISIBLE);
                            btnAddPost.setVisibility(View.INVISIBLE);
                        }
                        else {
                            btnAllPost.setVisibility(View.INVISIBLE);
                            btnAddPost.setVisibility(View.VISIBLE);
                        }
                }
                else {
                    Toast.makeText(MenuActivity.this, "Registration error", Toast.LENGTH_LONG).show(); // add messages invalid user name/password
                }

                d.dismiss();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();


        if (v==btnMainLogin) {
            if (btnMainLogin.getText().toString().equals("Login")) {
                createLoginDialogue();


            } else if (btnMainLogin.getText().toString().equals("Logout")) {
                firebaseAuth.signOut();
                btnMainLogin.setText("Login");
                btnAllPost.setVisibility(View.INVISIBLE);
                btnAddPost.setVisibility(View.INVISIBLE);
            }
        }
        else if (v==btnMainRegister) {

            createRegisterDialog();
        }
        else if(v==btnReg)
        {
            register();

            }
        else if(v==btnLogin)
        {
            login();



        }
        else if (v==btnAllPost)
        {
            if(firebaseAuth.getCurrentUser().getEmail().equals("admin1@gmail.com")) {
                Intent intent = new Intent(MenuActivity.this, AllPostActivity.class);
                startActivity(intent);
            }

        }
        else if (v==btnAddPost) {

            Intent intent2 = new Intent(MenuActivity.this, AddPostActivity.class);
            startActivity(intent2);
        }
    }
}
