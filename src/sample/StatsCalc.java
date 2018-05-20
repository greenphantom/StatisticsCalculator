package sample;//Run in terminal command: clear && clear && javac RunStats.java && java RunStats

// TODO: Weighted Mean
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.math.*;

public class StatsCalc {

	public StatsCalc() {};

    public static void calculateStats(int ...dataSet) // Uses varargs for maximum effectiveness
    {
        Number data[] = convertToNumArray(dataSet);
        performCalculation(false,data);
    }

    public static void calculateStats(double ...dataSet) // Uses varargs for maximum effectiveness
    {
        Number data[] = convertToNumArray(dataSet);
        performCalculation(true,data);
    }

    private static void performCalculation(boolean doub,Number ...data){
        System.out.println("===============================================================");
        System.out.println("\nDescriptive Stats...\n");
        int n = getLength(data);
        print("Number of data elements: ",n);

        // using for each loop to display contents of data
        print(doub,"Elements in the data set: ",data);

        // Sorted data set
        sort(data);
        print(doub,"Sorted Elements in the data set: ",data);

        // Sum of the data set
        print(doub,"Sum of the data set: ", getSum(data));

        // Calculating the mean...
        Number mean = getMean(data);
        print(true,"Mean of the data (rounded): ", mean);

        // Calculating the trimmed mean...
        Number trimmedMean = getTrimmedMean(.2,data);
        print(true,"Trimmed mean of the data (rounded w/ alpha as 20%): ", trimmedMean);

        // Calculating the median...
        Number median = getMedian(data);
        print(true,"Median of the data - Q2 (rounded): ", median);

        // Calculating Q1...
        Number Q1 = getQ1(data);
        print(true,"Q1 - 25th Percentile (rounded): ", Q1);

        // Calculating Q3...
        Number Q3 = getQ3(data);
        print(true,"Q3 - 75th Percentile (rounded): ", Q3);

        // Calculating the mode...
        print(doub,"Mode(s) of the data: ", getMode(data));

        // Calculating the standard deviation...
        Number SD = getStandardDeviation(data,mean);
        print(true,"Standard Deviation of the data: ", SD);

        // Calculating the variance...
        Number Var = getVariance(SD);
        print(true,"Variance of the data: ", Var);

        // Calculating the range...
        print(doub,"Range of the data: ", getRange(data));

        // Calculating the quartile range...
        Number Qr = getQuartileRange(Q1,Q3);
        print(true,"Quartile range of the data: ", Qr);

        // Calculating the quartile deviation...
        Number Qd = getQuartileDeviation(Q1,Q3);
        print(true,"Quartile deviation of the data: ",Qd);

        // Calculating data outliers...
        print(true,"Outliers in the data: ", getOutliers(data,Q1,Q3,Qr));
    }

	private static Number[] convertToNumArray(int ...dataSet) {
    	Number nums[] = new Number[getLength(dataSet)];
    	for (int i = 0; i < getLength(dataSet); ++i){
    		nums[i] = new Number(dataSet[i]);
		}
		return nums;
	}

	private static Number[] convertToNumArray(double ...dataSet) {
    	Number nums[] = new Number[getLength(dataSet)];
    	for (int i = 0; i < getLength(dataSet); ++i){
    		nums[i] = new Number(dataSet[i]);
		}
		return nums;
	}
    
    public static void calculatePooledStats(int[] ...dataSets){
		printDataSets(dataSets);
        Number[] data = poolSets(dataSets);
		print(false,"Combined (sorted) pooled set: ", data);
		performCalculation(false, data);
	}
	
	private static void printDataSets(int[] ...dataSets){
		int setNum = 1;
		for (int[] set: dataSets) {
			String phrase = "Set number ".concat(Integer.toString(setNum));
			print(phrase.concat(": "), set);
			setNum++;
		}
	}
	
	private static Number[] poolSets(int[]... dataSets){
		ArrayList<Number> pool = new ArrayList<>();
		for (int[] set: dataSets){
			for (int i: set){
				pool.add(new Number(i));
			}
		}
		Number[] result = pool.toArray(new Number[0]);
		sort(result);
		return result;
	}

	private static Number[] convertToArray(ArrayList<Number> dataSet){
		return dataSet.toArray(new Number[0]);
	}

    private static int getLength(int dataSet[]){
        return dataSet.length;
    }

    private static int getLength(double dataSet[]){
        return dataSet.length;
    }
		
    private static int getLength(Number[] dataSet){
        return dataSet.length;
    }

	private static Number getMean(Number dataSet[]){
		Number mean = getSum(dataSet);
		
        if (mean.greaterThan(0)) {
            mean.divide(getLength(dataSet));
            return mean;
        }
        else { return null; }
	}
	
