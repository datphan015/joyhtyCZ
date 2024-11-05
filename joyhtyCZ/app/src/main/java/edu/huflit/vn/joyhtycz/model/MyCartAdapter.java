package edu.huflit.vn.joyhtycz.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.huflit.vn.joyhtycz.R;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<MyCartModel> mCartModelArrayList;
    private TextView mTvTotalPrice;


    public MyCartAdapter(Context context, ArrayList<MyCartModel> cartModelArrayList ){
        mContext = context;
        mCartModelArrayList = cartModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MyCartModel model = mCartModelArrayList.get(position);
        holder.tvName.setText(model.getChosename());
        holder.tvSize.setText(String.valueOf(model.getSize()));
        holder.tvPrice.setText(String.valueOf(model.getPrice()));
        holder.imgdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(position);
                Toast.makeText(mContext, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
        Glide.with(mContext)
                .load(model.getHinhanh())
                .error(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return mCartModelArrayList.size();
    }
    public void deleteItem(int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        MyCartModel model = mCartModelArrayList.get(position);
        Toast.makeText(mContext, String.valueOf(mCartModelArrayList.get(position).getChosename()), Toast.LENGTH_SHORT).show();
        if (model != null && model.getChosename() != null) {
            String id = model.getChosename();
            db.collection("Addcart").document(id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(mContext, "Deleted successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext, "Failed to delete", Toast.LENGTH_SHORT).show();
                        }
                    });
            mCartModelArrayList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mCartModelArrayList.size());
        } else {
            Toast.makeText(mContext, "Model or ID is null", Toast.LENGTH_SHORT).show();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageView;
        private TextView tvName, tvSize, tvPrice;
        ImageView imgdel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.chosetext);
            imgdel = itemView.findViewById(R.id.img_del);
            tvSize = itemView.findViewById(R.id.tv_size);
            tvPrice = itemView.findViewById(R.id.pricetext);
            circleImageView = itemView.findViewById(R.id.img_circle);
        }
    }

}