public class Polynomial {
    double[] coefficients;
    
    public Polynomial() {
        coefficients=new double[1];
        coefficients[0] = 0;
    }
    
    public Polynomial(double[] coefficients) {
        this.coefficients =coefficients;
    }
    
    public Polynomial add(Polynomial other) {
        int size1 = this.coefficients.length;
        int size2 = other.coefficients.length;
        int maxSize = size1;
        if (size2>maxSize) {
            maxSize = size2;
        }
        
        double[] result = new double[maxSize];
        
        for (int i = 0; i < maxSize; i++) {
            double coeff1 = 0;
            double coeff2 = 0;
            
            if (i < size1) {
                coeff1 = this.coefficients[i];
            }
            if (i < size2) {
                coeff2 = other.coefficients[i];
            }
            
            result[i] = coeff1 + coeff2;
        }
        
        return new Polynomial(result);
    }
    
    public double evaluate(double x) {
        double result = 0;
        
        for (int i = 0; i < coefficients.length; i++) {
            double term = coefficients[i];
            for (int j = 0; j<i; j++) {
                term = term * x;
            }
            result = result + term;
        }
        
        return result;
    }
    
    public boolean hasRoot(double x) {
        double value = evaluate(x);
        if (value ==0) {
            return true;
        } else {
            return false;
        }
    }
}