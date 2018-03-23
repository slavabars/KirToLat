package dcns.ru.kirtolat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class KirToLat extends AppCompatActivity {

    EditText text2kir, text2lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text2kir = (EditText)findViewById(R.id.textKir);
        text2lat = (EditText)findViewById(R.id.textLat);

        text2kir.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence textKir, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence textKir, int i, int i1, int i2) {
                text2lat.setText(textKir);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}
