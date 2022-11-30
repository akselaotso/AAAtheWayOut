package theWayOut

class Building(val x: Int, val y: Int):
  private val groundFloor = Room(x, y)
  private val secondFloor = Room(x, y)
  private val thirdFloor = Room(x, y)
  private val fourthFloor = Room(x, y)

  val player = Player(thirdFloor)

  groundFloor.setUp(secondFloor)
  secondFloor.setDown(groundFloor)
  secondFloor.setUp(thirdFloor)
  thirdFloor.setDown(secondFloor)
  thirdFloor.setUp(fourthFloor)
  fourthFloor.setDown(thirdFloor)

  private var code = scala.util.Random.between(1000, 9999)
  player.setCode(code)

  private var Block = Item("Block", "B", "a block")
  thirdFloor.add(Block) // the block is useless

  private var Door = Exit("Exit", "E", "The exit! Finally! Looks like it requires a 4 number combination...")
  groundFloor.add(Door)

  private var paper = Item("Note", "N", "A note... You should probably read it.")
  fourthFloor.add(paper) // the paper key is also useless

  private var fakeKey = Item("Paper Key", "P", "wait it's a key, but it's paper")
  secondFloor.add(fakeKey)

  def isWon: Boolean = player.exited

  def end = isWon || !player.continueGame


end Building
