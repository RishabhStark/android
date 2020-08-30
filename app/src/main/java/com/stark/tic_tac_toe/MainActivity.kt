package com.stark.tic_tac_toe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
private val boardCells=Array(3){ arrayOfNulls<ImageView>(3)}
var board=Board()
    var playerScore=0
    var computerScore=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadBoard()
        restart.setOnClickListener({
            board=Board()
            whowon.text=""
            mapBoardtoUi()
        })

    }

    private fun loadBoard() {
        for(i in boardCells.indices) {
            for(j in boardCells.indices) {
                boardCells[i][j]=ImageView(this)
                boardCells[i][j]?.setPadding(20,20,20,20)
                boardCells[i][j]?.layoutParams=GridLayout.LayoutParams().apply {
                    rowSpec=GridLayout.spec(i)
                    columnSpec=GridLayout.spec(j)
                    width=260
                    height=260
                    bottomMargin=5
                    topMargin=5
                    leftMargin=5
                    rightMargin=5


                }
                boardCells[i][j]?.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary))
                boardCells[i][j]?.setOnClickListener(cellClickListner(i,j))
            gameboard.addView(boardCells[i][j])

            }
        }
    }
    private fun mapBoardtoUi() {
        for(i in board.board.indices) {
            for(j in board.board.indices) {
                when(board.board[i][j]) {
                    Board.PLAYER->{
                       boardCells[i][j]?.setImageResource(R.drawable.ic_o)
                        boardCells[i][j]?.isEnabled=false
                    }
                    Board.COMPUTER->{
                        boardCells[i][j]?.setImageResource(R.drawable.ic_x)
                        boardCells[i][j]?.isEnabled=false
                    }
                    else -> {
                        boardCells[i][j]?.setImageResource(0)
                        boardCells[i][j]?.isEnabled=true
                    }
                }
            }
        }
    }
    inner class cellClickListner(private val i:Int,private val j:Int): View.OnClickListener{

        override fun onClick(v: View?) {
            if(!board.gameOver) {
                val cell = Cell(i, j)
                board.placeMove(cell, Board.PLAYER)
                board.minimax(0,Board.COMPUTER)
                board.computerMove?.let {
                    board.placeMove(it, Board.COMPUTER)
                }


                mapBoardtoUi()
            }
            when {
                board.hasComputerWon()-> {
                    for(cell in board.availableCells) {
                        boardCells[cell.i][cell.j]?.isEnabled=false
                    }
                    computerScore+=1
                    ai.text=computerScore.toString()
                    whowon.text="Computer won!"


                }
                board.hasPlayerWon()->{
                    for(cell in board.availableCells) {
                        boardCells[cell.i][cell.j]?.isEnabled=false
                    }
                    playerScore+=1
                    human.text=playerScore.toString()
                    whowon.text="Player won!"

                }
                board.gameOver->whowon.text="Draw!"
            }
        }

    }

}
