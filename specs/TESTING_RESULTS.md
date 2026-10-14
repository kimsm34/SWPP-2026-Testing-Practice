# Spec-Based Testing Results

I generated the tests from the intent-verified specs and did not change production code. The failures identify the two due-date bugs described in the tutorial.

## Local results

- `./gradlew testDebugUnitTest`: 12 tests total; 11 passed and 1 failed. The month-boundary case expected `[D-36, D+36]` for 2026-10-15 and 2026-11-20, but received `[D-5, D+5]`. The day component of a date period was used instead of the total calendar-day difference.
- `./gradlew connectedDebugAndroidTest`: 7 tests total; 3 passed and 4 failed. For a task state with no label, tests expected `("", GONE)` but received stale `("CLEAR", VISIBLE)` or `("D-Day", VISIBLE)`. The binding does not clear the text or hide the view when no label applies.

## CI results

The [Android Tests workflow](https://github.com/kimsm34/SWPP-2026-Testing-Practice/actions/runs/38558078133) completed with both test jobs failing on those same assertions. Setup, emulator startup, and test-result reporting succeeded; the failures came from the assertions, not from a CI setup error.

## Bug check

- Wrong D-day count: detected by `dueDateLabel_monthBoundaryCountsTotalDaysInBothDirections`.
- Stale label after a task has no due date or becomes null: detected by the `setDueDateLabel` tests for active-without-date, null task, valid nullable states, and recycled-view transition.
- Both bugs listed in the tutorial were detected. No production-code fix was made, as instructed for this exercise.
