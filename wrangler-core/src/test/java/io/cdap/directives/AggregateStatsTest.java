package io.cdap.directives;



import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.DirectiveContext;
import io.cdap.wrangler.api.TokenGroup;
import io.cdap.wrangler.api.parser.ColumnName;
import io.cdap.wrangler.api.parser.Text;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AggregateStatsTest {

  @Test
  public void testAggregateStatsExecution() throws Exception {
    AggregateStats directive = new AggregateStats();

    // Mock args
    TokenGroup args = TokenGroup.builder()
      .add("sourceSizeColumn", new ColumnName("bytes"))
      .add("sourceTimeColumn", new ColumnName("duration"))
      .add("targetSizeColumn", new Text("totalMB"))
      .add("targetTimeColumn", new Text("totalSeconds"))
      .build();

    directive.initialize(null, args);

    List<Row> inputRows = Arrays.asList(
      new Row("bytes", "10MB", "duration", "1s"),
      new Row("bytes", "5MB", "duration", "2s")
    );

    List<Row> output = directive.execute(inputRows, null);
    Row result = output.get(0);

    assertEquals(15.0, (double) result.getValue("totalMB"), 0.01);
    assertEquals(3.0, (double) result.getValue("totalSeconds"), 0.01);
  }
}
