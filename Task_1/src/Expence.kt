class Expense constructor (val sum: Int, val category: String, val date: String) { // создание класса и объявление первичного конструктора


    // Метод для вывода информации о конкретном расходе
    fun showExpense() {
        println("Расход: $sum | Категория: $category | Дата: $date")
    }
// гетеры для полей класса
    fun getSumm(): Int {
        return sum
    }

    fun getCategories(): String {
        return category
    }

    fun getData(): String {
        return date
    }
}