package ca.utoronto.utm.floatingpoint;

public class Quadratic {
	public float a,b,c;

	public Quadratic(float a, float b, float c) {
		this.a=a; this.b=b; this.c=c;
	}

	public float f(float x) {
		return a*x*x+b*x+c;
	}
	
	public float vertex() {
		return -b/(2*a);
	}
	
	public float discriminant() {
		return b*b-4*a*c;
	}
	
	public float root1() {
		float x=(-b+(float)Math.sqrt(discriminant()))/(2*a);
		return x;
	}

	public float root2() {
		float x=(-b-(float)Math.sqrt(discriminant()))/(2*a);
		return x;
	}

	public int numRoots() {
		float d=discriminant();
		if(d<0)return 0;
		if(d==0)return 1;
		return 2;
	}

	/**
	 * We approximate the absolute error behind a floating point
	 * calculation. Note that we can't actually compute
	 * the real absoluteError, since we don't have access
	 * to the real result of the calculation, and we are using
	 * floating point arithmetic to calculate.
	 * 
	 * @param a What the actual result of the calculation should be.
	 * @param c The computed result of the calculation.
	 * @return |a-c|
	 */
	public double absoluteError(double a, double c){
		return Math.abs(a-c);
	}

	/**
	 * We approximate the relative error behind a floating point
	 * calculation. Note that we can't actually compute
	 * the real relativeError, since we don't have access
	 * to the real result of the calculation, and we are using
	 * floating point arithmetic to calculate.
	 * 
	 * @param a What the actual result of the calculation should be.
	 * @param c The computed result of the calculation.
	 * @return
	 */
	public double relativeError(double a, double c){
		return Math.abs(a-c)/Math.abs(a);
	}
	
	public String toString() {
		String s="";
		s += "a="+a+"\n";
		s += "b="+b+"\n";
		s += "c="+c+"\n";
		s += "discriminant="+discriminant()+"\n";
		s += "vertex="+vertex()+"\n";
		s += "numRoots="+numRoots()+"\n";
		if(numRoots()>=1) {
			s += "root1="+root1()+"\n";

		}
		if(numRoots()==2) {
			s += "root2="+root2()+"\n";
		}
		return s;
	}
	
	public static void main(String [] args) {
		for(float factor=1.0f;factor<1e+10;factor*=10) {
			float a = factor*1.0f;
			float b = factor*2.0f;
			float c = factor*1.0f;

			Quadratic q = new Quadratic(a, b, c);
			System.out.println(q);
		}
	}
}
