class Expense (var sum: Int, var category: String, var date: String) { // создание класса и объявление первичного конструктора
    // Метод для вывода информации о конкретном расходе

    constructor(sum: Int, category: String, date: String,text:String) : this(sum,category,date){

    }
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