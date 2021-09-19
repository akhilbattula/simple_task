package com.akhil.simpltask

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.akhil.simpltask.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityMainBinding
    private val scoresArray = arrayListOf(0,1,2,3,4,5,6,7)
    private val playersList:ArrayList<Player> = ArrayList()
    //private val players = arrayListOf("Kirat Boli","N.S Nodhi","R Rumrah","Shashi Henra")
    private val targetScore = 40
    private val totalBalls = 24
    private lateinit var strikePlayer:Player
    private var strikePlayerScore = 0
    private lateinit var nonStrikePlayer:Player
    private var nonStrikePlayerScore = 0
    private var currentScore = 0
    private var wicketDown = 0
    var ballsPlayed = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        initData()

        strikePlayer = playersList[0]
        strikePlayerScore = 0
        nonStrikePlayer = playersList[1]
        nonStrikePlayerScore = 0


        viewBinding.tvPlayer1.text = "* ${strikePlayer.playerName} ($strikePlayerScore)"
        viewBinding.tvPlayer2.text = "${nonStrikePlayer.playerName} ($nonStrikePlayerScore)"

        viewBinding.btnBowl.setOnClickListener {
            if(currentScore>=targetScore){
                viewBinding.tvFinalScore.text = "Bengaluru won the match with ${if(3-wicketDown==1) {"1 wicket"}else{"${3-wicketDown} wickets"}} and ${if(totalBalls-ballsPlayed==1) {"1 ball"}else{"${totalBalls-ballsPlayed} balls"}}"

            }else if(ballsPlayed>totalBalls){
                viewBinding.tvFinalScore.text = "Bengaluru lost the match with ${if(3-wicketDown==1) {"1 wicket"}else{"${3-wicketDown} wickets"}}"

            }else if(wicketDown==3){
                viewBinding.tvFinalScore.text = "Bengaluru lost the match with 0 wickets"
                viewBinding.tvPlayer1.text = "${strikePlayer.playerName} ($strikePlayerScore)"

            }else{
                when(strikePlayer.getScore()){
                    0 -> changeScore(0)
                    1 -> changeScoreAndRotate(1)
                    2 -> changeScore(2)
                    3 -> changeScoreAndRotate(3)
                    4 -> changeScore(4)
                    5 -> changeScoreAndRotate(5)
                    6 -> changeScore(6)
                    7 -> playerOut()
                }
            }
        }
    }

    private fun changeScore(score:Int) {
        currentScore+=score
        ballsPlayed++;
        ballsToOvers(ballsPlayed)

        viewBinding.tvScore.text = "Score: $currentScore - $wicketDown"

        if(score==0){
            viewBinding.tvCurrentScore.text = "Wide"
            viewBinding.tvRuns.text = ""
        }else{
            viewBinding.tvCurrentScore.text = "$score"
            viewBinding.tvRuns.text = "Runs"

        }

        strikePlayerScore += score

        when {
            currentScore>=targetScore -> {
                viewBinding.tvFinalScore.visibility = View.VISIBLE
                viewBinding.tvFinalScore.text = "Bengaluru won the match with ${if(3-wicketDown==1) {"1 wicket"}else{"${3-wicketDown} wickets"}} and ${if(totalBalls-ballsPlayed==1) {"1 ball"}else{"${totalBalls-ballsPlayed} balls"}}"

            }
            ballsPlayed>totalBalls -> {
                viewBinding.tvFinalScore.visibility = View.VISIBLE
                viewBinding.tvFinalScore.text = "Bengaluru lost the match with ${if(3-wicketDown==1) {"1 wicket"}else{"${3-wicketDown} wickets"}}"

            }
            else -> {
            }
        }

        viewBinding.tvPlayer1.text = "* ${strikePlayer.playerName} ($strikePlayerScore)"
        viewBinding.tvPlayer2.text = "${nonStrikePlayer.playerName} ($nonStrikePlayerScore)"

    }

    private fun changeScoreAndRotate(score:Int){
        currentScore+=score
        ballsPlayed++;
        ballsToOvers(ballsPlayed)

        viewBinding.tvScore.text = "Score: $currentScore - $wicketDown"
        viewBinding.tvCurrentScore.text = "$score"
        viewBinding.tvRuns.text = "Runs"

        strikePlayerScore += score

        when {
            currentScore>=targetScore -> {
                viewBinding.tvFinalScore.visibility = View.VISIBLE
                viewBinding.tvFinalScore.text = "Bengaluru won the match with ${if(3-wicketDown==1) {"1 wicket"}else{"${3-wicketDown} wickets"}} and ${if(totalBalls-ballsPlayed==1) {"1 ball"}else{"${totalBalls-ballsPlayed} balls"}}"

            }
            ballsPlayed>=totalBalls -> {
                viewBinding.tvFinalScore.visibility = View.VISIBLE
                viewBinding.tvFinalScore.text = "Bengaluru lost the match with ${if(3-wicketDown==1) {"1 wicket"}else{"${3-wicketDown} wickets"}}"

            }
            else -> {
                rotateStrike()
            }
        }

        viewBinding.tvPlayer1.text = "* ${strikePlayer.playerName} ($strikePlayerScore)"
        viewBinding.tvPlayer2.text = "${nonStrikePlayer.playerName} ($nonStrikePlayerScore)"

    }

    private fun playerOut(){
        wicketDown++
        ballsPlayed++
        ballsToOvers(ballsPlayed)
        viewBinding.tvScore.text = "Score: $currentScore - $wicketDown"

        viewBinding.tvCurrentScore.text = "OUT"
        viewBinding.tvRuns.text = "${strikePlayer.playerName} ($strikePlayerScore)"

        if(wicketDown>=3){
            viewBinding.tvFinalScore.visibility = View.VISIBLE
            viewBinding.tvFinalScore.text = "Bengaluru lost the match with 0 wickets"
            viewBinding.tvPlayer1.text = "${strikePlayer.playerName} ($strikePlayerScore)"
        }else{
            strikePlayer = playersList[wicketDown+1]
            strikePlayerScore = 0
            viewBinding.tvPlayer1.text = "* ${strikePlayer.playerName} ($strikePlayerScore)"
        }

    }

    private fun ballsToOvers(numberOfBalls:Int){
        if(numberOfBalls%6==0){
            viewBinding.tvOvers.text = "Overs: ${numberOfBalls/6}.${numberOfBalls%6}"
            rotateStrike()
        }else{
            viewBinding.tvOvers.text = "Overs: ${numberOfBalls/6}.${numberOfBalls%6}"
        }
    }

    private fun rotateStrike(){
        var temp1 = strikePlayer
        strikePlayer = nonStrikePlayer
        nonStrikePlayer = temp1

        var temp2 = strikePlayerScore
        strikePlayerScore = nonStrikePlayerScore
        nonStrikePlayerScore = temp2
    }

    private fun initData(){
        /*playersList.add(Player("Kirat Boli", arrayListOf<Double>(0.05,0.35,0.60,0.70,0.85,0.86,0.95,1.0)))
        playersList.add(Player("N.S Nodhi", arrayListOf<Double>(0.10,0.50,0.70,0.75,0.85,0.86,0.90,1.0)))
        playersList.add(Player("R Rumrah", arrayListOf<Double>(0.20,0.50,0.65,0.70,0.75,0.76,0.80,1.0)))
        playersList.add(Player("Shashi Henra", arrayListOf<Double>(0.30,0.55,0.60,0.60,0.65,0.66,0.70,1.0)))*/

        playersList.add(Player("Kirat Boli", arrayListOf(5,35,60,70,85,86,95,100)))
        playersList.add(Player("N.S Nodhi", arrayListOf(10,50,70,75,85,86,90,100)))
        playersList.add(Player("R Rumrah", arrayListOf(20,50,65,70,75,76,80,100)))
        playersList.add(Player("Shashi Henra", arrayListOf(30,55,60,60,65,66,70,100)))
    }
}