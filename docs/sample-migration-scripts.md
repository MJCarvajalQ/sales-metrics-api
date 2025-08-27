# Sample Migration Scripts

## Overview
This document contains sample Flyway migration scripts for the Sales Metrics API, reflecting the current project structure with improved Response pattern.

## Migration File Structure

```
src/main/resources/db/migration/
├── V1__Create_users_table.sql
├── V2__Create_outreach_actions_table.sql
├── V3__Insert_sample_users.sql
├── V4__Insert_sample_outreach_actions.sql
└── V5__Add_indexes_for_performance.sql
```

## Migration Scripts

### V1__Create_users_table.sql
```sql
-- Create users table
-- This table stores user information for the sales metrics system

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Add comment to table
COMMENT ON TABLE users IS 'Stores user information for sales outreach tracking';
COMMENT ON COLUMN users.id IS 'Unique identifier for each user';
COMMENT ON COLUMN users.name IS 'Full name of the user';
COMMENT ON COLUMN users.email IS 'Email address (must be unique)';
COMMENT ON COLUMN users.created_at IS 'Timestamp when user was created';
COMMENT ON COLUMN users.updated_at IS 'Timestamp when user was last updated';
```

### V2__Create_outreach_actions_table.sql
```sql
-- Create outreach_action table
-- This table stores all outreach activities performed by users

CREATE TABLE outreach_action (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL CHECK (type IN ('EMAIL', 'CONNECTION', 'RESPONSE', 'MEETING')),
    date_time TIMESTAMP NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Foreign key constraint
    CONSTRAINT fk_outreach_action_user 
        FOREIGN KEY (user_id) 
        REFERENCES users(id) 
        ON DELETE CASCADE
);

-- Add comments to table
COMMENT ON TABLE outreach_action IS 'Stores outreach activities performed by users';
COMMENT ON COLUMN outreach_action.id IS 'Unique identifier for each outreach action';
COMMENT ON COLUMN outreach_action.user_id IS 'Reference to the user who performed the action';
COMMENT ON COLUMN outreach_action.type IS 'Type of outreach action (EMAIL, CONNECTION, RESPONSE, MEETING)';
COMMENT ON COLUMN outreach_action.date_time IS 'When the outreach action was performed';
COMMENT ON COLUMN outreach_action.notes IS 'Additional notes about the outreach action';
COMMENT ON COLUMN outreach_action.created_at IS 'Timestamp when record was created';
COMMENT ON COLUMN outreach_action.updated_at IS 'Timestamp when record was last updated';
```

### V3__Insert_sample_users.sql
```sql
-- Insert sample users for development/testing
-- This data supports the current API structure with OutreachActionResponse

INSERT INTO users (name, email) VALUES 
    ('Majo', 'majo@example.com'),
    ('Pedro', 'pedro@example.com'),
    ('Ana', 'ana@example.com');

-- Add more sample users for testing various scenarios
INSERT INTO users (name, email) VALUES 
    ('Carlos Martinez', 'carlos.martinez@example.com'),
    ('Sofia Rodriguez', 'sofia.rodriguez@example.com');
```

### V4__Insert_sample_outreach_actions.sql
```sql
-- Insert sample outreach actions
-- This data will be returned via OutreachActionResponse in the API

INSERT INTO outreach_action (user_id, type, date_time, notes) VALUES 
    -- Majo's actions
    (1, 'EMAIL', '2025-01-01 09:00:00', 'Initial outreach to prospect ABC Corp'),
    (1, 'CONNECTION', '2025-01-02 14:30:00', 'Connected on LinkedIn with decision maker'),
    (1, 'RESPONSE', '2025-01-03 11:15:00', 'Received positive response, scheduling meeting'),
    (1, 'MEETING', '2025-01-04 15:00:00', 'Demo call completed successfully'),
    
    -- Pedro's actions
    (2, 'EMAIL', '2025-01-01 10:00:00', 'Follow-up email to XYZ Inc'),
    (2, 'EMAIL', '2025-01-02 16:00:00', 'Second follow-up with pricing information'),
    (2, 'MEETING', '2025-01-05 13:00:00', 'Discovery call scheduled'),
    
    -- Ana's actions
    (3, 'CONNECTION', '2025-01-01 08:30:00', 'LinkedIn outreach to startup founder'),
    (3, 'EMAIL', '2025-01-02 09:45:00', 'Introductory email with company overview'),
    (3, 'RESPONSE', '2025-01-03 12:00:00', 'Prospect showed interest in our solution');
```

### V5__Add_indexes_for_performance.sql
```sql
-- Add indexes for better query performance
-- These support the common queries used by OutreachActionService

-- Index for querying actions by user (used by getActionsByUserId)
CREATE INDEX idx_outreach_action_user_id ON outreach_action(user_id);

-- Index for querying actions by date range (for future metrics features)
CREATE INDEX idx_outreach_action_date_time ON outreach_action(date_time);

-- Index for querying actions by type (for future analytics)
CREATE INDEX idx_outreach_action_type ON outreach_action(type);

-- Composite index for user + date queries (optimization for reporting)
CREATE INDEX idx_outreach_action_user_date ON outreach_action(user_id, date_time);

-- Add comments
COMMENT ON INDEX idx_outreach_action_user_id IS 'Optimizes queries filtering by user_id (supports getActionsByUserId)';
COMMENT ON INDEX idx_outreach_action_date_time IS 'Optimizes date range queries for metrics calculations';
COMMENT ON INDEX idx_outreach_action_type IS 'Optimizes queries filtering by action type';
COMMENT ON INDEX idx_outreach_action_user_date IS 'Optimizes user-specific date range queries';
```

## Integration with Current API Structure
- These scripts create the database schema that supports the current API structure
- Data inserted will be returned via `OutreachActionResponse`, `OutreachActionDetailResponse`, etc.
- The schema supports all current endpoints in `OutreachActionController` and `UserController`
- Indexes are optimized for current query patterns in `OutreachActionService`
