import com.wordnik.swagger.converter._

import models._

import com.wordnik.swagger.util.Json
import com.wordnik.swagger.models.properties._

import scala.collection.JavaConverters._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.Matchers

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
}