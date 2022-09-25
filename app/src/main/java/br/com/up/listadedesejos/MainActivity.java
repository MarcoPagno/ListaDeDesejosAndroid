package br.com.up.listadedesejos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import br.com.up.listadedesejos.adapters.GiftAdapter;
import br.com.up.listadedesejos.models.Gift;
import br.com.up.listadedesejos.repositories.GiftRepository;

public class MainActivity extends AppCompatActivity {

    ArrayList<Gift> gifts = GiftRepository.getInstance().getAll();

    private TextView textTotalView;
    private FloatingActionButton fabAddGift;
    private RecyclerView recyclerViewGifts;
    private TextView textViewGiftsNotFound;
    private GiftAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTotalView = findViewById(R.id.text_total_value);
        textViewGiftsNotFound = findViewById(R.id.text_gift_not_found);
        recyclerViewGifts = findViewById(R.id.recycler_gifts);
        fabAddGift = findViewById(R.id.fab_add_gift);

        recyclerViewGifts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

        updateTotalValue();

        fabAddGift.setOnClickListener(
        v -> {

            Intent intent = new Intent(
                getApplicationContext(),
                AddGiftActivity.class
            );
            startActivity(intent);

        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(gifts.size() > 0)
            textViewGiftsNotFound.setVisibility(View.INVISIBLE);
        else
            textViewGiftsNotFound.setVisibility(View.VISIBLE);

        updateTotalValue();
        
        setOnClickListener();
        recyclerViewGifts.setAdapter(new GiftAdapter(gifts, listener));

    }

    private void updateTotalValue()
    {
        int sum = 0;
        for (Gift gift : gifts) {
            sum += Integer.parseInt(gift.getPrice());
        }

        textTotalView.setText("Valor total: R$ "+ sum + ",00");
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            Intent intent = new Intent(getApplicationContext(), AddGiftActivity.class);
            intent.putExtra("input_text_name", gifts.get(position).getName());
            intent.putExtra("input_text_price", gifts.get(position).getPrice());
            intent.putExtra("input_text_description", gifts.get(position).getDescription());
            intent.putExtra("index_text", position);
            startActivity(intent);
        };
    }

}