# Spec: `TasksListBindings.kt` - AI draft

> Draft generated before human refinement. Expectations below are provisional and must be checked against feature intent.

## `setDueDateLabel(textView: TextView, task: Task?)`

**Intent**: Display a task's due-date label in a `TextView` when the task produces a label.

**Pre-conditions**
- `textView` is a valid Android view.

**Post-conditions**
- Success cases
  - A completed task sets the text to `CLEAR` and makes the view visible. `[Integration]`
    - Tip: Given a `TextView` and a completed task with no due date / When `setDueDateLabel` is called / Then the text is `CLEAR` and visibility is `VISIBLE`.
  - A task with a non-empty due-date label displays the returned label and is visible. `[Integration]`
    - Tip: Given a `TextView` and a task with a due date equal to today / When `setDueDateLabel` is called / Then the text is `D-Day` and visibility is `VISIBLE`.
- Error cases
  - None; a nullable task is accepted. `[Integration]`
    - Tip: Given a valid `TextView` and either a task or null / When `setDueDateLabel` is called / Then the call does not throw.
- Edge cases
  - When the task is null or has no label, this draft leaves existing view state unchanged. `[Integration]`
    - Tip: Given a `TextView` already showing `CLEAR` and an active task with `dueDate = null` / When `setDueDateLabel` is called / Then the text remains `CLEAR` and visibility remains `VISIBLE`.

**Undefined behavior** (optional)
- None identified in this initial draft.
