# Spec: `DueDateUtils.kt` - AI draft

> Draft generated before human refinement. Expectations below are provisional and must be checked against feature intent.

## `dueDateLabel(dueDate: LocalDate?, today: LocalDate, isCompleted: Boolean): String`

**Intent**: Build the task's D-day text from its due date and completion state.

**Pre-conditions**
- `today` is the date used as the reference date.

**Post-conditions**
- Success cases
  - A completed task returns `CLEAR`, even when `dueDate` is null. `[Unit]`
    - Tip: Given `dueDate = null`, `today = 2026-10-14`, and `isCompleted = true` / When `dueDateLabel` is called / Then return `CLEAR`.
  - An active task with no due date returns an empty string. `[Unit]`
    - Tip: Given `dueDate = null`, `today = 2026-10-14`, and `isCompleted = false` / When `dueDateLabel` is called / Then return `""`.
  - An active task due in two days returns `D-2`. `[Unit]`
    - Tip: Given `today = 2026-10-14`, `dueDate = 2026-10-16`, and `isCompleted = false` / When `dueDateLabel` is called / Then return `D-2`.
  - An active task overdue by one day returns `D+1`. `[Unit]`
    - Tip: Given `today = 2026-10-14`, `dueDate = 2026-10-13`, and `isCompleted = false` / When `dueDateLabel` is called / Then return `D+1`.
- Error cases
  - None; nullable due dates and either completion state are valid. `[Unit]`
    - Tip: Given each valid state above / When `dueDateLabel` is called / Then it returns a label without throwing.
- Edge cases
  - When due date equals today, return `D-Day`. `[Unit]`
    - Tip: Given `today = 2026-10-14`, `dueDate = 2026-10-14`, and `isCompleted = false` / When `dueDateLabel` is called / Then return `D-Day`.
  - For dates farther than one month apart, this draft expects the day component of the date period; for `2026-10-15` to `2026-11-20`, return `D-5`. `[Unit]`
    - Tip: Given `today = 2026-10-15`, `dueDate = 2026-11-20`, and `isCompleted = false` / When `dueDateLabel` is called / Then return `D-5`.

**Undefined behavior** (optional)
- None identified in this initial draft.
