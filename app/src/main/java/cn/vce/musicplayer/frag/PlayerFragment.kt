package cn.vce.musicplayer.frag

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.text.format.DateUtils
import android.view.View
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import cn.vce.musicplayer.base.BaseVmFragment
import cn.vce.musicplayer.music.PlayerService
import cn.vce.musicplayer.music.PlayerService.LocalBinder
import cn.vce.musicplayer.view.ProgressView
import com.google.android.material.floatingactionbutton.FloatingActionButton

open class PlayerFragment<BD: ViewDataBinding>: BaseVmFragment<BD>() {
    var mCoverView: View? = null
    var mTitleView: View? = null
    var mFabView: FloatingActionButton? = null


    var mService: PlayerService? = null

    var mBound = false
    var mTimeView: TextView? = null
    var mDurationView: TextView? = null
    var mProgressView: ProgressView? = null
    private val mUpdateProgressHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            val position = mService!!.position
            val duration = mService!!.duration
            onUpdateProgress(position, duration)
            sendEmptyMessageDelayed(0, DateUtils.SECOND_IN_MILLIS)
        }
    }



    override fun init(savedInstanceState: Bundle?) {
        initView()
    }

    override fun getLayoutId(): Int?  = null


    override fun initView() {
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to PlayerService, cast the IBinder and get PlayerService instance
            val binder = service as LocalBinder
            mService = binder.service
            mBound = true
            onBind()
        }

        override fun onServiceDisconnected(classname: ComponentName) {
            mBound = false
            onUnbind()
        }
    }

    private fun onUpdateProgress(position: Int, duration: Int) {
        if (mTimeView != null) {
            mTimeView!!.text = DateUtils.formatElapsedTime(position.toLong())
        }
        if (mDurationView != null) {
            mDurationView!!.text = DateUtils.formatElapsedTime(duration.toLong())
        }
        if (mProgressView != null) {
            mProgressView!!.progress = position
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Bind to PlayerService
        activity?.apply {
            val intent = Intent(this, PlayerService::class.java)
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        }
    }


    override fun onDestroy() {
        // Unbind from the service
        activity?.apply {
            if (mBound) {
                unbindService(mConnection)
                mBound = false
            }
        }
        super.onDestroy()
    }

    private fun onBind() {
        mUpdateProgressHandler.sendEmptyMessage(0)
    }

    private fun onUnbind() {
        mUpdateProgressHandler.removeMessages(0)
    }

    open fun play() {
        mService!!.play()
    }

    open fun pause() {
        mService!!.pause()
    }

}