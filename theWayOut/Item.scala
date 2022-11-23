package theWayOut

class Item(val name: String, val sign: String, val description: String): 
  override def toString = sign + " - " + name + ", " + description // items are stupid simple