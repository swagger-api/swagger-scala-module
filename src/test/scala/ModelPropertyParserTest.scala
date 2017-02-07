import io.swagger.converter._
import io.swagger.models.properties
import io.swagger.models.properties._
import models._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.JavaConverters._

@RunWith(classOf[JUnitRunner])
class ModelPropertyParserTest extends FlatSpec with Matchers {
  it should "verify swagger-core bug 814" in {
    val converter = ModelConverters.getInstance()
    val schemas = converter.readAll(classOf[CoreBug814])
    val model = schemas.get("CoreBug814")
    model should not be (null)
    val isFoo = model.getProperties().get("isFoo")
    isFoo should not be (null)
    isFoo.isInstanceOf[BooleanProperty] should be (true)
  }

  it should "process Option[String] as string" in {
    val converter = ModelConverters.getInstance()
    val schemas = converter.readAll(classOf[ModelWOptionString]).asScala.toMap
    val model = schemas.get("ModelWOptionString")
    model should be ('defined)
    val stringOpt = model.get.getProperties().get("stringOpt")
    stringOpt should not be (null)
    stringOpt.isInstanceOf[StringProperty] should be (true)
    stringOpt.getRequired should be (false)
    val stringWithDataType = model.get.getProperties().get("stringWithDataTypeOpt")
    stringWithDataType should not be (null)
    stringWithDataType.isInstanceOf[StringProperty] should be (true)
    stringWithDataType.getRequired should be (false)
  }

  it should "process Option[Model] as Model" in {
    val converter = ModelConverters.getInstance()
    val schemas = converter.readAll(classOf[ModelWOptionModel]).asScala.toMap
    val model = schemas.get("ModelWOptionModel")
    model should be ('defined)
    val modelOpt = model.get.getProperties().get("modelOpt")
    modelOpt should not be (null)
    modelOpt.isInstanceOf[RefProperty] should be (true)
  }

  it should "process Model with Scala BigDecimal as Number" in {
    case class TestModel(field: BigDecimal)

    val converter = ModelConverters.getInstance()
    val schemas = converter.readAll(classOf[TestModel]).asScala.toMap
    val model = schemas.values.headOption
    model should be ('defined)
    val modelOpt = model.get.getProperties().get("field")
    modelOpt shouldBe a [properties.DecimalProperty]
    modelOpt.getRequired should be (true)
  }

  it should "process Model with Scala Option BigDecimal as optional Number" in {
    val converter = ModelConverters.getInstance()
    val schemas = converter.readAll(classOf[ModelWOptionBigDecimal]).asScala.toMap
    val model = schemas.get("ModelWOptionBigDecimal")
    model should be ('defined)
    val optBigDecimal = model.get.getProperties().get("optBigDecimal")
    optBigDecimal should not be (null)
    optBigDecimal shouldBe a [properties.DecimalProperty]
    optBigDecimal.getRequired should be (false)
  }

  it should "process all properties as required barring Option[_] or if overridden in annotation" in {
    val schemas = ModelConverters
      .getInstance()
      .readAll(classOf[ModelWithOptionAndNonOption])
      .asScala

    val model = schemas("ModelWithOptionAndNonOption")
    model should not be (null)

    val optional = model.getProperties().get("optional")
    optional.getRequired should be (false)

    val required = model.getProperties().get("required")
    required.getRequired should be (true)

    val forcedRequired = model.getProperties().get("forcedRequired")
    forcedRequired.getRequired should be (true)

    val forcedOptional = model.getProperties().get("forcedOptional")
    forcedOptional.getRequired should be (false)
  }
}