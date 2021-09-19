package com.akhil.simpltask

import android.util.Log
import kotlin.random.Random

class Player(s: String, arrayList: ArrayList<Int>) {

    var playerName:String = s
    var probsList:ArrayList<Int> = arrayList

    fun getScore(): Int{

        val random = Random.nextInt(1,100);
        Log.e("akhill",""+random)
        if(random>=1 && random<=probsList[0]){
            return 0
        }else if(random>=(probsList[0]+1) && random<=probsList[1]){
            return 1
        }else if(random>=(probsList[1]+1) && random<=probsList[2]){
            return 2
        }else if(random>=(probsList[2]+1) && random<=probsList[3]){
            return 3
        }else if(random>=(probsList[3]+1) && random<=probsList[4]){
            return 4
        }else if(random>=(probsList[4]+1) && random<=probsList[5]){
            return 5
        }else if(random>=(probsList[5]+1) && random<=probsList[6]){
            return 6
        }else{
            return 7
        }

        /*var randomnumber = (Math.random() * 100).toInt()
        var runs = 0
        for (i in 0 until probsList.size) {
            if (randomnumber - probsList[i] <= 0) {
                runs = i
                break
            }
            randomnumber -= probsList[i]
        }
        return runs*/
    }
}