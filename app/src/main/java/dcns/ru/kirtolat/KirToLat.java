package dcns.ru.kirtolat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class KirToLat extends AppCompatActivity {

    EditText text2kir, text2lat;
    String[] arrayKir = {
            "ё",
            "й","ц","у","к","е","н","г","ш","щ","з","х","ъ",
            "ф","ы","в","а","п","р","о","л","д","ж","э",
            "я","ч","с","м","и","т","ь",
            "Ё",
            "Й","Ц","У","К","Е","Н","Г","Ш","Щ","З","Х","Ъ",
            "Ф","Ы","В","А","П","Р","О","Л","Д","Ж","Э",
            "Я","Ч","С","М","И","Т","Ь",
            "б","ю",".","Б","Ю",","
    };

    String[] arrayLat = {
            "`",
            "q","w","e","r","t","y","u","i","o","p","[","]",
            "a","s","d","f","g","h","j","k","l",";","'",
            "z","x","c","v","b","n","m",
            "~",
            "Q","W","E","R","T","Y","U","I","O","P","{","}",
            "A","S","D","F","G","H","J","K","L",":","\"",
            "Z","X","C","V","B","N","M",
            ",",".","/","<",">","?"
    };

    String chars, newLine, replaceAr, toCopy;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text2kir = (EditText)findViewById(R.id.textKir);
        text2lat = (EditText)findViewById(R.id.textLat);

        text2lat.setEnabled(false);

        text2kir.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence textKir, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence textKir, int i, int i1, int i2) {

                chars = String.valueOf(textKir);
                char[] textToArray = chars.toCharArray();

                newLine = "";
                for (int z = 0; z < textToArray.length; z++) {

                    replaceAr = String.valueOf(textToArray[z]);
                    Log.i("array", String.valueOf(textToArray[z]));

                    for(int x=0; x < arrayKir.length; x++){

                        Log.i("array2", String.valueOf(arrayKir[x]));

                        if(replaceAr.equals(arrayKir[x])){
                            replaceAr = String.valueOf(arrayLat[x]);
                            break;
                        }

                    }

                    newLine = newLine + "" + replaceAr;
                }

                text2lat.setText(newLine);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public void copyClip(View view) {
        ClipboardManager clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", text2lat.getText().toString());
        clipboard.setPrimaryClip(clip);
    }
}
