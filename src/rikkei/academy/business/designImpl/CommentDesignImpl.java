package rikkei.academy.business.designImpl;

import rikkei.academy.business.design.ICommentDesign;
import rikkei.academy.business.design.IPostDesign;
import rikkei.academy.business.entity.Comment;
import rikkei.academy.business.entity.Post;

import java.util.List;

public class CommentDesignImpl implements ICommentDesign {
    IPostDesign postService = new PostDesignImpl();
    @Override
    public List<Comment> getAll() {
        return null;
    }

    @Override
    public void save(Comment comment) {

    }

    @Override
    public Comment findById(String string) {
        return null;
    }

    @Override
    public void delete(Comment comment) {

    }

    @Override
    public String getNewId() {
        int idMax = 0;
        List<Post> postList = postService.getAll();
        for (Post post : postList) {
            List<Comment> commentList = post.getCommentList();
            for (Comment comment : commentList) {
                int commentId = Integer.parseInt(comment.getCommentId().replace("M", "0"));
                if (idMax < commentId) {
                    idMax = commentId;
                }
            }
        }
        idMax += 1;
        return "M" + String.format("%03d", idMax);
    }
}
