import java.util.Scanner
import kotlin.system.exitProcess

class MenuManager(val scanner: Scanner) {
    val archives = mutableListOf<String>()
    val notes = mutableListOf<MutableList<Note>>() // Список заметок для каждого архива

    fun start() {
        val mainMenu = MainMenu(this)
        while (true) {
            mainMenu.display()
            handleUserInput(mainMenu::handleUserChoice)
        }
    }

    private fun handleUserInput(action: (Int) -> Unit) {
        val choice = scanner.nextLine().toIntOrNull()
        if (choice != null) {
            action(choice)
        } else {
            println("Некорректный ввод. Попробуйте снова.")
        }
    }

    fun createArchive() {
        println("Введите имя архива:")
        val archiveName = scanner.nextLine().trim()
        if (archiveName.isEmpty()) {
            println("Имя архива не может быть пустым.")
            waitForUser()
            return
        }
        archives.add(archiveName)
        notes.add(mutableListOf())
        println("Архив '$archiveName' создан.")
        waitForUser()
    }

    fun selectArchive() {
        if (archives.isEmpty()) {
            println("Нет созданных архивов.")
            waitForUser()
            return
        }

        println("Выберите архив:")
        archives.forEachIndexed { index, archive -> println("$index. $archive") }
        val choice = scanner.nextLine().toIntOrNull()

        if (choice in archives.indices) {
            val noteMenu = NoteMenu(this, choice)
            noteMenu.start()
        } else {
            println("Некорректный выбор.")
            waitForUser()
        }
    }

    fun exitProgram() {
        println("Выход из программы.")
        exitProcess(0)
    }

    fun waitForUser() {
        println("Нажмите любую клавишу для продолжения.")
        scanner.nextLine()
    }
}

class MainMenu(private val menuManager: MenuManager) {
    private val options = listOf("Создать архив", "Выбрать созданный архив", "Выход")

    fun display() {
        println("Главное меню:")
        options.forEachIndexed { index, option -> println("$index. $option") }
    }

    fun handleUserChoice(choice: Int) {
        when (choice) {
            0 -> menuManager.createArchive()
            1 -> menuManager.selectArchive()
            2 -> menuManager.exitProgram()
            else -> println("Некорректный выбор. Попробуйте снова.")
        }
    }
}