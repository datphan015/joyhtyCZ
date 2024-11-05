package edu.huflit.vn.joyhtycz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.huflit.vn.joyhtycz.model.MyCartAdapter;
import edu.huflit.vn.joyhtycz.model.MyCartModel;

public class GioHangActivity extends AppCompatActivity {
    FirebaseFirestore db;
    private int total = 0;
    FirebaseAuth auth;
   RecyclerView recyclerView;
    private TextView tvTotalPrice;
    private TextView tvTotalPriceOutside;
    MyCartAdapter cartAdapter;
    ArrayList<MyCartModel> cartModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.rc_cart);
        tvTotalPrice = findViewById(R.id.tvblabla);

        recyclerView.setLayoutManager(new GridLayoutManager(GioHangActivity.this, 1));
        cartModelArrayList = new ArrayList<>();
        MyCartAdapter adapter = new MyCartAdapter(this, cartModelArrayList);
        recyclerView.setAdapter(adapter);

        db.collection("Addcart")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Lấy dữ liệu của từng tài liệu (document) trong collection "cart"
                                String itemName = document.getString("itemName");
                                int itemPrice = document.getLong("itemPrice").intValue();
                                int itemQuantity = document.getLong("itemQuantity").intValue();
                                String hinhanh = document.getString("itemImage");

                                // Tính giá trị của từng sản phẩm và thêm vào tổng giá trị
                                int itemTotal = itemPrice * itemQuantity;
                                total += itemTotal;

                                MyCartModel model = new MyCartModel(itemPrice, itemQuantity, itemName, hinhanh);
                                cartModelArrayList.add(model);
                            }


                            if (adapter != null) {
                                tvTotalPrice.setText(String.valueOf(total));

                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            // Xảy ra lỗi khi truy vấn dữ liệu
                            Toast.makeText(GioHangActivity.this, "Lỗi khi lấy dữ liệu từ giỏ hàng", Toast.LENGTH_SHORT).show();
                        }
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xảy ra lỗi khi truy vấn dữ liệu
                        Toast.makeText(GioHangActivity.this, "Lỗi khi lấy dữ liệu từ giỏ hàng", Toast.LENGTH_SHORT).show();
                    }
                });
        if (recyclerView != null && cartAdapter != null) {
            recyclerView.setAdapter(cartAdapter);
        }

    }

    private void calculateTotal() {
        for (MyCartModel model : cartModelArrayList) {
            total += model.getPrice() * model.getSize();
        }
    }

}