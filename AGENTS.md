# AGENTS.md

## 1. Agent Role

You are the autonomous development agent for this project.

Your responsibility is to develop, maintain, test, and document a simple truck freight management system.

`AGENTS.md` is the primary source of truth for:

* Development workflow
* Business constraints
* Task management
* Testing rules
* Documentation rules
* Error handling
* Project simplicity

Do not wait for the user to explicitly ask you to create Tasks, update documentation, run tests, or track progress. These are part of your responsibility.

---
## Implementation Execution Rule

When executing a development task, implementation is the primary objective.

The agent MUST NOT spend excessive time exploring the repository when the required context is already understood.

### Required execution behavior

1. Read the relevant Task first.
2. Inspect only the minimum files required to understand the current implementation.
3. Inspect the relevant module structure, source files, configuration, dependencies, and tests only when they are relevant to the current Task.
4. Do not perform exhaustive repository exploration.
5. Do not inspect unrelated services or files unless the current Task requires them.
6. Analysis must be time-bounded and purposeful.
7. Once the required context is understood, STOP exploring and START implementing immediately.
8. For implementation Tasks, the first implementation action MUST normally be creating or modifying a required project file.
9. Do not remain in `Inspect`, `Explore`, or planning mode after the implementation path is clear.
10. Do not repeatedly list directories or search for files that have already been inspected.
11. Do not repeatedly read the same file unless it changed or new information makes another read necessary.
12. Do not spend additional time looking for a "perfect" implementation when a simple correct implementation is already clear.
13. Do not wait for additional user confirmation when the requirements are clear.
14. Do not only explain what should be implemented. ACTUALLY IMPLEMENT IT.
15. After implementing a logical part of the Task, compile and test the affected module.
16. If compilation or tests fail, diagnose and fix the problem before continuing.
17. Continue implementation until the Task is complete or a real blocker prevents progress.

### Multi-service Tasks

When a Task affects multiple microservices:

1. Inspect only the information necessary for the current service.
2. Implement the current service.
3. Compile and test the current service.
4. Fix its problems.
5. Verify the current service.
6. Continue to the next required service.

Do NOT fully inspect all microservices before implementing the first one.

### Mandatory implementation transition

If:

- the Task is clear,
- the required files are known,
- the implementation approach is understood,
- and there is no blocker,

then the agent MUST transition from:

```text
Inspect → Implement## 2. Core Business

This project is a simple truck freight transportation management system.

A Customer can create a freight order containing:

* Origin
* Destination
* Cargo type
* Cargo weight
* Refrigerated transportation requirement

The system finds a suitable Truck and Driver.

The system must verify:

* Truck capacity
* Truck availability
* Refrigerated requirement
* Driver availability

A suitable Truck and Driver are assigned to the order.

After the trip is completed, the Customer can rate the Driver.

The main business entities are:

* Customer
* Driver
* Truck
* CargoOrder
* Trip
* Rating

The first version must remain simple.

Priority:

**Simple > Understandable > Working > Testable**

---

## 3. Business Rules

### Truck Matching

A Truck is suitable when:

1. Status is `AVAILABLE`.
2. Capacity is greater than or equal to cargo weight.
3. If `refrigeratedRequired = true`, the Truck must have `refrigerated = true`.

### Driver Matching

A Driver is suitable when:

1. Status is `AVAILABLE`.
2. Driver is able to receive a Trip.

If multiple valid Trucks or Drivers exist, select the simplest valid option.

Do not implement ranking, scoring, optimization, or complex matching unless explicitly requested.

---

## 4. Statuses

### Driver

* `AVAILABLE`
* `BUSY`

### Truck

* `AVAILABLE`
* `BUSY`

### CargoOrder

* `PENDING`
* `ASSIGNED`
* `COMPLETED`
* `CANCELLED`

### Trip

* `ASSIGNED`
* `STARTED`
* `COMPLETED`

---

## 5. Architecture

The initial system contains four Microservices.

### user-service

Responsible for:

* Customer
* Driver

### truck-service

Responsible for:

* Truck

### order-service

Responsible for:

* CargoOrder

### ride-service

Responsible for:

* Trip
* Rating

Each service owns its own domain and database.

A service MUST NOT directly access another service's database.

Services communicate through APIs or messaging.

REST is the default communication method.

Do not introduce Kafka, JMS, event-driven architecture, or other messaging technologies unless:

* The user explicitly requests them, or
* They are clearly required by an existing business requirement.

Do not change the architecture without a documented reason.

---

## 6. Scope Control

Implement only what the user requests and what is required to correctly support that request.

Do NOT introduce the following unless explicitly requested:

* Payment Gateway
* GPS Tracking
* Chat
* Notification Service
* AI
* Machine Learning
* Dynamic Pricing
* Advanced Route Optimization
* Multi-Tenant Architecture
* Complex Authorization Systems
* Unnecessary Design Patterns
* Unnecessary Abstractions
* Unnecessary Dependencies
* Unnecessary Microservices
* Enterprise-level complexity

Never invent new business features.

Never expand the scope of a Task without a clear requirement.

If a possible improvement is discovered, document it as a suggestion instead of implementing it automatically.

---

# 7. Mandatory Development Workflow

For every development request, follow this workflow:

```text
User Requirement
       ↓
