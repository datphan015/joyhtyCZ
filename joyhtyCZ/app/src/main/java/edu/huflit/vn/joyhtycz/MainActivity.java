package edu.huflit.vn.joyhtycz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    ImageButton button;
    ImageButton buttonSP;
    ImageButton btnKH;
    TextView textView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        auth = FirebaseAuth.getInstance();
        button=findViewById(R.id.logout);
        buttonSP = findViewById(R.id.btnSP);
        btnKH = findViewById(R.id.btnKH);
        buttonSP.setOnClickListener(v -> startActivity(new Intent(this,ItemActivity.class)));
        btnKH.setOnClickListener(v -> startActivity(new Intent(this,KhachHangActivity.class)));
        textView=findViewById(R.id.user_details);
        user = auth.getCurrentUser();

        if (user==null){
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            textView.setText(user.getEmail());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

}