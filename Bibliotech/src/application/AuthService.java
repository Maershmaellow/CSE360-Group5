package application;

import objects.User;

public class AuthService {
    
 
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
    
  
    public static User signUp(String asuriteId, String password, boolean isAdmin) {
        if (!DataStore.isSignupEnabled) {
            System.out.println("Signup is currently disabled");
            return null;
        }
        
        try {
            int asuId = Integer.parseInt(asuriteId);
            
            // Check if user already exists
            for (User user : DataStore.users) {
                if (user.getAsuID() == asuId) {
                    System.out.println("User with this ASU ID already exists");
                    return null;
                }
            }
            
            // Create new user
            User newUser = new User(asuId, password, isAdmin);
            DataStore.users.add(newUser);
            
            System.out.println("User created successfully: " + asuId);
            return newUser;
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid ASU ID format");
            return null;
        }
    }
    
  
    public static User signUp(String asuriteId, String password) {
        return signUp(asuriteId, password, false);
    }
    
    
    public static boolean isAsuIdAvailable(String asuriteId) {
        try {
            int asuId = Integer.parseInt(asuriteId);
            
            for (User user : DataStore.users) {
                if (user.getAsuID() == asuId) {
                    return false;
                }
            }
            return true;
            
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
