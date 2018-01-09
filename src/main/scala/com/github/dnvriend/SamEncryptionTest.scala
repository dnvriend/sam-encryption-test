package com.github.dnvriend

import com.github.dnvriend.lambda._
import com.github.dnvriend.lambda.annotation.HttpHandler
import com.github.dnvriend.lambda.annotation.policy._
import com.github.dnvriend.sam.serialization.resolver.SchemaResolver
import com.github.dnvriend.sam.serialization.serializer.SamSerializer
import com.sksamuel.avro4s.SchemaFor
import org.apache.avro.Schema
import play.api.libs.json._

import scalaz.Scalaz._

object Person {
  implicit val format: Format[Person] = Json.format
}
case class Person(name: String, age: Int)

object PersonResolver extends SchemaResolver {
  override def resolve(fingerprint: String): Option[Schema] = {
    Option(SchemaFor[Person].apply())
  }
}

@CloudWatchFullAccess
@AWSLambdaVPCAccessExecutionRole
@AWSKeyManagementServicePowerUser
@HttpHandler(path = "/person", method = "put")
 class SamEncryptionTest extends JsonApiGatewayHandler[Person] {
  val cmkArn: String = "arn:aws:kms:eu-west-1:015242279314:key/04a8c913-9c2b-42e8-a4b5-1bd2beccc3f2"
  override def handle(value: Option[Person],
                      pathParams: Map[String, String],
                      requestParams: Map[String, String],
                      request: HttpRequest,
                      ctx: SamContext): HttpResponse = {

    val result: DTry[Person] = for {
      person <- value.toRightDisjunction(new IllegalStateException("Could not deserialize person"))
      record <- SamSerializer.serialize(person, Option(cmkArn))
      result <- SamSerializer.deserialize[Person](record, PersonResolver, Option(cmkArn))
    } yield result

    result.fold(t => Response.error(t), person => Response.ok(person))
  }
}