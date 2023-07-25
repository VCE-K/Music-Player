package cn.vce.musicplayer.frag

import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import androidx.navigation.fragment.findNavController
import cn.vce.musicplayer.R
import cn.vce.musicplayer.databinding.ContentDetail2Binding
import cn.vce.musicplayer.view.TransitionAdapter
import com.andremion.music.MusicCoverView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailFragment: PlayerFragment<ContentDetail2Binding>() {

    override fun getLayoutId(): Int? = R.layout.content_detail2


    override fun init(savedInstanceState: Bundle?) {
        initView()
    }

    override fun initView() {

        mCoverView = binding.cover

        mTitleView = binding.title.root
        mTimeView = binding.time


        mDurationView = binding.duration
        mProgressView = binding.progress
        mFabView = binding.fab
        (mCoverView as MusicCoverView).setCallbacks(object : MusicCoverView.Callbacks {
            override fun onMorphEnd(coverView: MusicCoverView) {
                // Nothing to do
            }

            override fun onRotateEnd(coverView: MusicCoverView) {
                findNavController().navigateUp()
            }
        })


        (mFabView as FloatingActionButton).setOnClickListener {
            pause()
            (mCoverView as MusicCoverView).stop()
        }

        enterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.detail_content_enter_transition)
        returnTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.detail_content_return_transition)

        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.detail_shared_element_enter_transition)
        sharedElementReturnTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.detail_shared_element_return_transition)

        (sharedElementEnterTransition as Transition).addListener(object : TransitionAdapter() {
            override fun onTransitionEnd(transition: Transition) {
                play()
                (mCoverView as MusicCoverView).start()
            }
        })
    }

    fun onBackPressed() {
        pause()
        (mCoverView as MusicCoverView).stop()
    }


}