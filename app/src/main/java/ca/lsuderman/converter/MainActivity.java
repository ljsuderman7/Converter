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
        String[] conversionOptions = {"Temperature", "Length", "Mass", "Area", "Data", "Time"};
        ArrayAdapter<String> conversionAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, conversionOptions);
        txtConversion.setAdapter(conversionAdapter);

        String[] temperatureOptions = {"Celsius", "Fahrenheit", "Kelvin"};
        ArrayAdapter<String> temperatureAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, temperatureOptions);

        String[] lengthOptions = {"Millimetres", "Centimetres", "Metres", "Kilometers", "Inches", "Feet", "Yards", "Miles"};
        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, lengthOptions);

        String[] massOptions = {"Grams", "Kilograms", "Ounces", "Pounds"};
        ArrayAdapter<String> massAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, massOptions);

        String[] areaOptions = {"Acres", "Hectares", "Square centimetres", "Square feet", "Square inches", "Square metres"};
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, areaOptions);

        String[] dataOptions = {"Bits", "Bytes", "Kilobytes", "Megabytes", "Gigabytes", "Terabytes"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, dataOptions);

        String[] timeOptions = {"Milliseconds", "Seconds", "Minutes", "Hours", "Days"};
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, timeOptions);


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
                    case "Mass":
                        txtUnitFrom.setAdapter(massAdapter);
                        txtUnitTo.setAdapter(massAdapter);
                        break;
                    case "Area":
                        txtUnitFrom.setAdapter(areaAdapter);
                        txtUnitTo.setAdapter(areaAdapter);
                        break;
                    case "Data":
                        txtUnitFrom.setAdapter(dataAdapter);
                        txtUnitTo.setAdapter(dataAdapter);
                        break;
                    case "Time":
                        txtUnitFrom.setAdapter(timeAdapter);
                        txtUnitTo.setAdapter(timeAdapter);
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
                        conversion = lengthConversion(convertFrom, convertTo ,numberToConvert);
                        break;
                    case "Mass":
                        conversion = lengthConversion(convertFrom, convertTo ,numberToConvert);
                        break;
                    case "Area":
                        conversion = lengthConversion(convertFrom, convertTo ,numberToConvert);
                        break;
                    case "Data":
                        conversion = lengthConversion(convertFrom, convertTo ,numberToConvert);
                        break;
                    case "Time":
                        conversion = lengthConversion(convertFrom, convertTo ,numberToConvert);
                        break;
                    default:
                        //TODO: Make error snackbar
                        break;
                }

                // Displays the converted number with a decimal, only when necessary
                // TODO: display unit short form instead of whole word
                String conversionMessage;
                if (conversion % 1 == 0) {
                    String numberToConvertString = String.valueOf(numberToConvert);
                    String convertedNumberString = String.valueOf(conversion);

                    String numberToConvertNoDecimal = numberToConvertString.substring(0, numberToConvertString.indexOf("."));
                    String convertedNumberNoDecimal = convertedNumberString.substring(0, convertedNumberString.indexOf("."));

                    conversionMessage = numberToConvertNoDecimal + " " + convertFrom + " is " + convertedNumberNoDecimal + " " + convertTo;
                }
                else {
                    conversionMessage = numberToConvert + " " + convertFrom + " is " + conversion + " " + convertTo;
                }

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

    private double lengthConversion(String convertFrom, String convertTo, double numberToConvert){
        double conversion = 0.0;



        return conversion;
    }
}