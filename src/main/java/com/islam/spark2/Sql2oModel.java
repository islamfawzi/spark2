package com.islam.spark2;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class Sql2oModel implements Model {

    private Sql2o sql2o;
    //private UuidGenerator uuidGenerator;

    public Sql2oModel(Sql2o sql2o) {
        this.sql2o = sql2o;
        //uuidGenerator = new RandomUuidGenerator();
    }

    @Override
	public String createPost(String title, String content, List categories) {
		try (Connection conn = sql2o.beginTransaction()) {
            long id = (long) conn.createQuery("insert into posts(title, content, publishing_date) VALUES (:title, :content, :date)")
	                            .addParameter("title", title)
	                            .addParameter("content", content)
	                            .addParameter("date", new Date())
	                            .executeUpdate()
	                            .getKey();
            categories.forEach((category) ->
                    conn.createQuery("insert into posts_categories(post_uuid, category) VALUES (:post_uuid, :category)")
                    .addParameter("post_uuid", id)
                    .addParameter("category", category)
                    .executeUpdate());
            conn.commit();
            return "post inserted";
        }
	}

    @Override
    public String createComment(int post, String author, String content) {
        try (Connection conn = sql2o.open()) {
            conn.createQuery("insert into comments(post_uuid, author, content, approved, submission_date) VALUES (:post_uuid, :author, :content, :approved, :date)")
                    .addParameter("post_uuid", post)
                    .addParameter("author", author)
                    .addParameter("content", content)
                    .addParameter("approved", false)
                    .addParameter("date", new Date())
                    .executeUpdate();
            return "comment inserted";
        }
    }

    @Override
    public List<Post> getAllPosts() {
        try (Connection conn = sql2o.open()) {
            List<Post> posts = conn.createQuery("select * from posts")
                    .executeAndFetch(Post.class);
            posts.forEach((post) -> post.setCategories(getCategoriesFor(conn, post.getPost_uuid())));
            return posts;
        }
    }

    private List<String> getCategoriesFor(Connection conn, int post_uuid) {
        return conn.createQuery("select category from posts_categories where post_uuid=:post_uuid")
                .addParameter("post_uuid", post_uuid)
                .executeAndFetch(String.class);
    }

    @Override
    public List<Comment> getAllCommentsOn(int post) {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("select * from comments where post_uuid=:post_uuid")
                    .addParameter("post_uuid", post)
                    .executeAndFetch(Comment.class);
        }
    }

    @Override
    public boolean existPost(int post) {
        try (Connection conn = sql2o.open()) {
            List<Post> posts = conn.createQuery("select * from posts where post_uuid=:post")
                    .addParameter("post", post)
                    .executeAndFetch(Post.class);
            return posts.size() > 0;
        }
    }
}