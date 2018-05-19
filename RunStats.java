//Run in terminal command: clear && clear && javac RunStats.java && java RunStats

// TODO: Weighted Mean
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.math.*;

class RunStats{
	public static void main(String args[])
	{
		
		// Calling the varargs method with different number
		// of parameters
		StatsCalc.calculateStats();            // no parameter
		StatsCalc.calculateStats(100);         // one parameter
		StatsCalc.calculateStats(21,19);       // two parameter
		StatsCalc.calculateStats(31,28,34);    // three parameter
		StatsCalc.calculateStats(4,2,3,1);     // four parameters
		StatsCalc.calculateStats(52,6,48,23,17,94,78,48,15,29);     // ten parameters
		StatsCalc.calculateStats(10,85,2,15,35,87,83,31,2,60,93,90,7,91,27,16,77,16,38,81,92,84,42);     // 23 parameters
		
		int[] set0 = {10,85,2,15,35,87,83,31,2,60,93,90,7,91,27,16,77,16,38,81,92,84,42};
		int[] set1 = {52,6,48,23,17,94,78,48,15,29};
		int[] set2 = {31,28,34};
		
		System.out.println("===============================================================");
		System.out.println("\nPooled Stats...\n");
		StatsCalc.calculatePooledStats(set0,set1,set2);
		
		System.out.println("===============================================================");
		System.out.println("\nTransformed Stats...\n");
		StatsCalc.print("Before transformation: ", set1);
		int[] set3 = StatsCalc.translateData(2,8,set1);
		StatsCalc.print("Translated set (after f(x) = 2x+8 transformation): ", set3);
		StatsCalc.calculateStats(set3);
	}
}

class StatsCalc {

	public StatsCalc() {};

    static void calculateStats(int ...a) // Uses varargs for maximum effectiveness
    {
        int n = getLength(a);
        System.out.println("===============================================================");
        System.out.println("\nDescriptive Stats...\n");
        print("Number of data elements: ",n);

        // using for each loop to display contents of a
        print("Elements in the data set: ",a);

        // Sorted data set
        sort(a);
		print("Sorted Elements in the data set: ",a);

		// Sum of the data set
		print("Sum of the data set: ", getSum(a));

        // Calculating the mean...
        double mean = round(getMean(n,a),8);
        print("Mean of the data (rounded): ", mean);

        // Calculating the trimmed mean...
		print("Trimmed mean of the data (rounded w/ alpha as 20%): ",round(getTrimmedMean(n,.2,a),8));

        // Calculating the median...
        double median = getMedian(n,a);
		print("Median of the data - Q2 (rounded): ",round(median,8));

		// Calculating Q1...
		double Q1 = round(getQ1(n,a,median),8);
		print("Q1 - 25th Percentile (rounded): ",Q1);

		// Calculating Q3...
		double Q3 = round(getQ3(n,a,median),8);
		print("Q3 - 75th Percentile (rounded): ",Q3);

		// Calculating the mode...
		printInt("Mode(s) of the data: ", getMode(n,a));

		// Calculating the standard deviation...
		print("Standard Deviation of the data: ", round(getStandardDeviation(n,a,mean),8));

		// Calculating the variance...
		print("Variance of the data: ", round(getVariance(n,a,mean),8));

		// Calculating the range...
		print("Range of the data: ", getRange(n,a));

		// Calculating the quartile deviation...
		print("Quartile deviation of the data: ", round(getQuartileDeviation(Q1,Q3),8));

		// Calculating the quartile range...
		double Qr = round(getQuartileRange(Q1,Q3),8);
		print("Quartile range of the data: ", Qr);

		// Calaculating data outliers
		printInt("Outliers in the data: ", getOutliers(a,Q1,Q3,Qr));
    }
    
