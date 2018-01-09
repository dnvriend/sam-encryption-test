package com.github.dnvriend

import com.github.dnvriend.lambda.HttpResponse
import play.api.libs.json.{JsValue, Json, Writes}

object Response {
  def error(t: Throwable): HttpResponse = {
    HttpResponse.serverError.withBody(Json.toJson("error" -> t.getMessage))
  }
  def ok(msg: String): HttpResponse = {
    HttpResponse.ok.withBody(Json.toJson("msg" -> msg))
  }
  def ok(value: JsValue): HttpResponse = {
    HttpResponse.ok.withBody(value)
  }
  def ok[A: Writes](value: A): HttpResponse = {
    ok(Json.toJson(value))
  }
}
