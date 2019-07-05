package com.example.foodcare.Retrofit.A_entity;

public class AccountLog {

    private Account account;
    private boolean canLogin;

    public AccountLog() {
    }

    public AccountLog(Account account, boolean canLogin) {
        this.account = account;
        this.canLogin = canLogin;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isCanLogin() {
        return canLogin;
    }

    public void setCanLogin(boolean canLogin) {
        this.canLogin = canLogin;
    }
}
