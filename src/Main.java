package Connect4;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("hello");
        System.out.println("Enter the size of the array:");
        Scanner sc = new Scanner(System.in);
        int sizeArray = sc.nextInt();
        int v[] = new int[sizeArray];
        System.out.println(v.length);
        for (int i = 0; i < v.length; i++) {
            v[i] = i + 1;
        }
        for (int i = 0; i < v.length; i++) {
            System.out.print(v[i] + ",");
        }
    }
}
