package a77777_888.me.t.https.hhcustombasis.fragments.vacancy

import a77777_888.me.t.https.hhcustombasis.R
import a77777_888.me.t.https.hhcustombasis.databinding.VacancyFragmentBinding
import a77777_888.me.t.https.hhcustombasis.fragments.WebViewFragment
import a77777_888.me.t.https.hhcustombasis.fragments.base.BaseFragment
import a77777_888.me.t.https.hhcustombasis.fragments.employer.EmployerFragment
import a77777_888.me.t.https.hhcustombasis.model.*
import a77777_888.me.t.https.hhcustombasis.model.entities.vacancy.VacancyResponseEntity
import a77777_888.me.t.https.hhcustombasis.utils.toPatternDateStringFromISO8601String
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.View.*
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VacancyFragment : BaseFragment(R.layout.vacancy_fragment) {

    private lateinit var binding: VacancyFragmentBinding
    override val viewModel by viewModels<VacancyViewModel>()
    private lateinit var vacancyResponse: VacancyResponseEntity

    @SuppressLint("SetTextI18n")
    override val resultHandler: (result: Result<*>) -> Unit = {
        binding.run {

                when (it) {
                    is EmptyResult -> {}
                    is PendingResult -> {
                        waitLayout.visibility = VISIBLE
                        errorLayout.visibility = INVISIBLE
                        vacancyCardView.visibility = INVISIBLE
                    }
                    is SuccessResult -> {
                        vacancyResponse = it.value as VacancyResponseEntity
                        waitLayout.visibility = INVISIBLE
                        errorLayout.visibility = INVISIBLE
                        vacancyCardView.visibility = VISIBLE
                        initUI()
                    }
                    is ErrorResult -> {
                        waitLayout.visibility = INVISIBLE
                        errorLayout.visibility = VISIBLE
                        vacancyCardView.visibility = INVISIBLE

                        errorTextView.text = "${it.message}\n${it.error}"
                    }
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = VacancyFragmentBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val vacancyId = requireArguments().getString(ID)!!

        binding.tryAgainButton.setOnClickListener { viewModel.getVacancy(vacancyId) }

        viewModel.getVacancy(vacancyId)
    }

    @Suppress("DEPRECATION")
    @SuppressLint("SetTextI18n")
    private fun initUI() {

        vacancyResponse.run {

            with(binding) {

                nameTextView.text = name

                salaryTextView.text = salary?.let {
                     when {
                        it.from == null || it.from == 0 -> "до ${it.to}"
                        it.to == null || it.to == 0 -> it.from.toString()
                        else -> "${it.from} - ${it.to} ${it.currency}"
                    }
                } ?: ""

                employerNameButton.text = employer.name
                employerNameButton.setOnClickListener { onEmployerNameButtonPressed() }

                areaTextView
                    .append(" " + area.name + "   " +
                            published_at.toPatternDateStringFromISO8601String() )
                experienceTextView.append(experience.name)
                employmentAndScheduleTextView.text = "${employment.name}  ${schedule.name}"

                temporaryTextView.visibility =
                    if (accept_temporary == null || !accept_temporary ) GONE else VISIBLE

                descriptionTextView.text =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT)
                    else Html.fromHtml(description)

                alternateUrlButton.setOnClickListener { onAlternateUrlButtonPressed() }

                vacancyFromBrowserButton.setOnClickListener { onVacancyFromSitePressed(alternate_url) }
            }
        }

    }

    private fun onEmployerNameButtonPressed() {
        findNavController().navigate(
            R.id.employerFragment,
            bundleOf(EmployerFragment.EMPLOYER_ID to vacancyResponse.employer.id)
        )
    }

    private fun onAlternateUrlButtonPressed() {
        findNavController().navigate(
            R.id.webViewFragment,
            bundleOf(WebViewFragment.REFERENCE to vacancyResponse.alternate_url)
        )

    }

    private fun onVacancyFromSitePressed(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    companion object {
        const val ID = "vacancyId"
    }

}