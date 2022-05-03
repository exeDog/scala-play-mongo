package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def ping(): Action[AnyContent] = Action { _ =>
    Ok(System.currentTimeMillis.toString)
  }

  def json(): Action[AnyContent] = Action { _ => Ok(Json.obj("foo" -> "bar")) }

  def sayHello(name: String): Action[AnyContent] = Action { _ => Ok(s"Hello ${name}") }

  def postForm(): Action[JsValue] = Action(parse.json) { req => Ok(Json.obj("body" -> Json.toJson(req.body))) }
}