    static void calculateStats(double ...a) // Uses varargs for maximum effectiveness
    {
        int n = getLength(a);
        System.out.println("===============================================================");
        System.out.println("\nDescriptive Stats...\n");
        print("Number of data elements: ",n);

        // using for each loop to display contents of a
        print("Elements in the data set: ",a);

        // Sorted data set
        sort(a);
		print("Sorted Elements in the data set: ",a);

		// Sum of the data set
		print("Sum of the data set: ", getSum(a));

        // Calculating the mean...
        double mean = round(getMean(n,a),8);
        print("Mean of the data (rounded): ", mean);

        // Calculating the trimmed mean...
		print("Trimmed mean of the data (rounded w/ alpha as 20%): ",round(getTrimmedMean(n,.2,a),8));

        // Calculating the median...
        double median = getMedian(n,a);
		print("Median of the data - Q2 (rounded): ",round(median,8));

		// Calculating Q1...
		double Q1 = round(getQ1(n,a,median),8);
		print("Q1 - 25th Percentile (rounded): ",Q1);

		// Calculating Q3...
		double Q3 = round(getQ3(n,a,median),8);
		print("Q3 - 75th Percentile (rounded): ",Q3);

		// Calculating the mode...
		printInt("Mode(s) of the data: ", getMode(n,a));

		// Calculating the standard deviation...
		print("Standard Deviation of the data: ", round(getStandardDeviation(n,a,mean),8));

		// Calculating the variance...
		print("Variance of the data: ", round(getVariance(n,a,mean),8));

		// Calculating the range...
		print("Range of the data: ", getRange(n,a));

		// Calculating the quartile deviation...
		print("Quartile deviation of the data: ", round(getQuartileDeviation(Q1,Q3),8));

		// Calculating the quartile range...
		double Qr = round(getQuartileRange(Q1,Q3),8);
		print("Quartile range of the data: ", Qr);

		// Calaculating data outliers
		printInt("Outliers in the data: ", getOutliers(a,Q1,Q3,Qr));
    }
    
    static void calculatePooledStats(int[] ...dataSets){
		printDataSets(dataSets);
		int[] pooledSet = poolSets(dataSets);
		print("Combined (sorted) pooled set: ", pooledSet);
		print("Pooled mean of the set: ", round(getPooledMean(dataSets),8));
		print("Pooled variance of the set: ", round(getPooledVariance(dataSets),8));
		calculateStats(pooledSet);
	}
	
	static void printDataSets(int[] ...dataSets){
		int setNum = 1;
		for (int[] set: dataSets) {
			String phrase = "Set number ".concat(Integer.toString(setNum));
			print(phrase.concat(": "), set);
			setNum++;
		}
	}
	
	static int[] poolSets(int[] ...dataSets){ 
		ArrayList<Integer> pool = new ArrayList<>();
		for (int[] set: dataSets){
			for (int i: set){
				pool.add(i);
			}
		}
		int[] result = convertToIntArray(pool);
		sort(result);
		return result;
	}
	
	static Integer[] convertToArray(ArrayList<Integer> a){
		return a.toArray(new Integer[0]);
	}
	
	static Double[] convertToDoubleArray(ArrayList<Double> a){
		return a.toArray(new Double[0]);
	}

    static void sort(int[] a){
			Arrays.sort(a);
			return;
		}
		
    static void sort(double[] a){
			Arrays.sort(a);
			return;
		}

    static int getLength(int dataSet[]){
			return dataSet.length;
		}
		
    static int getLength(double dataSet[]){
			return dataSet.length;
		}

	static double getMean(int n, int a[]){
		double sum = 0;
        double mean = 0;

        for (int i: a) {
            sum += i;
        }

        if (sum > 0) { mean = (sum/n);}
        else { mean = 0;}
        return mean;
	}
	
	static double getMean(int n, double a[]){
		double sum = 0;
        double mean = 0;

        for (double i: a) {
            sum += i;
        }

        if (sum > 0) { mean = (sum/n);}
        else { mean = 0;}
        return mean;
	}

