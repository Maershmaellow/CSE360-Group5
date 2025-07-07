package application;
import javafx.collections.ObservableList;
import objects.User;
import objects.Transaction;
//CODE//
public class AdminService{
static ObservableList<User> getUsers(){
        return DataStore.users;
    }
    //eliminate user
    public static boolean removeUser(User user){
        if (user == null){
            System.out.println("Cannot remove null user");
            return false;
        }
        if (user.getAdminPriveleges()){
            System.out.println("Cannot remove admin user");
            return false;
        }
        boolean removed = DataStore.users.remove(user);
        if (removed){
            System.out.println("User removed: " + user.getAsuID());
        }
        return removed;
    }
    //user status
    public static boolean toggleUserStatus(User user){
        if (user == null) {
            System.out.println("Cannot toggle status of null user");
            return false;
        }
        if (user.getAdminPriveleges()){
            System.out.println("Cannot deactivate admin user");
            return false;
        }
        user.setActive(!user.isActive());
        String status = user.isActive() ? "activated" : "deactivated";
        System.out.println("User " + user.getAsuID() + " has been " + status);
        return true;
    }
    //transaction for admin
    public static ObservableList<Transaction> getTransactions(){
        return DataStore.transactions;
    }
    //turn on or off system features
    public static boolean toggleSystemFeature(String feature, boolean isEnabled){
        switch (feature.toLowerCase()){
            case "signup":
                DataStore.isSignupEnabled = isEnabled;
                System.out.println("Signup " + (isEnabled ? "enabled" : "disabled"));
                return true;
            case "buying":
                DataStore.isBuyingEnabled = isEnabled;
                System.out.println("Buying " + (isEnabled ? "enabled" : "disabled"));
                return true;
            case "selling":
                DataStore.isSellingEnabled = isEnabled;
                System.out.println("Selling " + (isEnabled ? "enabled" : "disabled"));
                return true;  
            default:
                System.out.println("Unknown feature: " + feature);
                return false;
        }
    }
  //is system feature on or off?
    public static boolean getSystemFeatureStatus(String feature){
        switch (feature.toLowerCase()){
            case "signup":
                return DataStore.isSignupEnabled;
            case "buying":
                return DataStore.isBuyingEnabled;
            case "selling":
                return DataStore.isSellingEnabled;
            default:
                return false;
        }
    }
//make a new admin user
    public static User createAdminUser(String asuriteId, String password){
        return AuthService.signUp(asuriteId, password, true);
    }
    //get the statiscs from sales of the store//
    public static String getSystemStats(){
        int totalUsers = DataStore.users.size();
        int activeUsers = (int) DataStore.users.stream().filter(User::isActive).count();
        int totalBooks = DataStore.books.size();
        int availableBooks = (int) DataStore.books.stream().filter(book -> !book.getSold()).count();
        int totalTransactions = DataStore.transactions.size();
        double totalRevenue = DataStore.transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
        StringBuilder stats = new StringBuilder();
        stats.append("=== SYSTEM STATISTICS ===\n");
        stats.append("Total Users: ").append(totalUsers).append("\n");
        stats.append("Active Users: ").append(activeUsers).append("\n");
        stats.append("Total Books: ").append(totalBooks).append("\n");
        stats.append("Available Books: ").append(availableBooks).append("\n");
        stats.append("Total Transactions: ").append(totalTransactions).append("\n");
        stats.append("Total Revenue: $").append(String.format("%.2f", totalRevenue)).append("\n");
        stats.append("\n=== SYSTEM FEATURES ===\n");
        stats.append("Signup Enabled: ").append(DataStore.isSignupEnabled).append("\n");
        stats.append("Buying Enabled: ").append(DataStore.isBuyingEnabled).append("\n");
        stats.append("Selling Enabled: ").append(DataStore.isSellingEnabled).append("\n");
        return stats.toString();
    }
    //reset system data for fresh start
    public static void resetSystemData() {
        System.out.println("WARNING: Resetting all system data!");
        DataStore.users.clear();
        DataStore.books.clear();
        DataStore.transactions.clear();
        System.out.println("System data reset complete");
    }
}
