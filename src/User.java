// User.java
public class User {
    private String personalNumber;
    private String pin;
    private Account salaryAccount;
    private Account savingsAccount;

    // Kanske personnummer borde kopplas till Lönekontot och sen slumpar jag fram ett sparkonto a la Nordea
    // Borde ha testat initialBalance 0
    public User(String personalNumber, String pin) {
        this.personalNumber = personalNumber;
        this.pin = pin;
        this.salaryAccount = new Account(personalNumber + "001", 1000); // Lönekonto
        this.savingsAccount = new Account(personalNumber + "002", 1000); // Sparkonto
    }

    public boolean validatePin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public Account getSalaryAccount() {
        return salaryAccount;
    }

    public Account getSavingsAccount() {
        return savingsAccount;
    }
}