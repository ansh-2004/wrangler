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
  * Utility class to parse time duration strings like "10s", "2h", "500ms" into milliseconds.
  */
 public class TimeDurationParser {
 
   /**
    * Parses a time duration string into its value in milliseconds.
    *
    * @param input the time string (e.g., "10s", "2h", "500ms")
    * @return the equivalent duration in milliseconds
    * @throws IllegalArgumentException if the input format is unknown
    */
   public static long parse(String input) {
     input = input.trim().toLowerCase();
     double value = Double.parseDouble(input.replaceAll("[^0-9.]", ""));
 
     if (input.endsWith("ms")) {
       return (long) value;
     }
     if (input.endsWith("s")) {
       return (long) (value * 1000);
     }
     if (input.endsWith("m")) {
       return (long) (value * 60 * 1000);
     }
     if (input.endsWith("h")) {
       return (long) (value * 60 * 60 * 1000);
     }
     if (input.endsWith("d")) {
       return (long) (value * 24 * 60 * 60 * 1000);
     }
 
     throw new IllegalArgumentException("Unknown time unit in: " + input);
   }
 }
 