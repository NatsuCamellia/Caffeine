package Caffeine.data;

public class Userdata {
    private String user_id;
    private Integer balance;
    private String last_signed;

    public Userdata(String user_id, Integer balance, String last_signed) {
        this.user_id = user_id;
        this.balance = balance;
        this.last_signed = last_signed;
    }
    
    public String getUser_id() {
        return user_id;
    }
    
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    
    public Integer getBalance() {
        return balance;
    }
    
    public void setBalance(Integer balance) {
        this.balance = balance;
    }
    
    public String getLast_signed() {
        return last_signed;
    }
    
    public void setLast_signed(String last_signed) {
        this.last_signed = last_signed;
    }
}
