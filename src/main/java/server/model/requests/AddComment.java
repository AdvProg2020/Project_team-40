package server.model.requests;

import server.model.Comment;
import server.model.enumerations.Status;

public class AddComment extends Request{

    private static final long serialVersionUID = -2090250636759593204L;
    private Comment comment;

    public AddComment(){}

    public AddComment(Comment comment){
        super("Add comment");
        this.comment = comment;
    }

    public Comment getComment(){
        return comment;
    }

    @Override
    public void action(){
        if(status == Status.Confirmed)
            comment.setStatus(Status.Confirmed);
    }

    @Override
    public String toString(){
        return super.toString() + "Comment : \n" + comment;
    }
}
