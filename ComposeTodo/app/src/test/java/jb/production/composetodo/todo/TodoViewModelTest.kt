package jb.production.composetodo.todo

import com.google.common.truth.Truth.assertThat
import jb.production.composetodo.util.generateRandomTodoItem
import org.junit.Test


class TodoViewModelTest {

    @Test
    fun whenRemovingItem_updateList() {
        // before
        val viewModel = TodoViewModel()
        val item1 = generateRandomTodoItem()
        val item2 = generateRandomTodoItem()
        viewModel.addItem(item1)
        viewModel.addItem(item2)

        // during
        viewModel.removeItem(item1)

        // after
        assertThat(viewModel.todoItems).isEqualTo(listOf(item2))
    }
}