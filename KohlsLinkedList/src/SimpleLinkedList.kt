package list
class SimpleLinkedList <E> : SimpleListForAnything<E>{

    private class Node <E> (val data : E, var next : Node<E>?)
    private var first : Node<E>? = null
    var isSorted = true

    override fun isEmpty() = first==null

    override fun addFirst(o: E) {
        first = Node(o, first)
        isSorted = false
    }

    override fun addLast(o: E) {
        if (isEmpty()) addFirst(o) //29;15 Erste Schritte
        else{
            var runPointer = first
            while (runPointer?.next != null){
                runPointer=runPointer.next
            }
            runPointer?.next = Node(o, null)
        }
        isSorted=false
    }

    override fun get(n: Int): E {
        if (n < 0) throw IndexOutOfBoundsException()
        var run = first
        var counter = 0
        while (run != null && counter < n){
            run = run.next
            counter++
        }
        return run?.data ?: throw IndexOutOfBoundsException()
    }

    override fun removeObject(obj: E) {
        var run = first

        if (run?.data == obj){
            first=first?.next
        }else{
            //Listen durchlaufen 21:07 Gute erklärung für diesen algo
            while (run?.next?.data != obj){
                run = run?.next
            }
            run?.next = run?.next?.next
        }
    }
}
