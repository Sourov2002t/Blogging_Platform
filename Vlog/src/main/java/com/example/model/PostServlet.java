package com.example.model;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/posts/*")
public class PostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PostDAO postDAO;

    @Override
    public void init() {
        String xmlFilePath = getServletContext().getRealPath("/WEB-INF/posts.xml");
        postDAO = new PostDAO(xmlFilePath);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // List all posts
            List<Post> posts = postDAO.getAllPosts();
            request.setAttribute("posts", posts);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/post-list.jsp");
            dispatcher.forward(request, response);
        } else if (pathInfo.equals("/new")) {
            // Show form to create a new post
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/post-form.jsp");
            dispatcher.forward(request, response);
        } else if (pathInfo.equals("/edit")) {
            // Show form to edit an existing post
            String id = request.getParameter("id");
            Post post = postDAO.getPostById(id);
            request.setAttribute("post", post);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/post-form.jsp");
            dispatcher.forward(request, response);
        } else {
            // View a specific post
            String id = pathInfo.substring(1); // Remove the leading '/'
            Post post = postDAO.getPostById(id);

            if (post != null) {
                request.setAttribute("post", post);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/post-view.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/posts");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            // Create a new post
            Post post = new Post();
            post.setTitle(request.getParameter("title"));
            post.setContent(request.getParameter("content"));
            post.setAuthor(request.getParameter("author"));

            postDAO.addPost(post);
            response.sendRedirect(request.getContextPath() + "/posts");
        } else if ("update".equals(action)) {
            // Update an existing post
            Post post = new Post();
            post.setId(request.getParameter("id"));
            post.setTitle(request.getParameter("title"));
            post.setContent(request.getParameter("content"));
            post.setAuthor(request.getParameter("author"));

            postDAO.updatePost(post);
            response.sendRedirect(request.getContextPath() + "/posts");
        } else if ("delete".equals(action)) {
            // Delete a post
            String id = request.getParameter("id");
            postDAO.deletePost(id);
            response.sendRedirect(request.getContextPath() + "/posts");
        }
    }
}