Read AGENTS.md
       ↓
Inspect Project
       ↓
Read Business Documentation
       ↓
Check Existing Tasks
       ↓
Create New Task
       ↓
TODO
       ↓
IN_PROGRESS
       ↓
Implement
       ↓
Test
       ↓
Problem?
   ┌───┴───┐
  YES     NO
   ↓       ↓
Diagnose  Verify
   ↓       ↓
BLOCKED   DONE
   ↓       ↓
Resolve   Update Report
   ↓
IN_PROGRESS
```

Do not skip required workflow steps.

---

## 8. Project Inspection

Before modifying code:

1. Inspect the project structure.
2. Read the relevant source files.
3. Understand the existing implementation.
4. Check relevant configuration files.
5. Check dependencies.
6. Check related services.
7. Check existing Tasks.
8. Read `docs/business.md` if it exists.
9. Read relevant previous progress reports when necessary.

Do not assume how the project works based only on filenames.

Do not rewrite working code without a reason.

---

## 9. Automatic Task Management

Every development task MUST have a Task file.

Create:

```text
tasks/
```

if it does not exist.

Task files must use:

```text
TASK-001.md
TASK-002.md
TASK-003.md
```

Task numbers must be unique and sequential.

Before creating a Task:

1. Inspect `tasks/`.
2. Find the highest existing Task number.
3. Use the next available number.
4. Never overwrite an existing Task.
5. Never reuse a previous Task number.

Do not ask the user to manually create a Task.

---

## 10. Task Creation

When a new development requirement is received:

1. Understand the requirement.
2. Check existing Tasks for duplicates.
3. Create the next Task file.
4. Set status to `TODO`.
5. Define the objective and requirements.
6. Define a simple implementation plan.
7. Change status to `IN_PROGRESS`.
8. Begin implementation.

Every Task must contain:

```markdown
# TASK-XXX

## Title

## Objective

## Requirements

## Plan

- [ ] Step 1
- [ ] Step 2
- [ ] Step 3

## Changes

## Problems

## Tests

## Final Result

## Status

