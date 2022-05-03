package repositories

import models.Post
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.play.json.collection._
import reactivemongo.play.json._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class PostRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {
  private def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection("posts"))

  def list(limit: Int = 100): Future[Seq[Post]] = collection.flatMap(
    _.find(BSONDocument())
    .cursor[Post](ReadPreference.primary)
      .collect[Seq](limit, Cursor.FailOnError[Seq[Post]]()))

  def read(id: BSONObjectID): Future[Option[Post]] = collection.flatMap(_.find(BSONDocument("_id" -> id)).one[Post])

  def create(post: Post): Future[WriteResult] = collection.flatMap(_.insert(post))

  def update(id: BSONObjectID, post: Post): Future[Option[Post]] = collection.flatMap(
    _.findAndUpdate(
      BSONDocument("_id" -> id),
      BSONDocument(f"$$set" -> BSONDocument(
        "title" -> post.title,
        "description" -> post.description)
      ),
      true
    ).map(_.result[Post])
  )

  def destroy(id: BSONObjectID): Future[Option[Post]] = collection.flatMap(_.findAndRemove(BSONDocument("_id" -> id) ).map(_.result[Post]))
}
