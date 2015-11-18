package io.swagger.scala.converter

import com.fasterxml.jackson.databind.`type`.CollectionLikeType
import io.swagger.annotations.ApiModelProperty

import io.swagger.converter._
import io.swagger.util.Json
import io.swagger.jackson.AbstractModelConverter

import io.swagger.models.Model
import io.swagger.models.properties._

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import java.lang.reflect.Type
import java.util.Iterator
import java.lang.annotation.Annotation

import scala.collection.JavaConverters._
import scala.reflect.api.JavaUniverse

object SwaggerScalaModelConverter {
  Json.mapper().registerModule(new DefaultScalaModule());
}

class SwaggerScalaModelConverter extends ModelConverter {
  SwaggerScalaModelConverter

  override
  def resolveProperty(`type`: Type, context: ModelConverterContext, 
    annotations: Array[Annotation] , chain: Iterator[ModelConverter]): Property = {
    val javaType = Json.mapper().constructType(`type`)
    val cls = javaType.getRawClass

    // handle scala enums
    if(cls != null && cls.getFields().map(_.getName).contains("MODULE$")) {
      val javaUniverse = scala.reflect.runtime.universe
      val m = javaUniverse.runtimeMirror(getClass.getClassLoader())
      val moduleSymbol = m.staticModule(cls.getName())
      val moduleMirror = m.reflectModule(moduleSymbol)
      val instance = moduleMirror.instance

      if(instance.isInstanceOf[Enumeration]) {
        val enumInstance = instance.asInstanceOf[Enumeration]
        
        if(enumInstance.values != null) {
          val sp = new StringProperty()
          for(v <- enumInstance.values)
            sp._enum(v.toString)
          return sp
        }
      }
    }

    // Unbox scala options
    val nextType = `type` match {
        case clt: CollectionLikeType if (isOption(cls)) => clt.getContentType
        case _ => `type`
      }

    if(chain.hasNext())
      chain.next().resolveProperty(nextType, context, annotations, chain)
    else
      null
  }

  private def isOption(cls: Class[_]): Boolean ={
    val optionClass = classOf[scala.Option[_]]

    cls == optionClass
  }

  override
  def resolve(`type`: Type, context: ModelConverterContext, chain: Iterator[ModelConverter]): Model = {
    val javaType = Json.mapper().constructType(`type`)
    val cls = javaType.getRawClass

    // ignore scala enums
    if(cls != null && cls.getFields().map(_.getName).contains("MODULE$")) {
      val javaUniverse = scala.reflect.runtime.universe
      val m = javaUniverse.runtimeMirror(getClass.getClassLoader())
      val moduleSymbol = m.staticModule(cls.getName())
      val moduleMirror = m.reflectModule(moduleSymbol)
      val instance = moduleMirror.instance

      if(instance.isInstanceOf[Enumeration]) {
        return null
      }
    }

    if(chain.hasNext()) {
      val next = chain.next()
      next.resolve(`type`, context, chain)
    }
    else
      null
  }
}
