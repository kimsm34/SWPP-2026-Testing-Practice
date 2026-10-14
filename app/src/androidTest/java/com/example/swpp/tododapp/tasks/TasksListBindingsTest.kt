package com.example.swpp.tododapp.tasks

import android.view.View
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.swpp.tododapp.data.Task
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class TasksListBindingsTest {

    @Test
    fun setDueDateLabel_completedWithOrWithoutDate_showsClear() {
        // GIVEN - A TextView and completed tasks with absent and present due dates
        onMain {
            val textView = TextView(targetContext())

            // WHEN - Bind each completed task to the same view
            setDueDateLabel(textView, Task(isCompleted = true, dueDate = null))

            // THEN - Show CLEAR for a completed task without a due date
            assertEquals("CLEAR", textView.text.toString())
            assertEquals(View.VISIBLE, textView.visibility)

            // WHEN - Bind a completed task with a due date
            setDueDateLabel(
                textView,
                Task(isCompleted = true, dueDate = LocalDate.of(2026, 10, 16))
            )

            // THEN - Completion still takes precedence
            assertEquals("CLEAR", textView.text.toString())
            assertEquals(View.VISIBLE, textView.visibility)
        }
    }

    @Test
    fun setDueDateLabel_activeDueToday_showsDday() {
        // GIVEN - An active task due on the current calendar date
        onMain {
            val textView = TextView(targetContext())
            val task = Task(isCompleted = false, dueDate = LocalDate.now())

            // WHEN - Bind the task
            setDueDateLabel(textView, task)

            // THEN - Show D-Day
            assertEquals("D-Day", textView.text.toString())
            assertEquals(View.VISIBLE, textView.visibility)
        }
    }

    @Test
    fun setDueDateLabel_activeWithoutDueDate_clearsAndHidesView() {
        // GIVEN - A reused TextView that still shows a completed-task label
        onMain {
            val textView = TextView(targetContext()).apply {
                text = "CLEAR"
                visibility = View.VISIBLE
            }
            val task = Task(isCompleted = false, dueDate = null)

            // WHEN - Bind an active task without a due date
            setDueDateLabel(textView, task)

            // THEN - Remove the stale label and hide the view
            assertEquals("" to View.GONE, textView.text.toString() to textView.visibility)
        }
    }

    @Test
    fun setDueDateLabel_nullTask_clearsAndHidesView() {
        // GIVEN - A reused TextView that still shows D-Day
        onMain {
            val textView = TextView(targetContext()).apply {
                text = "D-Day"
                visibility = View.VISIBLE
            }

            // WHEN - Bind a null task
            setDueDateLabel(textView, null)

            // THEN - Remove the stale label and hide the view
            assertEquals("" to View.GONE, textView.text.toString() to textView.visibility)
        }
    }

    @Test
    fun setDueDateLabel_validTextViewAndNullableTask_doesNotThrow() {
        // GIVEN - A valid TextView and valid nullable task states
        onMain {
            val textView = TextView(targetContext())

            // WHEN - Bind a completed task and then a null task
            setDueDateLabel(textView, Task(isCompleted = true))
            setDueDateLabel(textView, null)

            // THEN - Both calls complete; assertions below also check the final view state
            assertEquals("" to View.GONE, textView.text.toString() to textView.visibility)
        }
    }

    @Test
    fun setDueDateLabel_recycledViewSwitchesFromCompletedToActive_clearsClear() {
        // GIVEN - The same view is reused for a completed task and an active task, both without dates
        onMain {
            val textView = TextView(targetContext())

            // WHEN - Bind the completed task and then the active task
            setDueDateLabel(textView, Task(isCompleted = true, dueDate = null))
            setDueDateLabel(textView, Task(isCompleted = false, dueDate = null))

            // THEN - The active task has no label and the old CLEAR is removed
            assertEquals("" to View.GONE, textView.text.toString() to textView.visibility)
        }
    }

    private fun onMain(block: () -> Unit) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(block)
    }

    private fun targetContext() = InstrumentationRegistry.getInstrumentation().targetContext
}
