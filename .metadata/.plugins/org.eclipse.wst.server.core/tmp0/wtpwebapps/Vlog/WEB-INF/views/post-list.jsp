<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.text.SimpleDateFormat" %>
<%@ page import="com.example.model.Post" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blog Posts</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
        }
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        .post-list {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
        }
        .post-card {
            background: #fff;
            border-radius: 5px;
            padding: 20px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            transition: transform 0.3s ease;
        }
        .post-card:hover {
            transform: translateY(-5px);
        }
        .post-title {
            margin-top: 0;
            color: #007bff;
        }
        .post-meta {
            color: #666;
            font-size: 0.85em;
            margin-bottom: 10px;
        }
        .post-excerpt {
            margin-bottom: 15px;
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
            display: flex;
            justify-content: space-between;
            margin-top: 10px;
        }
        .header-actions {
            text-align: right;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Blog Posts</h1>
        
        <div class="header-actions">
            <a href="<%= request.getContextPath() %>/posts/new" class="btn">Create New Post</a>
        </div>
        
        <div class="post-list">
            <% 
            List<Post> posts = (List<Post>) request.getAttribute("posts");
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
            
            if (posts != null && !posts.isEmpty()) {
                for (Post post : posts) {
            %>
                <div class="post-card">
                    <h2 class="post-title"><%= post.getTitle() %></h2>
                    <div class="post-meta">
                        <span>By <%= post.getAuthor() %></span> |
                        <span><%= dateFormat.format(post.getCreatedDate()) %></span>
                    </div>
                    <div class="post-excerpt">
                        <%= post.getContent().length() > 150 ? post.getContent().substring(0, 150) + "..." : post.getContent() %>
                    </div>
                    <div class="actions">
                        <a href="<%= request.getContextPath() %>/posts/<%= post.getId() %>" class="btn">Read More</a>
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
            <% 
                }
            } else {
            %>
                <div style="grid-column: 1 / -1; text-align: center; padding: 50px;">
                    <p>No posts found. Create your first blog post!</p>
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>