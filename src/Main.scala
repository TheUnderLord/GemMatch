/**
  * Created by Alex on 11/30/2016.
  */




/*import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.canvas._
import scalafx.scene.control.Label
import scalafx.scene.input
import scalafx.scene.layout.BorderPane
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.event.ActionEvent*/

import scalafx.application.JFXApp
import scala.io.Source
import java.io.PrintWriter

import scalafx.Includes._
import scalafx.scene._
import scalafx.scene.control._
import scalafx.scene.shape._
import scalafx.scene.input._
import scalafx.scene.canvas._
import scalafx.animation._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.paint._
import scalafx.scene.image._

object Main extends JFXApp {
  val gridWidth = 10
  val gridHeight = 10
  val squareSize = 80
  val moveSpeed =   8
  val sceneWidth = gridWidth*squareSize
  val sceneHeight = (gridHeight-1)*squareSize
  
  val gemGrid = new GemMap(gridWidth,gridHeight,squareSize)
  //GemGrid.generateMap()
  case class Point2D(x:Int,y:Int)
  stage = new PrimaryStage {
    title = "maze"
    scene = new Scene(sceneWidth,sceneHeight) {

      var upPressed, downPressed, leftPressed, rightPressed = false
      val canvas = new Canvas(sceneWidth,sceneHeight)
      val gc = canvas.graphicsContext2D
      val renderer = new Renderer(squareSize,gc)
      canvas.onMouseClicked = (me: MouseEvent) => { 
        val gdX = me.sceneX.toInt/squareSize
        val gdY = me.sceneY.toInt/squareSize+1
        gemGrid(gdX,gdY).removeGem

      }
     
      var currentDirection = new Point2D(0,1)
      content = canvas
      canvas.requestFocus

      var lastTime = 0L
      
      
      val timer = AnimationTimer { time =>
        val interval = (time-lastTime)/1e6

        if(interval >= moveSpeed) {
          lastTime = time
          gemGrid.updateGrid

         
          renderer.render(gemGrid.getArray)
        }
      }
      timer.start




    }
  }
}
