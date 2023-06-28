data class Post(
    val id: Int,//Идентификатор записи
    val ownerId: Int,//Идентификатор владельца стены
    val date: Int,//Время публикации записи в формате unixtime
    val text: String,//Текст записи
    val replyOwnerId: Int,//Идентификатор владельца записи
    val replyPostId: Int,//Идентификатор записи
    val isFavorite: Boolean,//true, если объект добавлен в закладки у текущего пользователя
    val canEdit: Boolean,//Информация о том, может ли текущий пользователь редактировать запись
    val comments: Comments,//Информация о комментариях к записи
    val likes: Likes = Likes(),//Информация о лайках к записи
)

data class Comments(
    var count: Int = 0,//количество комментариев
    val canPost: Boolean = true,//информация о том, может ли текущий пользователь комментировать запись
    val groupsCanPost: Boolean = true,//могут ли сообщества комментировать запись
    val canClose: Boolean = true,//может ли текущий пользователь закрыть комментарии к записи
    val canOpen: Boolean = true,//может ли текущий пользователь открыть комментарии к записи
)

data class Likes(
    var count: Int = 0,//число пользователей, которым понравилась запись
    val userLikes: Boolean = false,//наличие отметки «Мне нравится» от текущего пользователя
    val canLike: Boolean = true,//информация о том, может ли текущий пользователь поставить отметку «Мне нравится»
    val canPublish: Boolean = true,//информация о том, может ли текущий пользователь сделать репост записи
)


object WallService {
    private var posts = emptyArray<Post>()
    private var lastId = 0

    fun addPost(post: Post): Post {
        posts += post.copy(id = ++lastId, comments = post.comments.copy(), likes = post.likes.copy())
        return posts.last()
    }

    fun update(newPost: Post): Boolean {
        for ((index, post) in posts.withIndex()) {
            if (post.id == newPost.id) {
                posts[index] = newPost.copy(likes = newPost.likes.copy())
                return true
            }
        }
        return false
    }

    fun print() {
        for (post in posts) {
            print(post)
            //print(' ')
            println()
        }
    }

    fun clear() {
        posts = emptyArray()
        lastId = 0
    }
}


fun main() {
    WallService.addPost(
        Post(
            1, 33, 1687545918, "Привет!", 1, 26, true, true,
            Comments(28), Likes(36)
        )
    )
    WallService.addPost(
        Post(
            2, 33, 1687545920, "Как жела?", 1, 26, true, true,
            Comments(30), Likes(50)
        )
    )
    println(
        WallService.update(
            Post(
                2, 33, 1687545920, "Как дела?", 1, 26, true, true,
                Comments(30), Likes(50)
            )
        )
    )
    WallService.print()
}