package hr.foi.air.fitfusion.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.fitfusion.R
import hr.foi.air.fitfusion.TrainingSessionActivity
import hr.foi.air.fitfusion.adapters.ClassAdapter
import hr.foi.air.fitfusion.adapters.ClassAdapterCardio
import hr.foi.air.fitfusion.adapters.ClassAdapterYoga
import hr.foi.air.fitfusion.data_classes.FirebaseManager
import hr.foi.air.fitfusion.entities.ClassesCardio
import hr.foi.air.fitfusion.entities.ClassesStrength
import hr.foi.air.fitfusion.entities.ClassesYoga


interface StrengthDataListener {
    fun onStrengthDataReceived(classesStrength: ArrayList<ClassesStrength>)
}

interface CardioDataListener {
    fun onCardioDataReceived(classesCardio: ArrayList<ClassesCardio>)
}

interface YogaDataListener {
    fun onYogaDataReceived(classesYoga: ArrayList<ClassesYoga>)
}



class HomeTrainerFragment : Fragment(),
    StrengthDataListener, CardioDataListener, YogaDataListener,
    ClassAdapter.RecyclerViewEvent, ClassAdapterCardio.RecyclerViewEvent2,
    ClassAdapterYoga.RecyclerViewEvent3 {
    private lateinit var firebaseManager: FirebaseManager
    private lateinit var classRecyclerviewStrength : RecyclerView
    private lateinit var classRecyclerviewCardio : RecyclerView
    private lateinit var classRecyclerviewYoga : RecyclerView
    private lateinit var classArrayListStrength : ArrayList<ClassesStrength>
    private lateinit var classArrayListCardio : ArrayList<ClassesCardio>
    private lateinit var classArrayListYoga : ArrayList<ClassesYoga>
    private lateinit var btnNewClass : Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trainer_home, container, false)
    }


    override fun onStrengthDataReceived(classesStrength: ArrayList<ClassesStrength>) {
        classRecyclerviewStrength.adapter = ClassAdapter(classesStrength, this)
    }

    override fun onCardioDataReceived(classesCardio: ArrayList<ClassesCardio>) {
        classRecyclerviewCardio.adapter = ClassAdapterCardio(classesCardio, this)
    }

    override fun onYogaDataReceived(classesYoga: ArrayList<ClassesYoga>) {
        classRecyclerviewYoga.adapter = ClassAdapterYoga(classesYoga, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseManager = FirebaseManager()
        classRecyclerviewStrength = view.findViewById(R.id.rvClassListStrength)
        classRecyclerviewStrength.layoutManager = LinearLayoutManager(view.context)
        classRecyclerviewStrength.setHasFixedSize(true)

        classRecyclerviewCardio = view.findViewById(R.id.rvClassListCardio)
        classRecyclerviewCardio.layoutManager = LinearLayoutManager(view.context)
        classRecyclerviewCardio.setHasFixedSize(true)

        classRecyclerviewYoga = view.findViewById(R.id.rvClassListYoga)
        classRecyclerviewYoga.layoutManager = LinearLayoutManager(view.context)
        classRecyclerviewYoga.setHasFixedSize(true)

        classArrayListStrength = arrayListOf()
        classArrayListCardio = arrayListOf()
        classArrayListYoga= arrayListOf()

        btnNewClass = view.findViewById(R.id.btnNewClass)
        btnNewClass.setOnClickListener {
            val intent = Intent(activity, TrainingSessionActivity::class.java)
            startActivity(intent)
        }

        context?.let {
            firebaseManager.showTrainingsList(
            classArrayListStrength,
            classArrayListCardio,
            classArrayListYoga,
            it,
            this,
            this,
            this)
        }

    }

    override fun onItemClick(position: Int) {
        val strength = classArrayListStrength[position]

        val bundle = Bundle().apply {
            putString("type", strength.type)
            putString("date", strength.date)
            putString("time", strength.time)
            putString("participants", strength.participants)
        }
        val detailsFragment = TrainingSessionDetailsFragment()
        detailsFragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, detailsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onItemClick2(position: Int) {
        val cardio = classArrayListCardio[position]
    }

    override fun onItemClick3(position: Int) {
        val yoga = classArrayListYoga[position]
    }
}
