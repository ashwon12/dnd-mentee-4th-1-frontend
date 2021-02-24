package com.example.myapplication

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import org.json.JSONArray

class SharedPreferenceUtil(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("searchHistory", MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun get(): String? = sharedPreferences.getString("searchHistory", "null")
    fun set(searchList: ArrayList<String>) {
        editor.putString("searchHistory", JSONArray(searchList).toString())
            .apply()
    }
    
    // 사용자 토큰
    fun saveToken(token : String)  {
        editor.putString("token", token).apply()
    }
    
    // 사용자 카카오 번호
    fun saveKakaoId(id : String) {
        editor.putString("k_id", id).apply()
    }
    
    // 사용자 구글 번호
    fun saveGoogleId(id: String) {
        editor.putString("g_id", id).apply()
    }

    //사용자 이메일, 이미지, 고유번호
    fun saveInfo(email: String, image: String) {
        editor.putString("email",email).apply()
        editor.putString("image",image).apply()
    }

    // 사용자 이름
    fun saveName(name: String){
        editor.putString("name", name).apply()
    }
    
    // 플래그 카카오 : 1번, 구글 : 2번
    fun saveFlag(flag: String) {
        editor.putString("flag", flag).apply()
    }

    fun getToken() : String? = sharedPreferences.getString("token", null)
    fun getName() : String? = sharedPreferences.getString("name", null)
    fun getEmail() : String? = sharedPreferences.getString("email",null)
    fun getImage() : String? = sharedPreferences.getString("image", null)
    fun getGoogleId() : String? = sharedPreferences.getString("g_id", null)
    fun getKakaoId() : String? = sharedPreferences.getString("k_id", null)
    fun getFlag(): String? = sharedPreferences.getString("flag", null)
}