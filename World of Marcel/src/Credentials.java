public class Credentials {
    private String email;
    private String password;

    Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean checkCredentials(String email, String password) {
        if(email.equals(this.email) && password.equals(this.password))
            return true;
        else
            return false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
