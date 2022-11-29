package theWayOut

import scala.math.random
import scala.collection.mutable.Map

class Room(x: Int, y: Int):
  private val height: Int = y
  private val width: Int = x

  // ("item name", (item, location))
  // locations all throughout the code represented simply as (x: Int, y: Int) tuples
  private val contents: Map[String, (Item, (Int, Int))] = Map()

  // setting upper and lower room
  private var upperFloor: Room = null
  private var lowerFloor: Room = null

  // setting up stairs for the room
  private val StairsUp = Stairs("Stairs up", "U", "You can use these stairs to go up with 'use stairs up'.", "up")
  private val StairsDown = Stairs("Stairs down", "D", "You can use these stairs to go down with 'use stairs down'.", "down")
 
  def locations: Vector[(Int, Int)] = contents.toVector.map(i => i(1)(1))

  def items: Vector[Item] = contents.toVector.map(i => i(1)(0))

  def contentMap = contents

  def up = upperFloor

  def down = lowerFloor

  def setUp(item: Room) =
    upperFloor = item
    contents.put(StairsUp.name.toLowerCase, (StairsUp, (3, 4)))

  def setDown(item: Room) =
    lowerFloor = item
    contents.put(StairsDown.name.toLowerCase, (StairsDown, (3, 5)))

  def add(item: Item) = // adds the item to an empty location
    var rand = scala.util.Random
    var newLoc = (rand.between(2, width - 1), rand.between(2, height - 1))
    if contents.nonEmpty then
      while locations.contains(newLoc) do
        newLoc = (rand.between(2, width - 1), rand.between(2, height - 1))
    contents.put(item.name.toLowerCase, (item, newLoc)) // each item's new location is ramdomized

  def remove(item: String) = contents.remove(item)

  def drawRoom() =
    for row <- 1 to height do // for each square check if there should be something and print it row at a time
      var print = ""
      if row == 1 || row == height then // no use running the column check if it's just gonna be a wall
        print += "#" * width
      else
        for col <- 1 to width do
          if col == 1 || col == width then // if there should be a wall print a wall
            print += "#"
          else if locations.contains((col, row)) then // else if there is an item on the square print the item's icon
            print += items(locations.indexOf((col, row))).sign
          else print += " " // else print empty space
      println(print)
    if items.nonEmpty then // print text explanations for all the items in the room
      println("You see: ")
      println(items.foldLeft("")((i, j) => i + s" ${j.toString} \n"))
    else println("Looks like the room is empty.")