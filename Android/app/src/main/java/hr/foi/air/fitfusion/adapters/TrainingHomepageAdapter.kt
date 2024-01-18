package hr.foi.air.fitfusion.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.fitfusion.R
import hr.foi.air.fitfusion.entities.Training

class TrainingHomepageAdapter(private val trainingsList: ArrayList<Training>, private val onCalendarClick:()-> Unit) : RecyclerView.Adapter<TrainingHomepageAdapter.TrainingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_booked_class, parent, false)
        return TrainingViewHolder(itemView)
    }

    override fun getItemCount(): Int = trainingsList.size

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        holder.bind(trainingsList[position], onCalendarClick)
    }

    class TrainingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val type: TextView = itemView.findViewById(R.id.type)
        private val date: TextView = itemView.findViewById(R.id.date)
        private val time: TextView = itemView.findViewById(R.id.time)

        fun bind(training: Training, onCalendarClick: () -> Unit) {
            type.text = training.type
            date.text = training.date
            time.text = training.time
        }
    }
}
