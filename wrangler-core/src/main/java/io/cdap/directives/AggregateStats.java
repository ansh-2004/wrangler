/*
 * Copyright © 2025 <Your Organization or Name>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package io.cdap.directives;

import io.cdap.wrangler.api.Directive;
import io.cdap.wrangler.api.DirectiveContext;
import io.cdap.wrangler.api.DirectiveExecutionException;
import io.cdap.wrangler.api.ExecutorContext;
import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.TokenGroup;
import io.cdap.wrangler.api.UsageDefinition;
import io.cdap.wrangler.api.parser.ColumnName;
import io.cdap.wrangler.api.parser.Text;
import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.api.parser.TimeDuration;

import java.util.ArrayList;
import java.util.List;

public class AggregateStats implements Directive {

  private String sizeColumn;
  private String timeColumn;
  private String targetSizeColumn;
  private String targetTimeColumn;

  private long totalBytes = 0;
  private long totalTimeMs = 0;
  private int rowCount = 0;

  @Override
  public UsageDefinition define() {
    return UsageDefinition.builder("aggregate-stats", "Aggregate byte size and time duration columns")
      .define("sourceSizeColumn", ColumnName.class)
      .define("sourceTimeColumn", ColumnName.class)
      .define("targetSizeColumn", Text.class)
      .define("targetTimeColumn", Text.class)
      .build();
  }

  @Override
  public void initialize(DirectiveContext ctx, TokenGroup args) throws DirectiveExecutionException {
    this.sizeColumn = ((ColumnName) args.value("sourceSizeColumn")).value();
    this.timeColumn = ((ColumnName) args.value("sourceTimeColumn")).value();
    this.targetSizeColumn = ((Text) args.value("targetSizeColumn")).value();
    this.targetTimeColumn = ((Text) args.value("targetTimeColumn")).value();
  }

  @Override
  public List<Row> execute(List<Row> rows, ExecutorContext ctx) throws DirectiveExecutionException {
    for (Row row : rows) {
      Object sizeVal = row.getValue(this.sizeColumn);
      Object timeVal = row.getValue(this.timeColumn);

      if (sizeVal instanceof ByteSize) {
        this.totalBytes += this.parseByteSize(((ByteSize) sizeVal).value());
      } else if (sizeVal instanceof String) {
        this.totalBytes += this.parseByteSize((String) sizeVal);
      }

      if (timeVal instanceof TimeDuration) {
        this.totalTimeMs += this.parseTimeDuration(((TimeDuration) timeVal).value());
      } else if (timeVal instanceof String) {
        this.totalTimeMs += this.parseTimeDuration((String) timeVal);
      }

      this.rowCount++;
    }

    double totalMB = this.totalBytes / (1024.0 * 1024.0);
    double totalSeconds = this.totalTimeMs / 1000.0;

    Row result = new Row();
    result.add(this.targetSizeColumn, totalMB);
    result.add(this.targetTimeColumn, totalSeconds);

    List<Row> output = new ArrayList<>();
    output.add(result);
    return output;
  }

  private long parseByteSize(String value) {
    return ByteSizeParser.parse(value);
  }

  private long parseTimeDuration(String value) {
    return TimeDurationParser.parse(value);
  }

  @Override
  public void destroy() {
    // No cleanup needed
  }
}
