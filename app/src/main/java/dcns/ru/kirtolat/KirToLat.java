package dcns.ru.kirtolat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class KirToLat extends AppCompatActivity {

    public Context context;

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

    ListView lvData;
    DB db;
    SimpleCursorAdapter adapter;
    Cursor cursor;

    private HashMap<String, String> map;
    ArrayList<HashMap<String, String>> copylist = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        copyListUpdate();

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

                    for(int x=0; x < arrayKir.length; x++){

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
        if(text2lat.getText().length() != 0){
            ClipboardManager clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("", text2lat.getText().toString());
            clipboard.setPrimaryClip(clip);

            Toast.makeText(this, "Значение скопированно", Toast.LENGTH_SHORT).show();

            db = new DB(this);
            db.open();

            if(!db.getFromText(text2lat.getText().toString())){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strDate = sdf.format(calendar.getTime());
                db.addRec(text2lat.getText().toString(), strDate);
                Toast.makeText(this, "Значение записано "+text2lat.getText().toString(), Toast.LENGTH_SHORT).show();
            }

            db.close();

            copyListUpdate();
        }
    }

    private void copyListUpdate(){
        db = new DB(this);
        db.open();
        cursor = db.getAllData();
        copylist.clear();

        if(cursor.moveToFirst()){
            do{
                map = new HashMap<>();
                map.put("text", cursor.getString(cursor.getColumnIndex("text")));
                map.put("data", cursor.getString(cursor.getColumnIndex("data")));
                copylist.add(map);
            }while(cursor.moveToNext());
        }

        db.close();

        lvData = (ListView)findViewById(R.id.listView);
        SimpleAdapter adapter = new SimpleAdapter(this, copylist, R.layout.copylist,
                new String[]{"text", "data"},
                new int[]{R.id.copytext1, R.id.copytext2});
        lvData.setAdapter(adapter);

        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view.findViewById(R.id.copytext1);
                if(textView.getText()!=null){
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("", textView.getText().toString());
                    clipboard.setPrimaryClip(clip);
                }
            }
        });
    }
}
