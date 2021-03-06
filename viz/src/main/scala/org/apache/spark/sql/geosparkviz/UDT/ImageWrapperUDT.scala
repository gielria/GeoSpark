/*
 * FILE: ImageWrapperUDT.scala
 * Copyright (c) 2015 - 2019 GeoSpark Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.spark.sql.geosparkviz.UDT

import org.apache.spark.sql.catalyst.util.{ArrayData, GenericArrayData}
import org.apache.spark.sql.types.{ArrayType, ByteType, DataType, UserDefinedType}
import org.datasyslab.geosparkviz.core.ImageSerializableWrapper
import org.datasyslab.geosparkviz.core.Serde.ImageWrapperSerializer

private[sql] class ImageWrapperUDT extends UserDefinedType[ImageSerializableWrapper]{
  override def sqlType: DataType = ArrayType(ByteType, containsNull = false)

  override def serialize(obj: ImageSerializableWrapper): GenericArrayData =
  {

    val serializer = new ImageWrapperSerializer
    new GenericArrayData(serializer.writeImage(obj))
  }

  override def deserialize(datum: Any): ImageSerializableWrapper =
  {
    datum match
    {
      case values: ArrayData => {
        val serializer = new ImageWrapperSerializer
        serializer.readImage(values.toByteArray())
      }
    }
  }

  override def userClass: Class[ImageSerializableWrapper] = classOf[ImageSerializableWrapper]
}
