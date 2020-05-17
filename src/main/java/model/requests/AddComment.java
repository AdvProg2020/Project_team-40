package model.requests;

import model.Comment;
import model.enumerations.Status;

public class AddComment extends Request{

    private Comment comment;

    public AddComment(Comment comment){
        super("Add comment");
        this.comment = comment;
    }

    @Override
    public void action(){
        if(status == Status.Confirmed)
            comment.setStatus(Status.Confirmed);
    }

    @Override
    public String toString(){
        return "Comment : \n" + comment;
    }
}
