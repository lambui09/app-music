package com.example.musicfinal.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.musicfinal.R
import com.example.musicfinal.database.AppDataHelper
import com.example.musicfinal.database.DataController
import com.example.musicfinal.database.model.Playlist
import com.example.musicfinal.database.model.Song
import com.example.musicfinal.database.updater.CommonUpdater
import com.example.musicfinal.ui.dialog.listener.AddPlaylistEventListener
import com.example.musicfinal.ui.fragment.ListSongFragment
import com.example.musicfinal.ui.fragment.PlayMusicFragment
import com.example.musicfinal.ui.fragment.SearchSongFragment
import com.example.musicfinal.ui.fragment.StandardFragment
import com.example.musicfinal.ui.fragment.listener.AdditionFragmentActionListener
import com.example.musicfinal.ui.fragment.listener.LibraryFragmentActionListener
import com.example.musicfinal.ui.fragment.listener.ListSongFragmentActionListener
import com.example.musicfinal.ui.fragment.listener.StandardFragmentActionListener
import com.example.musicfinal.utils.askForPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
        , StandardFragmentActionListener, ListSongFragmentActionListener, AddPlaylistEventListener,
        LibraryFragmentActionListener, AdditionFragmentActionListener {
    private var dataController: DataController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initDrawerLayout()
        setFirstFragment()
        checkAdditionFragment()
    }

    private fun initDrawerLayout() {
        //TODO : init recycler view that contain user's information
    }

    private fun initData() {
        if (askForPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE)) {
            dataController = DataController(this)
        }
    }

    fun getSongs(): List<Song> {
        dataController?.let {
            return it.songs
        }
        return listOf()
    }

    private fun setFirstFragment() {
        supportFragmentManager.beginTransaction()
                .add(R.id.flMain, StandardFragment())
                .commit()
    }

    private fun checkAdditionFragment() {
        intent.getStringExtra(FRAGMENT_NAME_KEY).let {
            if (it == PlayMusicFragment::class.qualifiedName) {
                val fragment = supportFragmentManager.findFragmentById(R.id.flMain)
                if (fragment !is PlayMusicFragment) {
                    replaceMainFragment(PlayMusicFragment())
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == REQUEST_CODE) {
            if (isResultGranted(grantResults)) {
                dataController = DataController(this)
            }
        }
    }

    private fun isResultGranted(grantResults: IntArray): Boolean {
        return grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
    }

    override fun onStartSearch() = replaceMainFragment(SearchSongFragment())

    override fun onViewUserInfo() {
        drawerLayout.openDrawer(Gravity.END)
    }

    override fun onBackToStandard() {
        supportFragmentManager.popBackStackImmediate()
    }

    override fun addPlaylist(name: String) {
        AppDataHelper.getInstance(this)
                .addPlaylist(Playlist(name = name), object : CommonUpdater {
                    override fun onFinish() {
                        val fragment = supportFragmentManager.findFragmentById(R.id.flMain)
                        if (fragment is StandardFragment) {
                            fragment.updatePlaylistFragment()
                        }
                    }
                })
    }

    override fun openHistorySong() =
            replaceMainFragment(ListSongFragment.instance(ListSongFragment.TYPE_HISTORY))

    override fun openFavoriteSong() =
            replaceMainFragment(ListSongFragment.instance(ListSongFragment.TYPE_FAVORITE))

    override fun openAllSong() =
            replaceMainFragment(ListSongFragment.instance(ListSongFragment.TYPE_ALL))

    override fun onStartPlay(songId: Long) =
            replaceMainFragment(PlayMusicFragment.newInstance(songId))

    private fun replaceMainFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flMain, fragment)
                .addToBackStack(null)
                .commit()
    }

    override fun onFavoriteChange(songId: Long) {
        dataController?.changeFavoriteState(songId)
        val fragment = supportFragmentManager.findFragmentById(R.id.flMain)
        if (fragment is ListSongFragment) {
            fragment.changeListSongView()
        }
    }

    companion object {
        private const val REQUEST_CODE = 1583
        const val FRAGMENT_NAME_KEY = "FRAGMENT_NAME_KEY"
    }
}
