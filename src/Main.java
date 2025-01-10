// Main.java
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Bank bank = new Bank();
    private static final int MAX_LOGIN_ATTEMPTS = 3;

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    running = false;
                    System.out.println("Programmet avslutas. Välkommen åter!");
                    break;
                default:
                    System.out.println("Ogiltigt val. Försök igen.");
            }
        }
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n=== MINIBANK ===");
        System.out.println("1. Skapa användare");
        System.out.println("2. Logga in");
        System.out.println("3. Avsluta");
        System.out.print("Välj ett alternativ: ");
    }

    private static void createUser() {
        try {
            System.out.print("Ange personnummer (10 siffror): ");
            String personalNumber = scanner.nextLine();
            if (!personalNumber.matches("\\d{10}")) {
                throw new IllegalArgumentException("Personnumret måste vara 10 siffror utan bindestreck.");
            }

            System.out.print("Ange PIN-kod (4 siffror): ");
            String pin1 = scanner.nextLine();
            if (!pin1.matches("\\d{4}")) {
                throw new IllegalArgumentException("PIN-koden måste vara 4 siffror.");
            }

            System.out.print("Upprepa PIN-kod (4 siffror): ");
            String pin2 = scanner.nextLine();
            if (!pin2.matches("\\d{4}")) {
                throw new IllegalArgumentException("PIN-koden måste vara 4 siffror.");
            }

            if (!pin1.equals(pin2)) {
                throw new IllegalArgumentException("PIN-koderna matchar inte.");
            }

            if (bank.createUser(personalNumber, pin1)) {
                System.out.println("Användare skapad framgångsrikt!");
            } else {
                System.out.println("Kunde inte skapa användare. Användaren finns redan.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Fel: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ett oväntat fel uppstod. Försök igen.");
        }
    }
    private static void loginUser() {
        int attempts = 0;
        while (attempts < MAX_LOGIN_ATTEMPTS) {
            System.out.print("Ange personnummer: ");
            String personalNumber = scanner.nextLine();
            System.out.print("Ange PIN-kod: ");
            String pin = scanner.nextLine();

            User user = bank.login(personalNumber, pin);
            if (user != null) {
                handleLoggedInUser(user);
                return;
            }

            attempts++;
            System.out.println("Fel inloggningsuppgifter. Försök kvar: " + (MAX_LOGIN_ATTEMPTS - attempts));
        }
        System.out.println("För många felaktiga försök. Programmet avslutas.");
        System.exit(0);
    }

    private static void handleLoggedInUser(User user) {
        boolean loggedIn = true;
        while (loggedIn) {
            displayUserMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    showAccounts(user);
                    break;
                case 2:
                    handleTransfer(user);
                    break;
                case 3:
                    loggedIn = false;
                    System.out.println("Du är nu utloggad.");
                    break;
                default:
                    System.out.println("Ogiltigt val. Försök igen.");
            }
        }
    }

    private static void displayUserMenu() {
        System.out.println("\n=== MINA SIDOR ===");
        System.out.println("1. Visa mina konton");
        System.out.println("2. Gör överföring");
        System.out.println("3. Logga ut");
        System.out.print("Välj ett alternativ: ");
    }

    private static void showAccounts(User user) {
        System.out.println("\nLönekonto: " + user.getSalaryAccount().getAccountNumber() +
                "\nSaldo: " + user.getSalaryAccount().getBalance() + " kr");
        System.out.println("\nSparkonto: " + user.getSavingsAccount().getAccountNumber() +
                "\nSaldo: " + user.getSavingsAccount().getBalance() + " kr");
    }

    private static void handleTransfer(User user) {
        System.out.println("\nVälj från vilket konto:");
        System.out.println("1. Lönekonto");
        System.out.println("2. Sparkonto");
        int accountChoice = getIntInput();

        Account fromAccount = (accountChoice == 1) ? user.getSalaryAccount() : user.getSavingsAccount();
        if (fromAccount == null) {
            System.out.println("Ogiltigt kontoval.");
            return;
        }

        System.out.print("Ange mottagarkonto: "); // Saknar förklaring på hur personnummer byggs upp.
        String toAccountNumber = scanner.nextLine();
        System.out.print("Ange belopp: "); // Saknar hantering av ',' och '.'
        double amount = getDoubleInput();

        if (bank.transfer(fromAccount, toAccountNumber, amount)) {
            System.out.println("Överföringen genomförd!");
        } else {
            System.out.println("Överföringen misslyckades. Kontrollera kontonummer och saldo.");
        }
    }
// Kod från AI (Har inte koll på hur parseInt fungerar. Kanske jag borde ha använt scanner.nextint)
// https://codingtechroom.com/question/understanding-integer-parsing-in-java-integer-parseint-vs-scanner-nextint
    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }

}
// Kod från AI (Har inte koll på hur parseInt fungerar. Borde ha använt scanner.nextdouble)
// Se ovan
    private static double getDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}