	private static Number getMedian(Number dataSet[]){
        int n = getLength(dataSet);
        if (n == 0) { return null;}
		if (n == 1) { return dataSet[0]; }
		sort(dataSet);
		Number median = new Number(0);
        if (n % 2 == 0 && n > 0) { // Meaning n is even
			Number x = new Number(dataSet[(n/2)-1]);
			Number y = new Number(dataSet[(n/2)]);
            x.add(y);
            median.add(x);
            median.multiply(.5);
        }
        else if (n > 0){
			median = dataSet[(n/2)];
		}
		return median;
	}

	public static void print(String phrase, int ...dataSet){
		System.out.print(phrase);

        for (int o: dataSet)
            System.out.print(o + " ");
        System.out.println("\n");
	}

	public static void print(boolean doub, String phrase, Number ...dataSet){
		System.out.print(phrase);
        try {
            if (doub) {
                printDoub(dataSet);
            } else {
                printInt(dataSet);
            }
        } catch (NullPointerException e){
            System.out.println("Unable to calculate the result...\n");
        }
	}

	private static void printInt(Number ...dataSet){
	    for (Number number: dataSet)
            System.out.print(number.getInt() + " ");
        System.out.println("\n");
	}

	private static void printDoub(Number ...dataSet){
        for (Number number: dataSet)
            System.out.print(number.getDoub(8) + " ");
        System.out.println("\n");
	}
	
	private static void print(String phrase, double ...dataSet){
		System.out.print(phrase);

        for (double o: dataSet)
            System.out.print(o + " ");
        System.out.println("\n");
	}
	
	private static void printInt(String phrase, Double ...dataSet){
		System.out.print(phrase);

        for (Double o: dataSet)
            System.out.print(o + " ");
        System.out.println("\n");
	}

	public static Number getTrimmedMean(double alpha, Number[] dataSet){
		if (getLength(dataSet) == 1) { return getMean(dataSet); }
		else {
			sort(dataSet);
			int discardRange = (int) (getLength(dataSet)*alpha);
			Number[] trimmedArray = Arrays.copyOfRange(dataSet,discardRange, getLength(dataSet)-discardRange);
			print(false, "Trimmed data set: ", trimmedArray);
			return getMean(trimmedArray);
		}
	}

    private static void sort(Number[] dataSet) {
        Arrays.sort(dataSet);
    }

	private static Number getQ1(Number[] dataSet){
		if (getLength(dataSet) < 2) { return null; }
		sort(dataSet);
		Number[] lowerHalf = Arrays.copyOfRange(dataSet,0, getLength(dataSet)/2);
		print(false, "Lower Half of the data: ",lowerHalf);
		return getMedian(lowerHalf);
	}

	private static Number getQ3(Number[] dataSet){
		if (getLength(dataSet) < 2) { return null; }
		sort(dataSet);
		Number[] upperHalf;
		if (getLength(dataSet) % 2 == 0) { upperHalf = Arrays.copyOfRange(dataSet,getLength(dataSet)/2, getLength(dataSet)); }
		else { upperHalf = Arrays.copyOfRange(dataSet,getLength(dataSet)/2+1, getLength(dataSet)); }
		print(false,"Upper Half of the data: ",upperHalf);
		return getMedian(upperHalf);
	}

	private static Number[] getMode(Number[] dataSet){
		HashMap<Double,Integer> tally = new HashMap<>();
		for (Number i : dataSet) {
			if (tally.containsKey(i.getDoub())) { tally.put(i.getDoub(),tally.get(i.getDoub())+1); }
			else { tally.put(i.getDoub(),1); }
		}

		Map.Entry<Double,Integer> maxEntry = null;
		ArrayList<Number> multipleModes = new ArrayList<>();
		for (Map.Entry<Double,Integer> entry: tally.entrySet()){
			if (maxEntry == null) {
				maxEntry = entry;
				multipleModes.add(new Number(entry.getKey()));
			}
			else if (entry.getValue().equals(maxEntry.getValue())) {
				multipleModes.add(new Number(entry.getKey()));
			}
			else if (entry.getValue().compareTo(maxEntry.getValue()) > 0){
				maxEntry = entry;
				multipleModes.clear();
				multipleModes.add(new Number(entry.getKey()));
			}
		}

		if (multipleModes.size() == getLength(dataSet)) { // If everything occurs at the same frequency, there should be no mode
			multipleModes.clear();
		}

		return convertToArray(multipleModes);
	}

	private static Number getStandardDeviation(Number[] dataSet, Number mean){
		if (getLength(dataSet) < 2) { return null; }
		Number sum = new Number(0);
		for (Number n: dataSet) {
		    Number i = new Number(n);
		    i.subtract(mean);
            i.pow(2);
            sum.add(i);
		}
		sum.divide(getLength(dataSet)-1);
        sum.pow(.5);
		return sum;
	}

	private static Number getStandardDeviation(Number[] dataSet){
		if (getLength(dataSet) < 2) { return null; }
		Number sum = new Number(0);
		Number mean = getMean(dataSet);
		for (Number n: dataSet) {
		    Number i = new Number(n);
		    i.subtract(mean);
            i.pow(2);
            sum.add(i);
		}
		sum.divide(getLength(dataSet)-1);
        sum.pow(.5);
		return sum;
	}

