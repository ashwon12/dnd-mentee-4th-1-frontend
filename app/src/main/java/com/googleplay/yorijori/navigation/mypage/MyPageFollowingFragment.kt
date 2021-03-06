import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.googleplay.yorijori.App
import com.googleplay.yorijori.R
import com.googleplay.yorijori.SharedPreferenceUtil
import com.googleplay.yorijori.data.datasource.remote.api.RecipeDTO
import com.googleplay.yorijori.data.repository.Repository
import com.googleplay.yorijori.navigation.mypage.FollowAdapter
import kotlinx.android.synthetic.main.fragment_mypage_follower.*

class MyPageFollowingFragment : Fragment() {

    private lateinit var v : View

    private var followingList = ArrayList<RecipeDTO.User>()
    private lateinit var rv_my_following : RecyclerView

    private val repository = Repository()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_mypage_following, container, false)

        setFollowing()

        return v
    }

    private fun setFollowing() {
        followingList.clear()

        rv_my_following = v.findViewById(R.id.rv_my_following)
        rv_my_following.layoutManager = LinearLayoutManager(App.instance, RecyclerView.VERTICAL, false)

        repository.getFollowing(
            success = {
                it.run {
                    val data = it.list
                    followingList.addAll(data!!)
                    val myAdapter = FollowAdapter(followingList)
                    myAdapter.notifyDataSetChanged()
                    rv_my_following.adapter =myAdapter
                    tv_my_follower_count.text = "전체 ${data.size}개"
                }
            },
            fail = {
                Log.d("fail", "fail fail fail")
            },
            token = SharedPreferenceUtil(App.instance).getToken().toString()
        )
    }
}