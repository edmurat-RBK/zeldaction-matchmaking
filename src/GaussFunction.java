public class GaussFunction {

    public double mu;
    public double sigma;

    public GaussFunction(double mu, double sigma) {
        this.mu = mu;
        this.sigma = sigma;

        if(this.sigma == 0.0) {
            this.sigma += 0.001;
        }
    }

    public double function(double x) {
        return (1 / (sigma * Math.sqrt(2 * Math.PI))) * (Math.exp(-(Math.pow(x - mu , 2)/(2 * Math.pow(sigma , 2)))));
    }

    public double simpson(double lowerBound, double higherBound, int pass) {
        pass += pass % 2;
        int z = 4;
        double deltaX = (higherBound - lowerBound) / pass;
        double somme = function(lowerBound) + function(higherBound);

        for(int i=1; i<pass; i++) {
            somme += z * function(lowerBound + i * deltaX);
            z = 6 - z;
        }

        return (somme * deltaX) / 3;
    }
}
