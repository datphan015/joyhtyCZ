package edu.huflit.vn.joyhtycz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.huflit.vn.joyhtycz.model.MainModel;
import edu.huflit.vn.joyhtycz.model.MyCartModel;

public class ItemActivity extends AppCompatActivity implements ItemAdapter.Listener {

    RecyclerView recview;
    ArrayList<MyCartModel> datalist = new ArrayList<>();
    FirebaseFirestore db;
    FloatingActionButton floatingAction;

    ItemAdapter adapter;

    private SearchView searchView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
floatingAction=findViewById(R.id.floatingActionButton);
floatingAction.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(ItemActivity.this,GioHangActivity.class);
        startActivity(intent);
    }
});
        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(ItemActivity.this,LinearLayoutManager.VERTICAL,true));
        recview.addItemDecoration(new DividerItemDecoration( ItemActivity.this,DividerItemDecoration.VERTICAL));
        datalist=new ArrayList<>();
        adapter=new ItemAdapter(datalist, ItemActivity.this,this);
        recview.setAdapter(adapter);
        showData();
    }
    public void showData(){
        db=FirebaseFirestore.getInstance();
        db.collection("sneaker").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            int size = document.get("size") != null ? Integer.parseInt(document.get("size").toString()) : 0;
                            String chose = document.get("chose") != null ? document.get("chose").toString() : "";
                            int price = document.get("price") != null ? Integer.parseInt(document.get("price").toString()) : 0;
                            String hinhanh = document.get("hinh_anh") != null ? document.get("hinh_anh").toString() : "";
                            MyCartModel mainModel = new MyCartModel(price,size,chose,hinhanh);
                            datalist.add(mainModel);
                            adapter.notifyDataSetChanged();
                        }

                    }
                });
    }



    @Override
    public void onItemListener(MyCartModel mainModel) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("shoes", mainModel);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        //search
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
        return true;
    }
    public void searchList(String text){
        ArrayList<MyCartModel> searchList = new ArrayList<>();
        for (MyCartModel mainModel: datalist){
            if (mainModel.getChosename().toLowerCase().contains(text.toLowerCase())){
                searchList.add(mainModel);
            }
        }
        adapter.searchDataList(searchList);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        List<MyCartModel> cartModelList = datalist;
        switch (item.getItemId()){
            case R.id.Sort:
                Collections.sort(cartModelList, new Comparator<MyCartModel>() {
                    @Override
                    public int compare(MyCartModel o1, MyCartModel o2) {
                        return o1.getChosename().compareToIgnoreCase(o2.getChosename());
                    }
                });
                adapter.notifyDataSetChanged();
                Toast.makeText(ItemActivity.this,"SẮP XẾP THÀNH CÔNG!",Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_back:
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }




}