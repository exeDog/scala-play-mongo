package models

import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json._
import play.api.libs.json.{JsObject, JsResult, JsValue, Json, OFormat}

case class Post(
  _id: Option[BSONObjectID],
  title: String,
  description: String
)

object Post {
  implicit val format: OFormat[Post] = Json.format[Post]
  implicit val postFormats: OFormat[Post] = new OFormat[Post] {
    override def reads(json: JsValue): JsResult[Post] = format.reads(json)

    override def writes(o: Post): JsObject = format.writes(o)
  }
}

