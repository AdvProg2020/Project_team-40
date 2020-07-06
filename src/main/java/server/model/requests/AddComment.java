package server.model.requests;

import server.model.Comment;
import server.model.enumerations.Status;

public class AddComment extends Request{

    private static final long serialVersionUID = -2090250636759593204L;
    private String commentID;

    public AddComment(String commentID){
        super("Add comment");
        this.commentID = commentID;
    }

    public Comment getComment(){
        return Comment.getComment(commentID);
    }

    @Override
    public void action(){
        if(status == Status.Confirmed)
            Comment.getComment(commentID).setStatus(Status.Confirmed);
    }

    @Override
    public String toString(){
        return super.toString() + "Comment : \n" + Comment.getComment(commentID);
    }
}