TODO
```

Update the Task while working.

---

## 11. Task Status

Allowed statuses:

* `TODO`
* `IN_PROGRESS`
* `BLOCKED`
* `DONE`

Valid transitions:

```text
TODO → IN_PROGRESS
IN_PROGRESS → BLOCKED
BLOCKED → IN_PROGRESS
IN_PROGRESS → DONE
```

Do not skip directly from `TODO` to `DONE`.

Never mark incomplete work as `DONE`.

---

## 12. Task Checkboxes

Use Markdown checkboxes to track progress.

Example:

```markdown
- [ ] Inspect existing code
- [ ] Implement Entity
- [ ] Implement Repository
- [ ] Implement Service
- [ ] Implement Controller
- [ ] Add Validation
- [ ] Add Tests
- [ ] Run Tests
- [ ] Verify Business Logic
```

Mark an item as completed only after it has actually been completed and verified.

---

## 13. BLOCKED Rule

If a technical problem prevents correct progress:

1. Stop normal implementation.
2. Investigate the problem.
3. Determine the likely root cause.
4. Attempt a safe and reasonable fix if possible.
5. Test the fix.

If the problem cannot be safely resolved:

Change:

```text
IN_PROGRESS → BLOCKED
```

Document in the Task:

* Problem
* Error message
* Root cause or suspected root cause
* What was investigated
* What was attempted
* Why progress is blocked
* What is required to continue

Do NOT:

* Hide errors
* Ignore errors
* Delete failing tests
* Remove validation to bypass errors
* Make random changes
* Change unrelated code
* Pretend the problem is solved

If a problem is resolved, change:

```text
BLOCKED → IN_PROGRESS
```

Then continue the Task.

---

## 14. DONE Rule

A Task may only become `DONE` when:

* All requirements are implemented.
* All Task checkboxes are completed.
* Relevant code is complete.
* The project compiles successfully.
* Relevant tests have been executed.
* Business logic has been verified.
* No known blocking error remains.
* Documentation has been updated.

Code changes alone do not mean the Task is complete.

---

## 15. Testing

After every significant implementation:

1. Compile the affected service or project.
2. Run relevant tests.
3. Check application errors.
4. Verify the business rules.
5. Fix discovered problems.
6. Run tests again after fixes.

Do not mark a Task `DONE` only because the code looks correct.

If tests cannot be executed because of an environment or dependency problem, document the exact reason.

---

## 16. Error Handling

When an error occurs:

1. Read the complete error.
2. Identify where it originates.
3. Find the root cause.
4. Make the smallest correct change.
5. Re-run the relevant test.

Do not randomly modify multiple files hoping the error disappears.

Do not hide exceptions.

Do not weaken validation just to make a test pass.

Do not remove functionality to bypass an error.

---

## 17. Ambiguous Requirements

If a requirement is ambiguous and different interpretations could change:

* Business logic
* Data model
* API behavior
* Architecture
* Security
* Existing functionality

Ask the user for clarification before implementation.

For small, low-risk decisions, choose the simplest reasonable solution and document the decision in the Task.

Never invent important business rules.

---

## 18. Business Documentation

Create:

```text
docs/
```

if it does not exist.

Create and maintain:

```text
docs/business.md
```

This file contains the detailed Business reference.

When a requirement changes the Business:

1. Update `docs/business.md`.
2. Document the reason in the related Task.
3. Include the change in `docs/progress-fa.md`.

Do not duplicate the entire Business specification inside `AGENTS.md`.

`AGENTS.md` contains rules.

`docs/business.md` contains detailed Business information.

---

## 19. Progress Documentation

Create:

```text
docs/progress-fa.md
```

if it does not exist.

This file MUST be written in Persian.

After every completed Task, append a progress report.

Each report must contain:

* Task number
* Task title
* Date
* What was implemented
* Files changed
* Why the changes were made
* Related Business logic
* Tests performed
* Problems encountered
* How problems were solved
* Final result
* Next step

Technical identifiers may remain in English.

Examples:

* Class
* Method
* Entity
* Service
* Repository
* API
* Endpoint
* Database

Do not replace technical identifiers with inaccurate translations.

---

## 20. Architecture Changes

Do not change the architecture unless required.

If an architectural change becomes necessary:

1. Explain why it is necessary.
2. Analyze its impact.
3. Document it in the Task.
4. Update relevant documentation.
5. Implement the smallest appropriate change.
6. Test the result.

Do not introduce architecture simply because a more complex design is possible.

---

## 21. Code Quality

Prefer:

* Simple code
* Clear naming
* Small methods
* Existing project conventions
* Minimal dependencies
* Straightforward Business logic
* Testable implementations

Avoid:

* Premature abstraction
* Over-engineering
* Unnecessary patterns
* Duplicate logic
* Dead code
* Unused dependencies
* Unnecessary refactoring

Follow the existing project's coding style unless there is a good reason to change it.

---

## 22. Dependency Rules

Before adding a dependency:

1. Check whether the existing project already provides the required functionality.
2. Check existing dependencies.
3. Determine whether the dependency is actually necessary.
4. Prefer existing technologies.
5. Add a new dependency only when justified.

Document important dependency decisions in the Task.

---

## 23. Database Rules

Respect service ownership.

A service must only access its own database.

Do not create cross-service database relationships.

Do not modify database structure without checking the existing entities, repositories, migrations, and configuration.

When database changes are required:

1. Document the change in the Task.
2. Implement it consistently with the project's current database approach.
3. Test the affected functionality.

---

## 24. API Rules

Before changing or creating an API:

1. Inspect existing Controllers.
2. Inspect related Services.
3. Follow existing endpoint conventions.
4. Validate input where required.
5. Use appropriate HTTP methods and status codes.
6. Avoid changing existing API behavior unless required.

Document important API decisions in the Task.

---

## 25. No Unrequested Work

Do not implement:

* Future features
* Personal assumptions
* Optional improvements
* Refactoring unrelated to the Task
* New architecture
* New services
* New messaging systems

unless explicitly requested or strictly required for the current Task.

If you identify useful future work, add it to the Task's `Next step` or report it to the user.

Do not implement it automatically.

---

## 26. Git Safety

Do not perform destructive Git operations unless explicitly requested.

Never automatically:

* Delete unrelated files
* Reset the repository
* Force push
* Rewrite Git history
* Discard user changes

Before modifying files, preserve existing user work.

Do not overwrite changes that were not created by the current Task.

---

## 27. User Changes

Assume existing uncommitted changes may belong to the user.

Before modifying a file with existing changes:

1. Inspect the changes.
2. Do not overwrite unrelated work.
3. Modify only what is required by the current Task.

Never discard user work to make the Task easier.

---

## 28. Completion Report

When a Task is completed, update:

```text
tasks/TASK-XXX.md
docs/progress-fa.md
```

The Task must contain the final status:

```text
DONE
```

The Persian progress report must clearly explain:

* What happened
* Why it happened
* What changed
* What was tested
* Whether the Business requirement was satisfied
* What should happen next

If blocked, the final status must remain:

```text
BLOCKED
```

---

## 29. Permanent Agent Behavior

The rules in this file apply to every future development request.

The user should not need to repeat:

* Create a Task
* Check existing Tasks
* Update Task status
* Inspect the code
* Test the code
* Handle errors
* Update Business documentation
* Update progress documentation
* Mark completed work
* Stop when blocked

These are permanent responsibilities of the Agent.

---

## 30. Final Principle

The purpose of this project is not to create the most complex system.

The purpose is to create a:

**Simple, Understandable, Working, Testable, and Maintainable truck freight management system.**

Always prefer the smallest correct solution.

Never trade correctness for speed.

Never trade simplicity for unnecessary architecture.

Never hide problems.

Never mark incomplete work as `DONE`.

---

## 31. Coding Standards for Microservices (Java/Spring Boot/Hibernate)

### 31.1 General Principles
- **Language**: Java 21 (LTS)
- **Framework**: Spring Boot 3.x
- **ORM**: Hibernate/JPA (Spring Data JPA)
- **Build**: Maven (multi-module)
- **Database**: H2 (dev/test), PostgreSQL (production)

### 31.2 Project Structure (per service)
```
service-name/
├── src/main/java/com/cargoflow/{service}name/
│   ├── entity/           # JPA Entities
│   ├── repository/       # Spring Data JPA Repositories
│   ├── service/          # Business logic (Interfaces + Impl)
│   ├── controller/       # REST Controllers
│   ├── dto/              # Data Transfer Objects (Request/Response)
│   ├── mapper/           # MapStruct or manual mappers
│   ├── exception/        # Custom exceptions & Global handler
│   ├── config/           # Spring configuration classes
│   └── ServiceNameApplication.java
├── src/main/resources/
│   ├── application.yml   # Configuration
│   └── db/migration/     # Flyway migrations (if used)
└── pom.xml
```

### 31.3 Naming Conventions
| Element | Convention | Example |
|---------|------------|---------|
| Package | lowercase, domain-driven | `com.cargoflow.userservice.entity` |
| Class | PascalCase | `Customer`, `DriverServiceImpl` |
| Interface | PascalCase, `I` prefix optional | `CustomerService` |
| Method | camelCase | `findById`, `createOrder` |
| Variable | camelCase | `customerId`, `truckList` |
| Constant | UPPER_SNAKE_CASE | `MAX_CAPACITY` |
| Database Table | snake_case, plural | `customers`, `cargo_orders` |
| Database Column | snake_case | `first_name`, `cargo_weight` |
| REST Endpoint | kebab-case, plural nouns | `/api/v1/customers`, `/api/v1/cargo-orders` |

### 31.4 Entity Standards
- Annotate with `@Entity`, `@Table(name = "table_name")`
- Use `@Id` with `@GeneratedValue(strategy = GenerationType.IDENTITY)`
- Use `@Column(nullable = false, length = 100)` for constraints
- Use `@Enumerated(EnumType.STRING)` for enums
- Use `@CreationTimestamp`, `@UpdateTimestamp` for audit fields
- Implement `equals()`/`hashCode()` based on ID only
- No Lombok `@Data` on entities (use explicit getters/setters)

### 31.5 Repository Standards
- Extend `JpaRepository<Entity, Long>`
- Use derived query methods: `findByStatusAndCapacityGreaterThanEqual`
- Use `@Query` for complex queries only
- No business logic in repositories

### 31.6 Service Layer Standards
- Define interface (e.g., `CustomerService`) + implementation (`CustomerServiceImpl`)
- Annotate impl with `@Service` and `@Transactional(readOnly = true)` default
- Use `@Transactional` on write methods
- Throw custom exceptions (not raw `RuntimeException`)
- No HTTP types (ResponseEntity, HttpStatus) in service layer

### 31.7 Controller Standards
- Annotate with `@RestController`, `@RequestMapping("/api/v1/resource")`
- Use `@Valid` on request DTOs
- Return `ResponseEntity<T>` with proper HTTP status codes
- Use DTOs for request/response (never expose entities directly)
- Handle exceptions via `@ControllerAdvice` global handler

### 31.8 DTO Standards
- Separate Request/Response DTOs (e.g., `CustomerRequest`, `CustomerResponse`)
- Use Jakarta Validation: `@NotNull`, `@Size`, `@Min`, `@Max`, `@Email`
- Use `@JsonProperty` for JSON field mapping if needed
- No JPA annotations in DTOs

### 31.9 Exception Handling
- Create custom exceptions: `ResourceNotFoundException`, `BusinessRuleViolationException`
- Global handler: `@ControllerAdvice` with `@ExceptionHandler`
- Return standardized error response:
```json
{
  "timestamp": "2026-08-29T10:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Customer not found with id: 123",
  "path": "/api/v1/customers/123"
}
```

### 31.10 Configuration Standards
- Use `application.yml` (not `.properties`)
- Profile-specific: `application-dev.yml`, `application-prod.yml`
- Externalize secrets via environment variables: `${DB_PASSWORD:}`
- Database: `ddl-auto: validate` (prod), `create-drop` (test only)

### 31.11 Inter-Service Communication
- REST via `WebClient` (reactive) or `RestClient` (sync, Spring Boot 3.2+)
- Use `@ConfigurationProperties` for service URLs
- Implement circuit breaker (Resilience4j) if needed
- Timeouts: connect 5s, read 30s

### 31.12 Testing Standards
- Unit tests: JUnit 5 + Mockito (`@ExtendWith(MockitoExtension.class)`)
- Integration tests: `@SpringBootTest` + Testcontainers (PostgreSQL)
- Test naming: `MethodName_Scenario_ExpectedResult`
- Coverage target: 80%+ for service layer

### 31.13 Service-Specific Rules

#### user-service (Port 8081)
- Entities: `Customer`, `Driver`
- Enums: `DriverStatus` (AVAILABLE, BUSY)
- Endpoints: `/api/v1/customers`, `/api/v1/drivers`

#### truck-service (Port 8082)
- Entity: `Truck`
- Enum: `TruckStatus` (AVAILABLE, BUSY)
- Fields: `licensePlate`, `capacity`, `refrigerated`, `status`
- Endpoints: `/api/v1/trucks`

#### order-service (Port 8083)
- Entity: `CargoOrder`
- Enum: `CargoOrderStatus` (PENDING, ASSIGNED, COMPLETED, CANCELLED)
- Fields: `origin`, `destination`, `cargoType`, `cargoWeight`, `refrigeratedRequired`, `status`
- Endpoints: `/api/v1/cargo-orders`

#### ride-service (Port 8084)
- Entities: `Trip`, `Rating`
- Enums: `TripStatus` (ASSIGNED, STARTED, COMPLETED)
- Trip links: `cargoOrderId`, `truckId`, `driverId` (no JPA relations across services)
- Endpoints: `/api/v1/trips`, `/api/v1/ratings`

### Memory Rules
At the beginning of every new development session, read docs/agent-state.md if it exists.
Use docs/agent-state.md to understand:
Current Task
Current Task status
Last completed Task
Last completed operation
Current implementation state
Recently changed files
Completed features
Current problems
Current blockers
Last successful build/test
Next concrete action
Use the memory file to decide which files actually need inspection.
Do NOT rescan the entire repository when the required information is already available in docs/agent-state.md.
Do NOT repeatedly inspect files that are already known to be unchanged.
The memory file does NOT replace source-code verification when verification is required.
If the memory file conflicts with the actual source code, trust the actual source code and immediately correct the memory file.
After every meaningful implementation step, update docs/agent-state.md.
After completing a Task, update docs/agent-state.md before marking the Task as DONE.
If a Task becomes BLOCKED, record the blocker in docs/agent-state.md.
When a blocker is resolved, update docs/agent-state.md.
Remove outdated information instead of continuously appending duplicate information.
Keep docs/agent-state.md concise and operational.
Do NOT copy source code or detailed implementation documentation into the memory file.
Do NOT use docs/agent-state.md as a replacement for Task files or business documentation.