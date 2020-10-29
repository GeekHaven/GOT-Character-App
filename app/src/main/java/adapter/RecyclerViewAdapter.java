package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.toolbox.StringRequest;
import com.example.gotcharacterapp.CharacterItem;
import com.example.gotcharacterapp.DisplayCharacterItem;
import com.example.gotcharacterapp.MainActivity;
import com.example.gotcharacterapp.MyDbHandler;
import com.example.gotcharacterapp.R;
import com.squareup.picasso.Picasso;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.core.content.ContextCompat.getDrawable;
import static androidx.core.content.ContextCompat.startActivity;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {
    private List<CharacterItem> displayList = new ArrayList<>();
    private Context context;
    public static List<CharacterItem> filteredList = new ArrayList<>();
    private String searchQuery = "";
    public RecyclerViewAdapter(MainActivity context, List<CharacterItem> displayList) {
        this.context = context;
        this.displayList = displayList;
        this.filteredList = displayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
         return new ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        if(searchQuery != null && searchQuery != ""){
            String name = filteredList.get(position).getName();
            int start = name.toLowerCase().indexOf(searchQuery.toLowerCase().trim());
            int end = start + searchQuery.length();
            if(start < 0 || end > name.length()){
                holder.textName.setText(filteredList.get(position).getName());
            }else {
                Spannable highlightedName = new SpannableString(name);
                ColorStateList highlightColor = new ColorStateList(new int[][]{new int[]{}},new int[]{Color.YELLOW});
                highlightedName.setSpan(new TextAppearanceSpan(null, Typeface.BOLD_ITALIC,-1,highlightColor,null),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.textName.setText(highlightedName);
            }

        }else{
            holder.textName.setText(filteredList.get(position).getName());
        }


        if(filteredList.get(position).getFavourite()){
            holder.heartIcon.setImageResource(R.drawable.ic_baseline_favorite_24);
        }else{
            holder.heartIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }

        holder.heartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDbHandler myDbHandler = new MyDbHandler(context);
                if(filteredList.get(position).getFavourite()){
                    holder.heartIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    filteredList.get(position).setFavourite(false);
                    myDbHandler.updateFavouriteState(filteredList.get(position),position);
                }else{
                    holder.heartIcon.setImageResource(R.drawable.ic_baseline_favorite_24);
                    filteredList.get(position).setFavourite(true);
                    myDbHandler.updateFavouriteState(filteredList.get(position),position);
                }
            }
        });


        if(filteredList.get(position).getHouse().equals("")){
            holder.textHouse.setText("--");
        }else{
            holder.textHouse.setText("House "+filteredList.get(position).getHouse());
        }

        if(!filteredList.get(position).getImage_url().equals("")){
            Picasso.with(context).load(filteredList.get(position).getImage_url()).placeholder(R.drawable.gotimage).into(holder.circleImageView);
        }

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<CharacterItem> filtering = new ArrayList<>();
            if(charSequence != null && charSequence != ""){

                searchQuery = charSequence.toString();

                for(CharacterItem item : displayList){
                    if(item.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filtering.add(item);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.count = filtering.size();
            filterResults.values = filtering;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            if(filterResults != null && filterResults.count > 0){
                try{
                    filteredList = (List<CharacterItem>) filterResults.values;
                    notifyDataSetChanged();
                }catch (Exception e){
                    Log.i("E>>>>>>>>>>>>>>", Objects.requireNonNull(e.getMessage()));
                }

            }

        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.
            OnClickListener{
            private TextView textName;
            private TextView textHouse;
            private CircleImageView circleImageView;
            private ImageView heartIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textName = itemView.findViewById(R.id.name);
            textHouse = itemView.findViewById(R.id.house);
            circleImageView = itemView.findViewById(R.id.profile_image);
            heartIcon =  itemView.findViewById(R.id.imageView2);


        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            Context context = view.getContext();
            if(pos != RecyclerView.NO_POSITION) {
                CharacterItem ob = filteredList.get(pos);
                Intent i = new Intent(context, DisplayCharacterItem.class);
                i.putExtra("Character Items", (Serializable) ob);
                i.putExtra("pos",pos);
                ((Activity)context).startActivityForResult(i,0);
            }
        }
    }
}

