Blog-Share
===

## Content
* [Design](#design)
* [API Documentation](#api-documentation)
  * [User related API](#user-related-api)
  * [Blog related API](#blog-related-api)
  * [Comment related API](#comment-related-api)
* [Key Points](#key-points)

## Design
<img src="DB Schema.png" alt=""> 

## Description of the project
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
  
  
## API Documentation
### User related API
- <b>Creating User</b>
  - <b>Endpoint:</b> *http://localhost:8091/users/signup*
  - <b>Payload:</b> example payload
      ```
      {
        "name" : "MyName",
        "username" : "MyUserName",
        "password" : "SomePass",
        "email": "username@test.com",
        "bio": "About my self"
      }
      ```
- <b>Login User</b>
  - <b>Endpoint:</b> *http://localhost:8091/users/login*
  - <b>Payload:</b> example payload
      ```
      {
        "username" : "MyUserName",
        "password" : "SomePass"
      }
      ```

### Blog related API
- <b>Post a Blog</b>
  - <b>Endpoint:</b> *http://localhost:8091/blogs/create*
  - <b>Payload:</b> example payload
      ```
      {
        "title" : "My Blog",
        "body" : "Something in body"
      }
      ```
- <b>Get Blog by Slug</b>
  - <b>Endpoint:</b> *http://localhost:8091/blogs/{slug}*  
- <b>Modify Blog</b>
  - <b>Endpoint:</b> *http://localhost:8091/blogs/{slug}*
  - <b>Payload:</b> example payload
      ```
      {
        "title" : "My Blog",
        "body" : "Edited this body"
      }
      ```
### Comment related API
- <b>Post Comment</b>
  - <b>Endpoint:</b> *http://localhost:8091/blogs/{slug}/comments*
  - <b>Payload:</b> example payload
      ```
      {
        "body" : "1 comment"
      }
      ```
- <b>Get All Comments by Slug</b>
  - <b>Endpoint:</b> *http://localhost:8091/blogs/{slug}/comments*  
- <b>Modify Comment by CommentId</b>
  - <b>Endpoint:</b> *http://localhost:8091/blogs/comments/{commentId}*
  - <b>Payload:</b> example payload
      ```
      {
        "body" : "1 comment edited"
      }
      ```
- <b>Delete Comment by CommentId</b>
  - <b>Endpoint:</b> *http://localhost:8091/blogs/comments/{commentId}*  
  
  
## Key Points
- Spring Boot & Maven builder
- Spring Security
- JWT (Json Web Token)
- My Sql Database
- Well defined custom Exceptions for all the APIs
