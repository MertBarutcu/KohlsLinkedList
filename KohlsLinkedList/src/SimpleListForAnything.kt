package list

interface SimpleListForAnything<E> {
    fun addFirst(o : E)
    fun addLast(o : E)
    fun contains(o : E) : Boolean
    fun size() : Int
    fun getFirst() : E
    fun get(n : Int) : E
    fun removeObject (obj : E)
    fun gibAus()
    fun isEmpty() : Boolean
}