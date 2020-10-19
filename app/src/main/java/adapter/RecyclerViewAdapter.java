package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotcharacterapp.CharacterItem;
import com.example.gotcharacterapp.MainActivity;
import com.example.gotcharacterapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<CharacterItem> displayList;
    private Context context;
    private boolean isFavorite = false;

    public RecyclerViewAdapter(MainActivity context, List<CharacterItem> displayList) {
        this.context = context;
        this.displayList = displayList;

    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        holder.textName.setText(displayList.get(position).getName());
        if (displayList.get(position).getHouse().equals("")) {
            holder.textHouse.setText("--");
        } else {
            holder.textHouse.setText("House " + displayList.get(position).getHouse());
        }

        if (!displayList.get(position).getImage_url().equals("")) {
            Picasso.with(context).load(displayList.get(position).getImage_url()).placeholder(R.drawable.gotimage).into(holder.circleImageView);
        }

        holder.btnLove.setOnClickListener(view -> {
            if (isFavorite) {
                holder.btnLove.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
            } else {
                holder.btnLove.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
            }
            isFavorite = !isFavorite;
        });

    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.
            OnClickListener {
        private TextView textName;
        private TextView textHouse;
        private CircleImageView circleImageView;
        private ImageView btnLove;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textName = itemView.findViewById(R.id.name);
            textHouse = itemView.findViewById(R.id.house);
            circleImageView = itemView.findViewById(R.id.profile_image);
            btnLove = itemView.findViewById(R.id.imageView2);

        }

        @Override
        public void onClick(View view) {

        }
    }
}

