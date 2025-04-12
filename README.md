# Wrangler Directive Extension Assignment

## 📘 Assignment Summary

This assignment involved extending the Wrangler project to support two new token types:
- `BYTE_SIZE` (e.g., "10MB", "1GB")
- `TIME_DURATION` (e.g., "10s", "5m", "1h")

A new directive called `AggregateStats` was implemented, which:
- Aggregates values from two columns (byte sizes and durations).
- Converts totals into megabytes and seconds.
- Supports parsing both `String` and new `Token` types like `ByteSize` and `TimeDuration`.

### 🔧 Modifications Done
- Updated `Directives.g4` to include `BYTE_SIZE` and `TIME_DURATION` tokens.
- Added `ByteSize.java` and `TimeDuration.java` in `wrangler-api`.
- Implemented `AggregateStats.java` directive in `wrangler-core`.
- Added parser logic `ByteSizeParser.java` and `TimeDurationParser.java`.
- Modified `RecipeVisitor.java` to handle new tokens.
- Added a unit test for `AggregateStats`.

### ✅ Build & Test

```bash
mvn clean install 
