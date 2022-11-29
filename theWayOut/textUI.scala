package theWayOut

import scala.io.StdIn.readLine

object textUI extends App:
  private val game = Building(16, 8)
  private val player = game.player
  runGame()

  def runGame() =
    println("Welcome to theWayOut. Your goal is to escape the  tower. Type 'help' for help. Try not to fight too much")
    println("The exit is blocked by a combination lock, so you'll need to find a note with the combination.")
    println("You're currently on the third floor.")
    while !game.end do
      player.location.drawRoom()
      turn()
    println("Game Over.")
    if game.isWon then
      println("Congratulations. You won.")
    else println("You lost, sorry. Do try again, though!")

  def turn() =
    val command: String = readLine("Command: ")
    val action = Act(command)
    println(action.execute(player).getOrElse("Unknown command."))

end textUI
