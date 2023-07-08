data class Post(
    val id: Int,//Идентификатор записи
    val ownerId: Int,//Идентификатор владельца стены
    val date: Int,//Время публикации записи в формате unixtime
    val text: String,//Текст записи
    val replyOwnerId: Int?,//Идентификатор владельца записи
    val replyPostId: Int,//Идентификатор записи
    val isFavorite: Boolean,//true, если объект добавлен в закладки у текущего пользователя
    val canEdit: Boolean,//Информация о том, может ли текущий пользователь редактировать запись
    val comments: Comments,//Информация о комментариях к записи
    val likes: Likes = Likes(),//Информация о лайках к записи
    val attachment: Array<Attachment> = arrayOf(PhotoAttachment(
        Photo(1,2,"https://vk.com/some_photo_link","https://vk.com/another_photo_link")),
        VideoAttachment(Video(1,2,"A Funny Video",30)))
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

interface Attachment {
    val type:String
}

data class Photo(
    val id: Int,
    val ownerId: Int,
    val photo130:String,
    val photo604:String
)
data class Video(
    val id: Int,
    val ownerId: Int,
    val title:String,
    val duration:Int
)
data class Audio(
    val id: Int,
    val ownerId: Int,
    val title:String,
    val duration:Int,
    val url:String
)
data class File(
    val id: Int,
    val ownerId: Int,
    val title:String,
    val size:Int,
    val ext:String,
    val url:String
)
data class Sticker(
    val productId: Int,
    val stickerId: Int,
)

data class PhotoAttachment(val photo: Photo):Attachment{
    override val type="photo"
}

data class VideoAttachment(val vide: Video):Attachment{
    override val type="video"
}

data class AudioAttachment(val audio: Audio):Attachment{
    override val type="audio"
}
data class FileAttachment(val file: File):Attachment{
    override val type="file"
}
data class StickerAttachment(val sticker: Sticker):Attachment{
    override val type="sticker"
}

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