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
import android.widget.inline.InlineContentView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private Button btnConvert;
    private TextView txtNumberToConvert, txtConverted;
    private AutoCompleteTextView txtUnitFrom, txtUnitTo, txtConversion;
    private TextInputLayout inlConversion, inlUnitFrom, inlUnitTo, inlNumberToConvert;

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
        inlConversion = findViewById(R.id.inlConversion);
        inlUnitFrom = findViewById(R.id.inlUnitFrom);
        inlUnitTo = findViewById(R.id.inlUnitTo);
        inlNumberToConvert = findViewById(R.id.inlNumberToConvert);

        String[] conversionOptions = {"Temperature", "Length", "Mass", "Time"};
        ArrayAdapter<String> conversionAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, conversionOptions);
        txtConversion.setAdapter(conversionAdapter);

        String[] temperatureOptions = {"Celsius", "Fahrenheit", "Kelvin"};
        ArrayAdapter<String> temperatureAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, temperatureOptions);

        String[] lengthOptions = {"Millimetres", "Centimetres", "Metres", "Kilometers", "Inches", "Feet", "Yards", "Miles"};
        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, lengthOptions);

        String[] massOptions = {"Milligram", "Grams", "Kilograms", "Ounces", "Pounds"};
        ArrayAdapter<String> massAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, massOptions);

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

                if (validateData()){
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
                            conversion = massConversion(convertFrom, convertTo ,numberToConvert);
                            break;
                        case "Time":
                            conversion = timeConversion(convertFrom, convertTo ,numberToConvert);
                            break;
                    }

                    // Displays the converted number with a decimal, only when necessary
                    // TODO: display unit short form instead of whole word
                    //       round number to 2 decimal places
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
            }
        });
    }

    private boolean validateData(){
        boolean validData = true;

        inlConversion.setError(null);
        inlUnitFrom.setError(null);
        inlUnitTo.setError(null);
        inlNumberToConvert.setError(null);

        if (String.valueOf(txtConversion.getText()).equals("")){
            inlConversion.setError("Select Conversion Type");
            validData = false;
        }

        if (String.valueOf(txtUnitFrom.getText()).equals("")){
            inlUnitFrom.setError("Select Unit");
            validData = false;
        }

        if (String.valueOf(txtUnitTo.getText()).equals("")){
            inlUnitTo.setError("Select Unit");
            validData = false;
        }

        if (String.valueOf(txtNumberToConvert.getText()).equals("")){
            inlNumberToConvert.setError("Enter number to convert");
            validData = false;
        }

        return validData;
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

        switch (convertFrom) {
            case "Millimetres":
                switch (convertTo) {
                    case "Centimetres":
                        conversion = numberToConvert / 10.0;
                        break;
                    case "Metres":
                        conversion = numberToConvert / 1000.0;
                        break;
                    case "Kilometers":
                        conversion = numberToConvert / 1000000.0;
                        break;
                    case "Inches":
                        conversion = numberToConvert / 25.4;
                        break;
                    case "Feet":
                        conversion = numberToConvert / 304.8;
                        break;
                    case "Yards":
                        conversion = numberToConvert / 914.4;
                        break;
                    case "Miles":
                        conversion = numberToConvert / 1609344.0;
                        break;
                }
                break;
            case "Centimetres":
                switch (convertTo) {
                    case "Millimetres":
                        conversion = numberToConvert * 10.0;
                        break;
                    case "Metres":
                        conversion = numberToConvert / 100.0;
                        break;
                    case "Kilometers":
                        conversion = numberToConvert / 100000.0;
                        break;
                    case "Inches":
                        conversion = numberToConvert / 2.54;
                        break;
                    case "Feet":
                        conversion = numberToConvert / 30.48;
                        break;
                    case "Yards":
                        conversion = numberToConvert / 91.44;
                        break;
                    case "Miles":
                        conversion = numberToConvert / 160934.4;
                        break;
                }
            case "Metres":
                switch (convertTo) {
                    case "Millimetres":
                        conversion = numberToConvert * 1000.0;
                        break;
                    case "Centimetres":
                        conversion = numberToConvert * 100.0;
                        break;
                    case "Kilometers":
                        conversion = numberToConvert / 1000.0;
                        break;
                    case "Inches":
                        conversion = numberToConvert * 39.37;
                        break;
                    case "Feet":
                        conversion = numberToConvert / 3.281;
                        break;
                    case "Yards":
                        conversion = numberToConvert / 1.094;
                        break;
                    case "Miles":
                        conversion = numberToConvert / 1609.0;
                        break;
                }
                break;
            case "Kilometers":
                switch (convertTo) {
                    case "Millimetres":
                        conversion = numberToConvert * 1000000.0;
                        break;
                    case "Centimetres":
                        conversion = numberToConvert * 100000.0;
                        break;
                    case "Metres":
                        conversion = numberToConvert * 1000.0;
                        break;
                    case "Inches":
                        conversion = numberToConvert * 39370.0;
                        break;
                    case "Feet":
                        conversion = numberToConvert * 3281.0;
                        break;
                    case "Yards":
                        conversion = numberToConvert * 1094.0;
                        break;
                    case "Miles":
                        conversion = numberToConvert / 1.609;
                        break;
                }
                break;
            case "Inches":
                switch (convertTo) {
                    case "Millimetres":
                        conversion = numberToConvert * 25.4;
                        break;
                    case "Centimetres":
                        conversion = numberToConvert * 2.54;
                        break;
                    case "Metres":
                        conversion = numberToConvert / 39.37;
                        break;
                    case "Kilometers":
                        conversion = numberToConvert / 39370.0;
                        break;
                    case "Feet":
                        conversion = numberToConvert * 12.0;
                        break;
                    case "Yards":
                        conversion = numberToConvert * 36.0;
                        break;
                    case "Miles":
                        conversion = numberToConvert / 63360.0;
                        break;
                }
                break;
            case "Feet":
                switch (convertTo) {
                    case "Millimetres":
                        conversion = numberToConvert * 304.8;
                        break;
                    case "Centimetres":
                        conversion = numberToConvert * 30.48;
                        break;
                    case "Metres":
                        conversion = numberToConvert / 3.281;
                        break;
                    case "Kilometers":
                        conversion = numberToConvert / 3281.0;
                        break;
                    case "Inches":
                        conversion = numberToConvert / 12.0;
                        break;
                    case "Yards":
                        conversion = numberToConvert / 3.0;
                        break;
                    case "Miles":
                        conversion = numberToConvert / 5280.0;
                        break;
                }
                break;
            case "Yards":
                switch (convertTo) {
                    case "Millimetres":
                        conversion = numberToConvert * 914.4;
                        break;
                    case "Centimetres":
                        conversion = numberToConvert * 91.44;
                        break;
                    case "Metres":
                        conversion = numberToConvert / 1.094;
                        break;
                    case "Kilometers":
                        conversion = numberToConvert / 1094.0;
                        break;
                    case "Inches":
                        conversion = numberToConvert * 36.0;
                        break;
                    case "Feet":
                        conversion = numberToConvert * 3.0;
                        break;
                    case "Miles":
                        conversion = numberToConvert / 1760.0;
                        break;
                }
                break;
            case "Miles":
                switch (convertTo) {
                    case "Millimetres":
                        conversion = numberToConvert / 1609344.0;
                        break;
                    case "Centimetres":
                        conversion = numberToConvert * 160900.0;
                        break;
                    case "Metres":
                        conversion = numberToConvert / 1609.0;
                        break;
                    case "Kilometers":
                        conversion = numberToConvert * 1.609;
                        break;
                    case "Inches":
                        conversion = numberToConvert * 63360.0;
                        break;
                    case "Feet":
                        conversion = numberToConvert * 5280.0;
                        break;
                    case "Yards":
                        conversion = numberToConvert * 1760.0;
                        break;
                }
                break;
        }

        return conversion;
    }

    private double massConversion(String convertFrom, String convertTo, double numberToConvert){
        double conversion = 0.0;

        switch (convertFrom) {
            case "Milligram":
                switch (convertTo) {
                    case "Grams":
                        conversion = numberToConvert / 1000.0;
                        break;
                    case "Kilograms":
                        conversion = numberToConvert / 1000000.0;
                        break;
                    case "Ounces":
                        conversion = numberToConvert / 28350.0;
                        break;
                    case "Pounds":
                        conversion = numberToConvert / 453600.0;
                        break;
                }
                break;
            case "Grams":
                switch (convertTo) {
                    case "Milligrams":
                        conversion = numberToConvert * 1000.0;
                        break;
                    case "Kilograms":
                        conversion = numberToConvert / 1000.0;
                        break;
                    case "Ounces":
                        conversion = numberToConvert / 28.35;
                        break;
                    case "Pounds":
                        conversion = numberToConvert / 453.6;
                        break;
                }
                break;
            case "Kilograms":
                switch (convertTo) {
                    case "Milligrams":
                        conversion = numberToConvert * 1000000.0;
                        break;
                    case "Grams":
                        conversion = numberToConvert * 1000.0;
                        break;
                    case "Ounces":
                        conversion = numberToConvert * 35.274;
                        break;
                    case "Pounds":
                        conversion = numberToConvert * 2.205;
                        break;
                }
                break;
            case "Ounces":
                switch (convertTo) {
                    case "Milligrams":
                        conversion = numberToConvert * 28350.0;
                        break;
                    case "Grams":
                        conversion = numberToConvert * 28.35;
                        break;
                    case "Kilograms":
                        conversion = numberToConvert / 35.274;
                        break;
                    case "Pounds":
                        conversion = numberToConvert / 16.0;
                        break;
                }
            case "Pounds":
                switch (convertTo) {
                    case "Milligrams":
                        conversion = numberToConvert * 453600.0;
                        break;
                    case "Grams":
                        conversion = numberToConvert * 453.6;
                        break;
                    case "Kilograms":
                        conversion = numberToConvert / 2.205;
                        break;
                    case "Ounces":
                        conversion = numberToConvert * 16.0;
                        break;
                }
                break;
        }

        return conversion;
    }

    private double timeConversion(String convertFrom, String convertTo, double numberToConvert){
        double conversion = 0.0;

        switch (convertFrom) {
            case "Milliseconds":
                switch (convertTo) {
                    case "Seconds":
                        conversion = numberToConvert / 1000.0;
                        break;
                    case "Minutes":
                        conversion = numberToConvert / 60000.0;
                        break;
                    case "Hours":
                        conversion = numberToConvert / 3600000.0;
                        break;
                    case "Days":
                        conversion = numberToConvert / 86400000.0;
                        break;
                }
                break;
            case "Seconds":
                switch (convertTo) {
                    case "Milliseconds":
                        conversion = numberToConvert * 1000.0;
                        break;
                    case "Minutes":
                        conversion = numberToConvert / 60.0;
                        break;
                    case "Hours":
                        conversion = numberToConvert / 3600.0;
                        break;
                    case "Days":
                        conversion = numberToConvert / 86400.0;
                        break;
                }
                break;
            case "Minutes":
                switch (convertTo) {
                    case "Milliseconds":
                        conversion = numberToConvert * 60000.0;
                        break;
                    case "Seconds":
                        conversion = numberToConvert * 60.0;
                        break;
                    case "Hours":
                        conversion = numberToConvert / 60.0;
                        break;
                    case "Days":
                        conversion = numberToConvert / 1440.0;
                        break;
                }
                break;
            case "Hours":
                switch (convertTo) {
                    case "Milliseconds":
                        conversion = numberToConvert * 3600000.0;
                        break;
                    case "Seconds":
                        conversion = numberToConvert * 3600.0;
                        break;
                    case "Minutes":
                        conversion = numberToConvert * 60.0;
                        break;
                    case "Days":
                        conversion = numberToConvert / 24.0;
                        break;
                }
                break;
            case "Days":
                switch (convertTo) {
                    case "Milliseconds":
                        conversion = numberToConvert * 86400000.0;
                        break;
                    case "Seconds":
                        conversion = numberToConvert * 86400.0;
                        break;
                    case "Minutes":
                        conversion = numberToConvert * 1440.0;
                        break;
                    case "Hours":
                        conversion = numberToConvert * 24.0;
                        break;
                }
                break;
        }

        return conversion;
    }
}