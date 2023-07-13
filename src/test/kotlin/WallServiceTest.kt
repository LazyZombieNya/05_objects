import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class WallServiceTest {
    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun addPost_1() {
        val post: Post = Post(
            1, 3, 1687546918, "П", 1, 26, true, true,
            Comments(28),Comment(0,0,""), Likes(36)
        )

        val result = WallService.addPost(post)
        assertEquals(post, result)

    }

    @Test
    fun update_true() {
        val post: Post = Post(
            1, 3, 1687546918, "П", 1, 26, true, true,
            Comments(28), Comment(0,0,""),Likes(36)
        )
        WallService.addPost(post)
        WallService.addPost(post)
        WallService.addPost(post)

        val result = WallService.update(post)
        assertEquals(true, result)

    }

    @Test
    fun update_false() {
        val post: Post = Post(
            7, 3, 1687546918, "П", 1, 26, true, true,
            Comments(28),Comment(0,0,""), Likes(36)
        )
        WallService.addPost(post)
        WallService.addPost(post)
        WallService.addPost(post)

        val result = WallService.update(post)
        assertEquals(false, result)

    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val comment:Comment = Comment(3,1, "Привет")

        val result = WallService.createComment(10,comment)
            //assertEquals(true, result)
    }
    @Test
    fun createComment() {
        val comment:Comment = Comment(1,1, "Привет")
        val post: Post = Post(
            7, 3, 1687546918, "П", 1, 26, true, true,
            Comments(28),Comment(0,0,""), Likes(36)
        )
        WallService.addPost(post)

        val result = WallService.createComment(1,comment)
        assertEquals(comment, result)
    }

//    @Test
//    fun print() {
//        val post: Post = Post(
//            7, 3, 1687546918, "П", 1, 26, true, true,
//            Comments(28), Likes(36)
//        )
//        WallService.addPost(post)
//        val result = WallService.print()
//        assertEquals(post, result)
//
//    }

}