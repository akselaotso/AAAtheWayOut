package theWayOut

import scala.collection.mutable.Map
import scala.io.StdIn.readLine

class Player(startingRoom: Room):
  private var continue = true
  private var hasExited = false
  private var myLocation = startingRoom
  private val myInventory = Map[String, Item]()
  private var myCode: Int = 0

  def setCode(code: Int) = myCode = code

  def location = myLocation

  def exited = hasExited

  def move(newRoom: Room) = myLocation = newRoom

  def continueGame = continue

  def exit() = continue = false

  def f[T](v: T) = v // type checker for distinguising Item and Stairs types in use() and take()

  def read(item: String) =
    if inventory.contains("Note") && item.toLowerCase == "note" then
      s"You read the note. It only contains 4 numbers: \n$myCode"
    else "You don't have anything to read."

  def help(): String =
    "List of commands:  " +
      "\n exit - end the game. " +
      "\n read - read a note. " +
      "\n toss - toss a select item from your inventory somewhere into your current room." +
      "\n use - use something in the room, for example a staircase or exit." +
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
        case _: Exit => "You obviously can't take that."
        case _: Item  =>
          myInventory.put(item, myLocation.contentMap(item)(0))
          myLocation.remove(item)
          "Got it!"
        case null => "Getting this message shouldn't be possible."
    else s"Good try."

  def inventory =
    "Your inventory: \n"+ myInventory.toVector.map((i, j) => j).foldLeft("")((i, j) => i + s" - ${j.toString.drop(4)} \n")

  def use(item: String) = // using items - stairs and exit
    var temp: Item = null
    if myLocation.contentMap.contains(item) then
      temp = myLocation.contentMap(item)(0)
    else if myInventory.contains(item) then
      temp = myInventory(item)
    f(temp) match // a separate case for everything.
      case _: Stairs =>
        if temp.sign == "U" then
          myLocation = myLocation.up
        else myLocation = myLocation.down
        s"You use the $item"
      case _: Exit =>
        var attempt = readLine("Enter passcode: ")
        if attempt == myCode.toString then
          this.hasExited = true
          "Correct code!"
        else "Wrong code."
      case _: Item =>
        if temp.sign == "B" then
          "The block is purely decorative."
        else if temp.sign == "P" then
          "A paper key rarely opens anything. This is no different."
        else "Can't use that item."
      case null => "Come on."

end Player
