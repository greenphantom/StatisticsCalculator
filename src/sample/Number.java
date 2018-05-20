package sample;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Number implements Comparable<Number>{
    private int Int;
    private double Doub;

    public Number(int Int) {
        this.Int = Int;
        Doub = Int;
    }

    public Number(double D) {
        Int = (int) D;
        Doub = D;
    }
    public Number(Number N) {
        Int = N.getInt();
        Doub = N.getDoub();
    }

    public int getInt() {
        return Int;
    }

    public double getDoub() {
        return Doub;
    }

    public void changeNumber(int I){
        Int = I;
        Doub = I;
    }

    public void changeNumber(double D){
        Int = (int) D;
        Doub = D;
    }

    public double getDoub(int decimalPlaces) {
        return round(Doub,decimalPlaces);
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void add(int n){
        changeNumber (Int + n);
    }

    public void add(double n){
        changeNumber (Doub + n);
    }

    public void add(Number n){
        changeNumber (Doub + n.getDoub());
    }
    
    public void subtract(int n){
        changeNumber (Int - n);
    }

    public void subtract(double n){
        changeNumber (Doub - n);
    }

    public void subtract(Number n){
        changeNumber (Doub - n.getDoub());
    }

    public void multiply(int n){
        changeNumber (Int * n);
    }

    public void multiply(double n){
        changeNumber (Doub * n);
    }

    public void multiply(Number n){
        changeNumber (Doub * n.getDoub());
    }

    public void divide(int n){
        changeNumber (Int / n);
    }

    public void divide(double n){
        changeNumber (Doub / n);
    }

    public void divide(Number n){
        changeNumber (Doub / n.getDoub());
    }

    public void modulo(int n){
        changeNumber (Int % n);
    }

    public void modulo(double n){
        changeNumber (Doub % n);
    }

    public void modulo(Number n){
        changeNumber (Doub % n.getDoub());
    }

    public void pow(double n){
        changeNumber(Math.pow(Doub, n));
    }

    public void pow(Number n){
        changeNumber(Math.pow(Doub, n.getDoub()));
    }

    public boolean greaterThan(int n){
        return Int > n;
    }

    public boolean lessThan(int n){
        return Int < n;
    }

    public boolean equals(int n){
        return Int == n;
    }
    
    public boolean greaterThan(double  n){
        return Doub > n;
    }

    public boolean lessThan(double  n){
        return Doub < n;
    }

    public boolean equals(double  n){
        return Doub == n;
    }
    
    public boolean greaterThan(Number  n){
        return Doub > n.getDoub();
    }

    public boolean lessThan(Number  n){
        return Doub < n.getDoub();
    }

    public boolean equals(Number  n){
        if (this == n) return true;
        if (n == null) return false;
        return Doub == n.getDoub();
    }

    @Override
    public int compareTo(Number number) {
        return (int) (Doub - number.getDoub());

        // Descending sort...
        //return number.getDoub() - Doub;
    }
}
