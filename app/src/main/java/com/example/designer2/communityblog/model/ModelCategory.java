package com.example.designer2.communityblog.model;

public class ModelCategory {
    private String thumnailUrl,catName,username,created_date,descriptio,caption;
    private  int id;



    public ModelCategory(String thumnailUrl, String catName,String username,String created_date,String descriptio,String caption, int id) {

        this.thumnailUrl = thumnailUrl;
        this.catName = catName;
        this.username = username;
        this.created_date = created_date;
        this.descriptio = descriptio;
        this.caption = caption;

        this.id = id;

    }

    public String getThumnailUrl() {
        return thumnailUrl;
    }

    public void setThumnailUrl(String thumnailUrl) {
        this.thumnailUrl = thumnailUrl;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String productName) {
        this.catName = catName;
    }
    public String getUsername(){return username;}
    public void setUsername(String username){this.username=username;}

    public String getCreated_date(){return created_date;}
    public void setCreated_date(String created_date){this.created_date=created_date;}

    public String getDescriptio(){return descriptio;}
    public void setDescriptio(String descriptio){this.descriptio=descriptio;}

    public String getCaption(){return caption;}
    public void setCaption(String caption){this.caption=caption;}



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