	static double getMedian(int n, int a[]){
		if (n == 1) { return a[0]; }
		sort(a);
		double median = 0;
        if (n % 2 == 0 && n > 0) { // Meaning n is even
			int x = a[(n/2)-1];
			int y = a[(n/2)];
            median = (x+y)*.5;
        }
        else if (n > 0){
			median = a[(n/2)];
		}
		return median;
	}
	
	static double getMedian(int n, double a[]){
		if (n == 1) { return a[0]; }
		sort(a);
		double median = 0;
        if (n % 2 == 0 && n > 0) { // Meaning n is even
			double x = a[(n/2)-1];
			double y = a[(n/2)];
            median = (x+y)*.5;
        }
        else if (n > 0){
			median = a[(n/2)];
		}
		return median;
	}

	static void print(String phrase, int ...a){
		System.out.print(phrase);

        for (int o: a)
            System.out.print(o + " ");
        System.out.println("\n");
	}

	static void print(String phrase, double ...a){
		System.out.print(phrase);

        for (double o: a)
            System.out.print(o + " ");
        System.out.println("\n");
	}

	static void printInt(String phrase, Integer ...a){
		System.out.print(phrase);

        for (Integer o: a)
            System.out.print(o + " ");
        System.out.println("\n");
	}
	
	static void printInt(String phrase, Double ...a){
		System.out.print(phrase);

        for (Double o: a)
            System.out.print(o + " ");
        System.out.println("\n");
	}
	
	static int[] convertToIntArray(ArrayList<Integer> arr){
		int[] result = new int[arr.size()];
		int j = 0;
		for (Integer i: arr){
			result[j] = i;
			j++;
		}
		return result;
	}

	static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	static double getTrimmedMean(int n, double alpha, int[] dataSet){
		if (n == 1) { return getMean(n,dataSet); }
		else {
			sort(dataSet);
			int discardRange = (int) (n*alpha);
			int[] trimmedArray = Arrays.copyOfRange(dataSet,discardRange, n-discardRange);
			print("Trimmed data set: ", trimmedArray);
			return getMean((n-(2*discardRange)),trimmedArray);
		}
	}
	
	static double getTrimmedMean(int n, double alpha, double[] dataSet){
		if (n == 1) { return getMean(n,dataSet); }
		else {
			sort(dataSet);
			int discardRange = (int) (n*alpha);
			double[] trimmedArray = Arrays.copyOfRange(dataSet,discardRange, n-discardRange);
			print("Trimmed data set: ", trimmedArray);
			return getMean((n-(2*discardRange)),trimmedArray);
		}
	}

	static double getQ1(int n, int[] dataSet, double median){
		if (n < 2) { return 0.0; }
		sort(dataSet);
		int[] lowerHalf = Arrays.copyOfRange(dataSet,0, n/2);
		print("Lower Half of the data: ",lowerHalf);
		return getMedian(getLength(lowerHalf),lowerHalf);
	}

	static double getQ3(int n, int[] dataSet, double median){
		if (n < 2) { return 0.0; }
		sort(dataSet);
		int[] upperHalf;
		if (n % 2 == 0) { upperHalf = Arrays.copyOfRange(dataSet,n/2, n); }
		else { upperHalf = Arrays.copyOfRange(dataSet,n/2+1, n); }
		print("Upper Half of the data: ",upperHalf);
		return getMedian(getLength(upperHalf),upperHalf);
	}

	static double getQ1(int n, double[] dataSet, double median){
		if (n < 2) { return 0.0; }
		sort(dataSet);
		double[] lowerHalf = Arrays.copyOfRange(dataSet,0, n/2);
		print("Lower Half of the data: ",lowerHalf);
		return getMedian(getLength(lowerHalf),lowerHalf);
	}

