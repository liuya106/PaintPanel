package ca.utoronto.utm.floatingpoint;

public class Questions {
	public static void main(String[] args) {
		example();
		q1(); // 1 MARK
		q2(); // 3 MARKS
		q3(); // 5 MARKS
	}

	public static void report(String label, String bits, String interpretation, float value) {
		System.out.println(label + "=" + bits + "=" + interpretation + "=" + value);
	}

	public static void example() {
		String label, bits, interpretation;
		float value;

		float f1 = 1.27f;
		label = "f1";
		bits = "0[01111111]01000101000111101011100";
		interpretation = "+1.01000101000111101011100x2^(0)";
		value = f1;
		report(label, bits, interpretation, value);

		float f2 = (float)Math.pow(2, 11);
		label = "f2";
		bits = "0[10001010]00000000000000000000000";
		interpretation = "+1.00000000000000000000000x2^(11)";
		value = f2;
		report(label, bits, interpretation, value);

		float f3 = (f1 * f2);
		label = "f3";
		bits = "0[10001010]01000101000111101011100";
		interpretation = "+1.01000101000111101011100x2^(11)";
		value = f3;
		report(label, bits, interpretation, value);

		float f4 = 7.199f;
		label = "f4";
		bits = "0[10000001]11001100101111000110101";
		interpretation = "+1.11001100101111000110101x2^(2)";
		value = f4;
		report(label, bits, interpretation, value);

		float f5 = f3 + f4;
		label = "f5";
		bits = "0[10001010]01000110000001010001011";
		interpretation = "+1.01000110000001010001011x2^(11)";
		value = f5;
		report(label, bits, interpretation, value);

		/**
		 * Output:
		 * f1=0[01111111]01000101000111101011100=+1.01000101000111101011100x2^(0)=1.27
		 * f2=0[10001010]00000000000000000000000=+1.00000000000000000000000x2^(11)=2048.0
		 * f3=0[10001010]01000101000111101011100=+1.01000101000111101011100x2^(11)=2600.96
		 * f4=0[10000001]11001100101111000110101=+1.11001100101111000110101x2^(2)=7.199
		 * f5=0[10001010]01000110000001010001011=+1.01000110000001010001011x2^(11)=2608.159
		 */
	}

	public static void q1() {
		String label, bits, interpretation;
		float value;

		float q1f1 = 7.35f; 
		label = "q1f1";
		bits = "0[10000001]11010110011001100110011"; // FIX THIS
		interpretation = "+1.11010110011001100110011x2^(2)"; // FIX THIS
		value = q1f1;
		report(label, bits, interpretation, value);
	}

	public static void q2() {
		String label, bits, interpretation;
		float value;

		float q2f1 = 121.91f;
		label = "q2f1";
		bits = "0[10000101]11100111101000111101100"; 
		interpretation = "+1.11100111101000111101100x2^(6)"; 
		value = q2f1;
		report(label, bits, interpretation, value);

		float q2f2 = 7.3f;
		label = "q2f2";
		bits = "0[10000001]11010011001100110011010"; 
		interpretation = "+1.11010011001100110011010x2^(2)"; 
		value = q2f2;
		report(label, bits, interpretation, value);

		float q2f3 = q2f1 + q2f2;
		label = "q2f3";
		bits = "0[10000110]00000010011010111000011"; 
		interpretation = "+1.00000010011010111000011x2^(7)"; 
		value = q2f3;
		report(label, bits, interpretation, value);
	}

	public static void q3() {
		/**
		 * As in the lecture notes example, we are going to find a and b such that
		 * a,b>0 and (a+b)-a == 0 and (a-a)+b==b
		 */
		String label, bits, interpretation;
		float value;

		float a = 1.76f * (float)Math.pow(2, 33); // DONT CHANGE THIS
		label = "a";
		bits = "0[00100001]11000010100011110101110"; // FIX THIS
		interpretation = "+1.11000010100011110101110x2^(33)"; // FIX THIS
		value = a;
		report(label, bits, interpretation, value);

		float b = 512.0f; // MAKE THIS AS LARGE AS POSSIBLE 
		label = "b";
		bits = "0[00001001]00000000000000000000000"; // FIX THIS
		interpretation = "1.00000000000000000000000x2^(9)"; // FIX THIS
		value = b;
		report(label, bits, interpretation, value);

		if (a>b && b>0f)
			System.out.println("b looks OK! (is it as large as possible?)");
		else
			System.out.println("b is wrong!");
		
		float q3f3 = a + b;
		if (a == q3f3)
			System.out.println("(a+b) is OK! (has the error we are looking for)");
		else
			System.out.println("(a+b) is wrong!");

		float q3f4 = q3f3 - a;
		if (q3f4 == 0.0f)
			System.out.println("(a+b)-a is OK! (has the error we are looking for)");
		else
			System.out.println("(a+b)-a is wrong!");
	}
}
