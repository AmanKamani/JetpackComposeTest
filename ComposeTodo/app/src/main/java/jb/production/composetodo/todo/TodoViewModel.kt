package jb.production.composetodo.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    var todoItems = mutableStateListOf<TodoItem>()
        private set

    // private state
    private var currentEditPosition by mutableStateOf(-1)

    val currentEditItem: TodoItem?
        get() = todoItems.getOrNull(currentEditPosition)

    fun addItem(item: TodoItem) {
        todoItems.add(item)
    }

    fun removeItem(item: TodoItem) {
        todoItems.remove(item)
        onEditDone()
    }

    fun onEditItemSelected(item: TodoItem){
        currentEditPosition = todoItems.indexOf(item)
    }

    fun onEditDone(){
        currentEditPosition = -1
    }

    fun onEditItemChange(item: TodoItem){
        val currentItem = requireNotNull(currentEditItem)
        require(currentItem.id == item.id){
            "You can only change an item with the same id as currentEditItem"
        }
        todoItems[currentEditPosition] = item
    }

}