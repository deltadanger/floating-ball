package com.floatingball.comunication;

public interface ITwitterAPI {
    public void updateStatus(String status, String url, String success, String failure, ConfirmParameter param);
}
