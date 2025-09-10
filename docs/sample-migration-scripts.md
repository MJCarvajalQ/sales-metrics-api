# Sample Liquibase Changelogs

## Overview
This document contains sample Liquibase changelog files for the Sales Metrics API, reflecting the current project structure with improved Response pattern.

## Changelog File Structure

```
src/main/resources/db/changelog/
├── db.changelog-master.yaml
├── changesets/
    ├── 0001-create-users-table.yaml
    ├── 0002-create-outreach-actions-table.yaml
    ├── 0003-insert-sample-users.yaml
    ├── 0004-insert-sample-outreach-actions.yaml
    └── 0005-add-indexes-for-performance.yaml
```

## Changelogs

### db.changelog-master.yaml
```yaml
databaseChangeLog:
  - include:
      file: db/changelog/changesets/0001-create-users-table.yaml
  - include:
      file: db/changelog/changesets/0002-create-outreach-actions-table.yaml
  - include:
      file: db/changelog/changesets/0003-insert-sample-users.yaml
  - include:
      file: db/changelog/changesets/0004-insert-sample-outreach-actions.yaml
  - include:
      file: db/changelog/changesets/0005-add-indexes-for-performance.yaml
```

### 0001-create-users-table.yaml
```yaml
databaseChangeLog:
  - changeSet:
      id: 0001
      author: majo
      changes:
        - createTable:
            tableName: users
            columns:
              - column:
                  name: id
                  type: BIGSERIAL
                  constraints:
                    primaryKey: true
              - column:
                  name: name
                  type: VARCHAR(255)
                  constraints:
                    nullable: false
              - column:
                  name: email
                  type: VARCHAR(255)
                  constraints:
                    nullable: false
                    unique: true
              - column:
                  name: created_at
                  type: TIMESTAMP
                  defaultValueComputed: CURRENT_TIMESTAMP
              - column:
                  name: updated_at
                  type: TIMESTAMP
                  defaultValueComputed: CURRENT_TIMESTAMP
        - addRemarks:
            tableName: users
            remarks: Stores user information for sales outreach tracking
      rollback:
        - dropTable:
            tableName: users
```

### 0002-create-outreach-actions-table.yaml
```yaml
databaseChangeLog:
  - changeSet:
      id: 0002
      author: majo
      changes:
        - createTable:
            tableName: outreach_action
            columns:
              - column:
                  name: id
                  type: BIGSERIAL
                  constraints:
                    primaryKey: true
              - column:
                  name: user_id
                  type: BIGINT
                  constraints:
                    nullable: false
              - column:
                  name: type
                  type: VARCHAR(50)
                  constraints:
                    nullable: false
              - column:
                  name: date_time
                  type: TIMESTAMP
                  constraints:
                    nullable: false
              - column:
                  name: notes
                  type: TEXT
              - column:
                  name: created_at
                  type: TIMESTAMP
                  defaultValueComputed: CURRENT_TIMESTAMP
              - column:
                  name: updated_at
                  type: TIMESTAMP
                  defaultValueComputed: CURRENT_TIMESTAMP
        - addForeignKeyConstraint:
            baseTableName: outreach_action
            baseColumnNames: user_id
            constraintName: fk_outreach_action_user
            referencedTableName: users
            referencedColumnNames: id
            onDelete: CASCADE
        - addCheckConstraint:
            tableName: outreach_action
            constraintName: ck_outreach_action_type
            checkConstraint: "type IN ('EMAIL','CONNECTION','RESPONSE','MEETING')"
      rollback:
        - dropTable:
            tableName: outreach_action
```

### 0003-insert-sample-users.yaml
```yaml
databaseChangeLog:
  - changeSet:
      id: 0003
      author: majo
      changes:
        - insert:
            tableName: users
            columns:
              - column: { name: name, value: "Majo" }
              - column: { name: email, value: "majo@example.com" }
        - insert:
            tableName: users
            columns:
              - column: { name: name, value: "Pedro" }
              - column: { name: email, value: "pedro@example.com" }
        - insert:
            tableName: users
            columns:
              - column: { name: name, value: "Ana" }
              - column: { name: email, value: "ana@example.com" }
        - insert:
            tableName: users
            columns:
              - column: { name: name, value: "Carlos Martinez" }
              - column: { name: email, value: "carlos.martinez@example.com" }
        - insert:
            tableName: users
            columns:
              - column: { name: name, value: "Sofia Rodriguez" }
              - column: { name: email, value: "sofia.rodriguez@example.com" }
      rollback:
        - delete:
            tableName: users
            where: "email in ('majo@example.com','pedro@example.com','ana@example.com','carlos.martinez@example.com','sofia.rodriguez@example.com')"
```

