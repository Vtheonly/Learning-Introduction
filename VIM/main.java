// main.java
public class Main {

    public static int add(int a, int b) {
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static void main(String[] args) {
        int resultAdd = add(20, 40);
        System.out.println("Result of add: " + resultAdd);

        int resultSubtract = subtract(40, 20);
        System.out.println("Result of subtract: " + resultSubtract);

        int[] numbers = {1, 2, 3, 4, 5};
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        System.out.println("Sum of numbers: " + sum);
    }
}