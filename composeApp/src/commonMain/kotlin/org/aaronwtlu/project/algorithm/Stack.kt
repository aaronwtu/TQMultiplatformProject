
package org.aaronwtlu.project.algorithm


class Stack<T> {
    private var arrayList = mutableListOf<T>()

    fun push(value: T) {
        arrayList.add(value)
    }

    fun pop(): T? {
        return arrayList.removeLastOrNull()
    }
}

fun testStack() {
    val stack = Stack<Int>()
    stack.push(2)
    stack.push(1)
    stack.pop()
    stack.pop()
}