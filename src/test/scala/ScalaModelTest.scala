import io.swagger.annotations.ApiModelProperty
import io.swagger.converter._
import io.swagger.models.properties._
import models._
import org.junit.runner.RunWith
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.junit.JUnitRunner

import scala.annotation.meta.field
import scala.collection.JavaConverters._

@RunWith(classOf[JUnitRunner])
class ScalaModelTest extends FlatSpec with Matchers {
  it should "extract a scala enum" in {
    val schemas = ModelConverters.getInstance().readAll(classOf[SModelWithEnum]).asScala
    val userSchema = schemas("SModelWithEnum")

    val orderSize = userSchema.getProperties.get("orderSize")
    orderSize.isInstanceOf[StringProperty] should be (true)

    val sp = orderSize.asInstanceOf[StringProperty]
    (sp.getEnum.asScala.toSet & Set("TALL", "GRANDE", "VENTI")).size should be (3)
  }

  it should "extract an optional scala enum" in {
    val schemas = ModelConverters.getInstance().readAll(classOf[SModelWithOptionalEnum]).asScala
    val userSchema = schemas("SModelWithOptionalEnum")

    val orderSize = userSchema.getProperties.get("orderSize")
    orderSize.isInstanceOf[StringProperty] should be (true)

    val sp = orderSize.asInstanceOf[StringProperty]
    (sp.getEnum.asScala.toSet & Set("TALL", "GRANDE", "VENTI")).size should be (3)
  }

  it should "read a scala case class with properties" in {
    val schemas = ModelConverters.getInstance().readAll(classOf[SimpleUser]).asScala
    val userSchema = schemas("SimpleUser")
    val id = userSchema.getProperties.get("id")
    id.isInstanceOf[LongProperty] should be (true)

    val name = userSchema.getProperties.get("name")
    name.isInstanceOf[StringProperty] should be (true)

    val date = userSchema.getProperties.get("date")
    date.isInstanceOf[DateTimeProperty] should be (true)
    date.getDescription should be ("the birthdate")
  }

  it should "read a model with vector property" in {
    val schemas = ModelConverters.getInstance().readAll(classOf[ModelWithVector]).asScala
    val model = schemas("ModelWithVector")
    val friends = model.getProperties.get("friends")
    friends.isInstanceOf[ArrayProperty] should be (true)
  }

  it should "read a model with vector of ints" in {
    val schemas = ModelConverters.getInstance().readAll(classOf[ModelWithIntVector]).asScala
    val model = schemas("ModelWithIntVector")
    val prop = model.getProperties.get("ints")
    prop.isInstanceOf[ArrayProperty] should be (true)
    prop.asInstanceOf[ArrayProperty].getItems.getType should be ("number")
  }
}

case class ModelWithVector (
  name: String,
  friends: Vector[String])
  
case class ModelWithIntVector (ints: Vector[Int])

case class SimpleUser (id: Long, name: String, @(ApiModelProperty @field)(value = "the birthdate") date: java.util.Date)