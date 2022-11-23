package theWayOut

import scala.io.StdIn.readLine

object textUI extends App:
  private val game = Building(16, 8)
  private val player = game.player
  runGame()

  def runGame() =
    println("Welcome to theWayOut. The point is to escape the building (more like a tower but hey).")
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
