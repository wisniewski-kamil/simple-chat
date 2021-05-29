package pl.sda.client.validators;

public class ClientValidator {
    public static boolean validateMessage(String message){
        return !message.isEmpty();
    }

    public static boolean validateLogin(String username, String password){
        return !(username.isEmpty() || password.isEmpty());
    }

    public static String validateRegister(String username, String firstPassword, String secondPassword){
        if(username.isEmpty() || firstPassword.isEmpty() || secondPassword.isEmpty()){
            return "All fields must be filled";
        } else if (!firstPassword.equals(secondPassword)){
            return "You must repeat the same password";
        } else if (firstPassword.contains(" ") || username.contains(" ")){
            return "Username and password can't contain spaces!";
        }
        return "";
    }
}
