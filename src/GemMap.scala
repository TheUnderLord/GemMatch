

class GemMap(width: Int, height: Int, pSize: Int) {
  val descendSpeed = 5
  var descendingGems = 0
  var mapGrid = Array.tabulate(width, height)((x, y) => Gem(-1, x, y))
  var finishedSwap = true

  def apply(x: Int, y: Int): Gem = {
    mapGrid(x)(y)
  }

  def generateMap(): Unit = {
    mapGrid = mapGrid.map { array => array.map { j => Gem(-1, j.x, j.y) } }
    mapGrid.foreach { x => println((x.map { i => i.getId }).mkString) }
  }

  def NearbyHelper(x: Int, y: Int, dx: Int, dy: Int, t: Int): List[Gem] = {
    if (checkIndex(x + dx, y + dy)) {
      if (mapGrid(x + dx)(y + dy).getId() == t&& !mapGrid(x + dx)(y + dy).isDescending) mapGrid(x + dx)(y + dy) :: NearbyHelper(x + dx, y + dy, dx, dy, t) else Nil
    } else {
      Nil
    }
    //NearbyHelper
  }

  def checkForNearby(x: Int, y: Int):Boolean = {
    val xList = mapGrid(x)(y) :: NearbyHelper(x, y, 1, 0, mapGrid(x)(y).getId()) ::: NearbyHelper(x, y, -1, 0, mapGrid(x)(y).getId())
    val yList = mapGrid(x)(y) :: NearbyHelper(x, y, 0, 1, mapGrid(x)(y).getId()) ::: NearbyHelper(x, y, 0, -1, mapGrid(x)(y).getId())
    if (xList.length >= 3) {

      xList.foreach(i => {

        mapGrid(i.x)(i.y).removeGem

      })
      true
    } else if (yList.length >= 3) {

      yList.foreach(i => {

        mapGrid(i.x)(i.y).removeGem


      })
      true

    } else false

  }

  def swap(x1:Int,y1:Int,x2:Int,y2:Int) = {

    mapGrid(x1)(y1).isSwapping = true
    mapGrid(x2)(y2).isSwapping = true
    mapGrid(x2)(y2).swapDirection = (x1-x2,y1-y2)
    mapGrid(x1)(y1).swapDirection = (x2-x1,y2-y1)
    println(x1+" "+y1)
    println(x2+" "+y2)

  }

  def updateSwap(ix:Int,iy:Int) = {
    val g = mapGrid(ix)(iy)
    val (x,y) = g.swapDirection
    mapGrid(ix)(iy).offsetX += x*g.descendSpeed
    mapGrid(ix)(iy).offsetY += y*g.descendSpeed
    if(g.offsetX > g.descendLength || g.offsetY > g.descendLength) {
      val back = mapGrid(ix + x)(iy + y)
      mapGrid(ix + x)(iy + y) = Gem(g.getId(), g.x+x, g.y+y)
      mapGrid(ix)(iy) = Gem(back.getId(), back.x-x, back.y-y)
      if ((checkForNearby(ix,iy)||checkForNearby(ix+x,iy+y)) || finishedSwap){} else {
        swap(ix,iy,ix+x,iy+y)
        finishedSwap = true
      }

    }
  }

  def updateGrid = {

    descendingGems = 0
    for (x <- mapGrid(0).indices; y <- mapGrid(x).indices) {
      val cGem = mapGrid(x)(y)
      if (cGem.isSwapping) updateSwap(x,y)
      if (y == 0) {
        if (cGem.getId() == 0) mapGrid(x)(y) = Gem(-1, x, y)
      }
      if (cGem.markRemove) mapGrid(x)(y) = Gem(0, x, y) else {}

      if (mapGrid(x)(y).isDescending) {
        descendingGems += 1
        if (checkIndex(x, y - 1) && !mapGrid(x)(y - 1).isDescending) {
          moveDown(x, y - 1)
        } else if (!checkIndex(x, y - 1)) {

        }
        if (cGem.finishedDescending) {
          mapGrid(x)(y).removeGem

          mapGrid(x)(y + 1) = Gem(cGem.getId(), cGem.x, cGem.y + 1)

          if (checkIndex(x, y + 2) && !mapGrid(x)(y + 2).isDescending && mapGrid(x)(y + 2).getId() != 0) {
            checkForNearby(x, y + 1)

          } else if (!checkIndex(x, y + 2)) {
            checkForNearby(x, y + 1)
          }
        } else {
          mapGrid(x)(y).updateGem
          //println("update")
        }
      }

      if (checkIndex(x, y + 1) && mapGrid(x)(y + 1).getId() == 0 && (!(cGem.isDescending) || mapGrid(x)(y + 1).isDescending)) {
        moveDown(x, y)
      } else {}
      //println(descendingGems)
    }

    def moveDown(x: Int, y: Int): Unit = {
      //for (i <- 0 to y by 1) {
      if (!mapGrid(x)(y).isDescending) {
        mapGrid(x)(y).isDescending_(true)

        //println("Gem at:"+x+y)
      }
    }

  }

  def checkIndex(x: Int, y: Int): Boolean = {
    if (x >= 0 && x < mapGrid.length && y >= 0 && y < mapGrid(x).length) true else false
  }

  def getArray(): Array[Array[Gem]] = {
    mapGrid.clone
  }

}