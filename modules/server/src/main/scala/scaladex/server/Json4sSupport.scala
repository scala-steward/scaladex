package scaladex.server

/*
 * Copyright 2015 Heiko Seeberger
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

import akka.http.scaladsl.marshalling.Marshaller
import akka.http.scaladsl.marshalling.ToEntityMarshaller
import akka.http.scaladsl.model.MediaTypes.`application/json`
import akka.http.scaladsl.unmarshalling.FromEntityUnmarshaller
import akka.http.scaladsl.unmarshalling.Unmarshaller
import org.json4s._

/**
 * Automatic to and from JSON marshalling/unmarshalling using an in-scope *Json4s* protocol.
 */
trait Json4sSupport {
  implicit val formats: DefaultFormats.type = DefaultFormats
  implicit val serialization: org.json4s.native.Serialization.type =
    native.Serialization

  /**
   * HTTP entity => `A`
   *
   * @tparam A type to decode
   * @return unmarshaller for `A`
   */
  implicit def json4sUnmarshaller[A: Manifest](
      implicit serialization: Serialization,
      formats: Formats
  ): FromEntityUnmarshaller[A] =
    Unmarshaller.byteStringUnmarshaller
      .forContentTypes(`application/json`)
      .mapWithCharset { (data, charset) =>
        try serialization.read(data.decodeString(charset.nioCharset.name))
        catch {
          case error: Throwable => throw new Exception(s"failed decoding data because ${error.getCause}")
        }
      }

  /**
   * `A` => HTTP entity
   *
   * @tparam A type to encode, must be upper bounded by `AnyRef`
   * @return marshaller for any `A` value
   */
  implicit def json4sMarshaller[A <: AnyRef](
      implicit serialization: Serialization,
      formats: Formats
  ): ToEntityMarshaller[A] =
    Marshaller.StringMarshaller.wrap(`application/json`)(
      serialization.writePretty[A]
    )
}
