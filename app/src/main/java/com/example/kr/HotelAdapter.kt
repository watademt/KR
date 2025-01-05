import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kr.R

class HotelAdapter(private val hotels: List<Hotel>) : RecyclerView.Adapter<HotelAdapter.HotelViewHolder>() {

    class HotelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.hotel_image)
        val name: TextView = view.findViewById(R.id.hotel_name)
        val description: TextView = view.findViewById(R.id.hotel_description)
        val price: TextView = view.findViewById(R.id.hotel_price)
        val bookButton: Button = view.findViewById(R.id.hotel_book_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hotel_item_layout, parent, false)
        return HotelViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val hotel = hotels[position]
        holder.name.text = hotel.name
        holder.description.text = hotel.description
        holder.price.text = hotel.price
        holder.image.setImageResource(hotel.imageResource)

        holder.bookButton.setOnClickListener {
            // Обработка нажатия кнопки "Забронировать"
        }
    }

    override fun getItemCount() = hotels.size
}
