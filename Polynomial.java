import java.io.*;
import java.util.*;

public class Polynomial {
    double[] coefficients;
    int[] exponents;
    
    public Polynomial() {
        coefficients=new double[1];
        exponents=new int[1];
        coefficients[0] = 0;
        exponents[0] = 0;
    }
    
    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients =coefficients;
        this.exponents =exponents;
    }
    
    public Polynomial(File file) throws IOException {
        Scanner sc = new Scanner(file);
        String line = sc.nextLine();
        sc.close();
        
        line = line.replace("-", "+-");
        String[] terms = line.split("\\+");
        
        int count = 0;
        for (int i = 0; i<terms.length; i++) {
            if (terms[i].length() >0) {
                count++;
            }
        }
        
        coefficients = new double[count];
        exponents = new int[count];
        int index = 0;
        
        for (int i = 0; i<terms.length; i++) {
            if (terms[i].length() >0) {
                String term = terms[i];
                
                if (term.contains("x")) {
                    int xPos = term.indexOf("x");
                    String coeffPart = term.substring(0, xPos);
                    String expPart = "";
                    if (xPos +1 <term.length()) {
                        expPart = term.substring(xPos +1);
                    }
                    
                    if (coeffPart.length() ==0) {
                        coefficients[index] = 1;
                    } else if (coeffPart.equals("-")) {
                        coefficients[index] = -1;
                    } else {
                        coefficients[index] = Double.parseDouble(coeffPart);
                    }
                    
                    if (expPart.length() ==0) {
                        exponents[index] = 1;
                    } else {
                        exponents[index] = Integer.parseInt(expPart);
                    }
                } else {
                    coefficients[index] = Double.parseDouble(term);
                    exponents[index] = 0;
                }
                index++;
            }
        }
    }
    
    public Polynomial add(Polynomial other) {
        int maxTerms = this.coefficients.length + other.coefficients.length;
        double[] tempCoeffs = new double[maxTerms];
        int[] tempExps = new int[maxTerms];
        int size = 0;
        
        for (int i = 0; i<this.coefficients.length; i++) {
            tempCoeffs[size] = this.coefficients[i];
            tempExps[size] = this.exponents[i];
            size++;
        }
        
        for (int i = 0; i<other.coefficients.length; i++) {
            boolean found = false;
            for (int j = 0; j<size; j++) {
                if (tempExps[j] ==other.exponents[i]) {
                    tempCoeffs[j] = tempCoeffs[j] + other.coefficients[i];
                    found = true;
                    break;
                }
            }
            if (!found) {
                tempCoeffs[size] = other.coefficients[i];
                tempExps[size] = other.exponents[i];
                size++;
            }
        }
        
        double[] finalCoeffs = new double[size];
        int[] finalExps = new int[size];
        for (int i = 0; i<size; i++) {
            finalCoeffs[i] = tempCoeffs[i];
            finalExps[i] = tempExps[i];
        }
        
        return new Polynomial(finalCoeffs, finalExps);
    }
    
    public Polynomial multiply(Polynomial other) {
        int maxTerms = this.coefficients.length * other.coefficients.length;
        double[] tempCoeffs = new double[maxTerms];
        int[] tempExps = new int[maxTerms];
        int size = 0;
        
        for (int i=0; i<this.coefficients.length; i++) {
            for (int j = 0; j<other.coefficients.length; j++) {
                double newCoeff = this.coefficients[i] * other.coefficients[j];
                int newExp = this.exponents[i] + other.exponents[j];
                
                boolean found = false;
                for (int k = 0; k<size; k++) {
                    if (tempExps[k] ==newExp) {
                        tempCoeffs[k] = tempCoeffs[k] + newCoeff;
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    tempCoeffs[size] = newCoeff;
                    tempExps[size] = newExp;
                    size++;
                }
            }
        }
        
        double[] finalCoeffs =new double[size];
        int[] finalExps = new int[size];
        for (int i=0; i<size; i++) {
            finalCoeffs[i] = tempCoeffs[i];
            finalExps[i] = tempExps[i];
        }
        
        return new Polynomial(finalCoeffs, finalExps);
    }
    
    public double evaluate(double x) {
        double result = 0;
        
        for (int i = 0; i<coefficients.length; i++) {
            double term = coefficients[i];
            for (int j = 0; j<exponents[i]; j++) {
                term = term * x;
            }
            result = result + term;
        }
        
        return result;
    }
    
    public boolean hasRoot(double x) {
        double value =evaluate(x);
        if (value ==0) {
            return true;
        } else {
            return false;
        }
    }
    
    public void saveToFile(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        
        for (int i = 0; i<coefficients.length; i++) {
            if (i >0 && coefficients[i]>=0) {
                writer.write("+");
            }
            
            writer.write(coefficients[i] + "");
            
            if (exponents[i]>0) {
                writer.write("x");
                if (exponents[i] >1) {
                    writer.write(exponents[i] + "");
                }
            }
        }
        
        writer.close();
    }
}