class AllExpense { // создание класса который хранит список расходов

    private var expenses = mutableListOf<Expense>() //MutableList: Позволяет добавлять, удалять и
    // изменять элементы позволяет динамически изменять содержимое списка.

    fun addExpense(i: Expense) {
        expenses.add(i)
    }

    fun showAllExpenses() {
        for( i in expenses) {
            i.showExpense()
        }

    }

    fun theSumOfExpensesByCategory(): Map<String, Int> {
        val sumByCategory = mutableMapOf<String, Int>()

        for (i in expenses) {
            val category: String = i.getCategories()
            val sum: Int = i.getSumm()

                /*
            if (sumByCategory[category] == null) {
                sumByCategory[category] = 0
            }
            */
            // Суммируем расходы по категориям
            sumByCategory[category] = sumByCategory.getOrDefault(category, 0) + sum
        }

        return sumByCategory
    }
}