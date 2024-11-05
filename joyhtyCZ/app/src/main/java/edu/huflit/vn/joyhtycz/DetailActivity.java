package edu.huflit.vn.joyhtycz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.huflit.vn.joyhtycz.model.MainModel;
import edu.huflit.vn.joyhtycz.model.MyCartModel;

public class DetailActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    SharedPreferences userPref;
    SharedPreferences.Editor userPrefEditor;
MyCartModel mainModel;
    TextView textchoseC,textpriceC,addtocart;

    int quantity = 1;
    ImageView imgCart;
    CircleImageView imgC;
    TextView tvBuyC,tvdelete, tvadd, tvquantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mAuth = FirebaseAuth.getInstance();
//        getCurrentUser();
        Anhxa();

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.parseInt(tvquantity.getText().toString());
                switch (v.getId()){
                    case R.id.tv_add:
                        quantity = quantity+1;
                        tvquantity.setText(""+quantity);
                        break;
                    case R.id.tv_delete:
                        if (quantity - 1 > 0)
                        {
                            quantity -= 1;
                            tvquantity.setText(""+quantity);
                        }
                        break;
                }
                String addToCartText = "Thêm | "+mainModel.getPrice()*quantity;
                tvBuyC.setText(addToCartText);
            }
        };
        tvadd.setOnClickListener(clickListener);
        tvdelete.setOnClickListener(clickListener);

//
        tvBuyC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }


        });
    }



    public void Anhxa(){
        mainModel = (MyCartModel) getIntent().getSerializableExtra("shoes");
        textchoseC=findViewById(R.id.chosetextC);
        textpriceC=findViewById(R.id.pricetextC);
        imgC = findViewById(R.id.img2);
        tvBuyC=findViewById(R.id.tvBuy);
        tvdelete = findViewById(R.id.tv_delete);
        tvadd=findViewById(R.id.tv_add);
        tvquantity=findViewById(R.id.tv_quantity);
        imgCart=findViewById(R.id.item_xemgiohang);

        imgC.setImageBitmap(Utils.convertToBitmapFromAssets(DetailActivity.this, mainModel.getHinhanh()));
        textchoseC.setText(mainModel.getChosename());
        textpriceC.setText(String.valueOf(mainModel.getPrice()));
        String addToCartText = "Thêm | " +mainModel.getPrice();
        tvBuyC.setText(addToCartText);
    }
    private void addToCart() {
        user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            String itemName = mainModel.getChosename();
            int itemPrice = mainModel.getPrice();
            String itemImage = mainModel.getHinhanh();
            int itemQuantity = Integer.parseInt(tvquantity.getText().toString());

            HashMap<String, Object> cartItem = new HashMap<>();
            cartItem.put("userId", userId);
            cartItem.put("itemName", itemName);
            cartItem.put("itemPrice", itemPrice);
            cartItem.put("itemImage", itemImage);
            cartItem.put("itemQuantity", itemQuantity);
            cartItem.put("timestamp", getCurrentTimestamp());

            database.collection("Addcart")
                    .document(itemName) // sử dụng itemname làm ID của document
                    .set(cartItem) // thêm các thông tin vào document
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(DetailActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(DetailActivity.this, "Thêm vào giỏ hàng không thành công", Toast.LENGTH_SHORT).show();

                        // Hiển thị thông báo lỗi hoặc thực hiện các tác vụ khác tại đây
                    });

        } else {
            // Người dùng chưa đăng nhập, yêu cầu đăng nhập trước khi thêm vào giỏ hàng
        }
    }
    private String getCurrentTimestamp() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return dateFormat.format(calendar.getTime());
    }
}