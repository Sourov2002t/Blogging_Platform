<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.example.model.Post" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Post</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: #fff;
            padding: 30px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            margin-top: 0;
        }
        .post-meta {
            color: #666;
            font-size: 0.9em;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        .post-content {
            margin-bottom: 30px;
            line-height: 1.8;
            white-space: pre-line;
        }
        .btn {
            display: inline-block;
            background: #007bff;
            color: #fff;
            border: none;
            padding: 7px 15px;
            border-radius: 3px;
            text-decoration: none;
            font-size: 14px;
            cursor: pointer;
            margin-right: 5px;
        }
        .btn-danger {
            background: #dc3545;
        }
        .actions {
            margin-top: 20px;
            display: flex;
            justify-content: space-between;
        }
    </style>
</head>
<body>
    <div class="container">
        <% 
        Post post = (Post) request.getAttribute("post");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm");
        %>
        
        <h1><%= post.getTitle() %></h1>
        
        <div class="post-meta">
            <p>
                <strong>Author:</strong> <%= post.getAuthor() %><br>
                <strong>Date:</strong> <%= dateFormat.format(post.getCreatedDate()) %>
            </p>
        </div>
        
        <div class="post-content">
            <%= post.getContent() %>
        </div>
        
        <div class="actions">
            <a href="<%= request.getContextPath() %>/posts" class="btn">Back to Posts</a>
            <div>
                <a href="<%= request.getContextPath() %>/posts/edit?id=<%= post.getId() %>" class="btn">Edit</a>
                <form method="post" action="<%= request.getContextPath() %>/posts" style="display:inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="<%= post.getId() %>">
                    <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this post?')">Delete</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>