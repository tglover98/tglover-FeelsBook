package com.example.tglover_feelsbook;

import java.util.Date;


public class Emotion {
    private String emotion;
    private Date date;
    private String comment;
    private static final Integer MAX_CHAR = 100;

    public Emotion(){
        this.emotion = "";
        this.date = new Date();
        this.comment = "";
    }

    public Emotion(String emotion){
        this.emotion = emotion;
        this.date = new Date();
        this.comment = "";
    }

    public Emotion(String emotion, Date date, String comment){
        this.emotion = emotion;
        this.date = date;
        this.comment = comment;
    }

    //getters
    public String toString(){return getEmotion();}
    public String getEmotion(){return this.emotion;}
    public Date getDate(){return this.date;}
    public String getComment(){ return this.comment; }

    //setters
    public void setEmotion(String emotion){this.emotion = emotion;}
    public void setDate(Date date){this.date = date;}
    public void set_comment(String comment){
        if (comment.length() > MAX_CHAR){
            this.comment = "";
        }
        else{
            this.comment = comment;

        }
    }

}
