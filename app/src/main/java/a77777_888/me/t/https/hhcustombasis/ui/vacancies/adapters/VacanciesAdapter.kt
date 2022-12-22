package a77777_888.me.t.https.hhcustombasis.ui.vacancies.adapters

import a77777_888.me.t.https.hhcustombasis.databinding.VacanciesItemBinding
import a77777_888.me.t.https.hhcustombasis.model.entities.vacancies.Vacancy
import a77777_888.me.t.https.hhcustombasis.utils.toPatternDateStringFromISO8601String
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

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
                    vacancy.published_at.toPatternDateStringFromISO8601String()

            if (vacancy.employer.logo_urls?.original == null) logoImageView.visibility = INVISIBLE
            else {
                logoImageView.visibility = VISIBLE

                Glide.with(logoImageView)
                    .load(vacancy.employer.logo_urls.original)
                    .into(logoImageView)
            }

            holder.binding.root.setOnClickListener {
                navigator.goToVacancy(vacancy.id)
            }

        }
    }

    inner class ViewHolder(val binding: VacanciesItemBinding)
        : RecyclerView.ViewHolder(binding.root)

}


class VacanciesDiffCallback : DiffUtil.ItemCallback<Vacancy>() {

    override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem == newItem
    }
}