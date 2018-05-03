package com.example.sergio.dp2aux;

import android.support.v17.leanback.widget.ListRow;

import java.util.ArrayList;
import java.util.List;

public class User_Admin {
    String uuid;
    String username;
    List<Sponsor> sponsor_list= new ArrayList<>();

    public User_Admin(String user_name, String uuid, List<Sponsor> list_sponsors){
        this.username=user_name;
        this.uuid=uuid;
        this.sponsor_list=list_sponsors;

    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Sponsor> getSponsor_list() {
        return sponsor_list;
    }

    public void setSponsor_list(List<Sponsor> sponsor_list) {
        this.sponsor_list = sponsor_list;
    }
}
