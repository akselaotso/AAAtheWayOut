package theWayOut

class Stairs(name: String, sign: String, description: String, originalDirection: String) extends Item(name, sign, description):
  private val direction = originalDirection 
  
  def isUp = direction.equals("up")
  
end Stairs
