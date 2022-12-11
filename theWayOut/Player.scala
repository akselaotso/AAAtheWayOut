package theWayOut

import scala.collection.mutable.Map
import scala.io.StdIn.readLine

class Player(startingRoom: Room):
  private var continue = true
  private var hasExited = false
  private var myLocation = startingRoom
  private val myInventory = Map[String, Item]()
  private var myCode: Int = 0

  def setCode(code: Int) = myCode = code // used to set up the game, external so it can be crossreferenced in other files.
  def location = myLocation
  def exited = hasExited
  def move(newRoom: Room) = myLocation = newRoom
  def continueGame = continue

  def exit() =
    continue = false
    "Exiting game."

  // reads the note. There are no other readable items, hence the specifict conditions.
  def read(item: String) =
    if inventory.contains("Note") && item.toLowerCase == "note" then
      s"You read the note. It only contains 4 numbers: \n$myCode"
    else "Couldn't read that. Is the item in your inventory?"

  // just prints out all the commands and their explanations
  def help(): String =
    "List of commands:  " +
      "\n exit - end the game. " +
      "\n read - read a note from your inventory. " +
      "\n toss - toss an item from your inventory somewhere in your current room." +
      "\n use - use something in the room, for example a staircase or exit." +
      "\n fight - fight pretty much anything. Be warned though, you aren't very good at fighting." +
      "\n take - pick up an item from the room you're currently in. " +
      "\n inventory - displays a list of your current inventory's contents."

  // fight anything, always losing
  def fight(item: String) =
    continue = false
    "You aren't great at fighting."

  // place the item in question to a random location in the current room
  def toss(item: String): String =
    if myInventory.contains(item) then
      myLocation.add(myInventory(item))
      myInventory.remove(item)
      "Nice throw."
    else "You can't toss that"

  // take an item if the item is in the room and of a takeable type
  def take(item: String): String =
    if myLocation.contentMap.contains(item) then
      var temp = myLocation.contentMap(item)(0)
      // first discard stairs and the exit as takeable items and then handle the rest of the cases. Using a match based on type
      temp match
        case _: Dude => "What are your trying to do."
        case _: Stairs => "You obviously can't take that."
        case _: Exit => "You obviously can't take that."
        case _: Item  =>
          myInventory.put(item, myLocation.contentMap(item)(0))
          myLocation.remove(item)
          "Got it!"
        case null => "Can't take that."
    else s"Good try."

  def inventory = // print the inventory in a legible manner
    "Your inventory: \n"+ myInventory.toVector.map((i, j) => j).foldLeft("")((i, j) => i + s" - ${j.toString.drop(4)} \n")

  def use(item: String) = // using items - stairs and exit
    var temp: Item = null
    if myLocation.contentMap.contains(item) then
      temp = myLocation.contentMap(item)(0)
    else if myInventory.contains(item) then
      temp = myInventory(item)
    // a separate case for everything, matching but based on the item's type. Easier to expand the system later.
    temp match
      case _: Dude => "What are your trying to do."
      case _: Stairs => // when a player uses stairs check the direction and move the player
        if temp.sign == "U" then
          myLocation = myLocation.up
        else myLocation = myLocation.down
        s"You use the $item"
      case _: Exit => // using an exit: ask for the code and end game if code is right
        if readLine("Enter passcode: ") == myCode.toString then
          this.hasExited = true
          "Correct code!"
        else "Wrong code."
      case _: Item => // just for fun a couple messages for using different stuff
        if temp.sign == "B" then
          "The block is purely decorative."
        else if temp.sign == "P" then
          "A paper key rarely opens anything. This is no different."
        else "Can't use that item."
      case null => "Come on."

end Player
