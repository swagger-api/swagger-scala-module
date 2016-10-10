package io.swagger.scala.converter

import java.lang.annotation.Annotation
import java.lang.reflect.Type
import java.util.Iterator

import com.fasterxml.jackson.databind.`type`.ReferenceType
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import io.swagger.converter._
import io.swagger.models.Model
import io.swagger.models.properties._
import io.swagger.util.{Json, PrimitiveType}

object SwaggerScalaModelConverter {
  Json.mapper().registerModule(new DefaultScalaModule())
}

class SwaggerScalaModelConverter extends ModelConverter {
  SwaggerScalaModelConverter

  override
  def resolveProperty(
    `type`: Type, context: ModelConverterContext, annotations: Array[Annotation] , chain: Iterator[ModelConverter]
  ): Property = {
    // Unbox Option
    val contentType = `type` match {
      case refType: ReferenceType if isOption(refType) =>
        refType.getContentType
      case _ =>
        `type`
    }

    val javaType = Json.mapper().constructType(contentType)
    val cls = javaType.getRawClass

    // Handle Scala types
    if (cls != null) {
      getEnumerationInstance(cls) match {
        // Handle scala enums
        case Some(enumInstance) =>
          if (enumInstance.values != null) {
            val sp = new StringProperty()
            for (v <- enumInstance.values)
              sp._enum(v.toString)
            return sp
          }
        // Handle BigDecimal
        case None if isBigDecimal(cls) =>
          return PrimitiveType.DECIMAL.createProperty()
        case None =>
      }
    }

    // Handle Java types
    if (chain.hasNext) {
      chain.next().resolveProperty(contentType, context, annotations, chain)
    } else {
      null
    }
  }

  override
  def resolve(`type`: Type, context: ModelConverterContext, chain: Iterator[ModelConverter]): Model = {
    val javaType = Json.mapper().constructType(`type`)
    getEnumerationInstance(javaType.getRawClass) match {
      case Some(enumInstance) => null // ignore scala enums
      case None =>
        if (chain.hasNext) {
          chain.next().resolve(`type`, context, chain)
        } else {
          null
        }
    }
  }

  private def getEnumerationInstance(cls: Class[_]): Option[Enumeration] = {
    if (cls.getFields.map(_.getName).contains("MODULE$")) {
      val javaUniverse = scala.reflect.runtime.universe
      val m = javaUniverse.runtimeMirror(Thread.currentThread().getContextClassLoader)
      val moduleMirror = m.reflectModule(m.staticModule(cls.getName))
      moduleMirror.instance match {
        case enumInstance: Enumeration => Some(enumInstance)
        case _ => None
      }
    } else {
      None
    }
  }

  private def isOption(refType: ReferenceType): Boolean = refType.getAnchorType.getRawClass == classOf[Option[_]]

  private def isBigDecimal(cls: Class[_]): Boolean = cls.isAssignableFrom(classOf[BigDecimal])
}
