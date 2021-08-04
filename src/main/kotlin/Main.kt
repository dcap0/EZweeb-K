import controller.Logic
import view.root

fun main(){
    val logic = Logic()
    logic.updateList()
    root(logic)
}