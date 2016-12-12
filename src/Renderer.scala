import scalafx.scene.canvas.GraphicsContext
import scalafx.Includes._
import scalafx.scene.paint.Color


class Renderer(BoxSize:Int,gc:GraphicsContext) {
  def render(a:Array[Array[Gem]]) = {
    gc.fill = Color.White
    gc.fillRect(0,0,10000,10000)
    a.foreach{ m =>
      m.foreach{ j =>
        val offset = (BoxSize - BoxSize*j.scale)/2
        gc.drawImage(j.getImage, j.x*BoxSize+j.offsetX+offset, (j.y-1)*BoxSize+j.offsetY-1+offset, BoxSize*j.scale, BoxSize*j.scale)
      }
    }
  }
}