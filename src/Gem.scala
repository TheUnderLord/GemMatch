import scalafx.scene.image.Image

class Gem(t:Int,inX:Int,inY:Int) {
  var id = t
  var descendLength = 80
  val descendSpeed = 4
  var markRemove = false
  var isSelected = false
  var _isDescending = false
  var finishedDescending = false
  var offsetX = 0
  var offsetY = 0

  var isSwapping = false
  var swapDirection = (0,0)

  var scale = 1.0
  def select(t:Boolean) = {
    isSelected = t
    if (t) scale = 1.1 else scale = 1
  }
  
  private val _x = inX
  private val _y = inY
  def x = _x
  def y = _y
  def isDescending = _isDescending
  def isDescending_(i:Boolean) = _isDescending = i
  def getId():Int = {
    id
  }

  def getImage = {
    id match {
      case 1 => Gem.img1
      case 2 => Gem.img2
      case 3 => Gem.img3
      case 4 => Gem.img4
      case 5 => Gem.img5
      case 6 => Gem.img6
      case _ => Gem.img0

    }
  }
  def removeGem = {
    markRemove = true

  }

  def updateGem = {
    if(offsetY<descendLength) {offsetY += descendSpeed} else {finishedDescending = true}

  }

}

object Gem {
  val gemTypes = 6
  val rand = new util.Random()
  def apply(t:Int,inX:Int,inY:Int):Gem = {
    t match{
      case -1 => new Gem(1+rand.nextInt(gemTypes),inX,inY)
      case 0 => new Gem(0,inX,inY)
      case a => new Gem(a,inX,inY)
    }

  }
  val img1 = new Image("file:Gem_Blue.png")
  val img2 = new Image("file:Gem_Orange.png")
  val img3 = new Image("file:Gem_Pale.png")
  val img4 = new Image("file:Gem_Purple.png")
  val img5 = new Image("file:Gem_Red.png")
  val img6 = new Image("file:Gem_Turquiose.png")
  val img0 = new Image("file:Gem_Clear.png")
}