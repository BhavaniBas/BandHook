package bandhook.android.com.bandhook

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Switch
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    var recyclerView: RecyclerView? = null
    private lateinit var bandHookSwitch: Switch
    var bandHookAdapter: BandHookAdapter? = null
    private lateinit var gridLayoutManager: GridLayoutManager
    var bandHookResponse: ArrayList<BandHookResponse>? = null
    var isChecked : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bandHookSwitch = findViewById(R.id.bandHookSwitch)
        recyclerView = findViewById(R.id.recyclerView)

        val apiInterface: RequestInterface = ApiClient().getApiClient()!!.create(RequestInterface::class.java)
        apiInterface.getBandHookList().enqueue(object : Callback<ArrayList<BandHookResponse>> {
            override fun onResponse(call: Call<ArrayList<BandHookResponse>>?,
                                    response: Response<ArrayList<BandHookResponse>>?) {
                if (response!!.isSuccessful) {
                    bandHookResponse = response.body()
                    setAdapter(bandHookResponse)
                } else {
                    Toast.makeText(this@MainActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<BandHookResponse>>?, t: Throwable?) {
                Toast.makeText(this@MainActivity, "Please try again !!!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    @SuppressLint("ResourceType")
    private fun setAdapter(bandHookResponse: ArrayList<BandHookResponse>?) {
        if(!isChecked) {
            gridAdapter(bandHookResponse)
        }
        bandHookSwitch.setOnCheckedChangeListener({ buttonView, isChecked ->
            if (isChecked) {
                recyclerAdapter(bandHookResponse)
            } else {
                gridAdapter(bandHookResponse)
            }
        })
    }

    fun gridAdapter(bandHookResponse: ArrayList<BandHookResponse>?) {
        bandHookAdapter = BandHookAdapter(bandHookResponse!!, this@MainActivity)
        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView!!.layoutManager = gridLayoutManager
        val spanCount = 2 // 2 columns
        val spacing = 10 // 50px
        val includeEdge = false
        isChecked = true
        recyclerView!!.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        recyclerView!!.hasFixedSize()
        recyclerView!!.adapter = bandHookAdapter
        bandHookAdapter!!.notifyDataSetChanged()

    }

    fun recyclerAdapter(bandHookResponse: ArrayList<BandHookResponse>?) {
        bandHookAdapter = BandHookAdapter(bandHookResponse!!, this@MainActivity)
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.hasFixedSize()
        recyclerView!!.adapter = bandHookAdapter
        bandHookAdapter!!.notifyDataSetChanged()
    }
}
