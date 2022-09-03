package ca.lsuderman.converter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnConvert;
    private TextView txtNumberToConvert, txtConverted;
    private AutoCompleteTextView txtUnitFrom, txtUnitTo, txtConversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConvert = findViewById(R.id.btnConvert);
        txtUnitFrom = findViewById(R.id.txtUnitFrom);
        txtUnitTo = findViewById(R.id.txtUnitTo);
        txtNumberToConvert = findViewById(R.id.txtNumberToConvert);
        txtConverted = findViewById(R.id.txtConverted);
        txtConversion = findViewById(R.id.txtConversion);

        //TODO: add more conversion options
        String[] conversionOptions = {"Temperature", "Length"};
        ArrayAdapter<String> conversionAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, conversionOptions);
        txtConversion.setAdapter(conversionAdapter);

        String[] temperatureOptions = {"Celsius", "Fahrenheit", "Kelvin"};
        ArrayAdapter<String> temperatureAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, temperatureOptions);

        String[] lengthOptions = {"Centimetres", "Metres", "Inches", "Feet",}; //TODO: add more lengths
        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, lengthOptions);


        txtConversion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (String.valueOf(txtConversion.getText())) {
                    case "Temperature":
                        txtUnitFrom.setAdapter(temperatureAdapter);
                        txtUnitTo.setAdapter(temperatureAdapter);
                        break;
                    case "Length":
                        txtUnitFrom.setAdapter(lengthAdapter);
                        txtUnitTo.setAdapter(lengthAdapter);
                        break;
                }
            }
        });

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double conversion = 0.0;
                switch (String.valueOf(txtConversion.getText())) {
                    case "Temperature":
                        conversion = temperatureConversion(String.valueOf(txtUnitFrom.getText()), String.valueOf(txtUnitTo.getText()),
                                Double.parseDouble(String.valueOf(txtNumberToConvert.getText())));
                        break;
                    case "Length":
                        lengthConversion();
                        break;
                    default:
                        //TODO: Make error snackbar
                        break;
                }

                //TODO: only display with a decimal if necessary
                //      make a nicer message
                txtConverted.setVisibility(View.VISIBLE);
                txtConverted.setText(String.valueOf(conversion));
            }
        });
    }

    private double temperatureConversion(String topUnit, String bottomUnit, double topNumber){
        double conversion = 0.0;
        if (topUnit.equals("Celsius")){
            if (bottomUnit.equals("Fahrenheit")){
                conversion = (topNumber * (9.0/5.0)) + 32.0;
            }
            else if (bottomUnit.equals("Kelvin")){
                conversion = topNumber + 273.15;
            }
        }
        else if(topUnit.equals("Fahrenheit")){
            if (bottomUnit.equals("Celsius")){
                conversion = (topNumber - 32.0) * (5.0/9.0);
            }
            else if (bottomUnit.equals("Kelvin")){
                conversion = ((topNumber - 32.0) * (5.0/9.0)) + 273.15;
            }
        }
        else if(topUnit.equals("Kelvin")){
            if (bottomUnit.equals("Celsius")){
                conversion = topNumber - 273.15;
            }
            else if (bottomUnit.equals("Fahrenheit")){
                conversion = ((topNumber - 273.15) * (9.0/5.0)) + 32.0;
            }
        }
        return conversion;
    }

    private void lengthConversion(){

    }
}