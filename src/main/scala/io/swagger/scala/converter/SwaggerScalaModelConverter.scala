package io.swagger.scala.converter

import com.wordnik.swagger.annotations.ApiModelProperty

import com.wordnik.swagger.converter._
import com.wordnik.swagger.util.Json
import com.wordnik.swagger.jackson.AbstractModelConverter

import com.wordnik.swagger.models.Model
import com.wordnik.swagger.models.properties._

import com.fasterxml.jackson.databind.ObjectMapper

import java.lang.reflect.Type
import java.util.Iterator

import scala.collection.JavaConverters._
import scala.reflect.api.JavaUniverse

class SwaggerScalaModelConverter extends ModelConverter {
  override
  def resolveProperty(`type`: Type, context: ModelConverterContext, chain: Iterator[ModelConverter]): Property = {
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
    if(chain.hasNext())
      chain.next().resolveProperty(`type`, context, chain)
    else
      null
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
