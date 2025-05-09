# SAT-Based Planner for STRIPS Domains

## Overview

This project implements a SAT-based planner using the schema proposed in the course materials.

## Features

- Full implementation of the planner encoding schema
- Fix for action decoding bug (absolute value missing when unpairing codes)
- Support for reusable encoding across multiple steps
- Map-based structure for fluent-action relationships to improve readability and maintainability

## Domain & Problem Setup

For testing, we defined a domain similar to the one presented in lecture slides: a robot places balls into rooms.

- **Domain file**: `d.pddl`
- **Problem files**:
  - `p1.pddl`
  - `p2.pddl`

### Known Issue

Despite substantial debugging, the planner currently behaves incorrectly. In particular, when solving `p2.pddl`, the planner "teleports" the robot directly to the goal room and drops the ball without performing a move action.

## Implementation Notes

### `SATEncoding` Class

- **Constructor**: Initializes all relevant problem elements:
  - Initial state
  - Action definitions
  - Fluent indices
- **`encode()` Method**: Encodes the planning problem from the current step up to a given step, avoiding redundant clause generation.

Instead of relying on static index offsets (e.g., `nb_fluents`), the implementation uses maps to assign and track unique identifiers for fluents and actions. This improves readability and reduces indexing errors.

### Data Structures

Maps are used extensively in `SATEncoding` to link fluents and actions, enabling the generation of:
- Action implications
- State transitions
- Mutex constraints

Each map is documented with its specific purpose (e.g., `Action2Precon`, `PosEff2Action`), helping clarify how actions affect and are affected by fluents.
