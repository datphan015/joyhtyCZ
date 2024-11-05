package edu.huflit.vn.joyhtycz;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import edu.huflit.vn.joyhtycz.model.MainModel;
import edu.huflit.vn.joyhtycz.model.MyCartModel;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.myviewholder> {

    ArrayList<MyCartModel> datalist = new ArrayList<>();
    Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Listener listener;
    public ItemAdapter(ArrayList<MyCartModel> datalist, Context context, Listener listener) {
        this.datalist = datalist;
        this.listener  = listener;
        this.context = context;

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item,parent,false);
        return new myviewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        MyCartModel mainModel = datalist.get(position);
        holder.chosetext.setText(mainModel.getChosename());
        holder.pricetext.setText(String.valueOf(mainModel.getPrice()));
//        holder.img.setImageBitmap(Utils.convertToBitmapFromAssets(context,mainModel.getHinhanh()));
        Glide.with(context).load(mainModel.getHinhanh()).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View v) {
                if (listener!=null)
                    listener.onItemListener(mainModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
    public void searchDataList(ArrayList<MyCartModel> searchList) {
        datalist = searchList;
        notifyDataSetChanged();
    }
    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView chosetext,pricetext;
        ImageView img;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            chosetext=itemView.findViewById(R.id.chosetext);
            pricetext=itemView.findViewById(R.id.pricetext);
            img=itemView.findViewById(R.id.img1);
        }
    }

    public interface Listener
    {
        void onItemListener(MyCartModel mainModel);
    }

}
