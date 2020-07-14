package server.model.chat;

import java.io.Serializable;

public class Message implements Serializable{
    private String sender;
    private String content;
    private long time;

    public Message(String sender, String content, long time){
        this.sender = sender;
        this.content = content;
        this.time = time;
    }

    public String getSender(){
        return sender;
    }

    public String getContent(){
        return content;
    }

    public long getTime(){
        return time;
    }
}
