package controllers

import models.Post.format

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import repositories.PostRepository

import scala.concurrent.ExecutionContext

@Singleton
class PostController @Inject()(implicit ec: ExecutionContext, cc: ControllerComponents, postRepo: PostRepository) extends BaseController {
  def getPosts: Action[AnyContent] = Action.async {
    postRepo.list().map { posts =>
      Ok(Json.toJson(posts))
    }
  }

  def getPost(id: String): Action[AnyContentAsJson] = Action.async {
    postRepo.read(id).map { post =>
      Ok(Json.toJson(post))
    }
  }

  def createPost: Action[AnyContentAsJson] = Action.async(parse.json) { req =>
    postRepo.create(req.body).map { post =>
      Ok(Json.toJson(post))
    }
  }

  def updatePost(id: String): Action[AnyContentAsJson] = Action.async(parse.json) { req =>
    postRepo.update(id, req.body).map { post =>
      Ok(Json.toJson(post))
    }
  }

  def deletePost(id: String): Action[AnyContentAsJson] = Action.async {
    postRepo.destroy(id).map(_ => Ok())
  }
}
