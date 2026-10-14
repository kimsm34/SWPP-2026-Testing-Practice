package com.example.swpp.tododapp.tasks

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class DueDateUtilsTest {

    @Test
    fun dueDateLabel_completedWithOrWithoutDate_returnsClear() {
        // GIVEN - A completed task, once without a due date and once with one
        val today = LocalDate.of(2026, 10, 14)

        // WHEN - Calculate each label
        val withoutDate = dueDateLabel(null, today, isCompleted = true)
        val withDate = dueDateLabel(LocalDate.of(2026, 10, 16), today, isCompleted = true)

        // THEN - Completion takes precedence over the due date
        assertEquals("CLEAR", withoutDate)
        assertEquals("CLEAR", withDate)
    }

    @Test
    fun dueDateLabel_activeWithoutDueDate_returnsEmptyString() {
        // GIVEN - An active task without a due date
        val today = LocalDate.of(2026, 10, 14)

        // WHEN - Calculate its label
        val label = dueDateLabel(null, today, isCompleted = false)

        // THEN - No label is shown
        assertEquals("", label)
    }

    @Test
    fun dueDateLabel_dueInTwoDays_returnsDMinusTwo() {
        // GIVEN - An active task due two calendar days after today
        val today = LocalDate.of(2026, 10, 14)
        val dueDate = LocalDate.of(2026, 10, 16)

        // WHEN - Calculate its label
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // THEN - Show the number of days remaining
        assertEquals("D-2", label)
    }

    @Test
    fun dueDateLabel_overdueByTwoDays_returnsDPlusTwo() {
        // GIVEN - An active task whose due date was two calendar days ago
        val today = LocalDate.of(2026, 10, 14)
        val dueDate = LocalDate.of(2026, 10, 12)

        // WHEN - Calculate its label
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // THEN - Show how many days overdue it is
        assertEquals("D+2", label)
    }

    @Test
    fun dueDateLabel_validNullableInput_returnsLabelWithoutThrowing() {
        // GIVEN - A valid nullable due date and non-null reference date
        val today = LocalDate.of(2026, 10, 14)

        // WHEN - Calculate the label
        val label = dueDateLabel(null, today, isCompleted = false)

        // THEN - Return the valid empty label without throwing
        assertNotNull(label)
        assertEquals("", label)
    }

    @Test
    fun dueDateLabel_todayAndAdjacentDays_returnsExpectedLabels() {
        // GIVEN - Due dates on today, one day ahead, and one day behind
        val today = LocalDate.of(2026, 10, 14)

        // WHEN - Calculate each label
        val todayLabel = dueDateLabel(today, today, isCompleted = false)
        val tomorrowLabel = dueDateLabel(today.plusDays(1), today, isCompleted = false)
        val yesterdayLabel = dueDateLabel(today.minusDays(1), today, isCompleted = false)

        // THEN - Distinguish the zero boundary and both adjacent days
        assertEquals("D-Day", todayLabel)
        assertEquals("D-1", tomorrowLabel)
        assertEquals("D+1", yesterdayLabel)
    }

    @Test
    fun dueDateLabel_monthBoundaryCountsTotalDaysInBothDirections() {
        // GIVEN - Dates 36 calendar days apart across a month boundary
        val october = LocalDate.of(2026, 10, 15)
        val november = LocalDate.of(2026, 11, 20)

        // WHEN - Calculate labels in both directions
        val futureLabel = dueDateLabel(november, october, isCompleted = false)
        val overdueLabel = dueDateLabel(october, november, isCompleted = false)

        // THEN - Count all elapsed calendar days, not only the period's day component
        assertEquals(listOf("D-36", "D+36"), listOf(futureLabel, overdueLabel))
    }

    @Test
    fun dueDateLabel_yearBoundaryCountsTotalDays_returnsDMinusTwo() {
        // GIVEN - A due date two days after today across a year boundary
        val today = LocalDate.of(2026, 12, 31)
        val dueDate = LocalDate.of(2027, 1, 2)

        // WHEN - Calculate the label
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // THEN - Count both calendar days
        assertEquals("D-2", label)
    }

    @Test
    fun dueDateLabel_leapDayCountsTowardTotalDays_returnsDMinusTwo() {
        // GIVEN - A date range that includes February 29 in a leap year
        val today = LocalDate.of(2024, 2, 28)
        val dueDate = LocalDate.of(2024, 3, 1)

        // WHEN - Calculate the label
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // THEN - Include February 29 in the calendar-day count
        assertEquals("D-2", label)
    }
}