	static double getQ3(int n, double[] dataSet, double median){
		if (n < 2) { return 0.0; }
		sort(dataSet);
		double[] upperHalf;
		if (n % 2 == 0) { upperHalf = Arrays.copyOfRange(dataSet,n/2, n); }
		else { upperHalf = Arrays.copyOfRange(dataSet,n/2+1, n); }
		print("Upper Half of the data: ",upperHalf);
		return getMedian(getLength(upperHalf),upperHalf);
	}

	static Integer[] getMode(int n, int[] dataSet){
		HashMap<Integer,Integer> tally = new HashMap<>();
		for (int i : dataSet) {
			if (tally.containsKey(i)) { tally.put(i,tally.get(i)+1); }
			else { tally.put(i,1); }
		}

		Map.Entry<Integer,Integer> maxEntry = null;
		ArrayList<Integer> multipleModes = new ArrayList<>();
		for (Map.Entry<Integer,Integer> entry: tally.entrySet()){
			if (maxEntry == null) {
				maxEntry = entry;
				multipleModes.add(entry.getKey());
			}
			else if (entry.getValue().equals(maxEntry.getValue())) {
				multipleModes.add(entry.getKey());
			}
			else if (entry.getValue().compareTo(maxEntry.getValue()) > 0){
				maxEntry = entry;
				multipleModes.clear();
				multipleModes.add(entry.getKey());
			}
		}

		if (multipleModes.size() == n) { // If everything occurs at the same frequency, there should be no mode
			multipleModes.clear();
		}

		return convertToArray(multipleModes);
	}
	
	static Double[] getMode(int n, double[] dataSet){
		HashMap<Double,Integer> tally = new HashMap<>();
		for (double i : dataSet) {
			if (tally.containsKey(i)) { tally.put(i,tally.get(i)+1); }
			else { tally.put(i,1); }
		}

		Map.Entry<Double,Integer> maxEntry = null;
		ArrayList<Double> multipleModes = new ArrayList<>();
		for (Map.Entry<Double,Integer> entry: tally.entrySet()){
			if (maxEntry == null) {
				maxEntry = entry;
				multipleModes.add(entry.getKey());
			}
			else if (entry.getValue().equals(maxEntry.getValue())) {
				multipleModes.add(entry.getKey());
			}
			else if (entry.getValue().compareTo(maxEntry.getValue()) > 0){
				maxEntry = entry;
				multipleModes.clear();
				multipleModes.add(entry.getKey());
			}
		}

		if (multipleModes.size() == n) { // If everything occurs at the same frequency, there should be no mode
			multipleModes.clear();
		}

		return convertToDoubleArray(multipleModes);
	}

	static double getStandardDeviation(int n, int[] dataSet, double mean){
		if (n < 2) { return 0.0; }
		double sum = 0;
		for (int i: dataSet) { sum += Math.pow((i-mean),2); }
		return Math.sqrt((sum / (n-1)));
	}
	
	static double getStandardDeviation(int n, double[] dataSet, double mean){
		if (n < 2) { return 0.0; }
		double sum = 0;
		for (double i: dataSet) { sum += Math.pow((i-mean),2); }
		return Math.sqrt((sum / (n-1)));
	}

	static int getSum(int[] dataSet){
		int sum = 0;
		for (int i: dataSet) { sum += i; }
		return sum;
	}
	
	static double getSum(double[] dataSet){
		double sum = 0;
		for (double i: dataSet) { sum += i; }
		return sum;
	}

	static double getVariance(int n, int[] dataSet, double mean){
		return Math.pow(getStandardDeviation(n,dataSet,mean),2);
	}
	
	static double getVariance(int n, double[] dataSet, double mean){
		return Math.pow(getStandardDeviation(n,dataSet,mean),2);
	}

	static int getRange(int n, int[] dataSet){
		if (n < 2) { return 0; }
		sort(dataSet);
		return (dataSet[n-1]-dataSet[0]); // Assuming data is sorted in ascending order
	}
	
	static double getRange(int n, double[] dataSet){
		if (n < 2) { return 0; }
		sort(dataSet);
		return (dataSet[n-1]-dataSet[0]); // Assuming data is sorted in ascending order
	}

