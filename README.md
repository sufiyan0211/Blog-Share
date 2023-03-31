Blog-Share
===

# Design
<img src="DB Schema.png" alt=""> 

# Description of the project
- This webapp have three entities (i.e User, Blog and Comment) the relation among them is shown in the above design.
- API's is authenticated by Spring Security and JWT (Json-Web-Token).
- Some of the authorizations
  - Access token of every user will expires in 30 mins.
  - Only a User can create a blog (i.e user should be loged in for creating blog).
  - Everyone can view the blog irrespective of whether the user is loged in or not.
  - Only Owner of the blog can modify the blog or delete the blog.
  - User should be loged in to comment on a blog
  - Either owner of the blog or the owner of a comment can delete a comment.
  - Only owner of the comment can modify the comment.
  
  
  # Key Points
  - Spring Boot & Maven builder
  - Spring Security
  - JWT (Json Web Token)
  - My Sql Database
  - Well defined custom Exceptions for all the APIs
