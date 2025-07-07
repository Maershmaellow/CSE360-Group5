package application;
import objects.User;
//CODE//
public class AuthService {
    
    /**
    //authenticate user wth proper credentials.
     * @param asuriteId The user's ASU ID
     * @param password The user's password
     * @return User object if authentication successful, null otherwise
     */
    
    public static User login(String asuriteId, String password) {
        try {
            int asuId = Integer.parseInt(asuriteId);
            
            for (User user : DataStore.users) {
                if (user.getAsuID() == asuId && user.getPassword().equals(password)) {
                    if (user.isActive()) {
                        return user;
                    } else {
                        System.out.println("Account is deactivated");
                        return null;
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ASU ID format");
        }
        
        return null; // Authentication failed
    }
    
    /**
     * Creates a new user account
     * @param asuriteId The new user's ASU ID
     * @param password The new user's password
     * @param isAdmin Whether the user should have admin privileges
     * @return The created User object, or null if signup failed
     */

    //sign up user
    public static User signUp(String asuriteId, String password, boolean isAdmin){
        if (!DataStore.isSignupEnabled){
            System.out.println("Signup is currently disabled");
            return null;
        }
        try{
            int asuId = Integer.parseInt(asuriteId);
            //check if the user exists in list
            for (User user : DataStore.users){
                if (user.getAsuID() == asuId){
                    System.out.println("User with this ASU ID already exists");
                    return null;
                }
            }
            //create a new user
            User newUser = new User(asuId, password, isAdmin);
            DataStore.users.add(newUser);
            System.out.println("User created successfully: " + asuId);
            return newUser;
        }
        catch (NumberFormatException e){
            System.out.println("Invalid ASU ID format");
            return null;
        }
    }
    public static User signUp(String asuriteId, String password){
        return signUp(asuriteId, password, false);
    }
//is asuId valid?
    public static boolean isAsuIdAvailable(String asuriteId){
        try {
            int asuId = Integer.parseInt(asuriteId);
            for (User user : DataStore.users){
                if (user.getAsuID() == asuId){
                    return false;
                }
            }
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
}
