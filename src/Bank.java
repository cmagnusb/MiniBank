// Bank.java
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<User> users;

    public Bank() {
        this.users = new ArrayList<>();
    }
// Notera att jag har två createUser.
// Den här är AI kod
    public boolean createUser(String personalNumber, String pin) {
        // Validera personnummer och PIN
        if (!isValidPersonalNumber(personalNumber) || !isValidPin(pin)) {
            return false;
        }

        // Kontrollera om användaren redan finns
        if (findUser(personalNumber) != null) {
            return false;
        }

        users.add(new User(personalNumber, pin));
        return true;
    }

    public User login(String personalNumber, String pin) {
        User user = findUser(personalNumber);
        if (user != null && user.validatePin(pin)) {
            return user;
        }
        return null;
    }

    private User findUser(String personalNumber) {
        return users.stream()
                .filter(u -> u.getPersonalNumber().equals(personalNumber))
                .findFirst()
                .orElse(null);
    }

    private boolean isValidPersonalNumber(String personalNumber) {
        return personalNumber.matches("\\d{10}");
    }

    private boolean isValidPin(String pin) {
        return pin.matches("\\d{4}");
    }

    public boolean transfer(Account fromAccount, String toAccountNumber, double amount) {
        if (amount <= 0) {
            return false;
        }

        // Hitta mottagarkontot. Kräver att man skriver in i format personnummer + 001  | 002 för Löne respektive Spar
        Account toAccount = findAccount(toAccountNumber);
        if (toAccount == null) {
            return false;
        }

        // Genomför överföringen
        if (fromAccount.withdraw(amount)) {
            toAccount.deposit(amount);
            return true;
        }
        return false;
    }
// Object i parentesen i if-villkoret känns lite udda, men är  så som python skulle ha gjort det
    private Account findAccount(String accountNumber) {
        for (User user : users) {
            if (user.getSalaryAccount().getAccountNumber().equals(accountNumber)) {
                return user.getSalaryAccount();
            }
            if (user.getSavingsAccount().getAccountNumber().equals(accountNumber)) {
                return user.getSavingsAccount();
            }
        }
        return null;
    }
}
