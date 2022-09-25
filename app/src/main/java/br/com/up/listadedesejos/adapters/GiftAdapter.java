package br.com.up.listadedesejos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.up.listadedesejos.R;
import br.com.up.listadedesejos.models.Gift;

public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.GiftViewHolder> {

    private ArrayList<Gift> gifts;
    private RecyclerViewClickListener listener;


    public GiftAdapter(ArrayList<Gift> gifts, RecyclerViewClickListener listener){
        this.gifts = gifts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View layout = layoutInflater.inflate(R.layout.view_item_gift, parent,false);

        return new GiftViewHolder(layout).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull GiftViewHolder holder, int position) {
        Gift gift = gifts.get(position);

        TextView textViewName = holder.itemView.findViewById(R.id.text_gift_name);
        textViewName.setText(gift.getName());

        TextView textViewGift = holder.itemView.findViewById(R.id.text_gift_price);
        textViewGift.setText(gift.getPrice());

        TextView textViewDescription = holder.itemView.findViewById(R.id.text_gift_description);
        textViewDescription.setText(gift.getDescription());

    }

    @Override
    public int getItemCount() {
        return gifts.size();
    }

    class GiftViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private GiftAdapter adapter;

        public GiftViewHolder(@NonNull View itemView){
            super(itemView);

            itemView.findViewById(R.id.card_view).setOnLongClickListener(view -> {
                adapter.gifts.remove(getAdapterPosition());
                adapter.notifyItemRemoved(getAdapterPosition());
                return true;
            });

            itemView.setOnClickListener(this);

        }

        public GiftViewHolder linkAdapter(GiftAdapter adapter){
            this.adapter = adapter;
            return this;
        }

        @Override
        public void onClick(View view){
            listener.onClick(view, getAdapterPosition());
        }

    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

}
