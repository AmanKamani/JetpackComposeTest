package jb.production.composetodo.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import jb.production.composetodo.ui.theme.ComposeTodoTheme

class TodoActivity : ComponentActivity() {

    private val viewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTodoTheme {
                Surface {
                    TodoActivityScreen(viewModel)
                }
            }
        }
    }

}

@Composable
private fun TodoActivityScreen(viewModel: TodoViewModel) {

    TodoScreen(
        items = viewModel.todoItems,
        currentlyEditing = viewModel.currentEditItem,
        onAddItem = viewModel::addItem,
        onRemoveItem = viewModel::removeItem,
        onStartEdit = viewModel::onEditItemSelected,
        onEditItemChange = viewModel::onEditItemChange,
        onEditDone = viewModel::onEditDone
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTodoTheme {
        Surface{

        }
    }
}