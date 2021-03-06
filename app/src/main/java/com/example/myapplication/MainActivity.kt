package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar
import songs_queue

val queuedSongs = ArrayList<String>() //Array where all the songs queued will be stored and will be passed to the Queue activity
val songsArray = arrayListOf<String>()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Add the songs
        songsArray.addAll(resources.getStringArray(R.array.sad))
        songsArray.addAll(resources.getStringArray(R.array.kpop))
        songsArray.addAll(resources.getStringArray(R.array.opm))

        //Map the views
        var songsListView = findViewById<ListView>(R.id.songsListView)
        //Adapter for the list view
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsArray)
        songsListView.adapter = adapter

        //Register the context menu to the List View
        registerForContextMenu(songsListView)

    }

    //Context Menu
    override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.song_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo //Allows us to inherit from the class Adapterview.AdapterCOntextMenuInfo to get the position
        return when (item.itemId) {
            R.id.add_queue -> {
                queuedSongs.add(songsArray[menuInfo.position])
                val snackbar = Snackbar.make(findViewById(R.id.songsListView), "${songsArray[menuInfo.position]} is added to the Queue.", Snackbar.LENGTH_LONG)
                snackbar.setAction("Queue", View.OnClickListener { //Lamda function
                    val intent = Intent(this, songs_queue::class.java)
                    startActivity(intent)
                })
                snackbar.show()
                true
            }
            else -> {
                return super.onContextItemSelected(item)
            }

        }

    }


    //Main Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.go_to_song -> {
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            R.id.go_to_album -> {
                startActivity(Intent(this, albums::class.java))
                true
            }
            R.id.go_to_queue -> {
                val intent = Intent(this, songs_queue::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }


    }
}
