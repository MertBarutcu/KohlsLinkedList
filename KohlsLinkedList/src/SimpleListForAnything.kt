package list

interface SimpleListForAnything<E> {
    fun addFirst(o : E)
    fun addLast(o : E)
    fun get(n : Int) : E
    fun removeObject (obj : E)
    fun isEmpty() : Boolean
}