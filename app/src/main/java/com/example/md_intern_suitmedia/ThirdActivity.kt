package com.example.md_intern_suitmedia

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.md_intern_suitmedia.databinding.ActivityThirdBinding
import org.json.JSONObject

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    private lateinit var userAdapter: UserAdapter
    private var users: List<User> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back) // Custom back icon, optional
        }

        toolbar.setNavigationOnClickListener {
            onBackPressed() // Handle the back button action
        }

        userAdapter = UserAdapter(users) { user ->
            val resultIntent = Intent().apply {
                putExtra("selectedUserName", "${user.firstName} ${user.lastName}")
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = userAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            fetchUsers()
        }

        // Load initial data
        fetchUsers()
    }

    private fun fetchUsers() {
        // Simulate fetching data from API
        val json = """{"page":1,"per_page":10,"total":12,"total_pages":2,"data":[{"id":1,"email":"george.bluth@reqres.in","first_name":"George","last_name":"Bluth","avatar":"https://reqres.in/img/faces/1-image.jpg"},{"id":2,"email":"janet.weaver@reqres.in","first_name":"Janet","last_name":"Weaver","avatar":"https://reqres.in/img/faces/2-image.jpg"},{"id":3,"email":"emma.wong@reqres.in","first_name":"Emma","last_name":"Wong","avatar":"https://reqres.in/img/faces/3-image.jpg"},{"id":4,"email":"eve.holt@reqres.in","first_name":"Eve","last_name":"Holt","avatar":"https://reqres.in/img/faces/4-image.jpg"},{"id":5,"email":"charles.morris@reqres.in","first_name":"Charles","last_name":"Morris","avatar":"https://reqres.in/img/faces/5-image.jpg"},{"id":6,"email":"tracey.ramos@reqres.in","first_name":"Tracey","last_name":"Ramos","avatar":"https://reqres.in/img/faces/6-image.jpg"},{"id":7,"email":"michael.lawson@reqres.in","first_name":"Michael","last_name":"Lawson","avatar":"https://reqres.in/img/faces/7-image.jpg"},{"id":8,"email":"lindsay.ferguson@reqres.in","first_name":"Lindsay","last_name":"Ferguson","avatar":"https://reqres.in/img/faces/8-image.jpg"},{"id":9,"email":"tobias.funke@reqres.in","first_name":"Tobias","last_name":"Funke","avatar":"https://reqres.in/img/faces/9-image.jpg"},{"id":10,"email":"byron.fields@reqres.in","first_name":"Byron","last_name":"Fields","avatar":"https://reqres.in/img/faces/10-image.jpg"}],"support":{"url":"https://reqres.in/#support-heading","text":"To keep ReqRes free, contributions towards server costs are appreciated!"}}"""
        val jsonObject = JSONObject(json)
        val data = jsonObject.getJSONArray("data")
        val newUsers = mutableListOf<User>()
        for (i in 0 until data.length()) {
            val userObject = data.getJSONObject(i)
            newUsers.add(
                User(
                    id = userObject.getInt("id"),
                    email = userObject.getString("email"),
                    firstName = userObject.getString("first_name"),
                    lastName = userObject.getString("last_name"),
                    avatar = userObject.getString("avatar")
                )
            )
        }
        users = newUsers
        userAdapter = UserAdapter(users) { user ->
            val resultIntent = Intent().apply {
                putExtra("selectedUserName", "${user.firstName} ${user.lastName}")
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
        binding.recyclerView.adapter = userAdapter
        binding.swipeRefreshLayout.isRefreshing = false
    }
}