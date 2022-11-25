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


  private var Block = Item("Block", "B", "a block")
  thirdFloor.add(Block) // the block is useless but the player can test out the take function

  private var Door = Exit("Exit", "E", "The exit! Finally! Maybe I could use a key here...")
  groundFloor.add(Door)
  
  private var Key = Item("Key", "K", "the key to your freedom")
  fourthFloor.add(Key)

  private var fakeKey = Item("Paper Key", "P", "wait what key is this it looks like the key but it's paper")
  secondFloor.add(fakeKey)

  def isWon: Boolean = player.exited

  def end = isWon || !player.continueGame


end Building
