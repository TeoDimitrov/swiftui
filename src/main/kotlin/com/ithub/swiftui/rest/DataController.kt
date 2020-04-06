package com.ithub.swiftui.rest

import com.ithub.swiftui.models.Post
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate


@RestController
@RequestMapping("/api/v1/posts")
class DataController(restTemplateBuilder: RestTemplateBuilder) {

    private val restTemplate: RestTemplate = restTemplateBuilder.build()

    companion object {
        const val API = "https://jsonplaceholder.typicode.com/posts"
    }

    /**
     * Test with the following command:
    curl --location --request GET 'http://localhost:8080/api/v1/posts'
     */
    @GetMapping()
    fun getAll(): ResponseEntity<Array<Post>> {
        val response = restTemplate.getForObject<Array<Post>>(API, Array<Post>::class.java)
        return ResponseEntity.ok(response!!)
    }

    /**
     * Test with the following command:
    curl --location --request GET 'http://localhost:8080/api/v1/posts/1'
     */
    @GetMapping("{id}")
    fun get(@PathVariable id: Long): ResponseEntity<Post> {
        val response = restTemplate.getForObject<Post>("$API/$id", Post::class.java)
        return ResponseEntity.ok(response!!)
    }

    /**
     * Test with the following command:
    curl --location --request POST 'http://localhost:8080/api/v1/posts' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "title": "New Post"
    }'
     */
    @PostMapping
    fun post(@RequestBody post: Post): ResponseEntity<Post> {
        val entity = HttpEntity(post)
        val response: ResponseEntity<Post> = restTemplate.postForEntity<Post>(API, entity, Post::class.java)
        return ResponseEntity.ok(response.body!!)
    }

    /**
     * Test with the following command:
    curl --location --request PUT 'http://localhost:8080/api/v1/posts/1' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "title": "New Post Updated"
    }'
     */
    @PutMapping("{id}")
    fun put(@PathVariable id: Long, @RequestBody post: Post): ResponseEntity<Post> {
        val entity = HttpEntity(post)
        val response: ResponseEntity<Post> = restTemplate.exchange(API, HttpMethod.PUT, entity, Post::class.java, id)
        return ResponseEntity.ok(response.body!!)
    }

    /**
     * Test with the following command:
    curl --location --request DELETE 'http://localhost:8080/api/v1/posts/101'
     */
    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) {
        restTemplate.delete("$API/$id")
    }

}