### 0004-insert-sample-outreach-actions.yaml
```yaml
databaseChangeLog:
  - changeSet:
      id: 0004
      author: majo
      changes:
        - insert:
            tableName: outreach_action
            columns:
              - column: { name: user_id, valueNumeric: 1 }
              - column: { name: type, value: "EMAIL" }
              - column: { name: date_time, valueDate: "2025-01-01T09:00:00" }
              - column: { name: notes, value: "Initial outreach to prospect ABC Corp" }
        - insert:
            tableName: outreach_action
            columns:
              - column: { name: user_id, valueNumeric: 1 }
              - column: { name: type, value: "CONNECTION" }
              - column: { name: date_time, valueDate: "2025-01-02T14:30:00" }
              - column: { name: notes, value: "Connected on LinkedIn with decision maker" }
        - insert:
            tableName: outreach_action
            columns:
              - column: { name: user_id, valueNumeric: 1 }
              - column: { name: type, value: "RESPONSE" }
              - column: { name: date_time, valueDate: "2025-01-03T11:15:00" }
              - column: { name: notes, value: "Received positive response, scheduling meeting" }
        - insert:
            tableName: outreach_action
            columns:
              - column: { name: user_id, valueNumeric: 1 }
              - column: { name: type, value: "MEETING" }
              - column: { name: date_time, valueDate: "2025-01-04T15:00:00" }
              - column: { name: notes, value: "Demo call completed successfully" }
        - insert:
            tableName: outreach_action
            columns:
              - column: { name: user_id, valueNumeric: 2 }
              - column: { name: type, value: "EMAIL" }
              - column: { name: date_time, valueDate: "2025-01-01T10:00:00" }
              - column: { name: notes, value: "Follow-up email to XYZ Inc" }
        - insert:
            tableName: outreach_action
            columns:
              - column: { name: user_id, valueNumeric: 2 }
              - column: { name: type, value: "EMAIL" }
              - column: { name: date_time, valueDate: "2025-01-02T16:00:00" }
              - column: { name: notes, value: "Second follow-up with pricing information" }
        - insert:
            tableName: outreach_action
            columns:
              - column: { name: user_id, valueNumeric: 2 }
              - column: { name: type, value: "MEETING" }
              - column: { name: date_time, valueDate: "2025-01-05T13:00:00" }
              - column: { name: notes, value: "Discovery call scheduled" }
        - insert:
            tableName: outreach_action
            columns:
              - column: { name: user_id, valueNumeric: 3 }
              - column: { name: type, value: "CONNECTION" }
              - column: { name: date_time, valueDate: "2025-01-01T08:30:00" }
              - column: { name: notes, value: "LinkedIn outreach to startup founder" }
        - insert:
            tableName: outreach_action
            columns:
              - column: { name: user_id, valueNumeric: 3 }
              - column: { name: type, value: "EMAIL" }
              - column: { name: date_time, valueDate: "2025-01-02T09:45:00" }
              - column: { name: notes, value: "Introductory email with company overview" }
        - insert:
            tableName: outreach_action
            columns:
              - column: { name: user_id, valueNumeric: 3 }
              - column: { name: type, value: "RESPONSE" }
              - column: { name: date_time, valueDate: "2025-01-03T12:00:00" }
              - column: { name: notes, value: "Prospect showed interest in our solution" }
      rollback:
        - delete:
            tableName: outreach_action
            where: "notes in ('Initial outreach to prospect ABC Corp','Connected on LinkedIn with decision maker','Received positive response, scheduling meeting','Demo call completed successfully','Follow-up email to XYZ Inc','Second follow-up with pricing information','Discovery call scheduled','LinkedIn outreach to startup founder','Introductory email with company overview','Prospect showed interest in our solution')"
```

### 0005-add-indexes-for-performance.yaml
```yaml
databaseChangeLog:
  - changeSet:
      id: 0005
      author: majo
      changes:
        - createIndex:
            tableName: outreach_action
            indexName: idx_outreach_action_user_id
            columns:
              - column:
                  name: user_id
        - createIndex:
            tableName: outreach_action
            indexName: idx_outreach_action_date_time
            columns:
              - column:
                  name: date_time
        - createIndex:
            tableName: outreach_action
            indexName: idx_outreach_action_type
            columns:
              - column:
                  name: type
        - createIndex:
            tableName: outreach_action
            indexName: idx_outreach_action_user_date
            columns:
              - column:
                  name: user_id
              - column:
                  name: date_time
      rollback:
        - dropIndex:
            tableName: outreach_action
            indexName: idx_outreach_action_user_id
        - dropIndex:
            tableName: outreach_action
            indexName: idx_outreach_action_date_time
        - dropIndex:
            tableName: outreach_action
            indexName: idx_outreach_action_type
        - dropIndex:
            tableName: outreach_action
            indexName: idx_outreach_action_user_date
```

## Integration with Current API Structure
- These Liquibase changesets create the database schema that supports the current API structure
- Data inserted will be returned via `OutreachActionResponse`, `OutreachActionDetailResponse`, etc.
- The schema supports all current endpoints in `OutreachActionController` and `UserController`
- Indexes are optimized for current query patterns in `OutreachActionService`
- All changesets include rollback support for safe database evolution

## Key Benefits of Liquibase Approach
- **Database Agnostic**: Works with H2, PostgreSQL, and other databases
- **Version Control**: Changes tracked in YAML format with clear versioning
- **Rollback Support**: Each changeset includes rollback operations
- **Spring Boot Integration**: Automatic execution on application startup
- **Validation**: Built-in validation of changeset integrity
