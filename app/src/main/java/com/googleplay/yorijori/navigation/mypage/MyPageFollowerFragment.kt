import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yorijori.App
import com.example.yorijori.R
import com.example.yorijori.SharedPreferenceUtil
import com.example.yorijori.data.datasource.remote.api.RecipeDTO
import com.example.yorijori.data.repository.Repository
import com.example.yorijori.navigation.mypage.FollowAdapter
import kotlinx.android.synthetic.main.fragment_mypage_follower.*

class MyPageFollowerFragment : Fragment() {

    private lateinit var v : View

    private var followerList = ArrayList<RecipeDTO.User>()
    private lateinit var rv_my_follower : RecyclerView

    private val repository = Repository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_mypage_follower, container, false)

        setFollower()

        return v
    }

    private fun setFollower() {
        followerList.clear()

        rv_my_follower = v.findViewById(R.id.rv_my_follower)
        rv_my_follower.layoutManager = LinearLayoutManager(App.instance, RecyclerView.VERTICAL, false)

        repository.getFollower(
            success = {
                it.run {
                    val data = it.list
                    followerList.addAll(data!!)
                    val myAdapter = FollowAdapter(followerList)
                    myAdapter.notifyDataSetChanged()
                    rv_my_follower.adapter = myAdapter
                    tv_my_follower_count.text = "전체 ${followerList.size}개"
                }
            },
            fail = {
                Log.d("fail", "fail fail fail")
            },
            token = SharedPreferenceUtil(App.instance).getToken().toString()
        )


    }
}

