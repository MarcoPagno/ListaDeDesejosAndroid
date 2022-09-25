package br.com.up.listadedesejos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import br.com.up.listadedesejos.models.Gift;
import br.com.up.listadedesejos.repositories.GiftRepository;

public class AddGiftActivity extends AppCompatActivity {

    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutGift;
    private TextInputLayout inputLayoutDescription;

    private TextInputEditText inputEditTextName;
    private TextInputEditText inputEditTextGift;
    private TextInputEditText inputEditTextDescription;

    private Button buttonAddGift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gift);

        inputLayoutName = findViewById(R.id.input_layout_name);
        inputLayoutGift = findViewById(R.id.input_layout_gift);
        inputLayoutDescription = findViewById(R.id.input_layout_description);

        inputEditTextName = findViewById(R.id.input_text_name);
        inputEditTextGift = findViewById(R.id.input_text_gift);
        inputEditTextDescription = findViewById(R.id.input_text_description);

        buttonAddGift = findViewById(R.id.button_add_gift);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            inputEditTextName.setText(extras.getString("input_text_name"));
            inputEditTextGift.setText(extras.getString("input_text_price"));
            inputEditTextDescription.setText(extras.getString("input_text_description"));
        }

        buttonAddGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(extras != null)
                    saveGift(extras.getInt("index_text"));
                else
                    saveGift(-1);
            }
        });

    }

    private void saveGift(int editContent){

        String name = inputEditTextName.getText().toString();
        String giftPrice = inputEditTextGift.getText().toString();
        String description = inputEditTextDescription.getText().toString();

        if(name.isEmpty()){
            inputLayoutName.setError("Favor inserir nome");
            return;
        }
        if(giftPrice.isEmpty()){
            inputLayoutGift.setError("Favor inserir preco");
            return;
        }

        Gift gift = new Gift(
                name,
                giftPrice,
                description
        );

        if (editContent == -1)
            GiftRepository.getInstance().save(gift);
        else
            GiftRepository.getInstance().update(editContent, gift);

        onBackPressed();
    }
}