package rikkei.academy.business.designImpl;

import rikkei.academy.business.entity.Post;
import rikkei.academy.business.design.IPostDesign;
import rikkei.academy.business.util.IOFile;
import rikkei.academy.business.util.ShopConstants;

import java.util.List;

public class PostDesignImpl implements IPostDesign {
    private static List<Post> postList;

    public PostDesignImpl() {
        postList = IOFile.readFromFile(ShopConstants.POST_PATH);
    }

    @Override
    public List<Post> getAll() {
        return postList;
    }

    @Override
    public void save(Post post) {
        if (findById(post.getPostId()) == null) {
            postList.add(post);
            System.out.println("Đã thêm thành công 1 bài Post.");
        } else {
            postList.set(postList.indexOf(findById(post.getPostId())), post);
        }
        IOFile.writeToFile(ShopConstants.POST_PATH, postList);
    }

    @Override
    public Post findById(String postId) {
        for (Post post : postList) {
            if (post.getPostId().equals(postId)) {
                return post;
            }
        }
        return null;
    }

    @Override
    public void delete(Post post) {
        postList.remove(post);
        System.out.println("Đã xoá thành công 1 bài Post.");
        IOFile.writeToFile(ShopConstants.POST_PATH, postList);
    }

    @Override
    public String getNewId() {
        int idMax = 0;
        for (Post post : postList) {
            int postId = Integer.parseInt(post.getPostId().replace("P", "0"));
            if (idMax < postId) {
                idMax = postId;
            }
        }
        idMax += 1;
        return "P" + String.format("%03d", idMax);
    }
}
