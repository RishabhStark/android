package com.stark.tic_tac_toe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_humanactivity.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.gameboard
import kotlinx.android.synthetic.main.activity_main.human
import kotlinx.android.synthetic.main.activity_main.restart
import kotlinx.android.synthetic.main.activity_main.whowon

class humanactivity : AppCompatActivity() {
    private val boardCells=Array(3){ arrayOfNulls<ImageView>(3)}
    private var board=Board()
   private var playerScore=0
    private var computerScore=0
    private var playerturn=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_humanactivity)
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
                    boardCells[i][j]?.layoutParams= GridLayout.LayoutParams().apply {
                        rowSpec= GridLayout.spec(i)
                        columnSpec= GridLayout.spec(j)
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
                  if(playerturn) {
                      board.placeMove(cell, Board.PLAYER)
                  playerturn=false}
                   else {
                      board.placeMove(cell,Board.COMPUTER)
                      playerturn=true

                  }


                    mapBoardtoUi()
                }
                when {
                    board.hasComputerWon()-> {
                        for(cell in board.availableCells) {
                            boardCells[cell.i][cell.j]?.isEnabled=false
                        }
                        computerScore+=1
                        p1.text=computerScore.toString()
                        whowon.text="Player2 won!"


                    }
                    board.hasPlayerWon()->{
                        for(cell in board.availableCells) {
                            boardCells[cell.i][cell.j]?.isEnabled=false
                        }
                        playerScore+=1
                        p2.text=playerScore.toString()
                        whowon.text="Player1 won!"

                    }
                    board.gameOver->whowon.text="Draw!"
                }
            }

        }

    }
