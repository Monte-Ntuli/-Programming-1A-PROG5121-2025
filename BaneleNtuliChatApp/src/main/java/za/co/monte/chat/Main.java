package za.co.monte.chat;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Registration ===");
        System.out.print("First name: ");
        String first = sc.nextLine().trim();
        System.out.print("Last name: ");
        String last = sc.nextLine().trim();
        System.out.print("Username (must contain _ and be <= 5 chars): ");
        String user = sc.nextLine().trim();
        System.out.print("Password (>=8, uppercase, number, special): ");
        String pass = sc.nextLine();
        System.out.print("Cellphone (+27#########): ");
        String phone = sc.nextLine().trim();

        Login login = new Login(first, last, user, pass, phone);
        String regMsg = login.registerUser();
        System.out.println(regMsg);

        System.out.println("\n=== Login ===");
        System.out.print("Enter username: ");
        String u = sc.nextLine().trim();
        System.out.print("Enter password: ");
        String p = sc.nextLine();

        boolean ok = login.loginUser(u, p);
        System.out.println(login.returnLoginStatus(ok));
    }
}
