package ca.lsuderman.converter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnConvert;
    private TextView txtTopConvert, txtBottomConvert;
    private AutoCompleteTextView txtTopUnit, txtBottomUnit, txtConversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConvert = findViewById(R.id.btnConvert);
        txtBottomConvert = findViewById(R.id.txtBottomConvert);
        txtTopConvert= findViewById(R.id.txtTopConvert);
        txtBottomUnit = findViewById(R.id.txtBottomUnit);
        txtTopUnit = findViewById(R.id.txtTopUnit);
        txtConversion = findViewById(R.id.txtConversion);

        String[] conversionOptions = {"Temperature", "Length"};
        ArrayAdapter<String> conversionAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, conversionOptions);
        txtConversion.setAdapter(conversionAdapter);

        //TODO: add more options
        String[] TemperatureOptions = {"Celsius", "Fahrenheit", "Kelvin"};
        String[] lengthOptions = {"Centimetres", "Metres", "Inches", "Feet", }; //TODO: add more


    }
}