	private static Number getSum(Number[] dataSet){
		Number sum = new Number(0);
		for (Number i: dataSet) { sum.add(i); }
		return sum;
	}

	private static Number getVariance(Number[] dataSet, Number mean){
		Number SD = getStandardDeviation(dataSet,mean);
        SD.pow(2.0);
        return SD;
	}

    private static Number getVariance(Number[] dataSet){
        Number SD = getStandardDeviation(dataSet);
        SD.pow(2);
        return SD;
    }

    private static Number getVariance(Number SD){
        if (SD != null) {
            Number Variance = new Number(SD);
            Variance.pow(2.0);
            return Variance;
        }
        else {
            return null;
        }
    }

    private static Number getRange(Number[] dataSet){
		if (getLength(dataSet) < 2) { return null; }
		sort(dataSet);
		Number n = new Number(dataSet[getLength(dataSet)-1]);
        n.subtract(dataSet[0]);
		return (n); // Assuming data is sorted in ascending order
	}

    private static Number getQuartileRange(Number[] dataSet) {
        if (getQ3(dataSet) != null) {
            Number Qr = new Number(getQ3(dataSet));
            Qr.subtract(getQ1(dataSet));
            return Qr;
        }
        else {
            return null;
        }
	}

    private static Number getQuartileRange(Number Q1, Number Q3) {
        if (Q3 != null && Q1 != null) {
            Number temp = new Number(Q3);
            temp.subtract(Q1);
            return temp;
        }
        else {
            return null;
        }
	}

    private static Number getQuartileDeviation(Number Q1, Number Q3) {
        if (Q3 != null && Q1 != null) {
            Number Qd = getQuartileRange(Q1, Q3);
            Qd.divide(2);
            return Qd;
        }
        else {
            return null;
        }
	}

    private static Number[] getOutliers(Number[] dataSet, Number Q1, Number Q3, Number quartileRange) {
        if (dataSet.length < 2) {
            return null;
        }
		ArrayList<Number> outliers = new ArrayList<>();
		Number upperLim = new Number(quartileRange);
		upperLim.multiply(1.5);
		upperLim.add(Q3);

		Number lowerLim = new Number(Q1);
		Number temp = new Number(quartileRange);
		temp.multiply(1.5);
		lowerLim.subtract(temp);
		for (Number i: dataSet) {
			if (i.greaterThan(upperLim) || i.lessThan(lowerLim)) { outliers.add(i); }
		}
		return convertToArray(outliers);
	}

	/*static Number getPooledMean(Number[][] dataSets){
		Number Denom = new Number(0);
		Number totalNumerator = new Number(0);
		for (Number[] set: dataSets) {
			int n = getLength(set);
			Number mean = getMean(set);
			mean.multiply(n);
			totalNumerator.add(mean);
			Denom.add(n);
		}
        totalNumerator.divide(Denom);
		return (totalNumerator);
	}
	
	static Number getPooledVariance(Number[][] dataSets){
		Number totalDenominator = new Number(0);
		Number totalNumerator = new Number(0);
        int counter = 0;
		for (Number[] set: dataSets) {
			int n = getLength(set);
			Number Sd = getStandardDeviation(n,set,getMean(set);
			Sd.pow(2);
			Sd.multiply(n-1);
			totalNumerator.add(Sd);
			totalDenominator.add(n);
			counter++;
		}
		totalDenominator.subtract(counter);
		totalNumerator.divide(totalDenominator);
		return totalNumerator;
	}*/

	public static Number getR(int[] x, int[] y){
		if (getLength(x) != getLength(y)) {
			return null; // Throw exception in future
		}
		Number[] numsX = convertToNumArray(x);
		Number[] numsY = convertToNumArray(y);
		Number cov = getCovariance(numsX, numsY);
		Number SDx = getStandardDeviation(numsX);
		Number SDy = getStandardDeviation(numsY);
		SDx.multiply(SDy);
		cov.divide(SDx);
		return cov;
		
	}
	
	private static Number getCovariance(Number[] x, Number[] y){
		int n = getLength(x);
		Number meanX = getMean(x);
		Number meanY = getMean(y);
		Number cov = new Number(0);
		for (int i = 0; i < n; ++i) {
		    Number temp = new Number(x[i]);
		    temp.multiply(y[i]);

		    Number temp2 = new Number(meanX);
		    temp2.multiply(meanY);
		    temp2.multiply(n);

		    temp.subtract(temp2);
			 cov.add(temp);
		}
		cov.divide(n-1);
		return cov;
	}
	
	static int[] translateData(double slope, double shift, int ...dataSet){
		int j = 0;
		for (int i: dataSet){
			dataSet[j] = (int) ((slope * i) + shift);
			j++;
		}
		return dataSet;
	}
	
	static double[] translateData(double slope, double shift, double ...dataSet){
		int j = 0;
		for (double i: dataSet){
			dataSet[j] = (slope * i) + shift;
			j++;
		}
		return dataSet;
	}
}
