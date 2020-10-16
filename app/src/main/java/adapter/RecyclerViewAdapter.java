package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotcharacterapp.Character;
import com.example.gotcharacterapp.MainActivity;
import com.example.gotcharacterapp.R;


import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private List<Character> displayList;
    private Context context;

    public RecyclerViewAdapter(MainActivity context, List<Character> displayList) {
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
        return displayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.
            OnClickListener{
            public TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            text = itemView.findViewById(R.id.name);
        }

        @Override
        public void onClick(View view) {

        }
    }
}

