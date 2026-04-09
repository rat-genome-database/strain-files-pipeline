# strain-files-pipeline

Reports strain files added or modified in RGD within the past week.

## Overview

Each week, the pipeline queries the `STRAIN_FILES` table for entries with a
`last_modified_date` within the past 7 days, and writes a summary report listing
each file's strain ID, content type, file name, file type, modifier, and a link
to the strain page on the RGD website. The report is then emailed to RGD curators.

## Logic

1. Load all strain files from the `STRAIN_FILES` table
2. Filter to entries with `last_modified_date` >= today minus 7 days
3. For each new file, log details and a link to the strain page
4. Log total count of new files

## Logging

- `status` — pipeline progress with details for each new file (also written to
  status.log, summary.log, detail.log)

## Build and run

Requires Java 17. Built with Gradle:
```
./gradlew clean assembleDist
```
