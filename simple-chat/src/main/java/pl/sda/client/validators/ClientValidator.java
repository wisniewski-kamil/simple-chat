package pl.sda.client.validators;

public class ClientValidator {
    public static boolean validateMessage(String message){
        return !message.isBlank();
    }

    public static boolean validateLogin(String username, String password){
        return !(username.isBlank() || password.isBlank());
    }

    public static String validateRegister(String username, String firstPassword, String secondPassword){
        if(username.isBlank() || firstPassword.isBlank() || secondPassword.isBlank()){
            return "All fields must be filled";
        } else if (!firstPassword.equals(secondPassword)){
            return "You must repeat the same password";
        } else if (firstPassword.contains(" ") || username.contains(" ")){
            return "Username and password can't contain spaces!";
        }
        return "";
    }
}
