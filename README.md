# Java Blog Application (Servlets & JSP)
## Overview

This is a simple blog management web application built using Java Servlets, JSP, and XML-based storage.
The application follows the MVC architecture and allows users to create, view, edit, and delete blog posts through a clean web interface.

It is designed to demonstrate core backend concepts without relying on external databases.

## Features

Create, read, update, and delete blog posts (CRUD)

XML-based data persistence (no database required)

MVC architecture using Servlets and JSP

Dynamic routing using servlet path mappings

Timestamped posts with author details

Responsive and clean JSP-based UI

## Tech Stack

Backend: Java, Servlets

Frontend: JSP, HTML, CSS

Data Storage: XML (DOM Parser)

Server: Apache Tomcat

Architecture: MVC (Model–View–Controller)

## Application Flow

User accesses the blog home page

Servlet handles routing (/posts/*)

PostDAO reads/writes blog data from XML

Data is passed to JSP views

JSP renders post list, details, and forms

## Key Implementation Details

XML file initialization on first run

UUID-based unique post IDs

DOM parsing for XML read/write operations

Date handling with formatted timestamps

## Separation of concerns:

Model: Post

Data Access: PostDAO

Controller: PostServlet

View: JSP files

## How to Run

Clone the repository

Import the project into Eclipse or IntelliJ

Configure Apache Tomcat server

Run the application

Open http://localhost:8080/your-app-name/

## Limitations & Improvements

XML storage is suitable only for small-scale usage

No authentication or user roles

Can be extended using JDBC, MySQL, and Spring MVC
