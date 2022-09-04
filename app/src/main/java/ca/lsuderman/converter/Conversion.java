package ca.lsuderman.converter;

public class Conversion {
    private int conversionId;
    private String conversionType;
    private String convertFrom;
    private String convertTo;
    private double numberToConvert;
    private double convertedNumber;

    public Conversion(int conversionId, String conversionType, String convertFrom, String convertTo, double numberToConvert, double convertedNumber) {
        this.conversionId = conversionId;
        this.conversionType = conversionType;
        this.convertFrom = convertFrom;
        this.convertTo = convertTo;
        this.numberToConvert = numberToConvert;
        this.convertedNumber = convertedNumber;
    }

    public int getConversionId() {
        return conversionId;
    }

    public void setConversionId(int conversionId) {
        this.conversionId = conversionId;
    }

    public String getConversionType() {
        return conversionType;
    }

    public void setConversionType(String conversionType) {
        this.conversionType = conversionType;
    }

    public String getConvertFrom() {
        return convertFrom;
    }

    public void setConvertFrom(String convertFrom) {
        this.convertFrom = convertFrom;
    }

    public String getConvertTo() {
        return convertTo;
    }

    public void setConvertTo(String convertTo) {
        this.convertTo = convertTo;
    }

    public double getNumberToConvert() {
        return numberToConvert;
    }

    public void setNumberToConvert(double numberToConvert) {
        this.numberToConvert = numberToConvert;
    }

    public double getConvertedNumber() {
        return convertedNumber;
    }

    public void setConvertedNumber(double convertedNumber) {
        this.convertedNumber = convertedNumber;
    }

    public Conversion(){

    }
}
