import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kr.R
import com.example.kr.Trip

class TripsAdapter(
    private val trips: List<Trip>,
    private val onLeaveReview: (Trip) -> Unit,
    private val onRepeatBooking: (Trip) -> Unit
) : RecyclerView.Adapter<TripsAdapter.TripViewHolder>() {

    // ViewHolder для каждого элемента списка
    class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tripName: TextView = itemView.findViewById(R.id.trip_name)
        val tripDetails: TextView = itemView.findViewById(R.id.trip_details)
        val tripImage: ImageView = itemView.findViewById(R.id.trip_image)
        val leaveReview: TextView = itemView.findViewById(R.id.leave_review)
        val repeatBooking: TextView = itemView.findViewById(R.id.repeat_booking)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trip_item, parent, false)
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = trips[position]
        holder.tripName.text = trip.name
        holder.tripDetails.text = "${trip.location}\n${trip.dates}\n${trip.price}"
        holder.tripImage.setImageResource(R.drawable.hotel_image) // Замените на реальное изображение

        // Обработка клика на "Оставить отзыв"
        holder.leaveReview.setOnClickListener {
            onLeaveReview(trip)
        }

        // Обработка клика на "Повторить бронирование"
        holder.repeatBooking.setOnClickListener {
            onRepeatBooking(trip)
        }
    }

    override fun getItemCount(): Int = trips.size
}
