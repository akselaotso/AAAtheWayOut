package theWayOut

class Item(val name: String, val sign: String, val description: String):
  override def toString = sign + " - " + name + ", " + description

end Item

// items are simple, the toString is used a bunch though