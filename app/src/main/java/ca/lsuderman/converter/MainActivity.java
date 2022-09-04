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
                String conversionType = String.valueOf(txtConversion.getText());
                String convertFrom = String.valueOf(txtUnitFrom.getText());
                String convertTo = String.valueOf(txtUnitTo.getText());
                double numberToConvert = Double.parseDouble(String.valueOf(txtNumberToConvert.getText()));
                switch (conversionType) {
                    case "Temperature":
                        conversion = temperatureConversion(convertFrom, convertTo ,numberToConvert);
                        break;
                    case "Length":
                        lengthConversion();
                        break;
                    default:
                        //TODO: Make error snackbar
                        break;
                }

                //TODO: only display with a decimal if necessary

                String conversionMessage = numberToConvert + " " + convertFrom + " is " + conversion + " " + convertTo;
                txtConverted.setVisibility(View.VISIBLE);
                txtConverted.setText(conversionMessage);

                try {
                    ((ConverterDB) getApplication()).addConversion(conversionType, convertFrom, convertTo, numberToConvert, conversion);
                } catch (Exception ex){
                    // no-op
                }
            }
        });
    }

    private double temperatureConversion(String convertFrom, String convertTo, double numberToConvert){
        double conversion = 0.0;
        if (convertFrom.equals("Celsius")){
            if (convertTo.equals("Fahrenheit")){
                conversion = (numberToConvert * (9.0/5.0)) + 32.0;
            }
            else if (convertTo.equals("Kelvin")){
                conversion = numberToConvert + 273.15;
            }
        }
        else if(convertFrom.equals("Fahrenheit")){
            if (convertTo.equals("Celsius")){
                conversion = (numberToConvert - 32.0) * (5.0/9.0);
            }
            else if (convertTo.equals("Kelvin")){
                conversion = ((numberToConvert - 32.0) * (5.0/9.0)) + 273.15;
            }
        }
        else if(convertFrom.equals("Kelvin")){
            if (convertTo.equals("Celsius")){
                conversion = numberToConvert - 273.15;
            }
            else if (convertTo.equals("Fahrenheit")){
                conversion = ((numberToConvert - 273.15) * (9.0/5.0)) + 32.0;
            }
        }
        return conversion;
    }

    private void lengthConversion(){

    }
}