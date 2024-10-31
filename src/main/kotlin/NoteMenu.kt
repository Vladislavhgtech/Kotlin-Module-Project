class NoteMenu(private val menuManager: MenuManager, private val archiveIndex: Int?) {
    private val options = listOf("Создать заметку", "Список заметок", "Вернуться в главное меню")
    private var exitMenu = false

    fun start() {
        while (!exitMenu) {
            display()
            handleUserChoice(menuManager.scanner.nextLine().toIntOrNull())
        }
    }

    private fun display() {
        println("Меню заметок для архива '${menuManager.archives[archiveIndex!!]}':")
        options.forEachIndexed { index, option -> println("$index. $option") }
    }

    private fun handleUserChoice(choice: Int?) {
        when (choice) {
            0 -> createNote()
            1 -> listNotes()
            2 -> exitMenu = true
            else -> println("Некорректный выбор. Попробуйте снова.")
        }
    }

    private fun createNote() {
        println("Введите содержимое заметки:")
        val noteContent = menuManager.scanner.nextLine().trim()
        if (noteContent.isEmpty()) {
            println("Содержание заметки не может быть пустым.")
            menuManager.waitForUser()
            return
        }
        menuManager.notes[archiveIndex!!].add(Note(noteContent))
        println("Заметка создана.")
        menuManager.waitForUser()
    }

    private fun listNotes() {
        val noteList = menuManager.notes[archiveIndex!!]
        if (noteList.isEmpty()) {
            println("Нет созданных заметок.")
            menuManager.waitForUser()
            return
        }

        println("Список заметок для архива '${menuManager.archives[archiveIndex!!]}':")
        noteList.forEachIndexed { index, note -> println("$index. ${note.content}") }
        println("Введите номер заметки для просмотра или нажмите любую другую клавишу для возврата:")

        val choice = menuManager.scanner.nextLine().trim()
        if (choice.toIntOrNull() in noteList.indices) {
            viewNote(choice.toInt())
        } else {
            println("Неверный ввод. Возвращение в меню.")
            menuManager.waitForUser()
        }
    }

    private fun viewNote(noteIndex: Int) {
        val note = menuManager.notes[archiveIndex!!][noteIndex]
        println("Просмотр заметки:\n${note.content}")
        menuManager.waitForUser()
    }
}