package models

import io.swagger.annotations.{ ApiModel, ApiModelProperty }
import models.OrderSize.OrderSize

import scala.annotation.meta.field

@ApiModel(description = "Scala model containing an Enumeration Value that is annotated with the dataType of the Enumeration class")
case class SModelWithEnum(
  @(ApiModelProperty @field)(example = "fo", value = "Order Size", dataType = "models.OrderSize$") orderSize: OrderSize = OrderSize.TALL
)

@ApiModel(description = "Scala model containing an Optional Enumeration Value")
case class SModelWithOptionalEnum(
  @(ApiModelProperty @field)(value = "Order Size", dataType = "models.OrderSize$") orderSize: Option[OrderSize] = None
)

case object OrderSize extends Enumeration(0) {
  type OrderSize = Value
  val TALL = Value("TALL")
  val GRANDE = Value("GRANDE")
  val VENTI = Value("VENTI")
}