/*
 * Copyright © 2025 <Your Name or Organization>
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

 package io.cdap.wrangler.api.parser;

 import com.google.gson.JsonElement;
 import com.google.gson.JsonPrimitive;
 
 /**
  * Token representing a byte size string value (e.g., "10MB", "512KB").
  */
 public class ByteSize implements Token {
 
   private final String value;
 
   /**
    * Constructs a ByteSize token.
    *
    * @param value the byte size string
    */
   public ByteSize(String value) {
     this.value = value;
   }
 
   /**
    * Returns the raw string value of this byte size.
    *
    * @return the string representation
    */
   public String value() {
     return this.value;
   }
 
   @Override
   public JsonElement toJson() {
     return new JsonPrimitive(this.value);
   }
 
   @Override
   public TokenType type() {
     return TokenType.BYTE_SIZE;
   }
 }
 