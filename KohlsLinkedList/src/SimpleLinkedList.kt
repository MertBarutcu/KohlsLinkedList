package list


import java.lang.Exception


class SimpleLinkedList <E> : SimpleListForAnything<E>, Iterable<E> {

    private class Node <E> (val data : E, var next : Node<E>?)

    private var first : Node<E>? = null

    var isSorted = true

    // diese klasse wird an die äussere Klasse gebnunden durch "inner" iteratoren zum durchlaufen einer liste 9:18
    //inner class ElementIterator : Iterator<E>{
//Innere Klassen und anonyme Objekte
    //immer wenn interatir aufgerufen. objekt wir erzeugt das dass iteratir interface implemnetiert. 5:03
    override fun iterator(): Iterator<E> = object : Iterator<E> {
        private var run = first
        override fun hasNext(): Boolean = run != null
        override fun next(): E {
            val res = run?.data ?: throw NoSuchElementException()
            run = run?.next
            return res
        }
    }


    override fun isEmpty() = first==null

    override fun addFirst(o: E) {
        //val newNode = Node (o, first)
        first = Node(o, first)     //so geht auch first wir erst nach auswertung init
        //first = newNode
        isSorted = false
    }

    override fun addLast(o: E) {
        if (isEmpty()) addFirst(o) //29;15 Erste Schritte
        else{
            var runPointer = first
            //runPointer könnte null sein deswegen ?
            while (runPointer?.next != null){
                // Wieso hier kein   | Elvis weil wir davor schon sichergestellt haben nicht null ist
                runPointer=runPointer.next //zeigt auf die  letzte Stelle also das NULL
            }
            runPointer?.next = Node(o, null)
        }
        isSorted=false
    }

    override fun contains(o: E): Boolean {
        var run = first
        while (run!=null){
            if (run?.data==o)return true
            run = run.next
        }
        return false
    }

    override fun size(): Int {
        var run = first
        var counter = 0
        while (run!=null){
            counter++
            run = run.next
        }
        return counter
    }

    override fun getFirst(): E = get(0) //ODER first?.data ?: throw IndexOutOfBoundsException()


    override fun get(n: Int): E {
        if (n < 0) throw IndexOutOfBoundsException()
        var run = first
        var counter = 0
        while (run != null && counter < n){
            run = run.next
            counter++
        }
        //es kann null retund werden obwohl ein Any return werden muss. wenn dies antrifft Execption "Listen durchlaufen 11:42"
        //         wenn null dann throw
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

    override fun gibAus() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    class UnsosrtedListExecption : Exception()

    //sortiert einfügen
    fun addSorted (obj : E, comp: Comparator <E> ){
        if (!isSorted) throw UnsosrtedListExecption()
        if (isEmpty() || comp.compare(obj, getFirst() ) <=0 ){
            addFirst(obj)
            isSorted = true
        }else{
            var run = first
            // liefert wert grösser kleiner oder gleich 0
            while (run?.next != null && comp.compare( obj, run?.next?.data ) > 0 ){
                run = run?.next
            }
            run?.next = Node(obj, run?.next)
        }
    }


    private fun removeAll(){
        first = null
        isSorted = true
    }

    fun sort(comp: Comparator<E>){

        var temp = first
        removeAll()

        while (temp != null){
            addSorted(temp.data, comp)
            temp = temp.next
        }
    }


    fun quickSort(comp: Comparator<E>){
        //AbbruchBedingung
        if (size() < 2) {
            for (e in this) println("Sortierte Liste? " +e)
            return
        }
        //Refernezgröße festlegen:
        val pivot = getFirst()

        val less = SimpleLinkedList<E>()
        val equal = SimpleLinkedList<E>()
        val greater = SimpleLinkedList<E>()
        for (e in this ) {
            val compared = comp.compare(e, pivot)
            when {
                compared < 0 -> less.addFirst(e)
                compared == 0 -> equal.addFirst(e)
                compared > 0 -> greater.addFirst(e)
            }
        }
        less.quickSort(comp)
        //equal.quickSort(comp)
        greater.quickSort(comp)
        removeAll()
        //Hier gibt es verbesserungs Potential listen direkt hinter einander hängen und nicht alle nach einander
        //zsm führen.
        for (e in less) addLast(e)
        for (e in equal) addLast(e)
        for (e in greater) addLast(e)

        isSorted = true
    }
}
