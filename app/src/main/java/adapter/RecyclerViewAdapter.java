package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotcharacterapp.MainActivity;
import com.example.gotcharacterapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private List<String> displayList;
    private Context context;

    public RecyclerViewAdapter(MainActivity context, List<String> displayList) {
        this.context = context;
        this.displayList = displayList;

    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
         return new ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 9;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.
            OnClickListener{
            public CircleImageView circleImageView;
            public TextView nameChar;
            public TextView houseChar;
            public ImageButton favButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            circleImageView = itemView.findViewById(R.id.CirImgCharacter);
            nameChar = itemView.findViewById(R.id.nameCharacter);
            houseChar = itemView.findViewById(R.id.nameHouse);
            favButton = itemView.findViewById(R.id.favBtn);
        }

        @Override
        public void onClick(View view) {

        }
    }
}

