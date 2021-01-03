package programs;

import java.util.Date;

public class TimeComplexityAnalysis {
	static int items[] = { 10, 0, 5, 45, 0, 50, 45, 96, 45, 10, 45, 12, 41, 5, 96, 45, 10, 45, 45, 96, 45, 10, 45, 45,
			96, 45, 10, 45, 45 };

	// O(1)
	public static void constantComplexity() {
		System.out.println("Array length : " + items.length);
		System.out.println("***********************constantComplexity*****************");
		long milliseconds = new Date().getTime();
		System.out.println(items[10]);
		System.out.println("Time Taken " + (new Date().getTime() - milliseconds) + "ms");
	}

	// O(n) O(2n) = O(n)
	public static void linearComplexity() {
		String str = "";
		System.out.println("***********************linearComplexity O(n)*****************");
		long milliseconds = new Date().getTime();
		for (int i = 0; i < items.length; i++) {
			str += items[i];
		}
		System.out.println("Time Taken " + (new Date().getTime() - milliseconds) + "ms");

		System.out.println("***********************linearComplexity O(4n)*****************");
		str = "";
		milliseconds = new Date().getTime();
		for (int i = 0; i < items.length; i++) {
			str += items[i];
		}
		for (int i = 0; i < items.length; i++) {
			str += items[i];
		}
		for (int i = 0; i < items.length; i++) {
			str += items[i];
		}
		for (int i = 0; i < items.length; i++) {
			str += items[i];
		}
		System.out.println("Time Taken " + (new Date().getTime() - milliseconds) + "ms");
		System.out.println("***********************linearComplexity O(1+n/2+100) --Drop the constants--*****************");
		str = "";
		milliseconds = new Date().getTime();
		System.out.println(items[0]);

		int middleIndex = items.length / 2;
		int index = 0;

		while (index < middleIndex) {
			str+=items[index];
			index++;
		}

		for (int i = 0; i < 100; i++) {
			str+="hi";
		}
		System.out.println("Time Taken " + (new Date().getTime() - milliseconds) + "ms");
	}

	// O(n^2) //O(n^3)
	public static void quadraticComplexity() {
		String str = "";
		System.out.println("***********************quadraticComplexity n^2*****************");
		long milliseconds = new Date().getTime();
		for (int firstItem : items) {
			for (int secondItem : items) {
				str += (firstItem + secondItem) + " ";
			}
		}
		System.out.println("Time Taken " + (new Date().getTime() - milliseconds) + "ms");
		str = "";
		System.out.println("***********************quadraticComplexity n^3*****************");
		milliseconds = new Date().getTime();
		for (int firstItem : items) {
			for (int secondItem : items) {
				for (int thirdItem : items) {
					str += (firstItem + secondItem + thirdItem) + " ";
				}
			}
		}
		System.out.println("Time Taken " + (new Date().getTime() - milliseconds) + "ms");
		str = "";
		System.out.println("***********************quadraticComplexity n^3 --Drop the less significant terms --*****************");
		milliseconds = new Date().getTime();
		for (int firstItem : items) {
			for (int secondItem : items) {
				for (int thirdItem : items) {
					str += (firstItem + secondItem + thirdItem) + " ";
				}
			}
		}
		for (int firstItem : items) {
			str+=firstItem;
		}
		System.out.println("Time Taken " + (new Date().getTime() - milliseconds) + "ms");
		

	}

	public static void main(String[] args) {
		constantComplexity();
		linearComplexity();
		quadraticComplexity();
	}

}
