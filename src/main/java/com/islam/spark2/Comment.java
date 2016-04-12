package com.islam.spark2;

import java.sql.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class Comment {
    int comment_uuid;
    int post_uuid;
    String author;
    String content;
    boolean approved;
    Date submission_date;
   
}
