# Spec: `TasksListBindings.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `setDueDateLabel(textView: TextView, task: Task?)`

**Intent**: Keep a task's current D-day label synchronized with its state. If no label applies, clear old text and hide the label view so recycled list rows cannot show stale content.

**Pre-conditions**
- `textView` is a non-null Android `TextView`.
- `task` may be null.

**Post-conditions**
- Success cases
  - A completed task displays `CLEAR` and the label view is `VISIBLE`, with or without a due date. `[Integration]`
    - Tip: Given a `TextView` and `Task(dueDate = null, isCompleted = true)`, then `Task(dueDate = 2026-10-16, isCompleted = true)` / When `setDueDateLabel` is called for each task / Then text is `CLEAR` and visibility is `VISIBLE` each time.
  - An active task due today displays `D-Day` and the view is `VISIBLE`. `[Integration]`
    - Tip: Given a `TextView` and `Task(dueDate = LocalDate.now(), isCompleted = false)` / When `setDueDateLabel` is called / Then text is `D-Day` and visibility is `VISIBLE`.
  - An active task with no due date clears the text and hides the view. `[Integration]`
    - Tip: Given a `TextView` displaying `CLEAR` with visibility `VISIBLE` and `Task(dueDate = null, isCompleted = false)` / When `setDueDateLabel` is called / Then text is `""` and visibility is `GONE`.
  - A null task clears the text and hides the view. `[Integration]`
    - Tip: Given a `TextView` displaying `D-Day` with visibility `VISIBLE` and `task = null` / When `setDueDateLabel` is called / Then text is `""` and visibility is `GONE`.
- Error cases
  - None; null task and absent due date are valid states. `[Integration]`
    - Tip: Given a `TextView`, `Task(isCompleted = true, dueDate = null)`, and then `task = null` / When `setDueDateLabel` is called for both states / Then both calls return without throwing.
- Edge cases
  - Switching the same recycled view from a completed task with no due date to an active task with no due date removes `CLEAR`. `[Integration]`
    - Tip: Given `Task(dueDate = null, isCompleted = true)` followed by `Task(dueDate = null, isCompleted = false)` on the same `TextView` / When `setDueDateLabel` is called for both / Then the second state has empty text and `GONE` visibility.

**Undefined behavior** (optional)
- None; every task state in this feature has a defined label or no visible label.
