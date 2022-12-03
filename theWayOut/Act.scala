package theWayOut

class Act(command: String):
  // just prosesses the commands similarly to Adventure,
  // except accepts commands with targets the names of which contain spaces

  private val commandText = command.trim.toLowerCase
  private val act = commandText.takeWhile( _ != ' ' )
  private val target = commandText.drop(act.length + 1)

  def execute(player: Player) = act match
    case "exit"      => Some(player.exit())
    case "toss"      => Some(player.toss(target))
    case "use"       => Some(player.use(target))
    case "fight"     => Some(player.fight(target))
    case "take"      => Some(player.take(target))
    case "read"      => Some(player.read(target))
    case "inventory" => Some(player.inventory)
    case "help"      => Some(player.help())
    case other       => None

end Act
