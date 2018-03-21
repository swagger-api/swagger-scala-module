import io.swagger.v3.core.converter._
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.models.media.{ArraySchema, DateTimeSchema, IntegerSchema, StringSchema}
import models.SModelWithEnum

import scala.collection.JavaConverters._
import scala.annotation.meta.field
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.Matchers

@RunWith(classOf[JUnitRunner])
class ScalaModelTest extends FlatSpec with Matchers {
  it should "extract a scala enum" in {
    val schemas = ModelConverters.getInstance().readAll(classOf[SModelWithEnum]).asScala
    val userSchema = schemas("SModelWithEnum")

    val orderSize = userSchema.getProperties().get("Order Size")
    orderSize should not be null
// TODO fix tests
//    orderSize shouldBe a [StringSchema]
//
//    val sp = orderSize.asInstanceOf[StringSchema]
//    (sp.getEnum().asScala.toSet & Set("TALL", "GRANDE", "VENTI")) should have size 3
  }

  it should "read a scala case class with properties" in {
    val schemas = ModelConverters.getInstance().readAll(classOf[SimpleUser]).asScala
    val userSchema = schemas("SimpleUser")
    val id = userSchema.getProperties().get("id")
    id shouldBe a [IntegerSchema]

    val name = userSchema.getProperties().get("name")
    name shouldBe a [StringSchema]

    val date = userSchema.getProperties().get("date")
    date shouldBe a [DateTimeSchema]
    //date.getDescription should be ("the birthdate")
  }

  it should "read a model with vector property" in {
    val schemas = ModelConverters.getInstance().readAll(classOf[ModelWithVector]).asScala
    val model = schemas("ModelWithVector")
    val friends = model.getProperties().get("friends")
    friends shouldBe a [ArraySchema]
  }
  
  it should "read a model with vector of ints" in {
    val schemas = ModelConverters.getInstance().readAll(classOf[ModelWithIntVector]).asScala
    val model = schemas("ModelWithIntVector")
    val prop = model.getProperties().get("ints")
    prop shouldBe a [ArraySchema]
    prop.asInstanceOf[ArraySchema].getItems.getType should be ("number")
  }
}

case class ModelWithVector (
  name: String,
  friends: Vector[String])
  
case class ModelWithIntVector (ints: Vector[Int])

case class SimpleUser (id: Long, name: String, @(Parameter @field)(description =  "the birthdate") date: java.util.Date)