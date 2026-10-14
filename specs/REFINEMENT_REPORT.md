# Spec Refinement Report

I reviewed the two AI-generated drafts against the due-date feature intent and the spec checklist. I kept the AI drafts unchanged and recorded the reviewed behavior in the two intent-verified specs. No production code or tests were changed in this step.

## `DueDateUtils.kt`

- Clarified that `N` is the total number of calendar days between `today` and `dueDate`, rather than only the day component of a date period. For example, 2026-10-15 to 2026-11-20 is 36 days, so the label is `D-36`, not `D-5`.
- Specified the distinct intent cases: completed tasks return `CLEAR` with or without a date; active tasks without a date return an empty string; future and overdue dates use `D-N` and `D+N`.
- Added exact boundary examples for today, one day before and after, month and year crossings, and leap day. These cases make the date-counting contract testable and check the boundaries highlighted by the feature.
- Stated that there are no rejected inputs: an absent due date is valid, and both dates are non-null `LocalDate` values when present.

## `TasksListBindings.kt`

- Made the view state part of the contract: a non-empty label is shown with `VISIBLE`; when there is no label, the text is cleared and the view is `GONE`.
- Added cases for a null task and an active task without a due date. These ensure a reused list row does not retain the previous task's label.
- Expanded the completed-task case to cover both absent and present due dates, since completion takes precedence and should show `CLEAR` in either case.
- Kept one active due-today case to verify that the binding displays the utility's `D-Day` result. The binding uses the current date internally, so the example sets the due date to `LocalDate.now()`.
- Marked view-binding cases as `[Integration]` because they assert Android `TextView` text and visibility; marked the pure date-label cases as `[Unit]`.

## Files

- AI drafts: `DueDateUtils-AI.spec.md`, `TasksListBindings-AI.spec.md`
- Intent-verified specs: `DueDateUtils.spec.md`, `TasksListBindings.spec.md`
