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

 package io.cdap.directives;

 /**
  * Utility class to parse byte size strings like "10MB", "512KB" into bytes.
  */
 public class ByteSizeParser {
 
   /**
    * Parses a byte size string into its numeric byte value.
    *
    * @param input the byte size string (e.g., "10MB", "512KB")
    * @return the byte value
    * @throws IllegalArgumentException if the input format is unknown
    */
   public static long parse(String input) {
     input = input.trim().toUpperCase();
     double value = Double.parseDouble(input.replaceAll("[^0-9.]", ""));
 
     if (input.endsWith("KB")) {
       return (long) (value * 1024);
     }
     if (input.endsWith("MB")) {
       return (long) (value * 1024 * 1024);
     }
     if (input.endsWith("GB")) {
       return (long) (value * 1024 * 1024 * 1024);
     }
     if (input.endsWith("TB")) {
       return (long) (value * 1024L * 1024L * 1024L * 1024L);
     }
     if (input.endsWith("B")) {
       return (long) value;
     }
 
     throw new IllegalArgumentException("Unknown byte unit in: " + input);
   }
 }
 