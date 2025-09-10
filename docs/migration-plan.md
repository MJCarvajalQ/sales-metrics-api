# Migration Plan Document

## Overview
This migration plan outlines the steps necessary to transition the Sales Metrics API from using an H2 in-memory database to PostgreSQL in production, utilizing Liquibase for schema management.

## Migration Strategy

### Phase 1: Research and Planning
- Research database migration tools (Liquibase selected based on mentor recommendation and industry best practices)
- Decide on PostgreSQL as the production database
- Design Liquibase changelog structure
- Plan for backward compatibility with rollback support

### Phase 2: Setup and Configuration
- Install PostgreSQL and configure database and user credentials
- Add Liquibase to the project and configure
- Prepare `application.yml` for PostgreSQL and Liquibase
- Create changelog directory structure

### Phase 3: Develop Liquibase Changesets
- Author changesets in `/src/main/resources/db/changelog/`
- Create baseline changeset for existing schema
- Write changesets for creating tables and inserting sample data
- Develop changesets for adding indexes and optimizing query performance
- Include rollback operations for all changesets

### Phase 4: Testing and Verification
- Verify locally with `mvn liquibase:validate`
- Execute all changesets on a PostgreSQL development instance
- CI step runs `mvn -Pci liquibase:update` on a disposable PostgreSQL container
- Verify that the application integrates with the new database
- Run tests to ensure data integrity and functionality
- Test rollback operations with `mvn liquibase:rollback`

### Phase 5: Documentation and Handover
- Document the database setup and migration process 
- Provide migration plan and scripts to the team
- Ensure all team members are aligned with the proposed approach

### Phase 6: Deployment Planning
- Plan a deployment pipeline for Liquibase changesets
- Production release executed automatically by Spring Boot on startup
- Establish rollback procedures using Liquibase tags and rollback commands
- Plan for production deployment with zero-downtime migrations

## Timeline

| Phase | Tasks | Duration |
|-------|-------|----------|
| Research and Planning | Tool evaluation, decision-making | 1 Week |
| Setup and Configuration | Installation, configuration | 1 Week |
| Develop Migration Scripts | Creation of migration files | 2 Weeks |
| Testing and Verification | Testing migrations and integrations | 1 Week |
| Documentation and Handover | Creating documentation, team alignment | 1 Week |
| Deployment Planning | Deployment strategies and rollback planning | 1 Week |

## Rollback Strategy

1. Each Liquibase changeset includes explicit rollback operations
2. Use Liquibase rollback commands:
   - `mvn liquibase:rollback -Dliquibase.rollbackCount=1` - Rollback last changeset
   - `mvn liquibase:rollbackToTag -Dliquibase.rollbackTag=v1.0` - Rollback to specific tag
3. Test rollback procedures in a non-production environment
4. Confirm that rollback operations restore the database to its previous state without data loss
5. Create rollback tags before major releases for easy recovery points

## Dependencies

- Approval from the development team
- Coordination with DevOps for database setup
- Integration testing resources availabilty

## Risks and Mitigations

- **Risk**: Migration may disrupt ongoing development if not planned carefully
  - **Mitigation**: Schedule migrations during non-peak hours

- **Risk**: Incomplete data conversion could lead to application errors
  - **Mitigation**: Thorough testing and data validation ahead of production rollout

- **Risk**: Compatibility issues with application code
  - **Mitigation**: Ensure application logic is thoroughly tested with the new schema

## Conclusion

This plan provides a clear roadmap for migrating the Sales Metrics API to a robust production-ready database setup with PostgreSQL, supported with a version-controlled schema using Liquibase. The Liquibase approach provides superior rollback capabilities, database portability, and enterprise-grade change management features.
