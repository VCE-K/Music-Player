package cn.vce.musicplayer.frag


import android.app.FragmentTransaction
import android.os.Bundle
import android.text.format.DateUtils
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import cn.vce.musicplayer.R
import cn.vce.musicplayer.databinding.ContentList2Binding
import cn.vce.musicplayer.databinding.ContentList3Binding
import cn.vce.musicplayer.databinding.ContentListItemBinding
import cn.vce.musicplayer.music.MusicContent
import cn.vce.musicplayer.view.ProgressView
import com.andremion.music.MusicCoverView
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.google.android.material.floatingactionbutton.FloatingActionButton


class PlaylistFragment: PlayerFragment<ContentList3Binding>() {

    override fun init(savedInstanceState: Bundle?) {
        initView()
    }

    override fun getLayoutId(): Int? = R.layout.content_list3

    override fun initView() {
        super.initView()
        mCoverView = binding.cover
        mTitleView = binding.title.root
        mTimeView = binding.time

        mDurationView = binding.duration
        mProgressView = binding.progress
        mFabView = binding.fab
        // Set the recycler adapter

        binding.tracks.linear().setup {
            addType<MusicContent.MusicItem>(R.layout.content_list_item)
            onBind {
                val mItem = getModel<MusicContent.MusicItem>()
                val  binding = getBinding<ContentListItemBinding>()

                val mCoverView = binding.cover as ImageView
                val mTitleView = binding.title as TextView
                val mArtistView = binding.artist as TextView
                val mDurationView = binding.duration as TextView

                mCoverView.setImageResource(mItem.cover)
                mTitleView.text = mItem.title
                mArtistView.text = mItem.artist
                mDurationView.text = DateUtils.formatElapsedTime(mItem.duration)
                binding.root.setOnClickListener{
                    // Nothing to do
                }
            }
        }.models = MusicContent.ITEMS

        (mCoverView as MusicCoverView).setOnClickListener {
            nav()
        }
        (mTitleView as LinearLayout).setOnClickListener {
            nav()
        }
        (mTimeView as TextView).setOnClickListener {
            nav()
        }
        (mDurationView as TextView).setOnClickListener {
            nav()
        }
        (mProgressView as ProgressView).setOnClickListener {
            nav()
        }
        (mFabView as FloatingActionButton).setOnClickListener {
            nav()
        }

    }

    private fun nav(){
        //exitTransition 是 Fragment A 转向 Fragment B 时，A 的出场动画，也就是 A 消失的动画。而
        exitTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.list_content_exit_transition)

        //reenterTransition 是当你从 Fragment B 返回 Fragment A 时，A 的重新进场动画，也就是 A 重新出现的动画。
        reenterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.list_shared_element_reenter_transition)

        //共享进入
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.list_shared_element_exit_transition)
        //共享返回
        sharedElementReturnTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.list_shared_element_reenter_transition)

        val extras = FragmentNavigator.Extras.Builder()
            .addSharedElement(mCoverView!!, mCoverView!!.transitionName)
            .addSharedElement(mTitleView!!, mTitleView!!.transitionName)
            .addSharedElement(mTimeView!!, mTimeView!!.transitionName)
            .addSharedElement(mDurationView!!, mDurationView!!.transitionName)
            .addSharedElement(mProgressView!!, mProgressView!!.transitionName)
            .addSharedElement(mFabView!!, mFabView!!.transitionName)
            .build()


        /*val fragment = DetailFragment()
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.commit {
            replace(R.id.nav_host_fragment, fragment)
            addToBackStack(null)
            addSharedElement(mCoverView!!, mCoverView!!.transitionName)
            addSharedElement(mTitleView!!, mTitleView!!.transitionName)
            addSharedElement(mTimeView!!, mTimeView!!.transitionName)
            addSharedElement(mDurationView!!, mDurationView!!.transitionName)
            addSharedElement(mProgressView!!, mProgressView!!.transitionName)
            addSharedElement(mFabView!!, mFabView!!.transitionName)
        }*/

        findNavController().navigate(
            R.id.action_music_fragment_dest_to_DetailFragment,
            null,
            null,
            extras)


        /*findNavController().navigate(
            R.id.action_music_fragment_dest_to_DetailFragment,
            null,
            null,
            extras
        )*/
    }



}


inline fun View?.finishView(){
    val parent = this?.parent as ViewGroup
    isVisible = false
    parent?.removeView(this)
}