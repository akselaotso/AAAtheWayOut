package theWayOut

import scala.collection.mutable.Map

class Player(startingRoom: Room):
  private var continue = true
  private var myLocation = startingRoom
  private val myInventory = Map[String, Item]()

  def location = myLocation

  def move(newRoom: Room) = myLocation = newRoom

  def continueGame = continue

  def exit() = continue = false


  def f[T](v: T) = v // type checker for distinguising Item and Stairs types in use() and take()

  def help(): String =
    "List of commands:  " +
      "\n exit - end the game. " +
      "\n toss - toss a select item from your inventory somewhere into your current room." +
      "\n use - use something in the room, for example a staircase." +
      "\n fight - fight pretty much anything. Be warned though, you aren't very good at fighting." +
      "\n take - pick up an item from the room you're currently in. " +
      "\n inventory - displays a list of your current inventory's contents."

  def fight(item: String) =
    continue = false
    "You aren't great at fighting."

  def toss(item: String): String =
    myLocation.add(myInventory(item))
    myInventory.remove(item)
    "Nice throw."

  def take(item: String): String =
    if myLocation.contentMap.contains(item) then
      var temp = myLocation.contentMap(item)(0)
      f(temp) match
        case _: Stairs => "You obviously can't take that."
        case _: Item  =>
          myInventory.put(item, myLocation.contentMap(item)(0))
          myLocation.remove(item)
          "Got it!"
        case null => "No. Getting this message shouldn't be possible."
    else s"Good try."

  def inventory =
    "Your inventory: \n "+ myInventory.toVector.map((i, j) => j).foldLeft("")((i, j) => i + s" ${j.toString.drop(4)} \n")

  def use(item: String) = // using items - only stairs but still
    if myLocation.contentMap.contains(item) then
      var temp = myLocation.contentMap(item)(0)
      f(temp) match
        case _: Stairs =>
          if temp.sign == "U" then
            myLocation = myLocation.up
          else myLocation = myLocation.down
          s"You use the $item"
        case _ => s"Come on. Can't use that."
    else s"Dude you clearly can't use that."

end Player
