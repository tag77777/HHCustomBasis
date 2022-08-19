package a77777_888.me.t.https.hhcustombasis.fragments.adapters

import a77777_888.me.t.https.hhcustombasis.databinding.VacanciesItemBinding
import a77777_888.me.t.https.hhcustombasis.model.entities.vacancies.Vacancy
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.internal.bind.util.ISO8601Utils
import java.text.ParsePosition
import java.text.SimpleDateFormat

interface VacanciesAdapterNavigator {
    fun goToVacancy(vacancyId: String)
}

class VacanciesAdapter(
    private val navigator: VacanciesAdapterNavigator
) : PagingDataAdapter<Vacancy, VacanciesAdapter.ViewHolder>(VacanciesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            VacanciesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vacancy = getItem(position) ?: return

        with(holder.binding) {

            nameTextView.text = vacancy.name
            vacancy.salary?.let {
                salaryTextView.text = when {
                    it.from == null || it.from == 0 -> "до ${it.to}"
                    it.to == null || it.to == 0 -> it.from.toString()
                    else -> "${it.from} - ${it.to} ${it.currency}"
                }

            }

            companyNameTextView.text = vacancy.employer.name

            areaTextView.text = "Размещено в г.${vacancy.area.name}   " +
                    getDateStringFromISO8601(vacancy.published_at)

            if (vacancy.employer.logo_urls?.original == null) logoImageView.visibility = INVISIBLE
            else {
                logoImageView.visibility = VISIBLE

                Glide.with(logoImageView)
                    .load(vacancy.employer.logo_urls.original)
                    .into(logoImageView)
            }

            holder.binding.root.setOnClickListener {
//                Singleton.vacancies = snapshot().items
                navigator.goToVacancy(vacancy.id)
            }

        }
    }

    inner class ViewHolder(val binding: VacanciesItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SimpleDateFormat")
    private fun getDateStringFromISO8601(iso8601: String): String = try {
        val data = ISO8601Utils.parse(iso8601, ParsePosition(0))
        SimpleDateFormat("d MMMM yyyy, HH:mm").format(data)
    } catch (e: Exception){ "" }
        // required API level 26
//         LocalDateTime.parse(iso8601.dropLast(5))
//            .format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm"))

}


class VacanciesDiffCallback : DiffUtil.ItemCallback<Vacancy>() {

    override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem == newItem
    }
}