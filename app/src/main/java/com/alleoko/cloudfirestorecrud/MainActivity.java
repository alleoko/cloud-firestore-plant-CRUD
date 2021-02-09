package com.alleoko.cloudfirestorecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText et_name, et_Desc, et_Class;
    Button btn_add, btn_view;
    FirebaseFirestore dbFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name = (EditText)findViewById(R.id.name);
        et_Desc = (EditText)findViewById(R.id.description);
        et_Class = (EditText)findViewById(R.id.classification);

        btn_add = (Button)findViewById(R.id.btn_add);
        btn_view = (Button)findViewById(R.id.btn_viewall);

        dbFirestore = FirebaseFirestore.getInstance();


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = UUID.randomUUID().toString();
                String name = et_name.getText().toString();
                String classification = et_Class.getText().toString();
                String description = et_Desc.getText().toString();


                addPlant(id, name, description, classification);
            }
        });
    }

    private void addPlant(String id, String name, String description, String classification ){

            if (!name.isEmpty()&&!description.isEmpty()&&!classification.isEmpty()){

                HashMap <String , Object> map = new HashMap<>();
                map.put("id", id);
                map.put("name", name);
                map.put("description", description);
                map.put("classification", classification);
                dbFirestore.collection("MyPlantCollection").document(id).set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Plant added", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Failed to add", Toast.LENGTH_SHORT).show();
                    }
                });



            }else{
                Toast.makeText(this, "Oops there's an empty field", Toast.LENGTH_SHORT).show();
            }

    };
}