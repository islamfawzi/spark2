package com.islam.spark2;

import java.util.List;
import java.util.UUID;

public interface Model {
    String createPost(String title, String content, List categories);
    String createComment(int post, String author, String content);
    List getAllPosts();
    List getAllCommentsOn(int post);
    boolean existPost(int post);
}



