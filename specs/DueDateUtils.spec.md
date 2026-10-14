# Spec: `DueDateUtils.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `dueDateLabel(dueDate: LocalDate?, today: LocalDate, isCompleted: Boolean): String`

**Intent**: Return the task-list label implied by completion state and due date. For active tasks, the signed day count is the total number of calendar days between `today` and `dueDate`, including across month, year, and leap-day boundaries.

**Pre-conditions**
- `today` is the calendar date against which the label is calculated.
- `dueDate` may be absent; dates use the ISO calendar system.

**Post-conditions**
- Success cases
  - A completed task returns `CLEAR` whether or not it has a due date. `[Unit]`
    - Tip: Given `today = 2026-10-14` and `isCompleted = true`, with `dueDate = null` and then `dueDate = 2026-10-16` / When `dueDateLabel` is called / Then return `CLEAR` for both inputs.
  - An active task without a due date returns an empty string. `[Unit]`
    - Tip: Given `today = 2026-10-14`, `dueDate = null`, and `isCompleted = false` / When `dueDateLabel` is called / Then return `""`.
  - An active task due two days later returns `D-2`. `[Unit]`
    - Tip: Given `today = 2026-10-14`, `dueDate = 2026-10-16`, and `isCompleted = false` / When `dueDateLabel` is called / Then return `D-2`.
  - An active task overdue by two days returns `D+2`. `[Unit]`
    - Tip: Given `today = 2026-10-14`, `dueDate = 2026-10-12`, and `isCompleted = false` / When `dueDateLabel` is called / Then return `D+2`.
- Error cases
  - None. `dueDate = null` is a valid input, and `today` is non-null by the function signature. `[Unit]`
    - Tip: Given each input above / When `dueDateLabel` is called / Then return the specified label without throwing.
- Edge cases
  - A due date equal to today returns `D-Day`; one day ahead returns `D-1`; one day behind returns `D+1`. `[Unit]`
    - Tip: Given `today = 2026-10-14` with due dates `2026-10-14`, `2026-10-15`, and `2026-10-13` / When `dueDateLabel` is called for each date / Then return `D-Day`, `D-1`, and `D+1`, respectively.
  - Total-day counting includes dates spanning a month boundary in both directions. `[Unit]`
    - Tip: Given `today = 2026-10-15` and `dueDate = 2026-11-20`, or `today = 2026-11-20` and `dueDate = 2026-10-15`, with `isCompleted = false` / When `dueDateLabel` is called / Then return `D-36` and `D+36`, respectively.
  - Total-day counting includes dates spanning a year boundary. `[Unit]`
    - Tip: Given `today = 2026-12-31`, `dueDate = 2027-01-02`, and `isCompleted = false` / When `dueDateLabel` is called / Then return `D-2`.
  - The count includes the leap day when crossing February in a leap year. `[Unit]`
    - Tip: Given `today = 2024-02-28`, `dueDate = 2024-03-01`, and `isCompleted = false` / When `dueDateLabel` is called / Then return `D-2`.

**Undefined behavior** (optional)
- None; all dates accepted by `LocalDate` are defined by calendar-day distance.