	static double getQuartileRange(int n, int[] dataSet, double median) {
		return getQ3(n,dataSet,median) - getQ1(n,dataSet,median);
	}

	static double getQuartileRange(double Q1, double Q3) {
		return Q3 - Q1;
	}

	static double getQuartileDeviation(double Q1, double Q3) {
		return getQuartileRange(Q1,Q3) / 2;
	}

	static Integer[] getOutliers(int[] dataSet, double Q1, double Q3, double quartileRange) {
		ArrayList<Integer> outliers = new ArrayList<>();
		for (int i: dataSet) {
			if (i > (Q3+(quartileRange*1.5)) || i < (Q1-(quartileRange*1.5))) { outliers.add(i); }
		}
		return outliers.toArray(new Integer[0]);
	}
	
	static Double[] getOutliers(double[] dataSet, double Q1, double Q3, double quartileRange) {
		ArrayList<Double> outliers = new ArrayList<>();
		for (double i: dataSet) {
			if (i > (Q3+(quartileRange*1.5)) || i < (Q1-(quartileRange*1.5))) { outliers.add(i); }
		}
		return convertToDoubleArray(outliers);
	}

	static double getPooledMean(int[][] dataSets){
		int totalDataLength = 0;
		double totalNumerator = 0;
		for (int[] set: dataSets) {
			int n = getLength(set); 
			totalNumerator += (n * getMean(n,set));
			totalDataLength += n;
		}
		return (totalNumerator/totalDataLength);
	}
	
	static double getPooledMean(double[][] dataSets){
		double totalDataLength = 0;
		double totalNumerator = 0;
		for (double[] set: dataSets) {
			int n = getLength(set); 
			totalNumerator += (n * getMean(n,set));
			totalDataLength += n;
		}
		return (totalNumerator/totalDataLength);
	}
	
	static double getPooledVariance(int[][] dataSets){
		int totalDenominator = 0;
		int counter = 0;
		double totalNumerator = 0;
		for (int[] set: dataSets) {
			int n = getLength(set); 
			totalNumerator += ((n-1) * Math.pow(getStandardDeviation(n,set,getMean(n,set)),2));
			totalDenominator += n;
			counter++;
		}
		return (totalNumerator/(totalDenominator-counter));
	}
	
	static double getPooledVariance(double[][] dataSets){
		int totalDenominator = 0;
		int counter = 0;
		double totalNumerator = 0;
		for (double[] set: dataSets) {
			int n = getLength(set); 
			totalNumerator += ((n-1) * Math.pow(getStandardDeviation(n,set,getMean(n,set)),2));
			totalDenominator += n;
			counter++;
		}
		return (totalNumerator/(totalDenominator-counter));
	}
	
	static double getR(int[] x, int[] y){
		if (getLength(x) != getLength(y)) {
			return 0.0; // Throw exception in future
		}
		double cov = getCovariance(x,y);
		double SDx = getStandardDeviation(getLength(x),x,getMean(getLength(x),x));
		double SDy = getStandardDeviation(getLength(y),y,getMean(getLength(y),y));
		return cov/(SDx*SDy);
		
	}
	
	static double getCovariance(int[] x, int[] y){
		int n = getLength(x);
		double meanX = getMean(getLength(x),x);
		double meanY = getMean(getLength(y),y);
		double cov = 0.0;
		for (int i = 0; i < n; ++i) {
			 cov += ((x[i]*y[i])-(n*meanX*meanY));
		}
		return cov/(n-1);
	}
	
	static int[] translateData(double slope, double shift, int ...a){
		int j = 0;
		for (int i: a){
			a[j] = (int) ((slope * i) + shift);
			j++;
		}
		return a;
	}
	
	static double[] translateData(double slope, double shift, double ...a){
		int j = 0;
		for (double i: a){
			a[j] = (slope * i) + shift;
			j++;
		}
		return a;
	}
}
