import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.space_dash_amit_shlomo_01.R
import com.example.space_dash_amit_shlomo_01.adapters.HighScoresAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.button.MaterialButton

class HighScoresFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HighScoresAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_high_scores, container, false)

        // הגדרת RecyclerView
        recyclerView = view.findViewById(R.id.high_scores_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = HighScoresAdapter(HighScoresManager.getTopScores()) { highScore ->
            // עדכון המפה עם מיקום השחקן
            val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
            mapFragment.getMapAsync { googleMap ->
                googleMap.clear()
                googleMap.addMarker(
                    MarkerOptions()
                        .position(highScore.location)
                        .title("Score: ${highScore.score}")
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(highScore.location, 10f))
            }
        }
        recyclerView.adapter = adapter

        // כפתור חזרה לתפריט
        view.findViewById<MaterialButton>(R.id.back_to_menu_button).setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return view
    }


}
