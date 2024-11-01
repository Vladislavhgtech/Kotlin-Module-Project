import java.util.Scanner


fun main() {
    val scanner = Scanner(System.`in`)
    val menuManager = MenuManager(scanner)
    menuManager.start()
}