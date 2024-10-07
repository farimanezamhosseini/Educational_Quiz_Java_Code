public class User {

    private int id;
    private String username;
    private String password;
    private int[] attempts = new int[100000];
    private int cAttempts = 0;

    public User() {
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password, int[] attempts, int cAttempts) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.attempts = attempts;
        this.cAttempts = cAttempts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int[] getAttempts() {
        return attempts;
    }

    public void setAttempts(int[] attempts) {
        this.attempts = attempts;
    }

    public int getcAttempts() {
        return cAttempts;
    }

    public void setcAttempts(int cAttempts) {
        this.cAttempts = cAttempts;
    }

    public String fileOutput(){
        String temp =  "" + id + " " + username + " " + password + " " + cAttempts + " ";
        for (int i = 0; i < cAttempts; i++){
            temp += attempts[i];
            if (i != cAttempts - 1){
                temp += " ";
            }
        }
        return temp;
    }

    public double getAverage(){

        int total = 0;

        for (int i = 0; i < cAttempts; i++){
            total += attempts[i];
        }
        return (double)total/cAttempts;
    }

    public void addNewAttempt(int score){
        attempts[cAttempts] = score;
        cAttempts++;
    }

}
